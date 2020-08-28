package facades;

import dto.CustomerDTO;
import entities.BankCustomer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class BankCustomerFacade {

    private static BankCustomerFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private BankCustomerFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BankCustomerFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BankCustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CustomerDTO getCustomerById(Long id) {
        EntityManager em = getEntityManager();
        try {
            BankCustomer cdto = em.find(BankCustomer.class, id);
            CustomerDTO cust = new CustomerDTO(cdto);
            return cust;
        } catch (NullPointerException e){
            return new CustomerDTO(new BankCustomer());
        } 
        
        finally {
            em.close();
        }
    }

    public List<CustomerDTO> getCustomerByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BankCustomer> query
                    = em.createQuery("Select e from BankCustomer e WHERE e.firstName = :name", BankCustomer.class);
            query.setParameter("name", name);
            List<CustomerDTO> cust = new ArrayList<CustomerDTO>();
            for (BankCustomer bc : query.getResultList()) {
                CustomerDTO dto = new CustomerDTO(bc);
                cust.add(dto);
            }
            return cust;
        } finally {
            em.close();
        }
    }

    public BankCustomer addCustomer(BankCustomer cust) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
        } finally {
            em.close();
        }
    }

    public List<BankCustomer> getAllBankCustomers() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<BankCustomer> query
                    = em.createQuery("Select e from BankCustomer e", BankCustomer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
