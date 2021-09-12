package fatec.api.Sirius.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.repository.RemarkRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class RemarkController {

	@Autowired
	RemarkRepository remarkRepository;
	
	@GetMapping("/remark")
	@ApiOperation(value="Retorna lista de remarks")
	public List<Remark> sectionList(){
		return remarkRepository.findAll();
	}
	@GetMapping("/remark/{id}")
	@ApiOperation(value="Retorna um remark")
	public Optional<Remark> uniqueSection(@PathVariable(value="id")String id){
		return  remarkRepository.findById(id);
	}
	@PostMapping("/remark")
	@ApiOperation(value="Salva um remark")
	public Remark remarkSave(@RequestBody Remark remark) {
		return  remarkRepository.save(remark);
	}
}
