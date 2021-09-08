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
public class NSecController {

	@GetMapping("/nsec")
	@ApiOperation(value="Retorna lista de numero de secao")
	public List<Nsec> sectionList(){
		return NsecRepository.findAll();
	}
	@GetMapping("/nsec/{id}")
	@ApiOperation(value="Retorna um numero de secao")
	public Optional<Nsec> uniqueSection(@PathVariable(value="id")String id){
		return  NsecRepository.findById(id);
	}
	@PostMapping("/nsec")
	@ApiOperation(value="Salva um numero de secao")
	public Nsec blockSave(@RequestBody Nsec block) {
		return  NsecRepository.save(block);
	}
}
