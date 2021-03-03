package springboot.service;



import springboot.model.Role;
import springboot.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public List<User> index();
//    public Optional<User> show(Long id);
    public User save(User user);
    public User update(Long id, User user);
    public void delete(Long id);
    public User getUserFromId(Long id);
    public Role getRoleFromId(int id);
    List<Role> getAllRole();
}
