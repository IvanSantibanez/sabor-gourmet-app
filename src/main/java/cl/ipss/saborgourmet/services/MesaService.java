package cl.ipss.saborgourmet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ipss.saborgourmet.models.Mesa;
import cl.ipss.saborgourmet.repositories.MesaRespository;

@Service
public class MesaService {

  @Autowired
  private MesaRespository mesaRespository;

  public Mesa crearMesa(Mesa mesa) {
    return mesaRespository.save(mesa);
  }

  public List<Mesa> ObtenerMesas() {
    return mesaRespository.findAll();
  }

  public Mesa obtenerMesaId(Long id) {
    return mesaRespository.findById(id).orElse(null);
  }

  public void eliminarMesa(Long id) {
    mesaRespository.deleteById(id);
  }

  public boolean existeNumMesa(int numero) {
    return mesaRespository.existsByNumero(numero);
  }

}
