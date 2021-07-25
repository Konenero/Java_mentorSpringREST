package Java_mentor_Spring_Boot.demo.service;

import Java_mentor_Spring_Boot.demo.dao.RoleDao;
import Java_mentor_Spring_Boot.demo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getRolesList() {
        return roleDao.getRolesList();
    }

    @Override
    public Role getRoleById(int id) {
        return roleDao.getRoleById(id);
    }
}
