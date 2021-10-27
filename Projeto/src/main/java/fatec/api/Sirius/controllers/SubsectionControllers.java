package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Subsection;
import fatec.api.Sirius.repository.SubsectionRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class SubsectionControllers {

	@Autowired
	SubsectionRepository subsectionRepository;
	
	@GetMapping("/subsection")
	@ApiOperation(value="Retorna lista de subseções")
	public List<Subsection> subsectionList(){
		return  subsectionRepository.findAll();				
	}
	
	@PostMapping("/subsection")
	@ApiOperation(value="Salva um subseções")
	public Subsection subsectionSave(@RequestBody Subsection subsection) {				
		return  subsectionRepository.save(subsection);
	}
	
	public File toFile(String dir) {
		File teste = new File(dir);
		return teste;
	}
	
	public String pdfName(String doc, String section, String block, String code) {
		return doc + "-" + section + "-" + block + "c" + code;
	}
}

