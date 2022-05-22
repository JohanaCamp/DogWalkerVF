package pe.edu.upc.spring.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;
import pe.edu.upc.spring.model.District;
import pe.edu.upc.spring.model.Owner;
import pe.edu.upc.spring.model.ServiceRequest;
import pe.edu.upc.spring.model.Status;
import pe.edu.upc.spring.model.Walker;
import pe.edu.upc.spring.service.IServiceRequestService;
import pe.edu.upc.spring.service.IStatusService;
import pe.edu.upc.spring.service.ITimeService;
import pe.edu.upc.spring.service.IWalkerService;

@Controller
@RequestMapping("/serviceRequest")
public class ServiceRequestController {
	@Autowired
	private IServiceRequestService srService;

	@Autowired
	private IStatusService sService;

	@Autowired
	private ITimeService tService;

	@Autowired
	private IWalkerService waService;

	@Autowired
	private IStatusService staService;

	@Autowired
	private WalkerController w;

	private Owner sesionOwner;
	private ServiceRequest sesionServiceRequest;
	private Walker sesionWalker;

	private String idOwner;
	private String idWalker;
	private District district;

	private List<ServiceRequest> listServiceRequestOwner;
	private List<ServiceRequest> listServiceRequestWalker;

	@RequestMapping("/irRegistrar")
	public String irPaginaRegistrar(Model model, @RequestParam(value = "id") Integer id) {
		model.addAttribute("serviceRequest", new ServiceRequest());
		model.addAttribute("listStatus", sService.listStatus());
		model.addAttribute("listTimes", tService.listTime());
		model.addAttribute("claseTime", "form-control itemselect");
		sesionWalker = waService.WalkerById(String.valueOf(id));

		return "serviceRequest";
	}

	@RequestMapping("/registrar")
	public String registrar(@Valid ServiceRequest objServiceRequest, BindingResult binRes, Model model)
			throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("listStatus", sService.listStatus());
			model.addAttribute("listTimes", tService.listTime());
			model.addAttribute("listWalker", waService.list());
			model.addAttribute("owner", sesionOwner);

