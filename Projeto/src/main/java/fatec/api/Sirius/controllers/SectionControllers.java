package fatec.api.Sirius.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.repository.DocumentRepository;
import fatec.api.Sirius.repository.SectionRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class SectionControllers {

	@Autowired
	SectionRepository sectionRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@GetMapping("/section")
	@ApiOperation(value="Retorna lista de seções")
	public List<Section> sectionList(){
		return  sectionRepository.findAll();
	}

}

