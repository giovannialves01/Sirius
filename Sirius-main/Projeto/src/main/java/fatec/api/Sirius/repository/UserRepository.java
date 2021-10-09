package fatec.api.Sirius.repository;

import fatec.api.Sirius.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {
    User findByUsername(String username);
}