			if (objServiceRequest.getTime() == null) {
				model.addAttribute("mensajeTime", "Seleccione el tiempo");
				model.addAttribute("claseTime", "form-control itemselect alert-danger");
			} else {
				model.addAttribute("claseTime", "form-control itemselect");
			}
			return "serviceRequest";

		} else {
			if (objServiceRequest.getTime() != null) {

				objServiceRequest.setWalker(sesionWalker);
				objServiceRequest
						.setTotalServiceCost(sesionWalker.getCostService() * objServiceRequest.getTime().getValue());
				objServiceRequest.setOwner(sesionOwner);
				objServiceRequest.setStatus(staService.listStatus().get(0));

				boolean flag = srService.save(objServiceRequest);
				if (flag) {
					sesionServiceRequest = objServiceRequest;
					return "redirect:/serviceRequest/listarSolicitudesDueno";
				} else {
					model.addAttribute("mensaje", "Error al guardar solicitud");
					return "redirect:/serviceRequest/irRegistrarDenuevo";
				}

			} else {

				return "redirect:/serviceRequest/irRegistrarDenuevo";
			}
		}
	}

	@RequestMapping("/actualizarRequest")
	public String actualizarRequest(@Valid ServiceRequest objServiceRequest, BindingResult binRes, Model model)
			throws ParseException {

		if (binRes.hasErrors()) {
			List<Status> listaEstados = sService.listStatus();
			listaEstados.remove(0);
			model.addAttribute("listStatus", listaEstados);
			model.addAttribute("owner", sesionOwner);
			return "serviceRequestEditWalker";
		} else {

			objServiceRequest.setWalker(sesionServiceRequest.getWalker());
			objServiceRequest.setTime(sesionServiceRequest.getTime());
			objServiceRequest.setOwner(sesionOwner);
			objServiceRequest.setStartTime(sesionServiceRequest.getStartTime());
			objServiceRequest.setTimeLimit(sesionServiceRequest.getTimeLimit());
			objServiceRequest.setTotalServiceCost(sesionServiceRequest.getTotalServiceCost());

			boolean flag = srService.save(objServiceRequest);
			if (flag) {

				sesionServiceRequest = objServiceRequest;
				return "redirect:/serviceRequest/listarSolicitudesPaseador";
			} else {
				model.addAttribute("mensaje", "Error al guadra solicitud");
				return "redirect:/serviceRequest/actualizarRequest";
			}
		}
	}

	@RequestMapping("/modificar/{id}")
	public String modificar(@PathVariable int id, Model model, RedirectAttributes objRedir) throws ParseException {
		Optional<ServiceRequest> objServiceRequest = srService.listId(id);
		if (objServiceRequest == null) {
			objRedir.addFlashAttribute("mensaje", "Error al modificar Solicitud");
			return "redirect:/serviceRequest/listar";
		} else {
			List<Status> listaEstados = sService.listStatus();
			listaEstados.remove(0);
			model.addAttribute("listStatus", listaEstados);
			System.out.print("ESTATUS 54EFDES " + sService.listStatus().remove(2).getIdStatus());
			sesionServiceRequest = objServiceRequest.get();
			sesionOwner = sesionServiceRequest.getOwner();
			if (objServiceRequest.isPresent())
				objServiceRequest.ifPresent(o -> model.addAttribute("serviceRequest", o));

			return "ServiceRequestEditWalker";
		}
	}

	@RequestMapping("/listarSolicitudesDueno")
	public String listarSolicitudesPorDueno(Model model) {
		idOwner = String.valueOf(sesionOwner.getIdOwner());
		listServiceRequestOwner = srService.listServiceRequestByOwner(idOwner);
		model.addAttribute("listServiceRequestByOwner", listServiceRequestOwner);
		model.addAttribute("owner", sesionOwner);
		return "serviceRequestListByOwner";
	}

	@RequestMapping("/listarSolicitudesPaseador")
	public String listarSolicitudesPorPaseador(Model model) {
		idWalker = String.valueOf(sesionWalker.getIdWalker());
		listServiceRequestWalker = srService.listServiceRequestByWalker(idWalker);
		model.addAttribute("listServiceRequestByWalker", listServiceRequestWalker);
		model.addAttribute("walker", sesionWalker);
		return "serviceRequestListByWalker";
	}
	
	@RequestMapping("/misPaseos")
	public String irMisPaseos(Model model) {
		
		model.addAttribute("mensajefechainicio", "");
		model.addAttribute("mensajefechafin", "");

		model.addAttribute("walker", sesionWalker);
		return "misPaseos";
	}

	@RequestMapping("/reporteMisPaseos")
	public String reporteMisPaseos(Model model,
			@RequestParam(value = "fechainicio", required = false) String fechainicio,
			@RequestParam(value = "fechafin", required = false) String fechafin) throws ParseException {
		List<ServiceRequest> listaSolicitudes;
		Date fechainicioDate = null;
		Date fechafinalDate = null;
		/*if (fechainicio == null) {
			model.addAttribute("mensajefechainicio", "Ingrese la fecha inicio");
			return "misPaseos";
		}
		
		if (fechafin == null) {
			model.addAttribute("mensajefechafin", "Ingrese la fecha fin");
			return "misPaseos";
		}*/
		if (fechainicio != null && fechafin != null) {
			model.addAttribute("mensajefechainicio", "");
			model.addAttribute("mensajefechafin", "");

			
			try {
				fechainicioDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechainicio);
				fechafinalDate = new SimpleDateFormat("yyyy-MM-dd").parse(fechafin);

			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			listaSolicitudes = srService.findServiceByDates(fechainicioDate, fechafinalDate);
			model.addAttribute("listaSolicitudes", listaSolicitudes);
			model.addAttribute("walker", sesionWalker);
			return "misPaseos";
			//return "redirect:/walker/misPaseos";
		}
		return "redirect:/serviceRequest/reporteMisPaseos";
	}

	@RequestMapping("/ListaPaseadores")
	public String irListaPaseadores(Map<String, Object> model) {
		List<Walker> listaDistrict;
		listaDistrict = waService.listByDistrict(district.getName());

		model.put("WalkerController", w);
		model.put("owner", sesionOwner);

		if (listaDistrict.isEmpty()) {
			model.put("listarPaseadores", waService.list());
		} else {
			model.put("listarPaseadores", listaDistrict);
		}
		return "walkerListByDistrict";
	}

	public void setOwner(Owner o) {
		sesionOwner = o;
	}

	public void setWalker(Walker w) {
		sesionWalker = w;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

}