package cl.ipss.saborgourmet.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
  uniqueConstraints = @UniqueConstraint(name = "uk_reserva_mesa_fecha", columnNames = {"mesa_id", "fecha"})
)
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String correoCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

    public Reserva() {
    }

    public Reserva(Long id, LocalDate fecha, String nombreCliente, String apellidoCliente, String telefonoCliente,
        String correoCliente, Mesa mesa) {
      this.id = id;
      this.fecha = fecha;
      this.nombreCliente = nombreCliente;
      this.apellidoCliente = apellidoCliente;
      this.telefonoCliente = telefonoCliente;
      this.correoCliente = correoCliente;
      this.mesa = mesa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

}
