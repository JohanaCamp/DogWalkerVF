package pe.edu.upc.spring.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import pe.edu.upc.spring.model.ServiceRequest;
import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.Walker;
import pe.edu.upc.spring.service.IDistrictService;
import pe.edu.upc.spring.service.IOwnerService;
import pe.edu.upc.spring.service.IServiceRequestService;
import pe.edu.upc.spring.service.IWalkerService;
import pe.edu.upc.spring.serviceimpl.JpaUserDetailsService;

@Controller
@RequestMapping("/owner")
public class OwnerController {
	@Autowired
	private IOwnerService oService;

	@Autowired
	private JpaUserDetailsService uService;

	@Autowired
	private IDistrictService dService;

	@Autowired
	private ServiceRequestController sController;

	@Autowired
	private DogController dogController;

	@Autowired
	private WalkerController walkerController;

	@Autowired
	private FeedbackController fController;

	@Autowired
	private IWalkerService wService;

	@Autowired
	private IServiceRequestService sService;

	private Owner sesionOwner;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private WalkerController w;

	private String contrasenaAnterior;

	@RequestMapping("/inicio")
	public String irPaginaInicio(Model model) {
		model.addAttribute("district", new District());
		return "bienvenido2";
	}

	@RequestMapping("/bienvenido")
	public String irPaginaBienvenida(Model model) {
		model.addAttribute("owner", sesionOwner);
		model.addAttribute("district", new District());
		return "bienvenidoOwner";
	}

	@RequestMapping("/tips")
	public String irTips(Model model) {
		model.addAttribute("owner", sesionOwner);
		return "Tips";
	}

	@RequestMapping("/noticias")
	public String irNoticias(Model model) {
		model.addAttribute("owner", sesionOwner);
		return "Noticias";
	}

	@RequestMapping("/menu")
	public String irMenuOwner() {
		// return "OwnerMenu";
		return "ownerMenuComplete";
	}

	@RequestMapping("/loginOwner")
	public String irPaginaLoginOwner() {
		return "loginOwner";
	}

	@RequestMapping("/irRegistrar")
	public String irPaginaRegistrar(Model model) {
		model.addAttribute("owner", new Owner());
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("claseDistrito", "form-control itemselect");
		return "owner";
	}

	@RequestMapping("/registrar")
	public String registrar(@Valid Owner objOwner, BindingResult binRes, Model model) throws ParseException {

		if (binRes.hasErrors()) {
			
			

			model.addAttribute("listadistrito", dService.listDistrict());

			if (objOwner.getDistrict() == null) {
				model.addAttribute("mensajeDistrito", "Seleccione su distrito");
				model.addAttribute("claseDistrito", "form-control itemselect alert-danger");
			} else {
				model.addAttribute("claseDistrito", "form-control itemselect");
			}
			if (objOwner.getIdOwner() > 0) {
				return "ownerEdit";
			} else {
				return "owner";
			}
		} else {

			if (objOwner.getDistrict() != null) {

				if (objOwner.getIdOwner() > 0) {
					if (objOwner.getPassword() == "") {
						objOwner.setPassword(contrasenaAnterior);
					} else {
						String bcryptPassword = passwordEncoder.encode(objOwner.getPassword());
						objOwner.setPassword(bcryptPassword);
					}
				} else {
					String bcryptPassword = passwordEncoder.encode(objOwner.getPassword());
					objOwner.setPassword(bcryptPassword);
				}
				boolean flagUsers;
				boolean flag;

				if (objOwner.getIdOwner() > 0) {// editado

					flagUsers = registrarUsuario(objOwner);
					flag = oService.save(objOwner);
				} else { // registra por primera vez

					Users users = uService.findByUsername(objOwner.getEmail());
					if (users != null) {
						model.addAttribute("mensaje",
								"Ya se ha creado una cuenta con este correo, por favor intente con otro");
						flagUsers = false;
						flag = false;
					} else {
						flagUsers = registrarUsuario(objOwner);
						flag = oService.save(objOwner);
					}

				}

				if (flag && flagUsers) {
					sesionOwner = objOwner;
					dogController.setOwner(sesionOwner);
					sController.setOwner(sesionOwner);
					fController.setOwner(sesionOwner);
					walkerController.setSesionOwner(sesionOwner);

					return "redirect:/owner/bienvenido";
				} else {

					if (objOwner.getIdOwner() > 0) {
						return "redirect:/owner/modificar";
					} else {

						return "redirect:/owner/irRegistrar";
					}
				}
			} else {

				if (objOwner.getIdOwner() > 0) {
					return "redirect:/owner/modificar";
				} else {

					return "redirect:/owner/irRegistrar";
				}
			}
		}
	}

