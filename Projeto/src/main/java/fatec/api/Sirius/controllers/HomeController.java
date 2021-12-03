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

			userRepository.save(user);
			ModelAndView andView = new ModelAndView("cadastro");
			Iterable<User> usersIt = userRepository.findAll();
			andView.addObject("users", usersIt);
			
			String concluido = null;
			concluido = "ok";

			System.out.println("User:"+user);
			return "cadastro.html";
		} catch (Exception e) {
			System.out.println("User:"+user);
			System.out.println("ERRO! "+e);
			e.printStackTrace();
			return "error";
		}
	}

}
