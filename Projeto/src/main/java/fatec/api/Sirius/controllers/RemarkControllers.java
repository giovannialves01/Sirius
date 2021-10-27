package fatec.api.Sirius.controllers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.repository.RemarkRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
public class RemarkControllers {

	@Autowired
	RemarkRepository remarkRepository ;
	
	@GetMapping("/remark")
	@ApiOperation(value="Retorna lista de remarks")
	public List<Remark> remarkList(){
		return  remarkRepository.findAll();				
	}

	@PostMapping("/remark")
	@ApiOperation(value="Salva um remark")
	public Remark remarkSave(@RequestBody Remark remark) {		
		return  remarkRepository.save(remark);
	}
	
	public File toFile(String dir) {
		File teste = new File(dir);
		return teste;
	}
	
	public String pdfName(String doc, String section, String block, String code) {
		return doc + "-" + section + "-" + block + "c" + code;
	}
}

	  
