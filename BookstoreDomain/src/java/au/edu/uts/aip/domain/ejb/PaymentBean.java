package au.edu.uts.aip.domain.ejb;

/**
 * the needed library
 */
import au.edu.uts.aip.domain.dto.CheckoutDTO;
import au.edu.uts.aip.domain.dto.CheckoutItemDTO;
import au.edu.uts.aip.domain.entity.BookOrder;
import au.edu.uts.aip.domain.entity.BookOrderLine;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.response.SerialResponse;
import au.edu.uts.aip.domain.remote.PaymentRemote;
import au.edu.uts.aip.domain.pin.dto.PinChargePost;
import au.edu.uts.aip.domain.pin.dto.PinCustomerPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPost;
import au.edu.uts.aip.domain.pin.dto.PinRecipientPut;
import au.edu.uts.aip.domain.pin.dto.PinTransferPost;
import au.edu.uts.aip.domain.pin.filter.PinAuthFilter;
import au.edu.uts.aip.domain.pin.filter.PinResponseLoggingFilter;
import au.edu.uts.aip.domain.pin.util.PinResponseUtil;
import au.edu.uts.aip.domain.util.ApiResponseUtil;
import au.edu.uts.aip.domain.validation.ValidationResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * PaymentBean is an EJB that is used to handle the payment related operations
 * 
 * It uses the Pin API to handle the payment 
 * Official Pin API documentation can be found here: https://pin.net.au/docs/api/
 *  @author team San Dang, Alex Tan, Xiaoyang Liu
 */
@Stateless
public class PaymentBean implements PaymentRemote {

    /**
    * API address
    */
    private static final String BASE_URL = "https://test-api.pin.net.au/1";
    /**
    * API secret key
    */
    private static final String API_KEY_SECRET = "***REMOVED***";
    private static final String PASSWORD = "";
    
    @EJB
    private UserBean userBean;
    
    @PersistenceContext
    private EntityManager em;

