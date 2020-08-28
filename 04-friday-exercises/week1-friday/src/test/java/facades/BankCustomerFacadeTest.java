package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import entities.MakeTestData;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.validation.constraints.AssertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankCustomerFacadeTest {

    private static final EntityManagerFactory ENF = Persistence.createEntityManagerFactory("pu");
    private static final BankCustomerFacade EF = BankCustomerFacade.getFacadeExample(ENF);

    public BankCustomerFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
//        Add code to setup entities for test before running any test methods
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
//        Put the test database in a proper state before each test is run
        EntityManager em = ENF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new BankCustomer("Kurt", "Wonnegut", "335567", 123, 5, "customerKurt"));
            em.persist(new BankCustomer("Derp", "Derpson", "335567", 112312, 4, "customerIsBig"));
            em.persist(new BankCustomer("Derpina", "Derpson", "335567", 1111, 2, "customer"));
            em.persist(new BankCustomer("Sven", "Svensen", "335567", 1, 3, "customee"));
            em.persist(new BankCustomer("lol", "lolsen", "335567", 1123, 1, "lolman"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
        EntityManager em = ENF.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM BankCustomer");
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetCustomerById() {
        CustomerDTO testCust = EF.getCustomerById(1L);
        assertEquals(testCust.getAccountNumber(), "335567");
    }

    @Test
    public void testGetCustomerByName() {
        List<CustomerDTO> cust = EF.getCustomerByName("Sven, Svensen");
        for (CustomerDTO customer : cust) {
            assertEquals("Sven, Svensen", customer.getFullName());
        }
    }
    
    
    @Test
    public void testAddCustomer() {
        List<BankCustomer> beforeAdd = EF.getAllBankCustomers();
        EF.addCustomer(new BankCustomer("test", "tester", "1234", 1, 1, "testindo"));
        List<BankCustomer> afterAdd = EF.getAllBankCustomers();
        assertTrue(afterAdd.size() > beforeAdd.size());
        
    }
    
    @Test
    public void testGetAllBankCustomers() {
        int expectedSize = 5;
        List<BankCustomer> cust = EF.getAllBankCustomers();
        int acutalSize = cust.size();
        assertEquals(expectedSize, acutalSize);
        
    }

}
