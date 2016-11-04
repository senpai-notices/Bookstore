package au.edu.uts.aip.domain.ejb;

import au.edu.uts.aip.domain.entity.Book;
import au.edu.uts.aip.domain.entity.BookSales;
import au.edu.uts.aip.domain.entity.Category;
import au.edu.uts.aip.domain.entity.Role;
import au.edu.uts.aip.domain.entity.Role.RoleType;
import au.edu.uts.aip.domain.entity.User;
import au.edu.uts.aip.domain.utility.SHA;
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

@Singleton
@Startup
public class DatabaseInitBean {

    @PersistenceContext
    private EntityManager em;

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
        em.persist(adminUser);
        System.out.println("Creating admin account...Done");

        System.out.println("Creating sample user account");
        RoleType[] randomRoleList = {RoleType.INACTIVATED, RoleType.USER, RoleType.BANNED};
        Random r = new Random();
        for (int i = 0; i < 80; i++) {
            User normalUser = new User();
            normalUser.setFullname("Full name Here");
            normalUser.setUsername("username" + i);
            normalUser.setPassword(SHA.hash256("123123"));
            normalUser.setEmail("sondang2412@gmail.com");

            RoleType randomRole = randomRoleList[r.nextInt(randomRoleList.length)];
            normalUser.setRole(roleMap.get(randomRole));

            em.persist(normalUser);
        }

        User[] verifiedUsers = new User[20];
        for (int i = 0; i < 20; i++) {
            User verifiedUser = new User();
            verifiedUser.setFullname("Full name here");
            verifiedUser.setUsername("username" + (i + 80));
            verifiedUser.setPassword(SHA.hash256("123123"));
            verifiedUser.setEmail("sondang2412@gmail.com");
            verifiedUser.setRole(roleMap.get(RoleType.VERIFIED));

            em.persist(verifiedUser);
            verifiedUsers[i] = verifiedUser;
        }
        User myUser = new User();
        myUser.setUsername("sondang2412");
        myUser.setFullname("Dang Cuu Son");
        myUser.setEmail("sondang2412@gmail.com");
        myUser.setPassword(SHA.hash256("qwerty"));
        myUser.setRole(roleMap.get(RoleType.VERIFIED));
        em.persist(myUser);
        System.out.println("Creating sample user account...Done");

        System.out.println("Importing sample books data");
        String filePath = this.getClass().getResource("/books.csv").getFile();

        HashMap<String, Category> categoryMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

//                    String categoryName = data[8];
//                    if (categoryMap.get(categoryName) == null){
//                        Category category = new Category();
//                        category.setCategoryName(categoryName);
//                        em.persist(category);
//                        categoryMap.put(categoryName, category);
//                    }
//                    
//                    Category category = categoryMap.get(categoryName);
//                    //category.getBooks().add(book);
//                    em.persist(category);
//                    book.setCategory(category);
//                    em.persist(book);


                    BookSales adminSales = new BookSales();
                    adminSales.setBook(book);
                    adminSales.setSeller(adminUser);
                    double brandNewPrice = (int)(Math.random() * 200);
                    adminSales.setPrice(brandNewPrice);
                    adminSales.setCondition("Brand new");
                    adminSales.setQuantity(r.nextInt(20) + 1);

                    BookSales userSales = new BookSales();
                    userSales.setBook(book);
                    User seller = verifiedUsers[r.nextInt(20)];
                    userSales.setSeller(seller);
                    double usedPrice = (int) (Math.random() * brandNewPrice);
                    userSales.setPrice(usedPrice);
                    userSales.setCondition("Used");
                    userSales.setQuantity(r.nextInt(2) + 1);

                    em.persist(adminSales);
                    em.persist(userSales);

                    book.getSales().add(adminSales);
                    book.getSales().add(userSales);
                    em.persist(book);

                    adminUser.getSellingBooks().add(adminSales);
                    seller.getSellingBooks().add(userSales);
                    em.persist(adminUser);
                    em.persist(seller);

                } catch (Exception ex) {
                    Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(DatabaseInitBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Init database...Done");
    }

    @PreDestroy
    protected void cleanup() {
        System.out.println("Cleaning database");

        Query q = em.createNativeQuery("drop view jdbcrealm_group");
        q.executeUpdate();
        q = em.createNativeQuery("drop view jdbcrealm_user");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Book_sales");
        q.executeUpdate();
        q = em.createNativeQuery("delete from Book");
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
