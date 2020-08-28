package dbfacade;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class CustomerFacade {

    private static EntityManagerFactory emf;
    private static CustomerFacade instance;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        CustomerFacade facade = CustomerFacade.getCustomerFacade(emf);
        
        Customer c1 = facade.addCustomer("Sebsen", "Bebsen");
        Customer c2 = facade.addCustomer("Derp", "Derpson");
        Customer c3 = facade.addCustomer("Derpina", "Derpson");
        
        System.out.println("Customer 1: " + facade.findById(c1.getId()));
        
        System.out.println("number of Derpsons: " + facade.findByLastName("Derpson").size());
        
        System.out.println("Number of Customers: " + facade.getNumberOfCustomers());
        
        System.out.println("Customers: ");
        for (Customer c : facade.getAllCustomers()){
            System.out.println(c.getFirstName() + " " + c.getLastName());
        }

    }

    public CustomerFacade() {
    }

    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    public Customer findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Customer c = em.find(Customer.class, id);
            return c;
        } finally {
            em.close();
        }
    }

    public List<entity.Customer> findByLastName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query
                    = em.createQuery("Select customer from Customer customer where customer.lastName = :name", Customer.class);
            query.setParameter("name", name);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public int getNumberOfCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createQuery("SELECT COUNT(c) FROM Customer c");

            return Integer.valueOf(q.getSingleResult().toString());
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query
                    = em.createQuery("Select customer from Customer customer", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer addCustomer(String fName, String lName) {
        Customer customer = new Customer(fName, lName);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
            return customer;
        } finally {
            em.close();
        }
    }

}
