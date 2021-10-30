package fatec.api.Sirius.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.repository.DocumentRepository;
import io.swagger.annotations.Api;

@RestController
@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
public class DocumentControllers {

	@Autowired
	DocumentRepository documentRepository;
	
	@GetMapping("/documentos")
	public ModelAndView allDocuments() {
		
		List<String> document = documentRepository.findAllDocument();	
		ModelAndView capsula = new ModelAndView("documents");
		capsula.addObject("documents", document);
		
		return capsula;
	}
	

}	
