package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	public static String uploadDirectory = "../Root/Master/";
	
	BlockControllers bc = new BlockControllers();

	@PostMapping("/UploadFile")
	public String upload(Model model,@RequestParam("files") MultipartFile[] files) {	
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file:files) {
			
			uploadDirectory = uploadDirectory + organizePath(file.getOriginalFilename());
			System.out.println(uploadDirectory);
			
			File CreateFile = bc.toFile(uploadDirectory);
			CreateFile.mkdirs();
					
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
		
		String achadoDoc = null;
		String achadoSecao = null;
		String achadoBlock = null;
		
		
		//Pega o doc ABC(SÓ LETRAS)-1234(SÓ NUMEROS) 
		String regexDoc = "\\D+-\\d+-";
		Pattern parteDoc = Pattern.compile(regexDoc);
    	Matcher matcherDoc = parteDoc.matcher(path);	
    	while(matcherDoc.find()){
    		achadoDoc = matcherDoc.group().substring(0, matcherDoc.group().length()-1);
    		System.out.println("doc " + achadoDoc);
    	}
    	
    	String regexSecao1 = "\\D+-\\d+-\\w+-";
    	String regexSecao2 = "[^\\D]+-$";
    	Pattern parteSecao = Pattern.compile(regexSecao1);
    	Matcher matcherSecao = parteSecao.matcher(path);	
    	while(matcherSecao.find()){
    		achadoSecao = matcherSecao.group();
    		System.out.println("Secao antes de arrumar1 " + achadoSecao);
    		
    		parteSecao = Pattern.compile(regexSecao2);
        	matcherSecao = parteSecao.matcher(achadoSecao);

        	while(matcherSecao.find()){
        		achadoSecao = matcherSecao.group();
        		System.out.println("Secao antes de arrumar2 " + achadoSecao);
        		achadoSecao = matcherSecao.group().substring(0, matcherSecao.group().length()-1);
        		System.out.println("Secao " + achadoSecao);
        	}
    		
    	}
    	
    	String regexBlock = "-\\d+c";
    	Pattern parteBlock = Pattern.compile(regexBlock);
    	Matcher matcherBlock = parteBlock.matcher(path);	
    	while(matcherBlock.find()){
    		achadoBlock = matcherBlock.group().substring(1, matcherBlock.group().length()-1);
    		System.out.println("bloco " + achadoBlock);
    	}
    	String caminho = achadoDoc + "/" + achadoSecao+ "/" + achadoBlock;
 
		return caminho;
	}
}
