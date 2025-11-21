package cl.ipss.saborgourmet.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ipss.saborgourmet.models.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{

  // Antes: boolean existsByMesaId(Long id);
  // Ahora: verifica si existe reserva para la misma mesa y fecha
  boolean existsByMesaIdAndFecha(Long mesaId, LocalDate fecha);

  // Nuevo: verifica existencia de reserva para misma mesa+fecha excluyendo una id (útil al editar)
  boolean existsByMesaIdAndFechaAndIdNot(Long mesaId, LocalDate fecha, Long id);
}
