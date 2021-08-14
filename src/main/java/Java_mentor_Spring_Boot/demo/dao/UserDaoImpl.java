package Java_mentor_Spring_Boot.demo.dao;

import Java_mentor_Spring_Boot.demo.model.User;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session session;

    @Override
    public void saveUser(User user) {
        session = entityManager.unwrap(Session.class);
        session.save(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findUser(Long id) {
        session = entityManager.unwrap(Session.class);
        return session.find(User.class, id);
    }

    @Override
    public User findUserByName(String email) {
        session = entityManager.unwrap(Session.class);
        TypedQuery<User> query = session.createQuery("FROM User u where u.email = :email");
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        session = entityManager.unwrap(Session.class);
        return (List<User>) session.createQuery("SELECT u FROM User u").getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        session = entityManager.unwrap(Session.class);
        User user = session.find(User.class, id);
        session.remove(user);
    }
}
