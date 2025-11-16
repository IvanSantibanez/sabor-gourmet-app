package cl.ipss.saborgourmet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ipss.saborgourmet.models.Mesa;

public interface MesaRespository extends JpaRepository<Mesa, Long>{

    boolean existsByNumero(int numero);

}
