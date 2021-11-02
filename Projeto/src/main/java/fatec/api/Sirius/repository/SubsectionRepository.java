package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Subsection;

public interface SubsectionRepository extends JpaRepository<Subsection, String> {

	
	@Query(value = "SELECT s FROM Subsection s WHERE section_id = :secId")
	Subsection findSubsectionBySectionId(@Param("secId")int docId);
	
	@Query(value = "SELECT s FROM Subsection s WHERE subs_name = :name")
	List<Subsection> findEquals(@Param("name")String name);
	
	Optional<Subsection> findById(String id);
}
