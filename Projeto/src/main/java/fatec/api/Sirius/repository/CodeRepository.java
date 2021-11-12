package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Code;

public interface CodeRepository extends JpaRepository<Code, String> {
	
	
	@Query(value = "SELECT c FROM Code c WHERE blo_id = :bloId")
	Code findCodeByBlockId(@Param("bloId")int bloId);
	
	@Query(value = "SELECT c FROM Code c WHERE cod_name = :name")
	List<Code> findEquals(@Param("name")String name);
	
	Optional<Code> findById(String id);
	 
}
