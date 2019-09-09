package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }

    }

    public Movie getMoviebyName(String title) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Movie> query = em.createQuery("Select m from Movie m WHERE m.title = :title", Movie.class).setParameter("title", title);
            Movie m = query.getResultList().get(0);
            return m;
        } finally {
            em.close();
        }

    }

    public List getAllMovies() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Movie> query = em.createQuery("Select m from Movie m", Movie.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void addMovie(Movie movie) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
//
    public static void main(String[] args) {
        //MovieFacade em = getFacade(Persistence.createEntityManagerFactory("pu"));
//        MovieFacade em = getFacade(EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE));
//
//        em.addMovie(new Movie("Godfather", 9.8, 1998));
//        em.addMovie(new Movie("Godfather II", 9.8, 1998));
//        em.addMovie(new Movie("Godfather III", 9.8, 1998));

    }

}
