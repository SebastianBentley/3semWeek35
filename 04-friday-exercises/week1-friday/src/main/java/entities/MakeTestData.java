package entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MakeTestData {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
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
            emf.close();
        }
        
        
        
    }
    
    
    
}
