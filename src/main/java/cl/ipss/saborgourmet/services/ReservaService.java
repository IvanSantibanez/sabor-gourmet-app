package cl.ipss.saborgourmet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ipss.saborgourmet.models.Reserva;
import cl.ipss.saborgourmet.repositories.ReservaRepository;

@Service
public class ReservaService {

  @Autowired
  private ReservaRepository reservaRepository;

  public Reserva crearReserva(Reserva reserva) {
    return reservaRepository.save(reserva);
  }

  public List<Reserva> ObtenerReservas() {
    return reservaRepository.findAll();
  }

  public Reserva obtenerReservaId(Long id) {
    return reservaRepository.findById(id).orElse(null);
  }

  public void eliminarReserva(Long id) {
    reservaRepository.deleteById(id);
  }

  public boolean existeReservaId(Long id) {
    return reservaRepository.existsById(id);
  }

  // comprobar si existen reservas asociadas a una mesa (usa findAll para compatibilidad)
  public boolean existeMesa(Long mesaId) {
    if (mesaId == null) {
      return false;
    }
    return reservaRepository.findAll().stream()
        .anyMatch(r -> r.getMesa() != null && r.getMesa().getId() == mesaId);
  }
}
