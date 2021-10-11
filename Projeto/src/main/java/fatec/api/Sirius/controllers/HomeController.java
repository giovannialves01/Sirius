package fatec.api.Sirius.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String login(){
        return"login";
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(){
        return "admin";
    }
    
    @RequestMapping("/home")
    public String home(){
        return "index";
    }
    
    @RequestMapping("/cadastro")
    public String cadastro(){
        return "cadastro";
    }
    
    @RequestMapping("/updown")
    public String updown(){
        return "updown";
    }
    
    @GetMapping("/cadastrar")
	public ModelAndView salvar(User user) {
		
			userRepository.save(user);
			ModelAndView andView = new ModelAndView("cadastro");
			Iterable<User> usersIt = userRepository.findAll();
			andView.addObject("users", usersIt);
			
		return andView;
	}

}
