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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.ipss.saborgourmet.models.Mesa;
import cl.ipss.saborgourmet.models.Reserva;
import cl.ipss.saborgourmet.repositories.ReservaRepository; // <-- nuevo import
import cl.ipss.saborgourmet.services.MesaService;
import cl.ipss.saborgourmet.services.ReservaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

  @Autowired
  private ReservaService reservaService;

  @Autowired
  private MesaService mesaService;

  @Autowired
  private ReservaRepository reservaRepository; // <-- inyectado para validaciones

  @GetMapping
  public String listarReservas(Model model) {
    try {
      model.addAttribute("reservas", reservaService.ObtenerReservas());
    } catch (Exception e) {
      model.addAttribute("error", "Ocurrió un error al cargar las reservas");
    }
    return "reservas/listar";
  }

  @GetMapping("/agregar")
  public String obtenerVistaAgregar(Model model) {
    model.addAttribute("reserva", new Reserva());
    model.addAttribute("mesas", mesaService.ObtenerMesas());
    return "reservas/crear";
  }

  @PostMapping("/agregar")
  public String crearReserva(@Valid @ModelAttribute Reserva reserva, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
    if (result.hasErrors()) {
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/crear";
    }

    if (reserva.getMesa() == null || reserva.getMesa().getId() <= 0L) {
      result.rejectValue("mesa", "error.mesa", "Seleccione una mesa");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/crear";
    }

    Mesa mesaSeleccionada = mesaService.obtenerMesaId(reserva.getMesa().getId());
    if (mesaSeleccionada == null) {
      result.rejectValue("mesa", "error.mesa", "Mesa no encontrada");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/crear";
    }
    reserva.setMesa(mesaSeleccionada);

    // Nueva validación: evitar reservas duplicadas mesa+fecha al crear
    if (reserva.getFecha() != null && reservaRepository.existsByMesaIdAndFecha(mesaSeleccionada.getId(), reserva.getFecha())) {
      result.reject("error.global", "Ya existe una reserva para esa mesa en la misma fecha");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/crear";
    }

    try {
      reservaService.crearReserva(reserva);
      redirectAttrs.addFlashAttribute("success", "Reserva creada correctamente.");
    } catch (Exception e) {
      result.reject("error.global", "Ocurrió un error al intentar crear la reserva");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/crear";
    }

    return "redirect:/reservas";
  }

  @GetMapping("/editar/{id}")
  public String obtenerReserva(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
    Reserva existente = reservaService.obtenerReservaId(id);
    if (existente == null) {
      redirectAttrs.addFlashAttribute("error", "No se encontró la reserva solicitada.");
      return "redirect:/reservas";
    }
    model.addAttribute("reserva", existente);
    model.addAttribute("mesas", mesaService.ObtenerMesas());
    return "reservas/editar";
  }

  @PostMapping("/editar/{id}")
  public String editarReserva(@PathVariable Long id, @Valid @ModelAttribute Reserva reserva, BindingResult result, Model model, RedirectAttributes redirectAttrs) {
    if (result.hasErrors()) {
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/editar";
    }

    if (reservaService.obtenerReservaId(id) == null) {
      result.reject("error.global", "No se encontró la reserva a editar");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/editar";
    }

    if (reserva.getMesa() == null || reserva.getMesa().getId() <= 0L) {
      result.rejectValue("mesa", "error.mesa", "Seleccione una mesa");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/editar";
    }
    Mesa mesaSeleccionada = mesaService.obtenerMesaId(reserva.getMesa().getId());
    if (mesaSeleccionada == null) {
      result.rejectValue("mesa", "error.mesa", "Mesa no encontrada");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/editar";
    }
    reserva.setMesa(mesaSeleccionada);

    if (reserva.getFecha() != null && reservaRepository.existsByMesaIdAndFechaAndIdNot(mesaSeleccionada.getId(), reserva.getFecha(), id)) {
      result.reject("error.global", "Ya existe otra reserva para esa mesa en la misma fecha");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/editar";
    }

    try {
      reserva.setId(id);
      reservaService.crearReserva(reserva);
      redirectAttrs.addFlashAttribute("success", "Reserva actualizada correctamente.");
    } catch (Exception e) {
      result.reject("error.global", "Ocurrió un error al intentar guardar la reserva");
      model.addAttribute("mesas", mesaService.ObtenerMesas());
      return "reservas/editar";
    }

    return "redirect:/reservas";
  }

  @GetMapping("/eliminar/{id}")
  public String eliminarReserva(@PathVariable Long id, RedirectAttributes redirectAttrs) {

    if (!reservaService.existeReservaId(id)) {
      redirectAttrs.addFlashAttribute("error", "No se encontró la reserva indicada.");
      return "redirect:/reservas";
    }

    try {
      reservaService.eliminarReserva(id);
      redirectAttrs.addFlashAttribute("success", "Reserva eliminada correctamente.");
    } catch (Exception e) {
      redirectAttrs.addFlashAttribute("error", "Ocurrió un error al intentar eliminar la reserva.");
    }

    return "redirect:/reservas";
  }
}
