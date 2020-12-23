package crud.dao;

import crud.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<User> getUsers() {
        TypedQuery<User> from_user = entityManager.createQuery("From User", User.class);
        return from_user.getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.joinTransaction();
        entityManager.persist(user);
    }

    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUser(long id) {
        Query query = entityManager.createQuery("Delete FROM User u where u.id = :id");
        query.setParameter("id", id);
        entityManager.joinTransaction();
        query.executeUpdate();
    }

    @Override
    public User getUser(long id) {
        Query query = entityManager.createQuery("From User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    @Override
    public User getUserByEmail(String email) {
        User email1 = entityManager.createQuery("From User u Where u.email = :email", User.class).setParameter("email", email).getSingleResult();
        return email1;
    }
}
