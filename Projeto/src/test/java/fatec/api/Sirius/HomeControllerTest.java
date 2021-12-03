package fatec.api.Sirius;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;


import org.junit.jupiter.api.Test;

import fatec.api.Sirius.controllers.HomeController;
import fatec.api.Sirius.model.User;
import junit.framework.Assert;

@SpringBootTest
class HomeControllerTest {

	@Test
	void testLogin() {
		fail("Not yet implemented");
	}

	@SuppressWarnings("deprecation")
	@Test
	void testSalvar() {
		HomeController homecon = new HomeController();
		User usertest =new User();
		usertest.setEmail("email");
		usertest.setLast_name("last_name");
		usertest.setName("name");
		usertest.setPassword("password");
		usertest.setUsername("naoexiste");
		String resultado=homecon.salvar(usertest);
		String esperado ="cadastro.html";
		Assert.assertEquals(esperado, resultado);
		
	}

}
