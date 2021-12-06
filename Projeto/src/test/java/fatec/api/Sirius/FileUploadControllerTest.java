package fatec.api.Sirius;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fatec.api.Sirius.controllers.FileUploadController;

class FileUploadControllerTest {

	FileUploadController fuc = new FileUploadController();
	
	//Teste 3: Esse teste verifica se já existe uma linha no codelist, se 1 entrada for true, o retorno deve ser true,
	//se todos forem false o retorno deve ser false
	@Test
	void testCheck() {
	
		boolean resultadoTrue1 = fuc.check(true, false, false, false, false);
		boolean resultadoTrue2 = fuc.check(false, false, true, false, false);
		boolean resultadoTrue3 = fuc.check(true, false, true, false, false);
		boolean resultadoTrue4 = fuc.check(true, false, false, false, true);
		boolean resultadoTrue5 = fuc.check(true, true, true, true, true);

		boolean resultadoFalse = fuc.check(false, false, false, false, false); //quando todos for false, não sexiste no codelist

		boolean esperadoTrue = true;
		boolean esperadoFalse = false;
		
		Assert.assertEquals(resultadoTrue1, esperadoTrue);
		Assert.assertEquals(resultadoTrue2, esperadoTrue);
		Assert.assertEquals(resultadoTrue3, esperadoTrue);
		Assert.assertEquals(resultadoTrue4, esperadoTrue);
		Assert.assertEquals(resultadoTrue5, esperadoTrue);
		Assert.assertEquals(resultadoFalse, esperadoFalse);

	}
	//Teste 4: Converte o nome de todos os arquivos de uma pasta e subpastas para um novo nome. 
	//Para o teste, deve-se dar a localização da pasta para funcionar.
	@Test
	void testChangeName() throws IOException {
				
		System.out.println("ABC-1234 existe? " + new File("../Root/Master/ABC-1234-00-00c50.docx").exists());
		System.out.println("XYZ-6789 existe? " + new File("../Root/Master/XYZ-6789-00-00c50.docx").exists());
		
		fuc.changeName("../Root/Master/" , "XYZ-6789");
		
		/*String[] lista = new File("../Root/Master/").list();
		
		String resultado = lista[0];
		String esperado = "XYZ-6789-00-00c50.docx";
		
		Assert.assertEquals(resultado, esperado);*/
		
	}

}
