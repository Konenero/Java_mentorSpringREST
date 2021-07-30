package Java_mentor_Spring_Boot.demo.dao;


import Java_mentor_Spring_Boot.demo.model.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    User findUserByName(String email);
    User findUser(int id);
    List<User> listUsers();
    void deleteUser(int id);
    void updateUser(User user);
}
