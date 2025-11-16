package cl.ipss.saborgourmet.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Mesa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Min(value = 1, message = "El número de mesa debe ser mayor a 0")
	private int numero;

	@Min(value = 1, message = "El número de capacidad debe ser mayor a 0")
	private int capacidad;

	@NotBlank(message= "El tipo de mesa es obligatorio")
	private String tipo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mesa", cascade = { CascadeType.ALL })
	private List<Reserva> reservas;

	public Mesa() {
	}

	public Mesa(long id, int numero, int capacidad, String tipo, List<Reserva> reservas) {
		this.id = id;
		this.numero = numero;
		this.capacidad = capacidad;
		this.tipo = tipo;
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

}
