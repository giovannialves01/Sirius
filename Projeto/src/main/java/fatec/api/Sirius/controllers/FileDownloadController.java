package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.io.Files;

import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.repository.RemarkRepository;

@Controller
public class FileDownloadController {
	
	@Autowired
	RemarkRepository rr;
	
	BlockControllers bc = new BlockControllers();
	
	public static String zipDirectory = "../Root/Master/";
	public static String Directory = "../Root/Master/";
	String compactName = "compact.pdf";
	
	@GetMapping("/DownloadFile")
	public void DownloadFile(String document, String section, String subsection, String block, String code, String remark,HttpServletRequest request, HttpServletResponse response ) throws Exception {		
		
		File pathDirectory = null;
		String fileName = null;
		if(!section.equals("") && remark.equals("") && !code.equals("")) {
			if(subsection.equals("")){
				Directory = Directory + document + "/" + section + "/" + block + "/" + document + "-" + section + "-" + block + "c" + code + ".pdf";
				fileName = document + "-" + section + "-" + block + "c" + code + ".pdf";
	    	}else {
	    		Directory = Directory + document + "/" + section + "/" + subsection + "/" + block + "/" + document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".pdf";
	    		fileName = document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".pdf";
	    	}				
						
		 	pathDirectory = toFile(Directory);
				
				
			response.setContentType("application/pdf");
		    response.addHeader("Content-Disposition", "attachment; filename="+fileName);
		    try
		    {
		        Files.copy(pathDirectory, response.getOutputStream());
		        response.getOutputStream().flush();
		    } 
		    catch (IOException ex) {
		        ex.printStackTrace();
		    }

		    Directory = "../Root/Master/";	
	}
		
		
		if(section.equals("") && subsection.equals("") && block.equals("") && !remark.equals("") && code.equals("")) {
			
		List<Remark> remarks = rr.findRemCodeEquals(remark);
		
		for (Remark file : remarks) {
			
			String d = file.getBlock().getSubsection().getSection().getDocument().getName();
			String s = file.getBlock().getSubsection().getSection().getName();			
			String sub = file.getBlock().getSubsection().getName();						
			String b = file.getBlock().getName();
			
			String path = nameBuilder(d, s, sub, b);
			
			pathDirectory = toFile(zipDirectory);
	    	Directory = Directory + path;    					
		}
		
		response.setContentType("application/zip");
	    response.addHeader("Content-Disposition", "attachment; filename="+"Pdf Full E Delta");
	    try
	    {
	        Files.copy(pathDirectory, response.getOutputStream());
	        response.getOutputStream().flush();
	    } 
	    catch (IOException ex) {
	        ex.printStackTrace();
	    }

	    Directory = "../Root/Master/";	
	    
		System.out.println("Começo da funçao de download por traço");
		
		
		}
		
	}
	
	public String nameBuilder(String achadoDoc, String achadoSecao, String achadoSubs, String achadoBlock) {
		String caminho = null;
		if (achadoSubs.equals("")) {
			caminho = achadoDoc + "/" + achadoSecao + "/" + achadoBlock;
		} else {
			caminho = achadoDoc + "/" + achadoSecao + "/" + achadoSubs + "/" + achadoBlock;
		}

		return caminho;
	}
	
	public File toFile(String string) {
		File nameFile = new File(string);
		return nameFile;
	}
}