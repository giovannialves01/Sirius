package fatec.api.Sirius.controllers;

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
	@GetMapping("/block/{id}")
	@ApiOperation(value="Retorna um blocos")
	public Optional<Block> uniqueSection(@PathVariable(value="id")String id){
		return  blockRepository.findById(id);
	}
	@PostMapping("/block")
	@ApiOperation(value="Salva um blocos")
	public Block blockSave(@RequestBody Block block) {
		return  blockRepository.save(block);
	}
}

