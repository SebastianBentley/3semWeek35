package facades;

import entities.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class EmployeeFacade {

    private static EmployeeFacade instance;
    private static EntityManagerFactory emf;
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new Employee("Kurt", "Wonnegut", 335567));
            em.persist(new Employee("Hanne", "Olsen", 435867));
            em.persist(new Employee("Jan", "Olsen", 411567));
            em.persist(new Employee("Irene", "Petersen", 33567));
            em.persist(new Employee("Tian", "Wonnegut", 56567));
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
    
    //Private Constructor to ensure Singleton
    private EmployeeFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static EmployeeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new EmployeeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Employee getEmployeeById(Long id){
        EntityManager em = emf.createEntityManager();
        try {
            Employee e = em.find(Employee.class, id);
            return e;
        } finally {
            em.close();
        }
    }
    
    public List<Employee> getEmployeesByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query
                    = em.createQuery("Select e from Employee e WHERE e.name = :name", Employee.class);
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    
    public List<Employee> getAllEmployees() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query
                    = em.createQuery("Select e from Employee e", Employee.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Employee getEmployeeWithHighestSalary(){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE e.salary = (SELECT MAX(e.salary) FROM Employee e)");
            Employee e = (Employee) query.getSingleResult();
            return e;
        } finally {
            em.close();
        }
    }
    
    public Employee createEmployee(String name, String adress, int salary) {
        Employee emp = new Employee(name, adress, salary);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(emp);
            em.getTransaction().commit();
            return emp;
        } finally {
            em.close();
        }
    }
    
    
    
}
