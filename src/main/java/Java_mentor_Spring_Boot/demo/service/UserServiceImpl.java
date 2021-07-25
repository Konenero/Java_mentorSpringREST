package Java_mentor_Spring_Boot.demo.service;

import Java_mentor_Spring_Boot.demo.dao.UserDao;
import Java_mentor_Spring_Boot.demo.model.Role;
import Java_mentor_Spring_Boot.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsServiceImpl")
public class UserServiceImpl implements UserService, UserDetailsService {
    private UserDao userDao;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;

    @Autowired
    public void setUserDaoAndEncoder(UserDao userDao,
                                     PasswordEncoder passwordEncoder,
                                     RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return  new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isEnabled(), user.isAccountNonLocked(),
                user.getRoles());
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        User userToSave = new User();
        userToSave.setUsername(user.getUsername());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setRoles(user.getRoles());
        userDao.saveUser(userToSave);
    }

    public Set<Role> getSetOfRoles(List<String> rolesId){
        Set<Role> roleSet = new HashSet<>();
        for (String id: rolesId) {
            roleSet.add(roleService.getRoleById(Integer.parseInt(id)));
        }
        return roleSet;
    }

    @Override
    @Transactional
    public User findUser(int id) {
        return userDao.findUser(id);
    }

    @Override
    @Transactional
    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
