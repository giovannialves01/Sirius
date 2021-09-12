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

import fatec.api.Sirius.model.Code;
import fatec.api.Sirius.repository.CodeRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class CodeController {
	
	@Autowired
	CodeRepository codeRepository;
	
	@GetMapping("/code")
	@ApiOperation(value="Retorna lista de codigo")
	public List<Code> sectionList(){
		return codeRepository.findAll();//
	}
	@GetMapping("/code/{id}")
	@ApiOperation(value="Retorna um codigo")
	public Optional<Code> uniqueSection(@PathVariable(value="id")String id){
		return  codeRepository.findById(id);
	}
	@PostMapping("/code")
	@ApiOperation(value="Salva um codigo")
	public Code codeSave(@RequestBody Code code) {
		return  codeRepository.save(code);
	}

}
