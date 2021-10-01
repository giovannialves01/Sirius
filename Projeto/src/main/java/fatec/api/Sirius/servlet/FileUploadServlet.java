package fatec.api.Sirius.servlet;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.http.Part;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

@WebServlet(name="FileUploadServlet",urlPatterns= {"/fileuploadservlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024
		* 100)
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Part filePart=request.getPart("file");
		String fileName=filePart.getSubmittedFileName();
		for (Part part : request.getParts()){
			part.write("D:\\Desktop\\fatec\\PI\\Sirius\\Root"+fileName);
		}
		response.getWriter().print("Arquivo upado com sucesso.");
	}
}
