package Java_mentor_Spring_Boot.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String surname;
    @Column
    private Long age;
    @Column
    private String email;
    @Transient
    private String[] roleString;


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles = new HashSet<>();

    public User(String username, String password, String surname, Long age, String email, Set<Role> roles) {
        this.username = username;
        this.password = password;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public User() {
    }

    public String[] getRoleString() {
        return roleString;
    }

    public void setRoleString(String[] roleString) {
        this.roleString = roleString;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }


    public String getRolesString() {
        return roles.stream().map(Role::toString).collect(Collectors.joining(" "));
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof User)) return false;
        User user = (User) obj;
        return user.username.equals(this.username);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
