package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.io.Files;

@Controller
public class FileDownloadController {
	
	BlockControllers bc = new BlockControllers();
	
	public static String Directory = "../Root/Master/";
	
	@GetMapping("/DownloadFile")
	public void DownloadFile(String document, String section, String subsection, String block, String code, String remark,HttpServletRequest request, HttpServletResponse response ) throws Exception {		
		
		String fileName = null;
		
		if(subsection.equals("")){
			Directory = Directory + document + "/" + section + "/" + block + "/" + document + "-" + section + "-" + block + "c" + code + ".pdf";
			fileName = document + "-" + section + "-" + block + "c" + code + ".pdf";
    	}else {
    		Directory = Directory + document + "/" + section + "/" + subsection + "/" + block + "/" + document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".pdf";
    		fileName = document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".pdf";
    	}				
		
		File pathDirectory = bc.toFile(Directory);		
		
		response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename="+fileName);
        try
        {
            Files.copy(pathDirectory, response.getOutputStream());
            response.getOutputStream().flush();
            System.out.println("normal");
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }

		Directory = "../Root/Master/";
		
		
	
	}
}
