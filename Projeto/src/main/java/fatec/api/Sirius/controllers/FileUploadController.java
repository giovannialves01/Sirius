package fatec.api.Sirius.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {
	
	public static String uploadDirectory = "../Root";
	

	@PostMapping("/UploadFile")
	public String upload(Model model,@RequestParam("files") MultipartFile[] files) {	
		StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file:files) {
			Path fileNameAndPath = Paths.get(uploadDirectory,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNames+"   "+fileNameAndPath+"   "+file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "updown";
	}
}
