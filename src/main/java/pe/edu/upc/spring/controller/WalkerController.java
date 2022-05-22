package pe.edu.upc.spring.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.District;
import pe.edu.upc.spring.model.Owner;
import pe.edu.upc.spring.model.Role;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.Walker;
import pe.edu.upc.spring.service.IDistrictService;
import pe.edu.upc.spring.service.IFeedbackService;
import pe.edu.upc.spring.service.IPersonalityService;
import pe.edu.upc.spring.service.IWalkerService;
import pe.edu.upc.spring.serviceimpl.JpaUserDetailsService;

@Controller
@RequestMapping("/walker")
public class WalkerController {

	@Autowired
	private IDistrictService dService;

	@Autowired
	private JpaUserDetailsService uService;

	@Autowired
	private IPersonalityService pService;

	@Autowired
	private IWalkerService wService;

	@Autowired
	private ServiceRequestController sController;

	@Autowired
	private FeedbackController feedbackController;

	@Autowired
	private IFeedbackService fService;

	@Autowired
	private WalkerController w;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private Walker sesionWalker;
	private Owner sesionOwner;
	private String contrasenaAnterior;

	@RequestMapping("/inicio")
	public String irPaginaInicio(Model model) {
		model.addAttribute("district", new District());
		return "bienvenido2";
	}

	@RequestMapping("/bienvenido")
	public String irPaginaBienvenida(Model model) {
		model.addAttribute("walker", sesionWalker);
		model.addAttribute("district", new District());
		return "bienvenidoWalker";
	}

	@RequestMapping("/consejos")
	public String irConsejos(Model model) {
		model.addAttribute("walker", sesionWalker);
		return "Consejos";
	}

	@RequestMapping("/noticias")
	public String irNoticias(Model model) {
		model.addAttribute("walker", sesionWalker);
		return "Noticias2";
	}

	@RequestMapping("/menu")
	public String irMenuWalker() {
		// return "menuWalker";
		return "walkerMenuComplete";
	}

	@RequestMapping("/irBuscar")
	public String irBuscar() {
		return "walkerListByDistrict";
	}

	@RequestMapping("/irRegistrar") // función registrar paseador
	public String irPaginaRegistrar(Model model) {
		model.addAttribute("walker", new Walker());
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("listpersonalidad", pService.listPersonality());
		model.addAttribute("claseDistrito", "form-control itemselect");
		model.addAttribute("clasePersonalidad", "form-control itemselect");

		return "walker";
	}

	@RequestMapping("/irEnterateMas") // función registrar paseador
	public String irPaginaEnterateMas(Model model) {
		model.addAttribute("walker", new Walker());
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("listpersonalidad", pService.listPersonality());
		return "enterateMas";
	}

	@RequestMapping("/registrar")
	public String registrar(@Valid Walker objWalker, BindingResult binRes, Model model) throws ParseException {

		if (binRes.hasErrors()) {
			model.addAttribute("listadistrito", dService.listDistrict());
			model.addAttribute("listpersonalidad", pService.listPersonality());
			if (objWalker.getDistrict() == null) {
				model.addAttribute("mensajeDistrito", "Seleccione su distrito");
				model.addAttribute("claseDistrito", "form-control itemselect alert-danger");
			} else {
				model.addAttribute("claseDistrito", "form-control itemselect");
			}
			
			if (objWalker.getPersonality() == null) {
				model.addAttribute("mensajePersonalidad", "Seleccione una personalidad");
				model.addAttribute("clasePersonalidad", "form-control itemselect alert-danger");
			} else {
				model.addAttribute("clasePersonalidad", "form-control itemselect");
			}
			if (objWalker.getIdWalker() > 0) {
				return "walkerEdit";
			} else {
				return "walker";
			}
		} else {
			if (objWalker.getDistrict() != null && objWalker.getPersonality() != null) {
				if (objWalker.getIdWalker() > 0) {
					if (objWalker.getPassword() == "") {
						objWalker.setPassword(contrasenaAnterior);

					} else {
						String bcryptPassword = passwordEncoder.encode(objWalker.getPassword());
						objWalker.setPassword(bcryptPassword);
					}
				} else {
					String bcryptPassword = passwordEncoder.encode(objWalker.getPassword());
					objWalker.setPassword(bcryptPassword);
				}
				boolean flagUsers;
				boolean flag;
				if (objWalker.getIdWalker() > 0) {
					flagUsers = registrarUsuario(objWalker);
					flag = wService.save(objWalker);
				} else {
					Users users = uService.findByUsername(objWalker.getEmail());
					if (users != null) {
						model.addAttribute("mensaje",
								"Ya se ha creado una cuenta con este correo, por favor intente con otro");
						flagUsers = false;
						flag = false;
					} else {
						flagUsers = registrarUsuario(objWalker);
						flag = wService.save(objWalker);
					}
				}
				if (flag && flagUsers) {

					sesionWalker = objWalker;
					sController.setWalker(sesionWalker);
					return "redirect:/walker/bienvenido";
				} else {
					if (objWalker.getIdWalker() > 0) {
						return "redirect:/walker/modificar";
					} else {
						model.addAttribute("mensaje", "Ocurrio un error");
						return "redirect:/walker/irRegistrar";
					}
				}
			} else {

				if (objWalker.getIdWalker() > 0) {
					return "redirect:/walker/modificar";
				} else {
					model.addAttribute("mensaje", "Ocurrio un error");
					return "redirect:/walker/irRegistrar";
				}
			}
		}
	}

