package sirius.test;

import java.io.File;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import fatec.api.Sirius.controllers.CodelistController;

class CodelistControllerTest {

	CodelistController cc = new CodelistController();
	@Test
	void testCodelistController() {
		
		System.out.println("O arquivo ABC-1234-00-00c50.docx existe? " + new File("../Root/Master/ABC-1234-00-00c50.docx").exists());
		
		cc.deleteFiles("../Root/Master/ABC-1234-00-00c50.docx");
		
		System.out.println("O arquivo ABC-1234-00-00c50.docx existe? " + new File("../Root/Master/ABC-1234-00-00c50.docx").exists());

		boolean resultado = new File("../Root/Master/ABC-1234-00-00c50.docx").exists();
		boolean esperado = false;
		
		Assert.assertEquals(resultado, esperado);
	}

}
