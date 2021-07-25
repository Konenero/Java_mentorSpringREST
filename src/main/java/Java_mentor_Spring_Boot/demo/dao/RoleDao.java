package Java_mentor_Spring_Boot.demo.dao;


import Java_mentor_Spring_Boot.demo.model.Role;

import java.util.List;

public interface RoleDao {
    List<Role> getRolesList();
    Role getRoleById(int id);
}
