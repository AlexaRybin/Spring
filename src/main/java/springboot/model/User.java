package springboot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "User")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "last_name")
    private String lastName;

    @Transient
    private String passwordConfirm;

    @Transient
    private String roleForHTML;

    @ManyToMany(fetch = FetchType.EAGER) // узнать что такое fetch = FetchType.EAGER
    private Set<Role> roles;

    public User(Long id, String name,String lastName, String password, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.lastName= lastName;
    }

    public User(Long id, String name, String lastName, int age, String password, String email, Set<Role> role) {
        this.email = email;
        this.age = age;
        this.roles = role;
        this.id = id;
        this.password = password;
        this.name = name;
        this.lastName= lastName;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public void setRole(Set<Role> role) {
        this.roles = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRoleStr(User user) {
        String roleStr = new String();
        Set<Role> set = user.getRole();
        for (Role role : set) {
            roleStr += role.getRole().replace("ROLE_", " ");
        }
        return roleStr;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRoleForHTML() {
        return roleForHTML;
    }

    public void setRoleForHTML(String roleForHTML) {
        this.roleForHTML = roleForHTML;
    }
}
