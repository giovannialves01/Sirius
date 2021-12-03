package sirius.test;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fatec.api.Sirius.controllers.FileDownloadController;

class FileDownloadControllerTest {

	FileDownloadController fdc = new FileDownloadController();
	
	//Teste 1: Esse método faz a verificação do nome do documento, caso tenha subseção o valor retornado deve ser TRUE
	@Test
	void testHaveSubs() {
		
		boolean resultadoFalse = fdc.haveSubs("ABC-1234-00-00c50.pdf");
		boolean resultadoTrue = fdc.haveSubs("ABC-1234-00-00-00c50.pdf");

		boolean esperadoFalse = false;
		boolean esperadoTrue = true;

		Assert.assertEquals(esperadoFalse, resultadoFalse);
		Assert.assertEquals(esperadoTrue, resultadoTrue);
	}
	//Teste 2: Esse metodo monta a localização do documento dentro da pasta Master
	@Test
	void testNameBuilder() {	
		
		String resultado1 = fdc.nameBuilder("ABC-1234", "00", "", "00");
		String resultado2 = fdc.nameBuilder("ABC-1234", "00", "00", "00");
		
		String esperado1 = "ABC-1234/00/00";
		String esperado2 = "ABC-1234/00/00/00";
		
		Assert.assertEquals(resultado1, esperado1);
		Assert.assertEquals(resultado2, esperado2);
	}
	
	@Test 
	void testDeleteFULL() {
		
		 //fdc.deleteFULL("C:/ARQ");
	}
	
}