	@RequestMapping("/modificar")
	public String modificar(Model model) {
		contrasenaAnterior = sesionOwner.getPassword();
		sesionOwner.setPassword("");
		model.addAttribute("owner", sesionOwner);
		model.addAttribute("listadistrito", dService.listDistrict());
		return "ownerEdit";

	}

	@RequestMapping("/listarCanes")
	public String listarCanes(Model model) throws ParseException {
		model.addAttribute("owner", sesionOwner);
		/* cService.ListDogByOwner(sesionOwner.getIdOwner()); */
		return "listCanes";
	}

	@RequestMapping("/validarUsuario")
	public String ingresarCuenta(@Valid Owner objOwner, BindingResult binRes) throws ParseException {
		List<Owner> listOwners;
		objOwner.setEmail(objOwner.getEmail());
		objOwner.setPassword(objOwner.getPassword());
		listOwners = oService.findByEmailAndPassword(objOwner.getEmail(), objOwner.getPassword());

		if (!listOwners.isEmpty()) {
			objOwner = listOwners.get(0);
			sesionOwner = objOwner;
			dogController.setOwner(sesionOwner);
			walkerController.setSesionOwner(sesionOwner);
			sController.setOwner(sesionOwner);
			fController.setOwner(sesionOwner);
			return "redirect:/owner/bienvenido";
		} else {
			return "ownerLogin";
		}
	}

	public void setSesionOwner(Owner sesionOwner) {
		this.sesionOwner = sesionOwner;
		dogController.setOwner(sesionOwner);
		walkerController.setSesionOwner(sesionOwner);
		sController.setOwner(sesionOwner);
		fController.setOwner(sesionOwner);
	}

	public boolean registrarUsuario(Owner owner) {
		Users users;
		users = uService.findByUsername(owner.getEmail());
		if (users == null) {
			users = new Users();
			List<Role> listRoles = new ArrayList<Role>();
			Role role = new Role();
			role.setRol("ROLE_OWNER");
			listRoles.add(role);
			users.setPassword(owner.getPassword());
			users.setRoles(listRoles);
			users.setEnabled(true);
			users.setUsername(owner.getEmail());
		} else if (users.getPassword() != owner.getPassword()) {
			users.setPassword(owner.getPassword());
		}
		boolean flagUsers = uService.save(users);
		return flagUsers;
	}

	@RequestMapping("/topPaseadores")
	public String irPaginaTopPaseadores(Model model) {

		model.addAttribute("owner", sesionOwner);

		int contador = 0;
		int ref = 0;
		boolean registrar = false;
		List<Walker> wtotal = new ArrayList<Walker>();
		wtotal = wService.list();
		List<ServiceRequest> b = new ArrayList<ServiceRequest>();

		b = sService.listServiceRequest();

		for (int j = 0; j < wService.list().size(); j++) {
			wtotal.get(j).setCostService(0);
		}

		for (int j = 0; j < wService.list().size(); j++) {
			Walker ws = wService.list().get(j);
			for (int i = 0; i < b.size(); i++) {
				ServiceRequest s = b.get(i);

				if (ws.getIdWalker() == s.getWalker().getIdWalker()) {
					contador = contador + 1;
					ref = j;
					registrar = true;
				}
			}
			if (registrar) {
				wtotal.get(ref).setCostService((double) contador);
			}
			contador = 0;
			ref = 0;
			registrar = false;
		}

		List<Walker> wfinal = new ArrayList<Walker>();
		double mayor;
		double max_antiguo;
		String nombre;
		int n;
		District d;
		for (int i = 0; i < wtotal.size(); i++) {
			mayor = wtotal.get(i).getCostService();
			for (int j = i + 1; j < wtotal.size() - 1; j++) {
				if (mayor < wtotal.get(j).getCostService()) {
					max_antiguo = mayor;
					mayor = wtotal.get(j).getCostService();
					nombre = wtotal.get(i).getFirstNames();
					n = wtotal.get(i).getIdWalker();
					d = wtotal.get(i).getDistrict();

					wtotal.get(i).setCostService(mayor);
					wtotal.get(i).setFirstNames(wtotal.get(j).getFirstNames());
					wtotal.get(i).setIdWalker(wtotal.get(j).getIdWalker());
					wtotal.get(i).setDistrict(wtotal.get(j).getDistrict());
					wtotal.get(j).setCostService(max_antiguo);
					wtotal.get(j).setFirstNames(nombre);
					wtotal.get(j).setIdWalker(n);
					wtotal.get(j).setDistrict(d);
				}
			}

		}

		for (int i = 0; i < wtotal.size(); i++) {

			if (wtotal.get(i).getCostService() > 0) {
				wfinal.add(wtotal.get(i));
			}
		}

		model.addAttribute("listarPaseadores", wfinal);
		model.addAttribute("WalkerController", w);
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("serviceRequest", new ServiceRequest());
		model.addAttribute("walker", new Walker());

		return "topWalker";
	}

