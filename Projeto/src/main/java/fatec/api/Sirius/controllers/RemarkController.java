package fatec.api.Sirius.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class RemarkController {

	@GetMapping("/remark")
	@ApiOperation(value="Retorna lista de remarks")
	public List<Remark> sectionList(){
		return RemarkRepository.findAll();
	}
	@GetMapping("/remark/{id}")
	@ApiOperation(value="Retorna um remark")
	public Optional<Remark> uniqueSection(@PathVariable(value="id")String id){
		return  RemarkRepository.findById(id);
	}
	@PostMapping("/remark")
	@ApiOperation(value="Salva um remark")
	public Remark blockSave(@RequestBody Remark block) {
		return  RemarkRepository.save(block);
	}
}
