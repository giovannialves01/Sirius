package fatec.api.Sirius;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fatec.api.Sirius.controllers.CodelistController;
import fatec.api.Sirius.model.Document;
import fatec.api.Sirius.repository.DocumentRepository;
import org.junit.Assert;

@SpringBootTest
class TestDeleteLine {

	@Autowired
	private DocumentRepository dr;
	@Autowired
	private CodelistController cc;
	
	@Test
	void testDeleteLine() {
		
		Document afterDelete = null;
		String esperado = null;
				
		String id = "14";
		Document beforeDelete = dr.findById(Integer.parseInt(id));
		System.out.println(beforeDelete.getName());
		cc.deleteLine(id);
		if(dr.findById(Integer.parseInt(id)) != null) {
			afterDelete = dr.findById(Integer.parseInt(id));
		}
		Assert.assertNotEquals(beforeDelete, afterDelete);
		
		Assert.assertEquals(esperado, afterDelete);
	}

}
