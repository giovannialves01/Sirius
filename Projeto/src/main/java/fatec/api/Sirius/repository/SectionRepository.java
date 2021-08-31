package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.Sirius.model.Section;

public interface SectionRepository extends JpaRepository<Section, String> {

	Optional<Section> findById(String id);
}
