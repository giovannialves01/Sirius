package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Block;

public interface BlockRepository extends JpaRepository<Block, String> {
	
	
	@Query(value = "SELECT b FROM Block b WHERE subsection_id = :subId")
	Block findBlockBySubsectionId(@Param("subId")int subId);
	
	@Query(value = "SELECT b FROM Block b WHERE blo_name = :name")
	List<Block> findEquals(@Param("name")String name);
	
	Optional<Block> findById(String id);
	 
}
