package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.District;
import pe.edu.upc.spring.model.Dog;
import pe.edu.upc.spring.model.Owner;
import pe.edu.upc.spring.service.ICharacterService;
import pe.edu.upc.spring.service.IDogService;
import pe.edu.upc.spring.service.IRaceService;

@Controller
@RequestMapping("/dog")
public class DogController {
	@Autowired
	private IDogService dService;

	@Autowired
	private IRaceService rService;

	@Autowired
	private ICharacterService cService;

	private String idOwner;
	private Owner sesionOwner;
	private List<Dog> listDog;

	@RequestMapping("/bienvenido")
	public String irPaginaBienvenida(Model model) {
		model.addAttribute("district", new District());
		return "bienvenido";
	}

	@RequestMapping("/menu")
	public String irMenuOwner() {
		return "dogMenu";
	}

	@RequestMapping("/irRegistrar")
	public String irPaginaRegistrar(Model model) {
		model.addAttribute("dog", new Dog());
		model.addAttribute("listaRaza", rService.listRace());
		model.addAttribute("listaCaracter", cService.listCharacter());
		model.addAttribute("claseRaza", "form-control itemselect");
		model.addAttribute("claseCaracter", "form-control itemselect");

		return "dog";
	}

	@RequestMapping("/registrar")
	public String registrar(@Valid Dog objDog, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listaRaza", rService.listRace());
			model.addAttribute("listaCaracter", cService.listCharacter());
			model.addAttribute("owner", sesionOwner);
			if (objDog.getRace() == null) {
				model.addAttribute("mensajeRaza", "Seleccione una raza");
				model.addAttribute("claseRaza", "form-control itemselect alert-danger");
			} else {
				model.addAttribute("claseRaza", "form-control itemselect");
			}
			
			
			if (objDog.getCharacter() == null) {
				model.addAttribute("mensajeCaracter", "Seleccione un caracter");
				model.addAttribute("claseCaracter", "form-control itemselect alert-danger");
			} else {
				model.addAttribute("claseCaracter", "form-control itemselect");
			}
			
			
			if(objDog.getIdDog() > 0) {
				return "dogEdit";
			}
			else {
				model.addAttribute("mensaje", "Ocurrio un error");
				return "dog";
			}	
			
		} else {
			if (objDog.getRace()  != null && objDog.getCharacter()  != null ) {

			objDog.setOwner(sesionOwner);
			boolean flag = dService.save(objDog);
			if (flag)
				return "redirect:/dog/listarCanes";
			else {
				if(objDog.getIdDog() > 0) {
					model.addAttribute("listaRaza", rService.listRace());
					model.addAttribute("listaCaracter", cService.listCharacter());
					return "dogEdit";
				}
				else {
					model.addAttribute("mensaje", "Ocurrio un error");
					return "redirect:/dog/irRegistrar";
				}	
				
			}
			
			} else {

				if(objDog.getIdDog() > 0) {
					model.addAttribute("listaRaza", rService.listRace());
					model.addAttribute("listaCaracter", cService.listCharacter());
					return "dogEdit";
				}
				else {
					model.addAttribute("mensaje", "Ocurrio un error");
					return "redirect:/dog/irRegistrar";
				}
			}
		}
	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<Dog> objDog = dService.listById(id);
		if (objDog == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un error");
			return "redirect:/dog/listarCanes";
		} else {
			model.addAttribute("listaRaza", rService.listRace());
			model.addAttribute("listaCaracter", cService.listCharacter());

			if (objDog.isPresent())
				objDog.ifPresent(d -> model.addAttribute("dog", d));
			return "dogEdit";
		}
	}

	@RequestMapping("/eliminar")
	public String eliminar(Map<String, Object> model, @RequestParam(value = "id") Integer id) {
		try {
			if (id != null && id > 0) {
				dService.delete(id);
				model.put("listDogByOwner", dService.ListDogByOwner(idOwner));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("mensaje", "Ocurrio un error");
			model.put("listDogByOwner", dService.ListDogByOwner(idOwner));
		}
		return "redirect:/dog/listarCanes";
	}
	@Secured("ROLE_OWNER")
	@RequestMapping("/listarCanes")
	public String listarCanes(Model model) {
		idOwner = String.valueOf(sesionOwner.getIdOwner());
		listDog = dService.ListDogByOwner(idOwner);
		model.addAttribute("owner", sesionOwner);
		model.addAttribute("ListDogByOwner", listDog);
		return "dogLists";
	}

	public void setOwner(Owner o) {
		sesionOwner = o;
	}

}
