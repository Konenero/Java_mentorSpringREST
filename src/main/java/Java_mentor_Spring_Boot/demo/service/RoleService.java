package Java_mentor_Spring_Boot.demo.service;


import Java_mentor_Spring_Boot.demo.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRolesList();
    Role getRoleById(int id);
}
