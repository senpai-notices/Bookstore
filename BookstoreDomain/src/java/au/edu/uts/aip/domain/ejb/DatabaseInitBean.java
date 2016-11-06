package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Address;
import au.edu.uts.aip.domain.entity.Suburb;
import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.Role.RoleType;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.util.SHA;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;

/**
 * DatabaseInitBean is a JavaBean class to initialize the database setting up
 * It is a controller class
 * 
 * It has two methods:
 * init(): used to initialize the database setting
 * cleanup(): used to stop the database connection and clean up useless data
 * 
 *  @author Son Dang, Alex Tan, Xiaoyang Liu
 */
@Singleton
@Startup
public class DatabaseInitBean {

    @PersistenceContext
    private EntityManager em;

    /**
    * init function is used to initialize and start the database connection and set it up
    * return none
    */
    @PostConstruct
    protected void init() {
        System.out.println("Init database");

        System.out.println("Setting up roles");
        RoleType[] allRoles = RoleType.class.getEnumConstants();
        HashMap<RoleType, Role> roleMap = new HashMap<>();
        for (RoleType roleType : allRoles) {
            Role role = new Role();
            role.setRoleName(roleType.toString());
            em.persist(role);
            roleMap.put(roleType, role);
        }

        System.out.println("Setting up roles...Done");

        System.out.println("Setting up views");
        Query q = em.createNativeQuery("create view jdbcrealm_user (username, password) as select username, password from Bookstore_User");
        q.executeUpdate();
        q = em.createNativeQuery("create view jdbcrealm_group (username, groupname)"
                + "as select u.username, r.roleName from Bookstore_User u left join Bookstore_Role r on u.role_id = r.id");
        q.executeUpdate();
        System.out.println("Setting up views...Done");

        System.out.println("Creating admin account");

        User adminUser = new User();
        adminUser.setFullname("Administrator");
        adminUser.setUsername("admin1");
        adminUser.setPassword(SHA.hash256("qwerty"));
        adminUser.setEmail("admin@test.com");
        adminUser.setRole(roleMap.get(RoleType.ADMIN));
        adminUser.setRecipientToken("rp_Y9rAv-gcPzXkeKkNfuBXUQ");
        
        Address adminAddress = new Address();
        adminAddress.setAddressLine1("123 Fake street");
        adminAddress.setAddressPostcode(2192);
        adminAddress.setAddressState("NSW");
        adminAddress.setAddressCountry("Australia");
        adminAddress.setAddressCity("Sydney");
        em.persist(adminAddress);
        
        adminUser.setAddress(adminAddress);
        
        em.persist(adminUser);
        System.out.println("Creating admin account...Done");

        System.out.println("Creating sample user account");
        
        User myUser = new User();
        myUser.setUsername("sondang2412");
        myUser.setFullname("Dang Cuu Son");
        myUser.setEmail("sondang2412@gmail.com");
        myUser.setPassword(SHA.hash256("qwerty"));
        myUser.setRole(roleMap.get(RoleType.USER));
        myUser.setRecipientToken("rp_PxxcoyvLclg-j9cTwPG9YQ");
        
        Address myAddress = new Address();
        myAddress.setAddressLine1("321 Real Street");
        myAddress.setAddressPostcode(2192);
        myAddress.setAddressState("NSW");
        myAddress.setAddressCountry("Australia");
        myAddress.setAddressCity("Sydney");
        em.persist(myAddress);
        
        myUser.setAddress(myAddress);
        em.persist(myUser);
        
        System.out.println("Creating sample user account...Done");

        System.out.println("Importing sample books data");
        String booksCsvfilePath = this.getClass().getResource("/books.csv").getFile();
        Random r = new Random();
        try (BufferedReader br = new BufferedReader(new FileReader(booksCsvfilePath))) {
            String line;
            while ((line = br.readLine()) != null) {

                try {
                    String[] data = line.split(",");

                    Book book = new Book();
                    book.setIsbn10(data[0].substring(1, data[0].length() - 1));
                    book.setIsbn13(data[1].substring(1, data[1].length() - 1));
                    book.setTitle(data[2]);

                    book.setAuthor(data[3]);
                    data[3] = data[3].replaceAll("\"", "");

                    book.setPublisher(data[4]);

                    book.setPublishYear(Integer.parseInt(data[5].substring(0, 4)));
                    book.setPageCount(Integer.parseInt(data[6]));

                    book.setImgPath(data[7]);
                    em.persist(book);

                    BookSales adminSales = new BookSales();
                    adminSales.setBook(book);
                    adminSales.setSeller(adminUser);
                    double brandNewPrice = (int) (Math.random() * 200);
                    adminSales.setPrice(brandNewPrice);
                    adminSales.setCondition("Brand new");
                    adminSales.setQuantity(r.nextInt(20) + 1);
                    
                    em.persist(adminSales);

                    book.getSales().add(adminSales);
                    em.persist(book);

                    adminUser.getSellingBooks().add(adminSales);
                    em.persist(adminUser);

                } catch (NumberFormatException ex) {
                    Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
        }

//        CriteriaBuilder qb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//        cq.select(qb.count(cq.from(Suburb.class)));
//        Long count = em.createQuery(cq).getSingleResult();
//        if (count != 15418) {

            System.out.println("Importing suburbs data");
            String suburbCsvFilePath = this.getClass().getResource("/suburbs-development.csv").getFile();

            try (BufferedReader br = new BufferedReader(new FileReader(suburbCsvFilePath))) {
                String line;
                while ((line = br.readLine()) != null) {

                    try {
                        String[] data = line.split(",");

                        Suburb suburb = new Suburb();
                        suburb.setName(data[0]);
                        suburb.setStateName(data[1]);
                        suburb.setPostcode(Integer.parseInt(data[2]));

                        em.persist(suburb);

                    } catch (NumberFormatException ex) {
                        Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } catch (IOException ex) {
                Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
            }
//        }
            System.out.println("Init database...Done");
    }

    /**
    * cleanup function is used to stop the connection of the database and clean up the data
    * return none
    */
    @PreDestroy
    protected void cleanup() {
        System.out.println("Cleaning database");

        Query q = em.createNativeQuery("drop view jdbcrealm_group");
        q.executeUpdate();
        q = em.createNativeQuery("drop view jdbcrealm_user");
        q.executeUpdate();

//        CriteriaBuilder qb = em.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//        cq.select(qb.count(cq.from(Suburb.class)));
//        Long count = em.createQuery(cq).getSingleResult();
//        if (count != 15418) {
            q = em.createNativeQuery("delete from suburb");
            q.executeUpdate();
//        }
        
        q = em.createNativeQuery("delete from BookOrderLine");
        q.executeUpdate();
        q = em.createNativeQuery("delete from BookOrder");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Book_sales");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Book");
        q.executeUpdate();
        q = em.createNativeQuery("delete from address");
        q.executeUpdate();
        q = em.createNativeQuery("delete from bank_account");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Bookstore_User");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Bookstore_Role");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Category");
        q.executeUpdate();

        System.out.println("Cleaning database...Done");
    }

}