package Java_mentor_Spring_Boot.demo.dao;

import Java_mentor_Spring_Boot.demo.model.Role;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    private EntityManager entityManager;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session session;


    @Override
    public List<Role> getRolesList() {
        session = entityManager.unwrap(Session.class);
        return session.createQuery("from Role").getResultList();
    }

    @Override
    public Role getRoleById(int id) {
        session = entityManager.unwrap(Session.class);
        return session.find(Role.class, id);
    }
}
