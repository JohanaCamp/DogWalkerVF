package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.District;
import pe.edu.upc.spring.model.Feedback;
import pe.edu.upc.spring.model.Owner;
import pe.edu.upc.spring.model.Walker;
import pe.edu.upc.spring.service.IFeedbackService;
import pe.edu.upc.spring.service.IWalkerService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private IWalkerService wService;

	@Autowired
	private IFeedbackService fService;

	@Autowired
	private WalkerController w;

	private Owner sesionOwner;
	private Walker sesionWalker;
	private District district;

	@RequestMapping("/ListaPaseadores")
	public String irListaPaseadores(Map<String, Object> model) {
		List<Walker> listaDistrict;
		listaDistrict = wService.listByDistrict(district.getName());

		model.put("WalkerController", w);
		model.put("owner", sesionOwner);

		if (listaDistrict.isEmpty()) {
			model.put("listarPaseadores", wService.list());
		} else {
			model.put("listarPaseadores", listaDistrict);
		}
		return "walkerListByDistrict";
	}

	@RequestMapping("/Comentarios")
	public String modificar(Model model, @RequestParam(value = "id") Integer id) throws ParseException {
		sesionWalker = wService.WalkerById(String.valueOf(id));
		model.addAttribute("owner", sesionOwner);
		if (sesionWalker == null) {
			model.addAttribute("walker", sesionWalker);
			return "redirect:/feedback/ListaPaseadores";
		} else {
			model.addAttribute("listaFeedbacks",
					fService.FeedbackByIdWalker(String.valueOf(sesionWalker.getIdWalker())));
			model.addAttribute("Feedback", new Feedback());
			model.addAttribute("claseComentario", "form-control item");
			model.addAttribute("mensajeComentario", "");

			return "listFeedbacks";
		}
	}

	@RequestMapping("/ActualizarComentarios")
	public String modificar(Model model) throws ParseException {
		model.addAttribute("listaFeedbacks", fService.FeedbackByIdWalker(String.valueOf(sesionWalker.getIdWalker())));
		model.addAttribute("Feedback", new Feedback());
		model.addAttribute("owner", sesionOwner);
		model.addAttribute("claseComentario", "form-control item");
		model.addAttribute("mensajeComentario", "");
		return "listFeedbacks";
	}
	
	@RequestMapping("/ActualizarComentarioss")
	public String modificarError(Model model) throws ParseException {
		model.addAttribute("listaFeedbacks", fService.FeedbackByIdWalker(String.valueOf(sesionWalker.getIdWalker())));
		model.addAttribute("Feedback", new Feedback());
		model.addAttribute("owner", sesionOwner);
		model.addAttribute("claseComentario", "form-control item alert-danger");
		model.addAttribute("mensajeComentario", "Solo se permite como máximo 200 caracteres en el comentario");
		return "listFeedbacks";
	}

	@RequestMapping("/Crear")
	public String registrar(@Valid Feedback objFeedback, BindingResult binRes, Model model) throws ParseException {
		if (binRes.hasErrors()) {
			model.addAttribute("walker", sesionWalker);
			model.addAttribute("owner", sesionOwner);
			

			return "redirect:/feedback/ActualizarComentarioss";
		} else {

			
				
				objFeedback.setOwner(sesionOwner);
				objFeedback.setWalker(sesionWalker);
				boolean flag = fService.save(objFeedback);
				model.addAttribute("claseComentario", "form-control item");

				if (flag) {
					return "redirect:/feedback/ActualizarComentarios";
				} else {
					model.addAttribute("mensaje", "Ocurrio un error");
					return "redirect:/feedback/ActualizarComentarios";
				}

			
		}
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public void setOwner(Owner o) {
		this.sesionOwner = o;
	}

}
