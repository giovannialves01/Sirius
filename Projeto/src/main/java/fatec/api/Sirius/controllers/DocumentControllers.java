package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.repository.DocumentRepository;
import fatec.api.Sirius.repository.RemarkRepository;
import io.swagger.annotations.Api;

@Controller
@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
public class DocumentControllers {

	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	RemarkRepository rr;
	
	public static String Directory = "../Root/Master/";
	public static String zipDirectory = "../Root/Zip/";
	
	@GetMapping("/documentos")
	public ModelAndView allDocuments() {
		
		List<String> document = documentRepository.findAllDocument();	
		ModelAndView capsula = new ModelAndView("documents");
		capsula.addObject("documents", document);
		
		return capsula;
	}
	
	@PostMapping("/atualizaRemark")
	public String atualizaRemark(String remark, String doc, int id) {
		
			Remark rem = rr.findById(id);
			rem.setName(remark);
			rr.save(rem);
			
		
		return "redirect:/codelist/" + doc ;
	}


}	
