package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.Sirius.model.Block;

public interface BlockRepository extends JpaRepository<Block, String> {
	
	Optional<Block> findById(String id);
	 
}
