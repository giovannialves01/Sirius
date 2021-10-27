package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.Sirius.model.Subsection;

public interface SubsectionRepository extends JpaRepository<Subsection, String> {

	Optional<Subsection> findById(String id);
}
