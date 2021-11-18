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
	public static String Revision = "../Root/Rev/";
	public static int stackRevision = 1;
	String compactName = "compact.pdf";
	
	@GetMapping("/DownloadFile")
	public String DownloadFile(String document, String section, String subsection, String block, String code, String remark,HttpServletRequest request, HttpServletResponse response ) throws Exception {		
		
		File pathDirectory = null;
		String fileName = null;
		if(!section.equals("") && remark.equals("") && !code.equals("")) {
			if(subsection.equals("")){
				Directory = Directory + document + "/" + section + "/" + block + "/" + document + "-" + section + "-" + block + "c" + code + ".docx";
				fileName = document + "-" + section + "-" + block + "c" + code + ".docx";
	    	}else {
	    		Directory = Directory + document + "/" + section + "/" + subsection + "/" + block + "/" + document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".docx";
	    		fileName = document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".docx";
	    	}				
						
		 	pathDirectory = toFile(Directory);
			
		 	String newRevision = Revision + "Rev" + String.valueOf(stackRevision);
		 	new File(newRevision).mkdirs();		 	
		 	FileUploadController.copy(pathDirectory, new File(newRevision + "/" + fileName), true);
				
			response.setContentType("application/docx");
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
		    stackRevision = stackRevision + 1;
	}
		
		if(!section.equals("") && remark.equals("") && code.equals("")) {
			if(subsection.equals("")){
				fileName = document + "-" + section + "-" + block;
				String newRevision = Revision + "Rev" + String.valueOf(stackRevision);
			 	new File(newRevision).mkdirs();		 	
			 	FileUploadController.copyAllFiles(new File(Directory + document + "/" + section + "/" + block), new File(newRevision), true);		 	
			 	FileUploadController fuc = new FileUploadController();
			 	fuc.compact(document + "/" + section + "/" + block);
			}else {
	    		fileName = document + "-" + section + "-" + subsection + "-" + block;
	    		String newRevision = Revision + "Rev" + String.valueOf(stackRevision);
			 	new File(newRevision).mkdirs();		 	
			 	FileUploadController.copyAllFiles(new File(Directory + document + "/" + section + "/" + subsection + "/" + block), new File(newRevision), true);		 	
			 	FileUploadController fuc = new FileUploadController();
			 	fuc.compact(document + "/" + section + "/" + subsection + "/" + block);
			}				
						
		 	pathDirectory = toFile("..\\Root\\Master\\folder.zip");
			 	
				
			response.setContentType("application/zip");
		    response.addHeader("Content-Disposition", "attachment; filename="+fileName+".zip");
		    try
		    {
		        Files.copy(pathDirectory, response.getOutputStream());
		        response.getOutputStream().flush();
		    } 
		    catch (IOException ex) {
		        ex.printStackTrace();
		    }

		    Directory = "../Root/Master/";
		    stackRevision = stackRevision + 1;
	}
		
		
		if(section.equals("") && subsection.equals("") && block.equals("") && !remark.equals("") && code.equals("")) {
			
		List<Remark> remarks = rr.findAll();
		
		for (Remark file : remarks) {
			//Se for ALL sempre faz download
			if(file.getName().equals("ALL")) {
				downloadFile(file);			
			}
			//Se tiver mais de um remark faz uma tratativa para ler todos os remark e faz download
			if(file.getName().contains(",")) {
				String[] files = file.getName().split(",");
				for(String each:files) {
					if(each.contains(" ")) {
						if(each.equals(" " + remark)) {
							downloadFile(file);
						}					
					}
					else {
						if(each.equals(remark)) {
							downloadFile(file);
						}
					}
				}
			}
			//Se tiver apenas um remark. Verifica se é igual e faz download
			if(file.getName().equals(remark)) {
				downloadFile(file);
			}
			
			
			
		}
				
		}	
			return "redirect:updown";
		}
	
	public void downloadFile(Remark file) {
		
		String fileName = null;
		
		String d = file.getCode().getBlock().getSubsection().getSection().getDocument().getName();
		String s = file.getCode().getBlock().getSubsection().getSection().getName();			
		String sub = file.getCode().getBlock().getSubsection().getName();						
		String b = file.getCode().getBlock().getName();
		String c = file.getCode().getName();
		
		
		
		if(sub.equals("")) {					
			Directory = Directory + d + "/" + s + "/" + b + "/";
			fileName = d + "-" + s + "-" + b + "c" + c + ".docx";
			
	    }else {
	    	Directory = Directory + d + "/" + s + "/" + sub + "/" + b + "/";
	    	fileName = d + "-" + s + "-" + sub + "-" + b + "c" + c + ".docx";
	    }	
		

	 	if(!new File("../Root/FullDelta/FULL/").exists()) {
	 		new File("../Root/FullDelta/FULL/").mkdirs();
	 	}
	 	
	 	try {
			FileUploadController.copy(new File(Directory + fileName), new File( "../Root/FullDelta/FULL/" + fileName), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	
	 	Directory = "../Root/Master/";
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