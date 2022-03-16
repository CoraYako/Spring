package com.libreria.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "editoriales")
public class Autor implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_autor")
    private String id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_alta", nullable = false)
    private Date alta;

    public Autor(String id, String nombre, Date alta, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.alta = alta;
        this.activo = activo;
    }

    public Autor() {
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Autor{" + "id=" + id + ", nombre=" + nombre + ", activo=" + activo + ", alta=" + alta + '}';
    }

}
