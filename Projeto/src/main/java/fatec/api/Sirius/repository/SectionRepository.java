package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Section;

public interface SectionRepository extends JpaRepository<Section, String> {

	
	@Query(value = "SELECT s FROM Section s WHERE document_id = :docId")
	Section findSectionByDocId(@Param("docId")int docId);
	
	@Query(value = "SELECT s FROM Section s WHERE sec_name = :name")
	List<Section> findEquals(@Param("name")String name);
	
	Optional<Section> findById(String id);
	
	
	
}
