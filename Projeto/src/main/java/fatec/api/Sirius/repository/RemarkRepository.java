package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Code;
import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Remark;

public interface RemarkRepository extends JpaRepository<Remark, String> {

	Remark findById(int id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM Document d WHERE d.id = :idDoc")
	void deleteLine(@Param("idDoc")int idDoc);
	
	@Query(value = "SELECT r FROM Remark r WHERE rem_name = :code")
	List<Remark> findRemCodeEquals(@Param("code")String code);
	
	@Query(value = "SELECT r FROM Remark r WHERE code_id = :codeId")
	Remark findRemarkByCodeId(@Param("codeId")int codeId);
}
