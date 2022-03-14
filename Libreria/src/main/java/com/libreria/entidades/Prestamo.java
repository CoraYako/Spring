package com.libreria.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_prestamo")
    private String id;

    @Temporal(TemporalType.DATE)
    @Column(name = "prestamo", nullable = false)
    private Date prestamo;

    @Temporal(TemporalType.DATE)
    @Column(name = "devolucion", nullable = false)
    private Date devolucion;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "libro", nullable = false)
    private List<Libro> libros;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "activo")
    private Boolean activo;

    public Prestamo(String id, Date prestamo, Date devolucion, List<Libro> libros, Cliente cliente, Boolean activo) {
        this.id = id;
        this.prestamo = prestamo;
        this.devolucion = devolucion;
        this.libros = libros;
        this.cliente = cliente;
        this.activo = activo;
    }

    public Prestamo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Date prestamo) {
        this.prestamo = prestamo;
    }

    public Date getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Date devolucion) {
        this.devolucion = devolucion;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", prestamo=" + prestamo + ", devolucion=" + devolucion + ", libros=" + libros + ", cliente=" + cliente + ", activo=" + activo + '}';
    }

}
