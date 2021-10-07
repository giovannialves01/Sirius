package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileDownloadController {
	
	BlockControllers bc = new BlockControllers();
	
	public static String downloadDirectory = "/";
	public static String Directory = "../Root/Master/";
	
	@GetMapping("/DownloadFile")
	public String DownloadFile(String document, String section, String subsection, String block, String code, String remark) throws Exception {		
		
		if(subsection.equals("")){
			Directory = Directory + document + "/" + section + "/" + block + "/" + document + "-" + section + "-" + block + "c" + code + ".pdf";
			downloadDirectory = downloadDirectory + document + "-" + section + "-" + block + "c" + code + ".pdf";
    	}else {
    		Directory = Directory + document + "/" + section + "/" + subsection + "/" + block + "/" + document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".pdf";
    		downloadDirectory = downloadDirectory + document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".pdf";
    	}
		
		
		System.out.println(Directory);
		System.out.println(downloadDirectory);
		File pathDownload = bc.toFile(downloadDirectory);
		File pathDirectory = bc.toFile(Directory);		
		
		try {
			InputStream in = new FileInputStream(pathDirectory);	
			OutputStream out = new FileOutputStream(pathDownload);
			byte[] buffer = new byte[1024];
			int count = 0;
			while((count = in.read(buffer)) > 0) {
				out.write(buffer,0,count);
			}
			in.close();
			out.close();
			
			downloadDirectory = "/";
			Directory = "../Root/Master/";
			
		} catch (Exception e) {
			return "updown";
		}
		
		downloadDirectory = "/";
		Directory = "../Root/Master/";
		
		return "updown";
	
	}
}
