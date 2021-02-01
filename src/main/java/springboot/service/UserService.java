package springboot.service;



import springboot.model.Role;
import springboot.model.User;

import java.util.List;

public interface UserService {
    public List<User> index();
//    public Optional<User> show(Long id);
    public User save(User user);
    public User update(Long id, User user, String role);
    public void delete(Long id);
    public User getUserFromId(Long id);
    public Role getRoleFromId(int id);
}
