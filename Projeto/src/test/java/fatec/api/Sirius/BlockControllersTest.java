package fatec.api.Sirius;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fatec.api.Sirius.controllers.BlockControllers;

@SpringBootTest
public class BlockControllersTest {
	@Autowired
	private BlockControllers blockControllers;

	@Test
	void testPdfName() {
		String resultado=blockControllers.pdfName("Abb-1234", "00", "11", "00");
		String esperado = "Abb-134-00-11c00";
		Assert.assertEquals(esperado, resultado);
	}

}
