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

import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.repository.SectionRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class SectionControllers {

	@Autowired
	SectionRepository sectionRepository;
	
	@GetMapping("/section")
	@ApiOperation(value="Retorna lista de seções")
	public List<Section> sectionList(){
		return  sectionRepository.findAll();
	}
	@GetMapping("/section/{id}")
	@ApiOperation(value="Retorna uma seção")
	public Optional<Section> uniqueSection(@PathVariable(value="id")String id){
		return  sectionRepository.findById(id);
	}
	@PostMapping("/section")
	@ApiOperation(value="Salva uma seção")
	public Section sectionSave(@RequestBody Section section) {
		return  sectionRepository.save(section);
	}
}

