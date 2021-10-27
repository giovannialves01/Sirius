package fatec.api.Sirius.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.repository.DocumentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
public class DocumentControllers {

	@Autowired
	DocumentRepository documentRepository;
	
	@GetMapping("/document")
	@ApiOperation(value="Retorna lista dos documentos")
	public List<Document> documentList(){
		return documentRepository.findAll();
	}
	


}	
