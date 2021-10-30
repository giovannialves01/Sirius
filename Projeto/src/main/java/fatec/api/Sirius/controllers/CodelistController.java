package fatec.api.Sirius.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.repository.DocumentRepository;
import fatec.api.Sirius.repository.RemarkRepository;
import io.swagger.annotations.Api;

@Controller
@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
public class CodelistController {
	
	@Autowired
	RemarkRepository rr;
	
	@Autowired
	DocumentRepository dr;
	
	@GetMapping("/codelist/{nomeDocumento}")
	public ModelAndView codelist(@PathVariable String nomeDocumento) {

		List<Remark> remark = rr.findAll();
		List<Remark> filtroDocumento = new ArrayList<Remark>();
		
		for(int i = 0 ; i < remark.size(); i++){
			if(remark.get(i).getBlock().getSubsection().getSection().getDocument().getName().equals(nomeDocumento)) {
				filtroDocumento.add(remark.get(i));
			}
     }
		
		
		ModelAndView capsula = new ModelAndView("codelist");

		capsula.addObject("remarks", filtroDocumento);
		
		return capsula;
	}
	
	@GetMapping("/delete/{id}")
	public String deleteLine(@PathVariable("id") int idDoc) {

		
		Document doc = dr.findById(idDoc);
		rr.deleteLine(idDoc);
		
		return "redirect:/codelist/" + doc.getName();
	}	
	

}
