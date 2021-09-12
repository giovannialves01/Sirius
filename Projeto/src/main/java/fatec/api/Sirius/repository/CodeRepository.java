package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.Sirius.model.Code;
import fatec.api.Sirius.model.Section;

public interface CodeRepository extends JpaRepository<Code,String>{
	Optional<Code> findById(String id);

}
