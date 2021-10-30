package fatec.api.Sirius.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fatec.api.Sirius.model.Remark;

public interface RemarkRepository extends JpaRepository<Remark, String> {

	Optional<Remark> findById(String id);
	/*
	@Query(value = "SELECT r FROM Remark r WHERE Remark.getBlock().getSubsection().getSection().getDocument().getName() = :name")
	List<Remark> findAllDocuments(@Param("name")String name);*/
}
