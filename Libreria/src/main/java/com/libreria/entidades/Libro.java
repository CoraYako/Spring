package com.libreria.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "libros")
public class Libro implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_libro")
    private String id;

    @Column(name = "isbn", unique = true, nullable = false)
    private Integer isbn;

    @Column(name = "titulo", nullable = false, unique = true)
    private String titulo;

    @Column(name = "año", nullable = false)
    private Integer anio;

    @Column(name = "ejemplares", nullable = false)
    private Integer ejemplares;

    @Column(name = "prestados", nullable = false)
    private Integer prestados;

    @Column(name = "restantes", nullable = false)
    private Integer restantes;

    @JoinColumn(name = "autor", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Autor autor;

    @JoinColumn(name = "editorial", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Editorial editorial;

    @Temporal(TemporalType.TIME)
    @Column(name = "fecha_alta", nullable = false)
    private Date alta;

    @Column(name = "activo")
    private Boolean activo;

    public Libro(String id, Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            Autor autor, Editorial editorial, Date alta, Boolean activo) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.anio = anio;
        this.ejemplares = ejemplares;
        this.prestados = prestados;
        this.restantes = restantes;
        this.autor = autor;
        this.editorial = editorial;
        this.alta = alta;
        this.activo = activo;
    }

    public Libro() {
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getEjemplares() {
        return ejemplares;
    }

    public void setEjemplares(Integer ejemplares) {
        this.ejemplares = ejemplares;
    }

    public Integer getPrestados() {
        return prestados;
    }

    public void setPrestados(Integer prestados) {
        this.prestados = prestados;
    }

    public Integer getRestantes() {
        return restantes;
    }

    public void setRestantes(Integer restantes) {
        this.restantes = restantes;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", isbn=" + isbn + ", titulo=" + titulo + ", anio=" + anio + ", ejemplares=" + ejemplares + ", prestados=" + prestados + ", restantes=" + restantes + ", autor=" + autor + ", editorial=" + editorial + ", alta=" + alta + ", activo=" + activo + '}';
    }

}
