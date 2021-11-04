package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
import fatec.api.Sirius.repository.BlockRepository;
import fatec.api.Sirius.repository.DocumentRepository;
import fatec.api.Sirius.repository.RemarkRepository;
import fatec.api.Sirius.repository.SectionRepository;
import fatec.api.Sirius.repository.SubsectionRepository;

@Controller
public class FileUploadController {

	@Autowired
	RemarkRepository remarkRepository;

	@Autowired
	SectionRepository sr;

	@Autowired
	SubsectionRepository sub;

	@Autowired
	BlockRepository blo;

	@Autowired
	DocumentRepository doc;

	public static String uploadDirectory = "../Root/Master/";

	RemarkControllers rc = new RemarkControllers();

	@PostMapping("/UploadFile")
	public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
		StringBuilder fileNames = new StringBuilder();

		String concluido = null;
		for (MultipartFile file : files) {

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

			remark.getBlock().getSubsection().getSection()
					.setDocument(remark.getBlock().getSubsection().getSection().getDocument());
			remark.getBlock().getSubsection().getSection().setName(nameSection(file.getOriginalFilename()));

			remark.getBlock().getSubsection().setSection(remark.getBlock().getSubsection().getSection());
			remark.getBlock().getSubsection().setName(nameSubs(file.getOriginalFilename()));

			remark.getBlock().setSubsection(remark.getBlock().getSubsection());
			remark.getBlock().setName(nameBlock(file.getOriginalFilename()));

			remark.setBlock((remark.getBlock()));
			remark.setName("");

			List<Document> l = doc.findDocEquals(nameDoc(file.getOriginalFilename()));

			if (check(doc.findDocEquals(nameDoc(file.getOriginalFilename())).isEmpty(),
					blo.findEquals(nameBlock(file.getOriginalFilename())).isEmpty(),
					sub.findEquals(nameSubs(file.getOriginalFilename())).isEmpty(),
					sr.findEquals(nameSection(file.getOriginalFilename())).isEmpty())) {
				remarkRepository.save(remark);
			}

			// INICIO DA ANALISE
			System.out.println("ANALISE DO DOCUMENTO");
			System.out.println("--------------------------------------------");
			// VALIDAR NOME
			String nomedopdf = nameDoc(file.getOriginalFilename());
			System.out.println("Nome do Documento: " + nomedopdf);
			if (nomedopdf.length() != 8) {
				System.out.println("=>ERRO! Tamanho do Documento não é 8: "+nomedopdf.length());
			} else {

				if (nomedopdf.substring(0, 3).matches("[A-Z]*")) {
					System.out.println("=>ERRO! 3 Primeiros caracteres não são só letras: " + nomedopdf.substring(0, 3));
				} else {
					String ultnum=nomedopdf.substring(4, 8);
					try {
						Double.parseDouble(ultnum);
						System.out.println("=>OK! 8 Caracteres + 3 Letras no início + 4 Números no fim: "+nomedopdf);
					} catch (NumberFormatException e) {
						System.out.println("=>ERRO! 4 Ultimos caracteres não são só numeros: "+ultnum);
					}
				}

			}
			System.out.println("--------------------------------------------");
			// VALIDAR SECTION
			Boolean aprovar=false;
			String nomedasection = nameSection(file.getOriginalFilename());
			System.out.println("Seção: " + nomedasection);
			int count = 0;
			for (int i = 0; i < nomedasection.length(); i++) {
				if (nomedasection.charAt(i) != ' ')
					count++;
			}
			System.out.println("Tamanho da seção:" + count);
			if (count >= 2 && count <= 4) {
				System.out.println("=>OK! Tamanho está entre 2 e 4: "+count);
				aprovar=true;
			} else {
				System.out.println("=>ERRO! Tamanho não esta entre 2 e 4: " +count);
			}
			System.out.println("--------------------------------------------");
			// VALIDAR SUBSECTION
			String nomedasubsection = nameSubs(file.getOriginalFilename());
			System.out.println("Sub-Seção: " + nomedasubsection);
			if (nomedasubsection.length() > 0) {
				try {
					Double.parseDouble(nomedasubsection);
					System.out.println("=>OK! Subseção consiste apenas de numeros: "+nomedasubsection);
					aprovar=true;
				} catch (NumberFormatException e) {
					System.out.println("=>ERRO! Subseção não consiste apenas de numeros: "+nomedasubsection);
				}
			} else {
				System.out.println("=>OK! Subseção pode ser vazia");
				System.out.println(nomedasubsection.length());
				aprovar=true;
			}
			System.out.println("--------------------------------------------");
			// VALIDAR BLOCO
			String nomedoblock = nameBlock(file.getOriginalFilename());
			System.out.println("Bloco: " + nomedoblock);
			try {
				Double.parseDouble(nomedoblock);
				System.out.println("=>OK! Bloco consiste apenas de números: "+nomedoblock);
				aprovar=true;
			} catch (NumberFormatException e) {
				System.out.println("=>ERRO! Bloco não consiste apenas de numeros: "+nomedoblock);
			}
			System.out.println("--------------------------------------------");
			System.out.println("Aprovado: "+aprovar);
			// FIM DA ANALISE
			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				Files.write(fileNameAndPath, file.getBytes());
				uploadDirectory = "../Root/Master/";
				concluido = "ok";
				model.addAttribute("concluido", "ok");

				System.out.println(concluido);
			} catch (IOException e) {
				e.printStackTrace();
				concluido = "error";

			}
		}
		return "updown.html";
	}

	public String organizePath(String path) {
		String namePath = nameBuilder(nameDoc(path), nameSection(path), nameSubs(path), nameBlock(path));
		return namePath;
	}

	// Pega o doc ABC(SÓ LETRAS)-1234(SÓ NUMEROS)
	public String nameDoc(String path) {
		String achadoDoc = null;
		String regexDoc = "\\D+-\\d+-";
		Pattern parteDoc = Pattern.compile(regexDoc);
		Matcher matcherDoc = parteDoc.matcher(path);
		while (matcherDoc.find()) {
			achadoDoc = matcherDoc.group().substring(0, matcherDoc.group().length() - 1);
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
		while (matcherSecao.find()) {
			achadoSecao = matcherSecao.group();

			parteSecao = Pattern.compile(regexSecao2);
			matcherSecao = parteSecao.matcher(achadoSecao);

			while (matcherSecao.find()) {
				achadoSecao = matcherSecao.group();
				achadoSecao = matcherSecao.group().substring(0, matcherSecao.group().length() - 1);
				return achadoSecao;
			}

		}
		return "";
	}

	public String nameSubs(String path) {
		String achadoSubs = null;
		if (haveSubs(path)) {

			String regexSubs1 = "-\\w+-\\d+c";
			String regexSubs2 = "^-\\w+";
			Pattern parteSubs = Pattern.compile(regexSubs1);
			Matcher matcherSubs = parteSubs.matcher(path);
			while (matcherSubs.find()) {
				achadoSubs = matcherSubs.group();

				parteSubs = Pattern.compile(regexSubs2);
				matcherSubs = parteSubs.matcher(achadoSubs);

				while (matcherSubs.find()) {
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
		while (matcherBlock.find()) {
			achadoBlock = matcherBlock.group().substring(1, matcherBlock.group().length() - 1);
			return achadoBlock;
		}
		return "";
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

	public boolean haveSubs(String name) {

		name = name.replace("-", " ");
		String[] words = name.split(" ");

		if (words.length == 5) {
			return true;
		}
		return false;
	}

	public File toFile(String string) {
		File nameFile = new File(string);
		return nameFile;
	}

	public boolean check(boolean doc, boolean sec, boolean subs, boolean block) {
		System.out.println(doc + " " + sec + " " + subs + " " + block);
		if (doc == true || sec == true || subs == true || block == true) {
			return true;
		}
		return false;
	}

}
