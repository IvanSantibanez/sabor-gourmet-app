package cl.ipss.saborgourmet.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int numero;
    private int capacidad;
    private boolean activa;

    @OneToMany(fetch= FetchType.LAZY, mappedBy= "mesa", cascade= {CascadeType.ALL})
    private List<Reserva> reservas;

    public Mesa() {
    }

    public Mesa(long id, int numero, int capacidad, boolean activa, List<Reserva> reservas) {
      this.id = id;
      this.numero = numero;
      this.capacidad = capacidad;
      this.activa = activa;
      this.reservas = reservas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

}
