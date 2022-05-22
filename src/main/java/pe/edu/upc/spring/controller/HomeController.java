package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.District;
import pe.edu.upc.spring.model.Walker;

@Controller
@RequestMapping("/pantalla") 
public class HomeController {
	
	@RequestMapping("/inicio")
	public String PaginaBienvenida(Model model) {
		model.addAttribute("district", new District());
		return "bienvenido2"; 
	}
	@RequestMapping("/quienessomos")
	public String PaginaquienesSomos() {
		return "quienessomos"; 
	}
	@RequestMapping("/contactanos")
	public String Paginacontactanos() {
		return "contactanos"; 
	}
	
	@RequestMapping("/enterateMas")
	public String PaginaEnterateMas() {
		return "enterateMas"; 
	}
	
	@RequestMapping("/regitrarSegunUsuario")
	public String irPaginaBienvenida() {
		return "registrarSegunUsuario"; 
	}
	
	@RequestMapping("/iniciarSegunUsuario")
	public String irPagina() {
		return "iniciarSegunUsuario"; 
	}
	@RequestMapping("/cerrarSesion")
	public String cerrarSesion() {
		return "bienvenido"; 
	}
			
	
	@RequestMapping("/buscarPaseador")
	public String reportePaseadorxDistrito(Map<String, Object> model, @ModelAttribute District district)
			throws ParseException {
		System.out.print("Ingrese a buscarPaseador *****");

	
		return "Hola";

	}

	
}
