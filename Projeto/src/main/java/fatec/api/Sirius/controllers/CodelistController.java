package fatec.api.Sirius.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.model.Subsection;
import fatec.api.Sirius.repository.BlockRepository;
import fatec.api.Sirius.repository.DocumentRepository;
import fatec.api.Sirius.repository.RemarkRepository;
import fatec.api.Sirius.repository.SectionRepository;
import fatec.api.Sirius.repository.SubsectionRepository;
import io.swagger.annotations.Api;

@RestController
@RequestMapping(value="/api")
@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
public class CodelistController {
	
	@Autowired
	private DocumentRepository dr;
	
	@Autowired
	SectionRepository sr;
	
	@Autowired
	SubsectionRepository subr;
	
	@Autowired
	BlockRepository br;
	
	@Autowired
	RemarkRepository rr;
	
	@GetMapping("/lista")
	public ModelAndView todosClientesOrdenadoPorNome() {
		List<Document> document = dr.findAll();
		List<Section> section = sr.findAll();
		List<Subsection> subsection = subr.findAll();
		List<Block> block = br.findAll();
		List<Remark> remark = rr.findAll();
		
		
		ModelAndView capsula = new ModelAndView("codelist");
		capsula.addObject("documents", document);
		capsula.addObject("sections", section);
		capsula.addObject("subsections", subsection);
		capsula.addObject("blocks", block);
		capsula.addObject("remarks", remark);
		
		return capsula;
	}
}
