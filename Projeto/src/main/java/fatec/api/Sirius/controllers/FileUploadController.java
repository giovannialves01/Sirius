package fatec.api.Sirius.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadController {
	
	public static String uploadDirectory = "../Root";
	
	@PostMapping("/UploadFiles")
	public String upload(Model model,@RequestParam("Files") MultipartFile[] files) {	
		System.out.println("Opa");
		/*StringBuilder fileNames = new StringBuilder();
		for(MultipartFile file:files) {
			Path fileNameAndPath = Paths.get(uploadDirectory,file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("msg", "Successfully uploaded files " + fileNames.toString());
		*/return "updown";
	}
}
