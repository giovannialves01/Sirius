package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.repository.BlockRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class BlockControllers {

	@Autowired
	BlockRepository blockRepository;
	
	@GetMapping("/block")
	@ApiOperation(value="Retorna lista de blocos")
	public List<Block> sectionList(){
		return  blockRepository.findAll();
	}
	@GetMapping({"/block/{doc}/{section}/{block}/{code}/{remark}", "/block/{doc}/{section}/{block}/{code}", "/block/{doc}/{section}/{block}/{remark}", "/block/{doc}/{section}/{remark}", "/block/{doc}/{section}/{code}", "/block/{doc}/{section}/{code}/{remark}"})
	@ApiOperation(value="Retorna um blocos")
	public void download(@PathVariable(value="doc")String doc, @PathVariable(value="section")String section, @PathVariable(value="block")String block, @PathVariable(value="code")String code, @PathVariable(value="remark")String remark){
		
		File file = new File("..\\Root\\Main");
		
		String auxPath = criaPath(file.toString(), doc, section, block, pdfName(doc, section, block, code));
		file = toFile(auxPath);
		System.out.println(auxPath);
		
		
		return ;
	}
	
	
	@PostMapping("/block")
	@ApiOperation(value="Salva um blocos")
	public Block blockSave(@RequestBody Block block) {
		
		File file = new File("..\\Root\\Main");
		
		String auxPath = file.toString() + "\\" + block.getSection().getDocument().getDocument().toString() + "\\" + block.getSection().getSection().toString() + "\\" + block.getBlock().toString();
		
		Section auxSection = new Section();
		auxSection.setSection(block.getSection().getSection());
		auxSection.setPath((file.toString() + "\\" + block.getSection().getDocument().getDocument().toString() + "\\" + block.getSection().getSection().toString()));
		
		Document doc = block.getSection().getDocument();
		doc.setDocument(block.getSection().getDocument().getDocument().toString());
		doc.setPath(file.toString() + "\\" + block.getSection().getDocument().getDocument().toString());
		auxSection.setDocument(doc);
		
		block.setPath(auxPath);
		block.setSection(auxSection);
		block.setDocumentName(block.getSection().getDocument().getDocument().toString());
		block.setSectionName(block.getSection().getSection().toString());
		
		file = toFile(auxPath);
		block.setPath(auxPath); 
		try{
		    file.mkdirs(); { 
		    	System.out.println("Deu certo");
		    } 
		} catch(Exception e){
		    e.printStackTrace();
		}
		
		return  blockRepository.save(block);
	}
	
	public File toFile(String dir) {
		File teste = new File(dir);
		return teste;
	}
	
	public String criaPath(String auxPath, String doc, String section, String block, String code) {
		
		if(doc != null) {
			auxPath = auxPath + "\\" + doc;
		}
		if(section != null) {
			auxPath = auxPath + "\\" + section;
		}
		if(block != null) {
			auxPath = auxPath + "\\" + block;
		}
		if(code != null) {
			auxPath = auxPath + "\\" + code;
		}
		return auxPath;
	}
	
	public String pdfName(String doc, String section, String block, String code) {
		return doc + "-" + section + "-" + block + "c" + code;
	}
}

