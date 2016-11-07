package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.dto.BookOrderDTO;
import au.edu.uts.aip.domain.dto.CheckoutDTO;
import au.edu.uts.aip.domain.dto.CheckoutItemDTO;
import au.edu.uts.aip.domain.entity.BookOrder;
import au.edu.uts.aip.domain.entity.BookOrderLine;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.remote.BookOrderRemote;
import au.edu.uts.aip.domain.response.SerialResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

@Stateless
public class BookOrderBean implements BookOrderRemote {
    
    @EJB
    private PaymentBean paymentBean;
    
    @EJB
    private UserBean userBean;
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public void checkout(CheckoutDTO checkoutDTO, String username){
        User user = userBean.getUserEntity(username);
        
        // create orders
        List<Long> saleIds = checkoutDTO.getItems().stream().map(CheckoutItemDTO::getSaleId).collect(Collectors.toList());
        TypedQuery<BookSales> typedQuery = em.createNamedQuery("BookSales.findSalesByIds", BookSales.class);
        typedQuery.setParameter("saleIds", saleIds);
        List<BookSales> bookSales = typedQuery.getResultList();
        
        // generate order line from each bookSale
        List<BookOrderLine> orderLineList = new ArrayList<>();
        for(BookSales bookSale: bookSales){
            CheckoutItemDTO matchedItem = null;
            for (CheckoutItemDTO item: checkoutDTO.getItems()){
                if (bookSale.getSalesId() == item.getSaleId()){
                    matchedItem = item;
                    break;
                }
            }
            
            if (matchedItem == null){
                throw new RuntimeException("Sales of book " + bookSale.getBook().getTitle()
                                            + " from seller " + bookSale.getSeller().getUsername()
                                            + " is not exist");
            }
            
            if (bookSale.getQuantity() < matchedItem.getBuyQuantity()){
                throw new RuntimeException("Sales of book " + bookSale.getBook().getTitle()
                                            + " from seller " + bookSale.getSeller().getUsername()
                                            + " only have " + bookSale.getQuantity() + " books remains");
            }
            
            bookSale.setQuantity(bookSale.getQuantity() - matchedItem.getBuyQuantity());
            if (bookSale.getQuantity() == 0){
                bookSale.getBook().getSales().remove(bookSale);
                bookSale.getSeller().getSellingBooks().remove(bookSale);
                em.remove(bookSale);
            }
            
            BookOrderLine orderLine = new BookOrderLine();
            orderLine.setBook(bookSale.getBook());
            orderLine.setSeller(bookSale.getSeller());
            orderLine.setBookCondition(bookSale.getCondition());
            orderLine.setQuantity(matchedItem.getBuyQuantity());
            orderLine.setUnitPrice(bookSale.getPrice());
            orderLine.setQuantity(matchedItem.getBuyQuantity());
            em.persist(orderLine);
            orderLineList.add(orderLine);
        }
        
        // add order lines to order
        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderLines(orderLineList);
        bookOrder.setOrderTimestamp(new Date());
        bookOrder.setPostageCost(checkoutDTO.getShippingCost());
        bookOrder.setOwner(user);
        em.persist(bookOrder);
        
        // create customer from card detail
        PinCustomerPost pinCustomerPost = new PinCustomerPost();
        pinCustomerPost.setEmail(user.getEmail());
        pinCustomerPost.setCard(checkoutDTO.getCard());
        
        SerialResponse createCustomerResponse = paymentBean.createCustomer(pinCustomerPost);
        
        // failed at create customer
        if (createCustomerResponse.getStatusCode() != Response.Status.CREATED.getStatusCode()){
            throw new ClientErrorException(createCustomerResponse.getBody().toString(), createCustomerResponse.getStatusCode());
        }
        
        // calculate total cost & charge money
        double totalCost = 0;
        for(BookOrderLine orderLine: orderLineList){
            totalCost += orderLine.getQuantity() * orderLine.getUnitPrice();
        }
        totalCost += checkoutDTO.getShippingCost();
        String description = "Order " + bookOrder.getId();
        String customerToken = createCustomerResponse.getBody().getJsonObject("response").getString("token");
        
        PinChargePost pinChargePost = new PinChargePost();
        pinChargePost.setAmount( (int)(totalCost * 100) + "") ;
        pinChargePost.setCustomerToken(customerToken);
        pinChargePost.setDescription(description);
        pinChargePost.setEmail(user.getEmail());
        
        SerialResponse chargeResponse = paymentBean.charge(pinChargePost);
        // failed at charge
        if (chargeResponse.getStatusCode() != Response.Status.CREATED.getStatusCode()){
            throw new ClientErrorException(chargeResponse.getBody().toString(), chargeResponse.getStatusCode());
        }
    }
    
    @Override
    public List<BookOrderDTO> getBuyOrder(String username) {
        User user = userBean.getUserEntity(username);
        
        TypedQuery<BookOrder> typedQuery = em.createNamedQuery("BookOrder.findByOwner", BookOrder.class);
        typedQuery.setParameter("owner", user);
        List<BookOrder> bookOrdersEntity = typedQuery.getResultList();
        
        List<BookOrderDTO> bookOrdersDTO = new ArrayList<>();
        bookOrdersEntity.stream().forEach(bookOrder -> {
            bookOrdersDTO.add(new BookOrderDTO(bookOrder));
        });
        
        return bookOrdersDTO;
    }
}
