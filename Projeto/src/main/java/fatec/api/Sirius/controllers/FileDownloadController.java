package fatec.api.Sirius.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
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
	public String DownloadFile(String document, String section, String subsection, String block, String code,
			String remark, HttpServletRequest request, HttpServletResponse response) throws Exception {

		File pathDirectory = null;
		String fileName = null;
		//download de um docx
		if (!section.equals("") && remark.equals("") && !code.equals("")) {
			if (subsection.equals("")) {
				Directory = Directory + document + "/" + section + "/" + block + "/" + document + "-" + section + "-"
						+ block + "c" + code + ".docx";
				fileName = document + "-" + section + "-" + block + "c" + code + ".docx";
			} else {
				Directory = Directory + document + "/" + section + "/" + subsection + "/" + block + "/" + document + "-"
						+ section + "-" + subsection + "-" + block + "c" + code + ".docx";
				fileName = document + "-" + section + "-" + subsection + "-" + block + "c" + code + ".docx";
			}

			pathDirectory = toFile(Directory);

			String newRevision = Revision + "Rev" + String.valueOf(stackRevision);
			new File(newRevision).mkdirs();
			FileUploadController.copy(pathDirectory, new File(newRevision + "/" + fileName), true);

			response.setContentType("application/docx");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			try {
				Files.copy(pathDirectory, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		//download de uma bloco
		if (!section.equals("") && remark.equals("") && code.equals("")) {
			if (subsection.equals("")) {
				fileName = document + "-" + section + "-" + block;
				String newRevision = Revision + "Rev" + String.valueOf(stackRevision);
				new File(newRevision).mkdirs();
				FileUploadController.copyAllFiles(new File(Directory + document + "/" + section + "/" + block),
						new File(newRevision), true);
				FileUploadController fuc = new FileUploadController();
				fuc.compact(document + "/" + section + "/" + block);
			} else {
				fileName = document + "-" + section + "-" + subsection + "-" + block;
				String newRevision = Revision + "Rev" + String.valueOf(stackRevision);
				new File(newRevision).mkdirs();
				FileUploadController.copyAllFiles(
						new File(Directory + document + "/" + section + "/" + subsection + "/" + block),
						new File(newRevision), true);
				FileUploadController fuc = new FileUploadController();
				fuc.compact(document + "/" + section + "/" + subsection + "/" + block);
			}

			pathDirectory = toFile("..\\Root\\Master\\folder.zip");

			response.setContentType("application/zip");
			response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".zip");
			try {
				Files.copy(pathDirectory, response.getOutputStream());
				response.getOutputStream().flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (section.equals("") && subsection.equals("") && block.equals("") && !remark.equals("") && code.equals("")) {

			List<Remark> remarks = rr.findAll();

			if (!new File("../Root/FullDelta/DELTA/").exists()) {
				new File("../Root/FullDelta/DELTA/").mkdirs();
			}

			if (new File(Revision + "/Rev" + (stackRevision - 1)).exists()) {
				File[] files = new File(Revision + "/Rev" + (stackRevision - 1)).listFiles();

				for (File each : files) {
					try {
						FileUploadController.copy(each, new File("../Root/FullDelta/DELTA/" + each.getName()), true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			for (Remark file : remarks) {
				// Se for ALL sempre faz download
				if (file.getName().equals("ALL")) {
					downloadFile(file);
				}
				// Se tiver mais de um remark faz uma tratativa para ler todos os remark e faz
				// download
				if (file.getName().contains(",")) {
					String[] files = file.getName().split(",");
					for (String each : files) {
						if (each.contains(" ")) {
							if (each.equals(" " + remark)) {
								downloadFile(file);
							}
						} else {
							if (each.equals(remark)) {
								downloadFile(file);
							}
						}
					}
				}
				// Se tiver apenas um remark. Verifica se é igual e faz download
				if (file.getName().equals(remark)) {
					downloadFile(file);
				}
			}	
		//Junção do full
		String[] lista = new File("../Root/FullDelta/FULL").list();
		PDFMergerUtility ut = new PDFMergerUtility();
		for (int i = 0; i < lista.length; i++) {
			System.out.println("Lista: " + lista[i]);
			try {
				String nomedoc = null;
				nomedoc = lista[i];
				InputStream docxInputStream = new FileInputStream("../Root/FullDelta/FULL/" + nomedoc);
				if(!new File("../Root/FullDelta/FULL/convPdf/").exists()) {
					new File("../Root/FullDelta/FULL/convPdf/").mkdirs();
				}
				OutputStream outputStream = new FileOutputStream(
						"../Root/FullDelta/FULL/convPdf/" + nomedoc.substring(0, nomedoc.length() - 5) + ".pdf");
				IConverter converter = LocalConverter.builder().build();
				converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF)
						.execute();
				ut.addSource("../Root/FullDelta/FULL/convPdf/" + nomedoc.substring(0, nomedoc.length() - 5) + ".pdf");
				System.out.println(ut);
				outputStream.close();
				System.out.println("success");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("saiu do loop");
		if(!new File("../Root/FullDelta/FULLDELTA/").exists()) {
			new File("../Root/FullDelta/FULLDELTA/").mkdirs();
		}
		ut.setDestinationFileName("../Root/FullDelta/FULLDELTA/FULL.pdf");
		ut.mergeDocuments();
		
		
		//Junção do delta
		String[] listadelta = new File("../Root/FullDelta/DELTA").list();
		PDFMergerUtility utdelta = new PDFMergerUtility();
		for (int i = 0; i < listadelta.length; i++) {
			System.out.println("Lista: " + listadelta[i]);
			try {
				String nomedoc = null;
				nomedoc = listadelta[i];
				InputStream docxInputStream = new FileInputStream("../Root/FullDelta/DELTA/" + nomedoc);
				if(!new File("../Root/FullDelta/DELTA/convPdf/").exists()) {
					new File("../Root/FullDelta/DELTA/convPdf/").mkdirs();
				}
				OutputStream outputStream = new FileOutputStream(
						"../Root/FullDelta/DELTA/convPdf/" + nomedoc.substring(0, nomedoc.length() - 5) + ".pdf");
				IConverter converter = LocalConverter.builder().build();
				converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF)
						.execute();
				utdelta.addSource("../Root/FullDelta/DELTA/convPdf/" + nomedoc.substring(0, nomedoc.length() - 5) + ".pdf");
				System.out.println(utdelta);
				outputStream.close();
				System.out.println("success");

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		System.out.println("saiu do loop delta");
		if(!new File("../Root/FullDelta/FULLDELTA/").exists()) {
			new File("../Root/FullDelta/FULLDELTA/").mkdirs();
		}
		utdelta.setDestinationFileName("../Root/FullDelta/FULLDELTA/DELTA.pdf");
		utdelta.mergeDocuments();
		
		generateLep();
		FileUploadController fuc = new FileUploadController();
		fuc.compact("../FullDelta/FULLDELTA");
		
		

		pathDirectory = toFile("..\\Root\\Master\\folder.zip");

		response.setContentType("application/zip");
		response.addHeader("Content-Disposition", "attachment; filename=" + document + remark + ".zip");
			
		try {
			Files.copy(pathDirectory, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		}
		Directory = "../Root/Master/";
		stackRevision = stackRevision + 1;
		
		
		return "redirect:updown";
	}

	public void downloadFile(Remark file) {

		String fileName = null;

		String d = file.getCode().getBlock().getSubsection().getSection().getDocument().getName();
		String s = file.getCode().getBlock().getSubsection().getSection().getName();
		String sub = file.getCode().getBlock().getSubsection().getName();
		String b = file.getCode().getBlock().getName();
		String c = file.getCode().getName();

		// Lê todos os arquivos que estão na REV
		if (new File(Revision + "/Rev" + (stackRevision - 1)).exists()) {
			File[] files = new File(Revision + "/Rev" + (stackRevision - 1)).listFiles();

			if (sub.equals("")) {
				fileName = d + "-" + s + "-" + b + "c" + c + ".docx";
			} else {
				fileName = d + "-" + s + "-" + sub + "-" + b + "c" + c + ".docx";
			}

			// Verifica se algum deles é o mesmo que o arquivo que estou buscando
			for (File each : files) {

				if (each.getName().equals(fileName)) {
					try {
						FileUploadController.copy(new File(Revision + "/Rev" + (stackRevision - 1) + "/" + fileName),
								new File("../Root/FullDelta/FULL/" + fileName), true);
						System.out.println("pegou da rev" + each.getName());
						return;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// Senão ele pegará da MASTER
		if (sub.equals("")) {
			Directory = Directory + d + "/" + s + "/" + b + "/";
			fileName = d + "-" + s + "-" + b + "c" + c + ".docx";
		} else {
			Directory = Directory + d + "/" + s + "/" + sub + "/" + b + "/";
			fileName = d + "-" + s + "-" + sub + "-" + b + "c" + c + ".docx";
		}

		if (!new File("../Root/FullDelta/FULL/").exists()) {
			new File("../Root/FullDelta/FULL/").mkdirs();
		}

		try {
			FileUploadController.copy(new File(Directory + fileName), new File("../Root/FullDelta/FULL/" + fileName),
					true);
			System.out.println("pegou da master" + fileName);
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
	
	
	public void generateLep() throws IOException {
		OutputStream os = new FileOutputStream("../Root/FullDelta/FULLDELTA/Lep.txt");
		Writer wr = new OutputStreamWriter(os);
		BufferedWriter br = new BufferedWriter(wr);
		File listaPathRev = new File("../Root/Rev");
		Map<String, String> map = new HashMap<String, String>();
		
		if(!listaPathRev.exists()) {
			br.close();
			return;
		}
		File[] listaRev = listaPathRev.listFiles();
		for(int i = listaRev.length; i != 0; i--) {
			for(String file : new File("../Root/Rev/Rev" + i).list()) {
				if(!map.containsKey(file)) {
				map.put(file, Integer.toString(i));
				}
			}
		}
		String[] listFull = new File("../Root/FullDelta/FULL").list();
		for(String each: listFull) {
			if(!each.equals("convPdf")) {
				int repete = quantPagPdf(new File("../Root/FullDelta/FULL/convPdf/" + each.substring(0, each.length() - 5) + ".pdf"));
				int i = 1;
				while(i <= repete) {
					if(map.containsKey(each)) {
						if(haveSubs(each)) {
							br.write(each + "   Pagina " + i + "   Revisão " + map.get(each));
							br.newLine();
						}else {
						br.write(each + "      Pagina " + i + "   Revisão " + map.get(each));
						br.newLine();
						}
						
					}else {
						if(haveSubs(each)) {
							br.write(each + "   Pagina " + i + "   Revisão Original");
							br.newLine();
						} else {
						br.write(each + "      Pagina " + i + "   Revisão Original");
						br.newLine();
						}
					}
					i++;
				}
				br.write("----------------------------------------------------");
				br.newLine();
			}
		}
		br.close();
		//System.out.println(map);
	}

	public File toFile(String string) {
		File nameFile = new File(string);
		return nameFile;
	}
	
	public boolean haveSubs(String name) {

		name = name.replace("-", " ");
		String[] words = name.split(" ");

		if (words.length == 5) {
			return true;
		}
		return false;
	}
	
	public int quantPagPdf(File path) throws IOException {
		PDDocument pdfDocument = null;
		pdfDocument = PDDocument.load(path);
		int i = pdfDocument.getNumberOfPages();
		pdfDocument.close();
		return i;
	}
}