package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeFacadeTest {

    private static final EntityManagerFactory ENF = Persistence.createEntityManagerFactory("pu");
    private static final EmployeeFacade EF = EmployeeFacade.getFacadeExample(ENF);

    public EmployeeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
//        Add code to setup entities for test before running any test methods

    }

    @AfterAll
    public static void tearDownClass() {
        ENF.close();
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test that was run
        EntityManager em = ENF.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Employee");
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    
    @BeforeEach
    public void setUp() {
//        Put the test database in a proper state before each test is run
        EntityManager em = ENF.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("Sven", "Wonnegut", 335567));
            em.getTransaction().commit();
            em.getTransaction().begin();
            em.persist(new Employee("Hannegård", "Olsen", 435867));
            em.persist(new Employee("Nerpson", "Olsen", 411567));
            em.persist(new Employee("Derpina", "Petersen", 33567));
            em.persist(new Employee("Derp", "Wonnegut", 56567));
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    /**
     * Test a method here.
     */
    @Test
    public void testGetemployeeById() {        
        String expectedName = "Sven";
        Employee emp = EF.getEmployeeById(6L); //6L BECAUSE ID's IS NOT CLEARED
        String actualName = emp.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGetEmployeeByName() {
        List<Employee> emp = EF.getEmployeesByName("Sven");
        for (Employee employee : emp) {
            assertEquals("Sven", employee.getName());
        }
    }

    @Test
    public void testGetAllEmployees() {
        int expectedSize = 5;
        int actualSize = EF.getAllEmployees().size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetEmployeeWithHighestSalary() {
        int expectedSalary = 435867;
        Employee emp = EF.getEmployeeWithHighestSalary();
        int actualSalary = emp.getSalary();
        assertEquals(expectedSalary, actualSalary);
    }

    @Test
    public void testCreateEmployee() {
        int sizeBefore = EF.getAllEmployees().size();
        EF.createEmployee("lol", "lolgade 1", 1);
        int sizeAfter = EF.getAllEmployees().size();
        assertTrue(sizeAfter > sizeBefore);
    }

}
