package com.libreria.entidades;

import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "foto")
public class Foto implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_foto")
    private String id;

    @Column(name = "tipo_mime", nullable = false)
    private String mime;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombre;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "contenido", nullable = false)
    private byte[] contenido;

    public Foto(String id, String mime, String nombre, byte[] contenido) {
        this.id = id;
        this.mime = mime;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public Foto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Foto{" + "id=" + id + ", mime=" + mime + ", nombre=" + nombre + ", contenido=" + Arrays.toString(contenido) + '}';
    }
}
