package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.Sirius.model.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
	
	Optional<Document> findById(String id);
	
}