    /**
    * createCustomer2 is used to create a customer
    * return ResponseDTO
     * @param pinCustomerPost
    */
    @Override
    public SerialResponse createCustomer2(PinCustomerPost pinCustomerPost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    
    /**
    * charge2 funtion is used to charge the money
     * @param pinChargePost
     * @return 
    */
    @Override
    public SerialResponse charge2(PinChargePost pinChargePost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinChargePost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    /**
    * createRecipient2 function is used to create a receipt
     * @param pinRecipientPost
     * @return 
    */
    @Override
    public SerialResponse createRecipient2(PinRecipientPost pinRecipientPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinRecipientPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    /**
    * transfer2 function is used to transfer the money
     * @param pinTransferPost
     * @return 
    */
    @Override
    public SerialResponse transfer2(PinTransferPost pinTransferPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/transfers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinTransferPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }

    /**
    * fetchRecipient function is used to fetch the requested recipient
    */
    @Override
    public JsonObject fetchRecipient(String recipientToken) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .path("{recipient-token}")
                .resolveTemplate("recipient-token", recipientToken)
                .request()
                .get();

        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return responseJson;
    }

    /**
    * editRecipient2 function is used to edit the information of the recipient
     * @param recipientToken
     * @param pinRecipientPut
     * @return 
    */
    @Override
    public SerialResponse editRecipient2(String recipientToken, PinRecipientPut pinRecipientPut) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .path("{recipient-token}")
                .resolveTemplate("recipient-token", recipientToken)
                .request()
                .put(Entity.entity(pinRecipientPut, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        ValidationResult validationResult = PinResponseUtil.validate(statusCode, responseJson);

        if (validationResult == null) {
            return new SerialResponse(responseJson, Response.Status.CREATED.getStatusCode());
        } else {
            return new SerialResponse(validationResult.toJson(), 422);
        }
    }
    // stop here. everything below here is deprecated.

    /**
    * createCustomer is used to create a customer
    */
    @Override
    public ValidationResult createCustomer(PinCustomerPost pinCustomerPost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/customers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinCustomerPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }
    /**
    * charge function is used to charge the money
    */
    @Override
    public ValidationResult charge(PinChargePost pinChargePost) {

        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/charges")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinChargePost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }
    /**
    * createRecipient function is used to create a recipient of the payment
    */
    @Override
    public ValidationResult createRecipient(PinRecipientPost pinRecipientPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinRecipientPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }

    
    /**
    * transfer function is used to transfer the money
    */
    @Override
    public ValidationResult transfer(PinTransferPost pinTransferPost) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/transfers")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(pinTransferPost, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }

    /**
    * editRecipient function is used to edit the information of the recipient 
    */
    @Override
    public ValidationResult editRecipient(String recipientToken, PinRecipientPut pinRecipientPut) {
        Client client = ClientBuilder.newClient()
                .register(new PinAuthFilter(API_KEY_SECRET, PASSWORD))
                .register(new PinResponseLoggingFilter());

        Response response = client.target(BASE_URL + "/recipients")
                .path("{recipient-token}")
                .resolveTemplate("recipient-token", recipientToken)
                .request()
                .put(Entity.entity(pinRecipientPut, MediaType.APPLICATION_JSON_TYPE));

        int statusCode = response.getStatus();
        JsonObject responseJson = ApiResponseUtil.toJson(response.readEntity(String.class));
        client.close();

        return PinResponseUtil.validate(statusCode, responseJson);
    }
    
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
            
            BookOrderLine orderLine = new BookOrderLine();
            orderLine.setBook(bookSale.getBook());
            orderLine.setSeller(bookSale.getSeller());
            orderLine.setBookCondition(bookSale.getCondition());
            orderLine.setQuantity(matchedItem.getBuyQuantity());
            orderLine.setUnitCost(bookSale.getPrice());
            
            orderLine.setQuantity(matchedItem.getBuyQuantity());
            em.persist(orderLine);
            orderLineList.add(orderLine);
        }
        
        // add order lines to order
        BookOrder bookOrder = new BookOrder();
        bookOrder.setOrderLines(orderLineList);
        bookOrder.setOrderTimestamp(new Date());
        bookOrder.setPostageCost(checkoutDTO.getShippingCost());
        em.persist(bookOrder);
        
        // create customer from card detail
        PinCustomerPost pinCustomerPost = new PinCustomerPost();
        pinCustomerPost.setEmail(user.getEmail());
        pinCustomerPost.setCard(checkoutDTO.getCard());
        
        SerialResponse createCustomerResponse = createCustomer2(pinCustomerPost);
        
        // failed at create customer
        if (createCustomerResponse.getStatusCode() != Response.Status.CREATED.getStatusCode()){
            throw new ClientErrorException(createCustomerResponse.getBody().toString(), createCustomerResponse.getStatusCode());
        }
        
        // calculate total cost & charge money
        double totalCost = 0;
        for(BookOrderLine orderLine: orderLineList){
            totalCost += orderLine.getQuantity() * orderLine.getUnitCost();
        }
        totalCost += checkoutDTO.getShippingCost();
        String description = "Order " + bookOrder.getId();
        String customerToken = createCustomerResponse.getBody().getJsonObject("response").getString("token");
        
        PinChargePost pinChargePost = new PinChargePost();
        pinChargePost.setAmount( (int)(totalCost * 100) + "") ;
        pinChargePost.setCustomerToken(customerToken);
        pinChargePost.setDescription(description);
        pinChargePost.setEmail(user.getEmail());
        
        SerialResponse chargeResponse = charge2(pinChargePost);
        // failed at charge
        if (chargeResponse.getStatusCode() != Response.Status.CREATED.getStatusCode()){
            throw new ClientErrorException(chargeResponse.getBody().toString(), chargeResponse.getStatusCode());
        }
    }
}
