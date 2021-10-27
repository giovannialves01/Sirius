package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.repository.BlockRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="APIREST Sirius")
@CrossOrigin(origins="*")
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
	
	
	@PostMapping("/block")
	@ApiOperation(value="Salva um blocos")
	public Block blockSave(@RequestBody Block block) {
				
		return  blockRepository.save(block);
	}
	
	public File toFile(String dir) {
		File teste = new File(dir);
		return teste;
	}
	
	public String pdfName(String doc, String section, String block, String code) {
		return doc + "-" + section + "-" + block + "c" + code;
	}
}

