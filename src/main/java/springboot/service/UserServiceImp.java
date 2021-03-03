package springboot.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.Role;
import springboot.model.User;
import springboot.repository.RoleRepository;
import springboot.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {


    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServiceImp(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public List<User> index() {
        return (List<User>) userRepository.findAll();
    }



    @Transactional
    @Override
    public User save(User user) {
        String role = user.getRoleForHTML();
        user.setRole(new HashSet<>());
        if (role.contains("ADMIN")){
            user.getRole().add(getRoleFromId(1));
        }
        if (role.contains("USER")){
            user.getRole().add(getRoleFromId(2));
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Transactional
    @Override
    public User update(Long id, User user) {
        Set<Role> setByUser = getUserFromId(id).getRole();
        Set<Role> arr = getSetUser(id);
        if (user.getRoleForHTML()!=null) {
            if (user.getRoleForHTML().contains("ADMIN")) {
                arr.add(getRoleFromId(1));
            }
            if (user.getRoleForHTML().contains("USER")) {
                arr.add(getRoleFromId(2));
            }
            user.setRole(arr);
        } else {
            user.setRole(setByUser);
        }
        if (!user.getPassword().equals(getUserFromId(id).getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.delete(getUserFromId(id));
    }

    @Override
    public User getUserFromId(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public Role getRoleFromId(int id) {
        return roleRepository.findRoleById(id);
    }

    @Override
    public List<Role> getAllRole() {
        return (List<Role>) roleRepository.findAll();
    }

    public Set<Role> getSetUser( Long id) {
        User user = userRepository.findUserById(id);
        Set<Role> setRole = user.getRole();

        return setRole;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(s);
        return user;
    }
}
