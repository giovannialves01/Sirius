package fatec.api.Sirius.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChangeViews {
	
	@RequestMapping(method = RequestMethod.GET, path = "/updown")
	public String upDown() {
		return "updown";
	}
}
