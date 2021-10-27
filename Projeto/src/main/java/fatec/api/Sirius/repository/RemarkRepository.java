package fatec.api.Sirius.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fatec.api.Sirius.model.Remark;

public interface RemarkRepository extends JpaRepository<Remark, String> {

	Optional<Remark> findById(String id);
}
