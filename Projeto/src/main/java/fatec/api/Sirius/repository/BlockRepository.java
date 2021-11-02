package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.model.Subsection;

public interface BlockRepository extends JpaRepository<Block, String> {
	
	
	@Query(value = "SELECT b FROM Block b WHERE subsection_id = :subId")
	Block findBlockBySubsectionId(@Param("subId")int subId);
	
	Optional<Block> findById(String id);
	 
}