	@RequestMapping("/buscarTopPaseadores")
	public String buscarPaseadoresTop(@ModelAttribute ServiceRequest serviceRequest, @ModelAttribute Walker walker,
			Model model) throws ParseException {

		Date dateBegin = serviceRequest.getDateService(); /// getdateService se reutilizo funcion de service
		Date dateEnd = walker.getDateOfBirth(); /// getdateofbirth se reutilizo funcion de walker
		int contador = 0;
		int ref = 0;
		boolean registrar = false;
		List<Walker> wtotal = new ArrayList<Walker>();
		wtotal = wService.list();
		List<ServiceRequest> b = new ArrayList<ServiceRequest>();

		if (dateBegin != null && dateEnd != null)
			b = sService.findServiceByDate(dateBegin, dateEnd, walker.getDistrict().getName());
		else {
			b = sService.listServiceRequest();

		}

		for (int j = 0; j < wService.list().size(); j++) {
			wtotal.get(j).setCostService(0); // costservice funciona como atributo de cantidad de solicitudes
		}

		for (int j = 0; j < wService.list().size(); j++) {
			Walker ws = wService.list().get(j);
			for (int i = 0; i < b.size(); i++) {
				ServiceRequest s = b.get(i);

				if (ws.getIdWalker() == s.getWalker().getIdWalker()) {
					contador = contador + 1;
					ref = j;
					registrar = true;
				}
			}
			if (registrar) {
				wtotal.get(ref).setCostService((double) contador);
			}
			contador = 0;
			ref = 0;
			registrar = false;
		}

		List<Walker> wfinal = new ArrayList<Walker>();
		double mayor;
		double max_antiguo;
		String nombre;
		int n;
		District d;
		for (int i = 0; i < wtotal.size(); i++) {
			mayor = wtotal.get(i).getCostService();
			for (int j = i + 1; j < wtotal.size() - 1; j++) {
				if (mayor < wtotal.get(j).getCostService()) {
					max_antiguo = mayor;
					mayor = wtotal.get(j).getCostService();
					nombre = wtotal.get(i).getFirstNames();
					n = wtotal.get(i).getIdWalker();
					d = wtotal.get(i).getDistrict();

					wtotal.get(i).setCostService(mayor);
					wtotal.get(i).setFirstNames(wtotal.get(j).getFirstNames());
					wtotal.get(i).setIdWalker(wtotal.get(j).getIdWalker());
					wtotal.get(i).setDistrict(wtotal.get(j).getDistrict());
					wtotal.get(j).setCostService(max_antiguo);
					wtotal.get(j).setFirstNames(nombre);
					wtotal.get(j).setIdWalker(n);
					wtotal.get(j).setDistrict(d);
				}
			}

		}

		for (int i = 0; i < wtotal.size(); i++) {

			if (wtotal.get(i).getCostService() > 0) {
				wfinal.add(wtotal.get(i));
			}
		}

		model.addAttribute("owner", sesionOwner);
		model.addAttribute("WalkerController", w);
		model.addAttribute("listadistrito", dService.listDistrict());
		model.addAttribute("listarPaseadores", wfinal);

		return "topWalker";
	}

}
