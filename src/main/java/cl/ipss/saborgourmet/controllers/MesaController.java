package cl.ipss.saborgourmet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.ipss.saborgourmet.models.Mesa;
import cl.ipss.saborgourmet.services.MesaService;
import cl.ipss.saborgourmet.services.ReservaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/mesas" })
public class MesaController {

  @Autowired
  private MesaService mesaService;

  @Autowired
  private ReservaService reservaService;

  @GetMapping
  public String listarMesas(Model model) {

    try {
      model.addAttribute("mesas", mesaService.ObtenerMesas());
    } catch (Exception e) {
      model.addAttribute("error", "Ocurrió un error al cargar las mesas");
    }
    return "mesas/listar";
  }

  @GetMapping("/agregar")
  public String obtenerVistaAgregar(Model model) {

    model.addAttribute("mesa", new Mesa());
    return "mesas/crear";
  }

  @PostMapping("/agregar")
  public String crearMesa(@Valid @ModelAttribute Mesa mesa, BindingResult result, Model model) {

    buscarMesaPorNumero(mesa, result);

    if (result.hasErrors()) {
      return "mesas/crear";
    }

    try {
      mesaService.crearMesa(mesa);
    } catch (Exception e) {
      result.reject("error.global", "Ocurrió un error al intentar crear la mesa");
      return "mesas/crear";
    }

    return "redirect:/mesas";
  }

  @GetMapping("/editar/{id}")
  public String obtenerMesa(@PathVariable Long id, Model model) {

    try {
      model.addAttribute("mesa", mesaService.obtenerMesaId(id));
    } catch (Exception e) {
      model.addAttribute("error", "Ocurrió un error al obtener la mesa");
      return "mesas.listar";
    }
    return "mesas/editar";
  }

  @GetMapping("/eliminar/{id}")
  public String eliminarMesa(@PathVariable Long id, BindingResult result) {

    buscarMesaPorId(id, result);
    buscarMesaConReserva(id, result);

    if (result.hasErrors()) {
      return "mesas/editar";
    }

    try {
      mesaService.eliminarMesa(id);
    } catch (Exception e) {
      result.reject("error.global", "Ocurrió un error al intentar eliminar la mesa");
    }

    return "redirect:/mesas";
  }

  private void buscarMesaConReserva(Long id, BindingResult result) {
    try {
      if (reservaService.existeMesa(id)) {
        result.reject("error.global", "Existe una reserva asociada a esta mesa");
      }
    } catch (Exception e) {
      result.reject("error.global", "Ocurrió un error al verificar la existencia de la mesa");

    }
  }

  private void buscarMesaPorId(Long id, BindingResult result) {
    try {
      if (mesaService.obtenerMesaId(id) == null) {
        result.reject("error.global", "No se encontró una mesa asociada a este id");
      }

    } catch (Exception e) {
      result.reject("error.global", "Ocurrió un error al verificar la existencia de la mesa");
    }
  }

  private void buscarMesaPorNumero(Mesa mesa, BindingResult result) {
    try {
      if (mesaService.existeNumMesa(mesa.getNumero())) {
        result.rejectValue("numero", "error.numero", "Ya existe una mesa con asociada a este número");
      }
    } catch (Exception e) {
      result.rejectValue("numero", "error.numero", "Ocurrió un error al verificar el número de mesa");
    }
  }

}
