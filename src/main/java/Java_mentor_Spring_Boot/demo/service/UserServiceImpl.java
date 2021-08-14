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

import java.util.*;

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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByName(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", email));
        }
        return  new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isEnabled(), user.isAccountNonLocked(),
                user.getRoles());
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setRoles(getSetOfRoles(Arrays.asList(user.getRoleString())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
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
    public User findUser(Long id) {
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
        user.setRoles(getSetOfRoles(Arrays.asList(user.getRoleString())));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

}
