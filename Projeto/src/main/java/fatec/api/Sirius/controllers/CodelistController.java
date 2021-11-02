package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@Controller
@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
public class CodelistController {
	
	@Autowired
	RemarkRepository rr;
	
	@Autowired
	DocumentRepository dr;
	
	@Autowired
	SectionRepository sr;
	
	@Autowired
	SubsectionRepository sub;
	
	@Autowired
	BlockRepository blo;
	
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
	public String deleteLine(@PathVariable("id") String idDoc) {
		

		Document doc = dr.findById(Integer.parseInt(idDoc));
		Section sec = sr.findSectionByDocId(doc.getId());
		Subsection subs = sub.findSubsectionBySectionId(sec.getId());
		Block block = blo.findBlockBySubsectionId(subs.getId());
		
		System.out.println(doc.getName());
		System.out.println(sec.getName());
		System.out.println(subs.getName());
		System.out.println(block.getName());
		
		if(subs.getName().equals("-")) {
		deleteFiles("../Root/Master/" + "/" + doc.getName() + "/" + sec.getName() + "/" + block.getName());
		} else {
		deleteFiles("../Root/Master/" + "/" + doc.getName() + "/" + sec.getName() + "/" + subs.getName() + "/" + block.getName());
		}
		rr.deleteLine(Integer.parseInt(idDoc));
		
		
		
		return "redirect:/codelist/" + doc.getName();
	}	
	
	public void deleteFiles(String path) {
		
		File file = toFile(path);
		System.out.println(path);
		
		String[]entries = file.list();
		for(String s: entries){
		    File currentFile = new File(file.getPath(),s);
		    currentFile.delete();
		}
		file.delete();
	}
	
	public File toFile(String string) {
		File nameFile = new File(string);
		return nameFile;
	}
}
