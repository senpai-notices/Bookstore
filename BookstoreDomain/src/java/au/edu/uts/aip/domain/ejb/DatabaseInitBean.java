package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSeller;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.Role.RoleType;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.utility.SHA;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Singleton
@Startup
public class DatabaseInitBean {
    @PersistenceContext
    private EntityManager em;
    
    @PostConstruct
    protected void init(){
        System.out.println("Init database");
        
        System.out.println("Setting up roles");
        RoleType[] allRoles = RoleType.class.getEnumConstants();
        HashMap<RoleType, Role> roleMap = new HashMap<>();
        for(RoleType roleType: allRoles){
            Role role = new Role();
            role.setRoleName(roleType.toString());
            em.persist(role);
            roleMap.put(roleType, role);
        }
        
        System.out.println("Setting up roles...Done");
        
        System.out.println("Setting up views");
        Query q = em.createNativeQuery("create view jdbcrealm_user (username, password) as select username, password from Bookstore_User");
        q.executeUpdate();
        q = em.createNativeQuery("create view jdbcrealm_group (username, groupname)" + 
                "as select u.username, r.roleName from Bookstore_User u left join Bookstore_Role r on u.role_id = r.id");
        q.executeUpdate();
        System.out.println("Setting up views...Done");
        
        System.out.println("Creating admin account");
        
        User adminUser = new User();
        adminUser.setFullname("Administrator");
        adminUser.setUsername("admin1");
        adminUser.setPassword(SHA.hash256("qwerty"));
        adminUser.setEmail("admin@test.com");
        adminUser.setRole(roleMap.get(RoleType.ADMIN));
        em.persist(adminUser);
        System.out.println("Creating admin account...Done");
        
        System.out.println("Creating sample user account");
        User normalUser = new User();
        normalUser.setFullname("Cuu Son Dang");
        normalUser.setUsername("sondang2412");
        normalUser.setPassword(SHA.hash256("qwerty"));
        normalUser.setEmail("sondang2412@gmail.com");
        normalUser.setRole(roleMap.get(RoleType.USER));
        em.persist(normalUser);
        System.out.println("Creating sample user account...Done");
        
        System.out.println("Importing sample books data");
        String filePath = this.getClass().getResource("/books.csv").getFile();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ( (line = br.readLine()) != null){
                
                try{
                    String[] data = line.split(",");
                
                    Book book = new Book();
                    book.setIsbn(data[0]);
                    book.setTitle(data[1]);
                    book.setName(data[2]);
                    data[3] = data[3].replaceAll("\"", "");
                    book.setPublishYear(Integer.parseInt(data[3]));
                    book.setPublisher(data[4]);
                    book.setImgPath(data[7]);

                    List<BookSeller> sellers = new ArrayList<>();
                    BookSeller adminSeller = new BookSeller();
                    adminSeller.setBook(book);
                    adminSeller.setSeller(adminUser);
                    double brandNewPrice = Math.random() * 200;
                    adminSeller.setPrice(brandNewPrice);
                    adminSeller.setCondition("Brand new");

                    BookSeller userSeller = new BookSeller();
                    userSeller.setBook(book);
                    userSeller.setSeller(normalUser);
                    double usedPrice = Math.random() * brandNewPrice;
                    userSeller.setPrice(usedPrice);
                    userSeller.setCondition("Used");

                    book.setSellers(sellers);
                    em.persist(book);
                } catch (Exception ex){
                    continue;
                }
                
            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Init database...Done");
    }
    
    @PreDestroy
    protected void cleanup(){
        System.out.println("Cleaning database");
        
        Query q = em.createNativeQuery("drop view jdbcrealm_group");
        q.executeUpdate();
        q = em.createNativeQuery("drop view jdbcrealm_user");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Book_Seller");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Book");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Bookstore_User");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Bookstore_Role");
        q.executeUpdate();
        
        System.out.println("Cleaning database...Done");
    }
    
}

