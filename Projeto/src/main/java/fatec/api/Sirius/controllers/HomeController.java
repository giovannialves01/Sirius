package fatec.api.Sirius.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fatec.api.Sirius.model.User;
import fatec.api.Sirius.repository.UserRepository;

@Controller
public class HomeController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/admin")
	public String admin() {
		return "admin";
	}

	@RequestMapping("/home")
	public String home() {
		return "index";
	}

	@RequestMapping("/cadastro")
	public String cadastro() {
		return "cadastro";
	}

	@RequestMapping("/updown")
	public String updown() {
		return "updown";
	}

	@GetMapping("/cadastrar")
	public String salvar(User user) {

		try {
		System.out.println("A");

		/*user.setEmail("teste@gmail.com");
		user.setLast_name("Teste1");
		user.setName("teste1");
		user.setPassword("teste");
		user.setEnabled(false);
		user.setUsername("TESTE2");*/
		
		
		System.out.println("1-" + user.getName());
		System.out.println("2-" + user.getId());
		System.out.println("3-" + user.getEmail());
		System.out.println("4-" + user.getLast_name());
		System.out.println("5-" + user.getPassword());
		System.out.println("6-" + user.getUsername());
		System.out.println("7-" + user.getClass());
		
		System.out.println("user null? " + (user == null ? "yep" : "nope"));

			userRepository.save(user);

			System.out.println("B");
			ModelAndView andView = new ModelAndView("cadastro");

			System.out.println("C");
			Iterable<User> usersIt = userRepository.findAll();

			System.out.println("D");
			andView.addObject("users", usersIt);

			System.out.println("E");
			String concluido = null;

			System.out.println("F");
			concluido = "ok";

			System.out.println("G*");
			// model.addAttribute("concluido","ok");

			return "cadastro.html";
		} catch (Exception e) {
	        e.printStackTrace();
			return "erro";
		}
	}

}
