package dbfacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerFacadeTest {

    private static EntityManagerFactory emf;
    private static CustomerFacade facade;

    public CustomerFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            emf = Persistence.createEntityManagerFactory("pu");
            facade = CustomerFacade.getCustomerFacade(emf);
            facade.addCustomer("Sebsen", "Bebsen");
            facade.addCustomer("Derp", "Derpson");
            facade.addCustomer("Derpina", "Derpson");

        } catch (Exception e) {
            System.out.println("Kan ikke oprette forbindelse til database");
        }
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {
        facade = CustomerFacade.getCustomerFacade(emf);
    }

    /**
     * Test of getCustomerFacade method, of class CustomerFacade.
     */
    @Test
    public void testGetCustomerFacade() {
        assertNotNull(facade);
    }

    /**
     * Test of findById method, of class CustomerFacade.
     */
    @Test
    public void testFindById() {
        int id = 1;

        String expResultName = "Sebsen";
        Customer result = facade.findById(id);
        assertEquals(expResultName, result.getFirstName());

    }

    /**
     * Test of findByLastName method, of class CustomerFacade.
     */
    @Test
    public void testFindByLastName() {

        String name = "Derpson";
        int expResultSize = 2;
        List<Customer> result = facade.findByLastName(name);
        assertEquals(expResultSize, result.size());

    }

    /**
     * Test of getNumberOfCustomers method, of class CustomerFacade.
     */
    @Test
    public void testGetNumberOfCustomers() {

        int expResult = 4;
        int result = facade.getNumberOfCustomers();
        assertEquals(expResult, result);

    }

    /**
     * Test of getAllCustomers method, of class CustomerFacade.
     */
    @Test
    public void testGetAllCustomers() {

        int expResultSize = 4;
        int result = facade.getAllCustomers().size();
        assertEquals(expResultSize, result);

    }

    /**
     * Test of addCustomer method, of class CustomerFacade.
     */
    @Test
    public void testAddCustomer() {

        String fName = "Test";
        String lName = "mcGee";
        int sizeBeforeAdd = facade.getAllCustomers().size();
        facade.addCustomer(fName, lName);
        int sizeAfterAdd = facade.getAllCustomers().size();

        assertTrue(sizeBeforeAdd < sizeAfterAdd);

    }

}
