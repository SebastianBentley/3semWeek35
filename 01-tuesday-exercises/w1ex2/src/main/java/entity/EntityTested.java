package entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class EntityTested {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        Customer c1 = new Customer("Sebsen", "Bebsen");
        Customer c2 = new Customer("Derp", "Derpson");
        Customer c3 = new Customer("Derpina", "Derpson");
       
        try {
            em.getTransaction().begin();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.getTransaction().commit();
            //Verify that books are managed and has been given a database id
            System.out.println(c1.getId());
            System.out.println(c2.getId());
            System.out.println(c3.getId());

        } finally {
            em.close();
        }
    }
}
