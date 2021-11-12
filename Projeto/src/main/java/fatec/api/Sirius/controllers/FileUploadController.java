package fatec.api.Sirius.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import fatec.api.Sirius.model.Block;
import fatec.api.Sirius.model.Code;
import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.model.Remark;
import fatec.api.Sirius.model.Section;
import fatec.api.Sirius.model.Subsection;
import fatec.api.Sirius.repository.BlockRepository;
import fatec.api.Sirius.repository.CodeRepository;
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
	DocumentRepository dr;
	
	@Autowired
	CodeRepository cr;

	public static String uploadDirectory = "../Root/Master/";

	RemarkControllers rc = new RemarkControllers();

	@GetMapping("/CompactFile")
	public String compact(@RequestParam("source") String source, @RequestParam("newDoc") String newDoc) throws ZipException, IOException {
		copyCodelistFromDB(source, newDoc);
		source = uploadDirectory + source;
		ZipUtils appZip = new ZipUtils(source);
		appZip.generateFileList(new File(source));
		appZip.zipIt("..\\Root\\Master\\folder.zip");
		extrairZip(new File("..\\Root\\Master\\folder.zip"), new File("..\\Root\\Master\\" + newDoc));
		return "documents";
	}

	@PostMapping("/UploadFile")
	public String upload(Model model, @RequestParam("files") MultipartFile[] files) {
		StringBuilder fileNames = new StringBuilder();

		String concluido = null;
		for (MultipartFile file : files) {

			uploadDirectory = uploadDirectory + organizePath(file.getOriginalFilename());
			File CreateFile = toFile(uploadDirectory);
			CreateFile.mkdirs();

			Remark remark = new Remark();

			remark.setCode(new Code());
			remark.getCode().setBlock(new Block());
			remark.getCode().getBlock().setSubsection(new Subsection());
			remark.getCode().getBlock().getSubsection().setSection(new Section());
			remark.getCode().getBlock().getSubsection().getSection().setDocument(new Document());

			remark.getCode().getBlock().getSubsection().getSection().getDocument().setName(nameDoc(file.getOriginalFilename()));

			remark.getCode().getBlock().getSubsection().getSection()
					.setDocument(remark.getCode().getBlock().getSubsection().getSection().getDocument());
			remark.getCode().getBlock().getSubsection().getSection().setName(nameSection(file.getOriginalFilename()));

			remark.getCode().getBlock().getSubsection().setSection(remark.getCode().getBlock().getSubsection().getSection());
			remark.getCode().getBlock().getSubsection().setName(nameSubs(file.getOriginalFilename()));

			remark.getCode().getBlock().setSubsection(remark.getCode().getBlock().getSubsection());
			remark.getCode().getBlock().setName(nameBlock(file.getOriginalFilename()));

			remark.getCode().setBlock((remark.getCode().getBlock()));
			
			remark.getCode().setName(nameCode(file.getOriginalFilename()));
			remark.setCode(remark.getCode());
								
			remark.setName("");

			List<Document> l = dr.findDocEquals(nameDoc(file.getOriginalFilename()));

			if (check(dr.findDocEquals(nameDoc(file.getOriginalFilename())).isEmpty(),
					blo.findEquals(nameBlock(file.getOriginalFilename())).isEmpty(),
					sub.findEquals(nameSubs(file.getOriginalFilename())).isEmpty(),
					sr.findEquals(nameSection(file.getOriginalFilename())).isEmpty(),
					cr.findEquals(nameSection(file.getOriginalFilename())).isEmpty()))
					 {
				remarkRepository.save(remark);
			}

			Boolean aprovarsec = false;
			Boolean aprovarsubs = false;
			Boolean aprovarbloco = false;
			Boolean aprovarnome = false;
			// INICIO DA ANALISE
			System.out.println("ANALISE DO DOCUMENTO");
			System.out.println("--------------------------------------------");
			// VALIDAR NOME
			String nomedopdf = nameDoc(file.getOriginalFilename());
			System.out.println("Nome do Documento: " + nomedopdf);
			if (nomedopdf.length() != 8) {
				System.out.println("=>ERRO! Tamanho do Documento não é 8: " + nomedopdf.length());
			} else {

				if (nomedopdf.substring(0, 3).matches(".*[^A-z].")) {
					System.out
							.println("=>ERRO! 3 Primeiros caracteres não são só letras: " + nomedopdf.substring(0, 3));
				} else {
					int poshifen = nomedopdf.indexOf("-");
					System.out.println("Posição do hifen:" + poshifen);
					if (poshifen == 3) {
						String ultnum = nomedopdf.substring(4, 8);
						try {
							Double.parseDouble(ultnum);
							System.out.println("=>OK! 3 Letras + Hífen + 4 Números no fim: " + nomedopdf);
							aprovarnome = true;
						} catch (NumberFormatException e) {
							System.out.println("=>ERRO! 4 Ultimos caracteres não são só numeros: " + ultnum);
						}
					} else {
						System.out.println("=>ERRO! Hífen na posição errada ou não encontrado: " + poshifen);
					}

				}

			}
			System.out.println("--------------------------------------------");
			// VALIDAR SECTION

			// sr.findEquals(nameSection(file.getOriginalFilename()))
			String nomesec = nameSection(file.getOriginalFilename());
			if (nomesec.equals(null)) {
				System.out.println("=>ERRO! Seção não pode ser vazia");
			} else {
				System.out.println("=>OK! Seção não esta vazia");
				aprovarsec = true;
			}
			System.out.println("Seção: " + nameSection(file.getOriginalFilename()));
			System.out.println("--------------------------------------------");

			// VALIDAR SUBSECTION
			String nomedasubsection = nameSubs(file.getOriginalFilename());
			System.out.println("Sub-Seção: " + nomedasubsection);
			if (nomedasubsection.length() > 0) {
				try {
					Double.parseDouble(nomedasubsection);
					System.out.println("=>OK! Subseção consiste apenas de numeros: " + nomedasubsection);
					aprovarsubs = true;
				} catch (NumberFormatException e) {
					System.out.println("=>ERRO! Subseção não consiste apenas de numeros: " + nomedasubsection);
				}
			} else {
				System.out.println("=>OK! Subseção pode ser vazia");
				System.out.println(nomedasubsection.length());
				aprovarsubs = true;
			}
			System.out.println("--------------------------------------------");
			// VALIDAR BLOCO
			String nomedoblock = nameBlock(file.getOriginalFilename());
			System.out.println("Bloco: " + nomedoblock);
			try {
				Double.parseDouble(nomedoblock);
				System.out.println("=>OK! Bloco consiste apenas de números: " + nomedoblock);
				aprovarbloco = true;
			} catch (NumberFormatException e) {
				System.out.println("=>ERRO! Bloco não consiste apenas de numeros: " + nomedoblock);
			}
			System.out.println("--------------------------------------------");
			System.out.println("Aprovado: " + aprovarnome + "/" + aprovarsec + "/" + aprovarsubs + "/" + aprovarbloco);
			// FIM DA ANALISE

			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				if (aprovarnome && aprovarsec && aprovarsubs && aprovarbloco) {
					Files.write(fileNameAndPath, file.getBytes());
					uploadDirectory = "../Root/Master/";
					concluido = "ok";
					model.addAttribute("concluido", "ok");
					System.out.println("concluido=" + concluido);
				} else {
					concluido = "error";
					model.addAttribute("concluido", "error");
					System.out.println("concluido=" + concluido);
				}
			} catch (IOException e) {
				e.printStackTrace();
				concluido = "error";
				model.addAttribute("concluido", "error");
				System.out.println("concluido=" + concluido);

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
		String[] partes = path.split("-");
		return partes[2];
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

	public String nameCode(String path) {
		String achadoCode = null;
		String regexCode = "c[^\\s\\D]+";
		Pattern parteCode = Pattern.compile(regexCode);
		Matcher matcherCode = parteCode.matcher(path);
		while (matcherCode.find()) {
			achadoCode = matcherCode.group().substring(1, matcherCode.group().length());
			return achadoCode;
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

	public boolean check(boolean doc, boolean sec, boolean subs, boolean block, boolean code) {
		System.out.println(doc + " " + sec + " " + subs + " " + block + " " + code);
		if (doc == true || sec == true || subs == true || block == true || code == true) {
			return true;
		}
		return false;
	}
	
	public void extrairZip( File arquivoZip, File diretorio ) throws ZipException, IOException {
		ZipFile zip = null;
		File arquivo = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[1024];
		try {

		    if( !diretorio.exists() ) {
		        diretorio.mkdirs();
		     }
		     if( !diretorio.exists() || !diretorio.isDirectory() ) {
		        throw new IOException("Informe um diretório válido");
		     }
		     zip = new ZipFile( arquivoZip );
		     Enumeration e = zip.entries();
		     while( e.hasMoreElements() ) {
		    	 ZipEntry entrada = (ZipEntry) e.nextElement();
		    	 arquivo = new File( diretorio, entrada.getName() );
		    	 if( entrada.isDirectory() && !arquivo.exists() ) {
		    		 arquivo.mkdirs();
		    		 continue;
		    	 }
		    	 if( !arquivo.getParentFile().exists() ) {
		    		 arquivo.getParentFile().mkdirs();
		    	 }
		    	 try {
		    		 is = zip.getInputStream( entrada );
		    		 os = new FileOutputStream( arquivo );
		    		 int bytesLidos = 0;
		    		 if( is == null ) {
		    			 throw new ZipException("Erro ao ler a entrada do zip: "+entrada.getName());
		    		 }
		    		 while( (bytesLidos = is.read( buffer )) > 0 ) {
		             os.write( buffer, 0, bytesLidos );
		    		 }
		         } finally {
		         if( is != null ) {
		             try {
		               is.close();
		             } catch( Exception ex ) {}
		         }
		         if( os != null ) {
		        	 try {
		               os.close();
		             } catch( Exception ex ) {}
		         }
		         }
		       }
		     } finally {
		       if( zip != null ) {
		         try {
		           zip.close();
		         } catch( Exception e ) {}
		       }
		     }
		   }
	
public void copyCodelistFromDB(String nameDoc, String nameNewDoc) {
		
		List<Document> document = dr.findDocEquals(nameDoc);
		for(int i = 0 ; i < document.size(); i++){
				
				int doc = document.get(i).getId();
				Section sec = sr.findSectionByDocId(doc);
				Subsection subs = sub.findSubsectionBySectionId(sec.getId());
				Block block = blo.findBlockBySubsectionId(subs.getId());
				Code code = cr.findCodeByBlockId(block.getId());
				Remark remark = remarkRepository.findRemarkByCodeId(code.getId());
				
				
				Remark newCodelist = new Remark();
				newCodelist.setCode(new Code());
				newCodelist.getCode().setBlock(new Block());
				newCodelist.getCode().getBlock().setSubsection(new Subsection());
				newCodelist.getCode().getBlock().getSubsection().setSection(new Section());
				newCodelist.getCode().getBlock().getSubsection().getSection().setDocument(new Document());
				
				newCodelist.getCode().getBlock().getSubsection().getSection().getDocument().setName(nameNewDoc);
				newCodelist.getCode().getBlock().getSubsection().getSection()
					.setDocument(newCodelist.getCode().getBlock().getSubsection().getSection().getDocument());
				
				newCodelist.getCode().getBlock().getSubsection().getSection().setName(sec.getName());
				newCodelist.getCode().getBlock().getSubsection().setSection(newCodelist.getCode().getBlock()
						.getSubsection().getSection());
				
				newCodelist.getCode().getBlock().getSubsection().setName(subs.getName());
				newCodelist.getCode().getBlock().setSubsection(newCodelist.getCode().getBlock().getSubsection());
				
				newCodelist.getCode().getBlock().setName(block.getName());
				newCodelist.getCode().setBlock(newCodelist.getCode().getBlock());
				
				newCodelist.getCode().setName(code.getName());	
				newCodelist.setCode(newCodelist.getCode());
																
				newCodelist.setName(remark.getName());
				
				System.out.println(i);
				remarkRepository.save(newCodelist);
				
				
			}	
     
	}

}
