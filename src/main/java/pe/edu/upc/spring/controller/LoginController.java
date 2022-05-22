package pe.edu.upc.spring.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.upc.spring.model.Owner;
import pe.edu.upc.spring.model.Walker;
import pe.edu.upc.spring.service.IOwnerService;
import pe.edu.upc.spring.service.IWalkerService;

@Controller
@RequestMapping
public class LoginController {
	
	@Autowired
	private IOwnerService oService;
	
	@Autowired
	private IWalkerService wService;
	
	@Autowired
	private OwnerController oController;
	
	@Autowired
	private WalkerController wController;

	@GetMapping(value = { "/login", "/" })
	public String login(@RequestParam(value = "error", required = false) String error,@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal,
			RedirectAttributes flash ) {
		System.out.print("***Login controller***");

		if (principal != null) {
			System.out.print("***Login principal ***");
			Owner owner = new Owner();
			owner = oService.findByEmail(principal.getName());
			if(owner==null) {
				Walker walker = new Walker();
				walker = wService.findByEmail(principal.getName());	
				wController.setSesionWalker(walker);
			
				return "redirect:/walker/bienvenido";
			}else {
				
				oController.setSesionOwner(owner);
			
				return "redirect:/owner/bienvenido";
			}
		}

		if (error != null) {
			model.addAttribute("error",	"Error en el login: Correo o contraseña incorrecta, por favor vuelva a intentarlo.");
		}

		if (logout != null) {
			model.addAttribute("success", "Ha cerrado sesiónn con éxito!");
			return "redirect:/pantalla/inicio";
		}

		return "login";
	}

}
