package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.model.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
	
	Document findById(int id);
	
	@Query(value = "SELECT d FROM Document d WHERE doc_name = :name")
	List<Document> findDocEquals(@Param("name")String name);
	
	@Query(value = "SELECT DISTINCT d.name FROM Document d")
	List<String> findAllDocument();
	
}