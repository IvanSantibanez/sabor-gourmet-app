package cl.ipss.saborgourmet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.ipss.saborgourmet.models.Mesa;
import cl.ipss.saborgourmet.models.Reserva;
import cl.ipss.saborgourmet.services.MesaService;
import cl.ipss.saborgourmet.services.ReservaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/mesas")
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
      model.addAttribute("error", "Ocurrió un error al cargar las mesas.");
    }
    return "mesas/listar";
  }

  @GetMapping("/agregar")
  public String obtenerVistaAgregar(Model model) {
    model.addAttribute("mesa", new Mesa());
    return "mesas/crear";
  }

  @PostMapping("/agregar")
  public String crearMesa(@Valid @ModelAttribute Mesa mesa, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
    if (result.hasErrors()) {
      return "mesas/crear";
    }

    // validar número único
    try {
      if (mesaService.existeNumMesa(mesa.getNumero())) {
        result.rejectValue("numero", "error.numero", "Ya existe una mesa con ese número.");
      }
    } catch (Exception e) {
      result.reject("error.global", "Error al validar número de mesa.");
    }

    if (result.hasErrors()) {
      return "mesas/crear";
    }

    try {
      mesaService.crearMesa(mesa);
      redirectAttrs.addFlashAttribute("success", "Mesa creada correctamente.");
    } catch (Exception e) {
      model.addAttribute("error", "No se pudo crear la mesa.");
      return "mesas/crear";
    }

    return "redirect:/mesas";
  }

  @GetMapping("/editar/{id}")
  public String obtenerMesa(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
    Mesa existente = mesaService.obtenerMesaId(id);
    if (existente == null) {
      redirectAttrs.addFlashAttribute("error", "No se encontró la mesa solicitada.");
      return "redirect:/mesas";
    }
    model.addAttribute("mesa", existente);
    return "mesas/editar";
  }

  @PostMapping("/editar/{id}")
  public String editarMesa(@PathVariable Long id, @Valid @ModelAttribute Mesa mesa, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
    // comprobar existencia
    Mesa existente = mesaService.obtenerMesaId(id);
    if (existente == null) {
      result.reject("error.global", "No se encontró la mesa a editar.");
    }

    if (result.hasErrors()) {
      return "mesas/editar";
    }

    // validar unicidad del número solo si cambió
    try {
      if (existente != null && existente.getNumero() != mesa.getNumero()) {
        if (mesaService.existeNumMesa(mesa.getNumero())) {
          result.rejectValue("numero", "error.numero", "Ya existe una mesa con ese número.");
        }
      }
    } catch (Exception e) {
      result.reject("error.global", "Error al validar número de mesa.");
    }

    if (result.hasErrors()) {
      return "mesas/editar";
    }

    try {
      mesa.setId(id);
      mesaService.crearMesa(mesa); // save/update
      redirectAttrs.addFlashAttribute("success", "Mesa actualizada correctamente.");
    } catch (Exception e) {
      model.addAttribute("error", "No se pudo guardar la mesa.");
      return "mesas/editar";
    }

    return "redirect:/mesas";
  }

  @GetMapping("/eliminar/{id}")
  public String eliminarMesa(@PathVariable Long id, RedirectAttributes redirectAttrs) {

    Mesa existente = mesaService.obtenerMesaId(id);
    if (existente == null) {
      redirectAttrs.addFlashAttribute("error", "No se encontró la mesa indicada.");
      return "redirect:/mesas";
    }

    // comprobar si hay reservas asociadas -> evitar eliminación
    try {
      List<Reserva> reservas = reservaService.ObtenerReservas();
      boolean tieneReservas = reservas.stream()
          .anyMatch(r -> r.getMesa() != null && r.getMesa().getId() == id);
      if (tieneReservas) {
        redirectAttrs.addFlashAttribute("error", "No se puede eliminar: existen reservas asociadas a esta mesa.");
        return "redirect:/mesas";
      }
    } catch (Exception e) {
      // si falla la comprobación, evitar eliminar y notificar
      redirectAttrs.addFlashAttribute("error", "Ocurrió un error al verificar reservas asociadas.");
      return "redirect:/mesas";
    }

    try {
      mesaService.eliminarMesa(id);
      redirectAttrs.addFlashAttribute("success", "Mesa eliminada correctamente.");
    } catch (Exception e) {
      redirectAttrs.addFlashAttribute("error", "Ocurrió un error al eliminar la mesa.");
    }

    return "redirect:/mesas";
  }
}
