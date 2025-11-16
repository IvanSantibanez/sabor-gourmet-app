package cl.ipss.saborgourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ipss.saborgourmet.models.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{

  boolean existsByMesaId(Long id);
}
