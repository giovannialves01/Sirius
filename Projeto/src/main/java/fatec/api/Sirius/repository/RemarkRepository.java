package fatec.api.Sirius.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Remark;

public interface RemarkRepository extends JpaRepository<Remark, String> {

	Optional<Remark> findById(String id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Document d WHERE d.id = :idDoc")
	void deleteLine(@Param("idDoc")int idDoc);
}
