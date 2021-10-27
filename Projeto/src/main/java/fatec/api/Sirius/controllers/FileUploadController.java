package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.model.Subsection;
import fatec.api.Sirius.repository.RemarkRepository;

@Controller
public class FileUploadController {
	
	@Autowired
	RemarkRepository remarkRepository;
	
	public static String uploadDirectory = "../Root/Master/";
	
	RemarkControllers rc = new RemarkControllers();

	@PostMapping("/UploadFile")
	public String upload(Model model,@RequestParam("files") MultipartFile[] files) {	
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file:files) {
			
			uploadDirectory = uploadDirectory + organizePath(file.getOriginalFilename());
			System.out.println(uploadDirectory);
			File CreateFile = toFile(uploadDirectory);
			CreateFile.mkdirs(); 
			
			Remark remark = new Remark();
			
			remark.setBlock(new Block());
			remark.getBlock().setSubsection(new Subsection());
			remark.getBlock().getSubsection().setSection(new Section());
			remark.getBlock().getSubsection().getSection().setDocument(new Document());
		
			remark.getBlock().getSubsection().getSection().getDocument().setName(nameDoc(file.getOriginalFilename()));
			
			remark.getBlock().getSubsection().getSection().setDocument(remark.getBlock().getSubsection().getSection().getDocument());
			remark.getBlock().getSubsection().getSection().setName(nameSection(file.getOriginalFilename()));
			
			remark.getBlock().getSubsection().setSection(remark.getBlock().getSubsection().getSection());
			remark.getBlock().getSubsection().setName(nameSubs(file.getOriginalFilename()));
			
			remark.getBlock().setSubsection(remark.getBlock().getSubsection());
			remark.getBlock().setName(nameBlock(file.getOriginalFilename()));
			
			remark.setBlock((remark.getBlock()));
			remark.setName("");
			
			remarkRepository.save(remark);
					
			Path fileNameAndPath = Paths.get(uploadDirectory,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {		
				Files.write(fileNameAndPath, file.getBytes());
				uploadDirectory = "../Root/Master/";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "updown";
	}
	
	public String organizePath(String path) {		
		String namePath = nameBuilder(nameDoc(path), nameSection(path), nameSubs(path), nameBlock(path));
		return namePath;
	}	
		
		//Pega o doc ABC(SÓ LETRAS)-1234(SÓ NUMEROS) 
	public String nameDoc(String path) {
		String achadoDoc = null;
		String regexDoc = "\\D+-\\d+-";
		Pattern parteDoc = Pattern.compile(regexDoc);
    	Matcher matcherDoc = parteDoc.matcher(path);	
    	while(matcherDoc.find()){
    		achadoDoc = matcherDoc.group().substring(0, matcherDoc.group().length()-1);
    		return achadoDoc;
    	}
    	return "";
	}
	
	public String nameSection(String path) {
		String achadoSecao = null;
    	String regexSecao1 = "\\D+-\\d+-\\w+-";
    	String regexSecao2 = "[^\\D]+-$";
    	Pattern parteSecao = Pattern.compile(regexSecao1);
    	Matcher matcherSecao = parteSecao.matcher(path);	
    	while(matcherSecao.find()){
    		achadoSecao = matcherSecao.group();   		
    		
    		parteSecao = Pattern.compile(regexSecao2);
        	matcherSecao = parteSecao.matcher(achadoSecao);

        	while(matcherSecao.find()){
        		achadoSecao = matcherSecao.group();     		
        		achadoSecao = matcherSecao.group().substring(0, matcherSecao.group().length()-1);   
        		return achadoSecao;
        	}
    		
    	}
    	return "";
	}
	public String nameSubs(String path) {
		String achadoSubs = null;
    	if(haveSubs(path)) {
    		
    		String regexSubs1 = "-\\w+-\\d+c";
        	String regexSubs2 = "^-\\w+";
        	Pattern parteSubs = Pattern.compile(regexSubs1);
        	Matcher matcherSubs = parteSubs.matcher(path);	
        	while(matcherSubs.find()){
        		achadoSubs = matcherSubs.group();       		
        		
        		parteSubs = Pattern.compile(regexSubs2);
            	matcherSubs = parteSubs.matcher(achadoSubs);

            	while(matcherSubs.find()){
            		achadoSubs = matcherSubs.group();
            		achadoSubs = matcherSubs.group().substring(1, matcherSubs.group().length());     
            		return achadoSubs;
            	}
        		
        	}
        	
    	}
    	return "";
    	}
    	
    	public String nameBlock(String path) {
    		String achadoBlock = null;
	    	String regexBlock = "-\\d+c";
	    	Pattern parteBlock = Pattern.compile(regexBlock);
	    	Matcher matcherBlock = parteBlock.matcher(path);	
	    	while(matcherBlock.find()){
	    		achadoBlock = matcherBlock.group().substring(1, matcherBlock.group().length()-1);  	
	    		return achadoBlock;
	    	}
	    	return "";
    	}
    	
    	public String nameBuilder(String achadoDoc,String achadoSecao,String achadoSubs,String achadoBlock) {
    		String caminho = null;
	    	if(achadoSubs.equals("")){
	    		caminho = achadoDoc + "/" + achadoSecao+ "/" + achadoBlock;
	    	}else {
	    		caminho = achadoDoc + "/" + achadoSecao+ "/" + achadoSubs+ "/" + achadoBlock;
	    	}
			
			return caminho;
		}
	
	public boolean haveSubs(String name) {
		
		name = name.replace("-", " ");
		String[] words = name.split(" ");
		
		if(words.length == 5){
			return true;
		}
		return false;
	}
	
	public File toFile(String string) {
		File nameFile = new File(string);
		return nameFile;
	}
	
}
