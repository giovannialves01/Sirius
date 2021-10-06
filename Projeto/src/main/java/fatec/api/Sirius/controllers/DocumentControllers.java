package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.repository.DocumentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
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
	
	//@GetMapping("/document/{doc}/{section}/{block}")
	//@ApiOperation(value="Retorna um documento")
	//public List<Document> uniqueDoc(@PathVariable(value="doc")String id, @PathVariable(value="section")String section, @PathVariable(value="block")String block){
	//	return documentRepository;
		
	//}
	
	
	@PostMapping("/document")
	@ApiOperation(value="Salva um documento")
	public Document documentSave(@RequestBody Document document) {
		File file = new File("..\\Root\\Main");
		
		String[] arquivos = file.list();
	    
	    for (String arquivo:arquivos) {
	    	System.out.println(arquivo);
	    }
		String auxPath = file.toString() + "\\" + document.getPath().toString() ;
		document.setPath(auxPath); 
		file = toFile(auxPath);
		
		try{
		    file.mkdirs(); { 	  
		    	System.out.println("Deu certo");
		    } 
		} catch(Exception e){
		    e.printStackTrace();
		} 
		return documentRepository.save(document);
	}
	
	public File toFile(String dir) {
		File teste = new File(dir);
		return teste;
	}

}	
