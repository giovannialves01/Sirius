package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {
	
	Optional<Document> findById(String id);
	
	
	
	//@Query("select DISTINCT ON (tb_document.id) tb_document.doc_name,tb_section.sec_name, tb_subsection.subs_name,tb_block.blo_name,tb_remark.rem_name from tb_document, tb_section, tb_subsection, tb_block, tb_remark")
	//List<String> codelist();
		
	@Query(value = "SELECT * FROM tb_document u WHERE u.name = :name", nativeQuery = true)
	Document findUserByStatusNative(@Param("name")String name);
	
}