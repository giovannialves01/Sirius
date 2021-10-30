package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fatec.api.Sirius.model.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
	
	Optional<Document> findById(String id);
	
	
	
	//@Query("select DISTINCT ON (tb_document.id) tb_document.doc_name,tb_section.sec_name, tb_subsection.subs_name,tb_block.blo_name,tb_remark.rem_name from tb_document, tb_section, tb_subsection, tb_block, tb_remark")
	//List<String> codelist();
	
	@Query(value = "SELECT DISTINCT d.name FROM Document d")
	List<String> findAllDocument();
	
}