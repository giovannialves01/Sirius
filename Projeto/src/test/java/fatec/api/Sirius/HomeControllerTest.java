package fatec.api.Sirius;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import fatec.api.Sirius.controllers.HomeController;
import fatec.api.Sirius.model.User;
import fatec.api.Sirius.repository.UserRepository;

@SpringBootTest
public class HomeControllerTest {
@Autowired
private HomeController homeCon;
   //
    @Test
    void testSalvar() {
        User usertest =new User();
        usertest.setEmail("aaaaa@gmail.com");
        usertest.setLast_name("Usuario");
        usertest.setName("Novo");
        usertest.setPassword("asd");
        usertest.setEnabled(false);
        usertest.setUsername("AAAAAeffre1");
        usertest.setId(80);
        String resultado=homeCon.salvar(usertest);
        System.out.println("Resultado:"+resultado);
        System.out.println("Usuario:"+usertest);
        String esperado ="cadastro.html";
         Assert.assertEquals(esperado, resultado);
    }

}
