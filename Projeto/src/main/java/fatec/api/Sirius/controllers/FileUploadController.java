package fatec.api.Sirius.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

	public void compact(String source) throws ZipException, IOException {
		source = uploadDirectory + source;
		ZipUtils appZip = new ZipUtils(source);
		appZip.generateFileList(new File(source));
		appZip.zipIt("..\\Root\\Master\\folder.zip");
	}

	@PostMapping("/UploadFile")
	public String upload(Model model, @RequestParam("files") MultipartFile[] files, MultipartFile excel) {
		
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
					cr.findEquals(nameSection(file.getOriginalFilename())).isEmpty())){
				
				
				if(excel.isEmpty()) {
					System.out.println("nao tem excel");
				}
				else {
					byte[] bytes;
					
						try {
							bytes = excel.getBytes();
							BufferedOutputStream stream=new BufferedOutputStream(new FileOutputStream(new File ("../codelist.xlsx")));
				            stream.write(bytes);
				            stream.close();
				           			          
				            
				            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("../codelist.xlsx"));
				            XSSFSheet sheet = workbook.getSheetAt(0);
					            
					        int linhas = sheet.getLastRowNum();
					        List<Row> row = new ArrayList<>(); 
					        for (int i = 2; i < sheet.getLastRowNum()+1; i++) {
					        	
					        	//System.out.println(normalizarCelula(sheet.getRow(i).getCell(2).getRawValue()) + "  " + nameBlock(file.getOriginalFilename()));
					        	if(normalizarCelula(sheet.getRow(i).getCell(1).toString()).equals(nameSection(file.getOriginalFilename()))) {
					        		if((sheet.getRow(i).getCell(2) == null && nameSubs(file.getOriginalFilename()).equals("")) || (normalizarCelula(sheet.getRow(i).getCell(2).toString()).equals(nameSubs(file.getOriginalFilename())))) {					        
					        			if(normalizarCelula(sheet.getRow(i).getCell(3).toString()).equals(nameBlock(file.getOriginalFilename()))) {
					        				if(normalizarCelula(sheet.getRow(i).getCell(5).toString()).equals(nameCode(file.getOriginalFilename()))) {
					        					if(sheet.getRow(i).getCell(6) != null) {
					        						remark.setName(normalizarCelula(sheet.getRow(i).getCell(6).toString()));
					        					}        					
					        				}
					        			}
					        		}
					        	}			       
					        }
					    
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}			
				}
				
				remarkRepository.save(remark);
				
				

			}

			Boolean aprovarsec = false;
			Boolean aprovarsubs = false;
			Boolean aprovarbloco = false;
			Boolean aprovarnome = false;
			// INICIO DA ANALISE
			// VALIDAR NOME
			String nomedopdf = nameDoc(file.getOriginalFilename());
			if (nomedopdf.length() != 8) {
			} else {

				if (nomedopdf.substring(0, 3).matches(".*[^A-z].")) {
				} else {
					int poshifen = nomedopdf.indexOf("-");
					if (poshifen == 3) {
						String ultnum = nomedopdf.substring(4, 8);
						try {
							Double.parseDouble(ultnum);
							aprovarnome = true;
						} catch (NumberFormatException e) {
						}
					} else {
					}

				}

			}
			// VALIDAR SECTION

			// sr.findEquals(nameSection(file.getOriginalFilename()))
			String nomesec = nameSection(file.getOriginalFilename());
			if (nomesec.equals(null)) {
			} else {
				aprovarsec = true;
			}

			// VALIDAR SUBSECTION
			String nomedasubsection = nameSubs(file.getOriginalFilename());
			if (nomedasubsection.length() > 0) {
				try {
					Double.parseDouble(nomedasubsection);
					aprovarsubs = true;
				} catch (NumberFormatException e) {
				}
			} else {
				aprovarsubs = true;
			}			
			// VALIDAR BLOCO
			String nomedoblock = nameBlock(file.getOriginalFilename());
			try {
				Double.parseDouble(nomedoblock);
				aprovarbloco = true;
			} catch (NumberFormatException e) {
			}
			// FIM DA ANALISE

			Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
			fileNames.append(file.getOriginalFilename());
			try {
				if (aprovarnome && aprovarsec && aprovarsubs && aprovarbloco) {
					Files.write(fileNameAndPath, file.getBytes());
					uploadDirectory = "../Root/Master/";
					concluido = "ok";
					model.addAttribute("concluido", "ok");
				} else {
					concluido = "error";
					model.addAttribute("concluido", "error");
				}
			} catch (IOException e) {
				e.printStackTrace();
				concluido = "error";
				model.addAttribute("concluido", "error");

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
		//System.out.println(doc + " " + sec + " " + subs + " " + block + " " + code);
		if (doc == true || sec == true || subs == true || block == true || code == true) {			
			return true;
		}
		/*=-=-=-=-=-=-=-=-=-=-=-=ARRUMAR AQUI=-=-=-=-=-=-=-=-=-=-=-*/
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
	
	@GetMapping("/copyFiles")
	public String createNewCodelistFromDocument(@RequestParam("source") String source, @RequestParam("newDoc") String newDoc) throws ZipException, IOException {
		copyCodelistFromDB(source, newDoc);
		copyAll(new File("../Root/Master/" + source), new File("../Root/Master/" + newDoc), true);
		return "redirect:documentos";
	}
	
	public static void copy(File origem, File destino, boolean overwrite) throws IOException {

        if (destino.exists() && !overwrite) {
            return;
        }

        FileInputStream source = new FileInputStream(origem);
        FileOutputStream destination = new FileOutputStream(destino);

        FileChannel sourceFileChannel = source.getChannel();
        FileChannel destinationFileChannel = destination.getChannel();

        long size = sourceFileChannel.size();
        sourceFileChannel.transferTo(0, size, destinationFileChannel);

    }


    public static void copyAll(File origem, File destino, String extensao, boolean overwrite) throws IOException, UnsupportedOperationException {
        if (!destino.exists()) {
            destino.mkdir();
        }
        if (!origem.isDirectory()) {
            throw new UnsupportedOperationException("Origem deve ser um diretório");
        }
        if (!destino.isDirectory()) {
            throw new UnsupportedOperationException("Destino deve ser um diretório");
        }
        File[] files = origem.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                copyAll(files[i], new File(destino + "\\" + files[i].getName()), overwrite);
            } else {
                if (files[i].getName().endsWith(extensao)) {
                    copy(files[i], new File(destino + "\\" + files[i].getName()), overwrite);
                }
            }
        }
    }


    public static void copyAll(File origem, File destino, boolean overwrite) throws IOException, UnsupportedOperationException {
        if (!destino.exists()) {
            destino.mkdir();
        }
        if (!origem.isDirectory()) {
            throw new UnsupportedOperationException("Origem deve ser um diretório");
        }
        if (!destino.isDirectory()) {
            throw new UnsupportedOperationException("Destino deve ser um diretório");
        }
        File[] files = origem.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                copyAll(files[i], new File(destino + "\\" + files[i].getName()), overwrite);
            } else {
                copy(files[i], new File(destino + "\\" + files[i].getName()), overwrite);
            }
        }
    }

    public String normalizarCelula(String celula){  
    	String listString[] = celula.split("\\.");
    	if(listString.length == 2) {
    		if(listString[0].equals("0")) {
    			return "00";
    		}
    		if(listString[0].equals("1")) {
    			return "01";
    		}
    		if(listString[0].equals("2")) {
    			return "02";
    		}
    		if(listString[0].equals("3")) {
    			return "03";
    		}
    		if(listString[0].equals("4")) {
    			return "04";
    		}
    		if(listString[0].equals("5")) {
    			return "06";
    		}
    		if(listString[0].equals("6")) {
    			return "06";
    		}
    		if(listString[0].equals("7")) {
    			return "07";
    		}
    		if(listString[0].equals("8")) {
    			return "08";
    		}
    		if(listString[0].equals("9")) {
    			return "09";
    		}
    		return listString[0];   	
    	}else {
    		return celula;
    	}
    	
    }
}
