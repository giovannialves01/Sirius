package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.repository.DocumentRepository;
import fatec.api.Sirius.repository.SectionRepository;
import io.swagger.annotations.ApiOperation;

@Controller
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
	@GetMapping("/section/{id}")
	@ApiOperation(value="Retorna uma seção")
	public Optional<Section> uniqueSection(@PathVariable(value="id")String id){
		return  sectionRepository.findById(id);
	}
	@PostMapping("/section")
	@ApiOperation(value="Salva uma seção")
	public Section sectionSave(@RequestBody Section section) {
		File file = new File("..\\Root\\Main");
		
		String auxPath = file.toString() + "\\" + section.getDocument().getPath().toString() + "\\" + section.getPath().toString();
		Document doc = new Document();
		
		doc.setDocument(section.getDocument().toString());
		doc.setPath(file.toString() + "\\" + section.getDocument().getPath().toString());

		section.setPath(auxPath);
		section.setDocument(doc);

		file = toFile(auxPath);
		try{
		    file.mkdirs(); { 	
		    	System.out.println("Deu certo");
		    } 
		} catch(Exception e){
		    e.printStackTrace();
		}
		
		return  sectionRepository.save(section);
	}
	
	public File toFile(String dir) {
		File teste = new File(dir);
		return teste;
	}
}

