package cl.ipss.saborgourmet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ipss.saborgourmet.repositories.ReservaRepository;

@Service
public class ReservaService {

  @Autowired
  ReservaRepository reservaRepository;

  public boolean existeMesa(Long id){
    return reservaRepository.existsByMesaId(id);
  }
}
