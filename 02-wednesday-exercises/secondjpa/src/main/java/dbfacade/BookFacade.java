package dbfacade;

import entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class BookFacade {

    private static EntityManagerFactory emf;
    private static BookFacade instance;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        BookFacade facade = BookFacade.getBookFacade(emf);
        Book b1 = facade.addBook("Author 1");
        Book b2 = facade.addBook("Author 2");
        //Find book by ID
        System.out.println("Book1: " + facade.findBook(b1.getId()).getAuthor());
        System.out.println("Book2: " + facade.findBook(b2.getId()).getAuthor());
        //Find all books
        System.out.println("Number of books: " + facade.getAllBooks().size());

    }

    private BookFacade() {
    }

    /**
     * Initializes a BookFacade, if it is not already initialized.
     * Then returns the BookFacade, that is initialized, or previously initialized.
     * 
     * @param _emf
     * @return BookFacade
     */
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    /**
     * Add a book to db, and return it.
     *
     * @param author
     * @return Book
     */
    public Book addBook(String author) {
        Book book = new Book(author);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } finally {
            em.close();
        }
    }

    /**
     * uses EntityManager to find a specific book, with the book id as param.
     *
     * @param id
     * @return Book
     */
    public Book findBook(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            Book book = em.find(Book.class, id);
            return book;
        } finally {
            em.close();
        }
    }

    /**
     * Uses EntityManager to return a List of all books in db. TypedQuery is
     * used, to ensure that it is of type Book.
     *
     * @return List<entity.Book>
     */
    public List<entity.Book> getAllBooks() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<entity.Book> query
                    = em.createQuery("Select book from Book book", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
