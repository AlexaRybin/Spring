package springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByName(String name);
    User findUserById(Long id);
}
