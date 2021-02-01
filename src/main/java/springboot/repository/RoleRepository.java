package springboot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleById(int id);
}
