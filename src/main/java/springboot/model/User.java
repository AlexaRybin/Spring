package springboot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "User")
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER) // узнать что такое fetch = FetchType.EAGER
    private Set<Role> roles;

    public User(Long id, String name, String password, String email){
        this.id = id;
        this.password= password;
        this.name = name;
        this.email = email;
    }
    public User(Long id, String name, int age, String password, String email, Set<Role> role) {
        this.email = email;
        this.age = age;
        this.roles = role;
        this.id = id;
        this.password= password;
        this.name = name;
    }

    public User() { }

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

    public void setPassword(String password) { this.password = password; }

    public  Set<Role> getRole(){
        return roles;
    }

    public void setRole(Set<Role> role){
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
}