	@RequestMapping("/modificar")
	public String modificar(Model model) {
		contrasenaAnterior = sesionWalker.getPassword();
		sesionWalker.setPassword("");
		model.addAttribute("walker", sesionWalker);
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("listpersonalidad", pService.listPersonality());
		return "walkerEdit";

	}

	@RequestMapping("/validarUsuario")
	public String ingresarCuenta(@Valid Walker objWalker, BindingResult binRes) throws ParseException {
		List<Walker> listWalkers;
		objWalker.setEmail(objWalker.getEmail());
		objWalker.setPassword(objWalker.getPassword());
		listWalkers = wService.findByEmailAndPassword(objWalker.getEmail(), objWalker.getPassword());

		if (!listWalkers.isEmpty()) {
			objWalker = listWalkers.get(0);
			sesionWalker = objWalker;
			sController.setWalker(sesionWalker);
			return "redirect:/walker/bienvenido";
		} else {
			return "walkerLogin";
		}

	}

	@RequestMapping("/buscar")
	public String buscarPorDistrito(Map<String, Object> model, @ModelAttribute District district)
			throws ParseException {
		List<Walker> listaDistrict;
		listaDistrict = wService.listByDistrict(district.getName());
		feedbackController.setDistrict(district);
		model.put("owner", sesionOwner);
		model.put("WalkerController", w);

		if (listaDistrict.isEmpty()&& district.getName().isEmpty() ) {
			model.put("listarPaseadores", wService.list());
		} else {
			model.put("listarPaseadores", listaDistrict);

		}

		return "walkerListByDistrict";

	}

	@RequestMapping("/reporteEnterateMas")
	public String reporteMisPaseos(Model model, @ModelAttribute Walker objWalker) throws ParseException {
		List<Walker> listaDistrict;
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("owner", sesionOwner);
		model.addAttribute("WalkerController", w);
		listaDistrict = wService.listByDistrict(objWalker.getDistrict().getName());
		model.addAttribute("listarPaseadores", listaDistrict);
		model.addAttribute("cantidadPaseadores", listaDistrict.size());
		return "enterateMas";
	}

	public int calcularEdad(Date dateOfBirth) {
		Calendar fechaNac = Calendar.getInstance();
		fechaNac.setTime(dateOfBirth);
		int year = fechaNac.get(Calendar.YEAR);
		int month = fechaNac.get(Calendar.MONTH);
		int day = fechaNac.get(Calendar.DAY_OF_MONTH);
		LocalDate fechaHoy = LocalDate.now();
		LocalDate fechaNacimiento = LocalDate.of(year, month, day);
		Period periodo = Period.between(fechaNacimiento, fechaHoy);
		return periodo.getYears();
	}

	@RequestMapping("/listar")
	public String listar(Map<String, Object> model, @ModelAttribute District district) {
		model.put("listarPaseadores", wService.list());
		return "walkerListByDistrict";
	}

	@Secured("ROLE_WALKER")
	@RequestMapping("/Comentarios")
	public String ListFeedbackByWalker(Model model) {
		model.addAttribute("listaFeedbacks", fService.FeedbackByIdWalker(String.valueOf(sesionWalker.getIdWalker())));
		model.addAttribute("walker", sesionWalker);
		return "FeedbackByWalkers";
	}

	public void setSesionOwner(Owner sesionOwner) {
		this.sesionOwner = sesionOwner;
	}

	public void setSesionWalker(Walker sesionWalker) {
		this.sesionWalker = sesionWalker;
		sController.setWalker(sesionWalker);
	}

	public boolean registrarUsuario(Walker walker) {
		Users users;
		users = uService.findByUsername(walker.getEmail());
		if (users == null) {
			users = new Users();
			List<Role> listRoles = new ArrayList<Role>();
			Role role = new Role();
			role.setRol("ROLE_WALKER");
			listRoles.add(role);
			users.setPassword(walker.getPassword());
			users.setRoles(listRoles);
			users.setEnabled(true);
			users.setUsername(walker.getEmail());

		} else if (users.getPassword() != walker.getPassword()) {
			users.setPassword(walker.getPassword());
		}
		boolean flagUsers = uService.save(users);
		return flagUsers;
	}

}
