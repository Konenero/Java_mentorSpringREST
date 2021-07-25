package Java_mentor_Spring_Boot.demo.service;



import Java_mentor_Spring_Boot.demo.model.Role;
import Java_mentor_Spring_Boot.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    void saveUser(User user);
    User findUser(int id);
    List<User> listUsers();
    User findUserByName(String name);
    Set<Role> getSetOfRoles(List<String> id);
    void deleteUser(int id);
    void updateUser(User user);
}
