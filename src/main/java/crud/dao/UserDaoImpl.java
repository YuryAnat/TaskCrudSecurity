package crud.dao;

import crud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Override
    public List<User> getUsers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<User> from_user = entityManager.createQuery("From User", User.class);
        return from_user.getResultList();
    }

    @Override
    public void saveUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
    }

    @Override
    public void editUser(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(user);
        transaction.commit();
    }

    @Override
    public void removeUser(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("Delete FROM User u where u.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        transaction.commit();
    }

    @Override
    public User getUser(long id) {
        Query query = entityManagerFactory.createEntityManager().createQuery("From User u where u.id = :id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }
}
