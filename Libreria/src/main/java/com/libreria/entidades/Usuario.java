package com.libreria.entidades;

import com.libreria.enumeradores.Genero;
import com.libreria.enumeradores.Rol;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id_usuario")
    private String id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "mail", nullable = false, unique = true)
    private String mail;

    @Column(name = "clave", nullable = false, unique = true)
    private String clave;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_usuario")
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_alta")
    private Date alta;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_baja")
    private Date baja;

    @OneToOne
    @JoinColumn(name = "foto_perfil")
    private Foto foto;

    public Usuario(String id, String nombre, String apellido, String mail, String clave, Rol rol, Genero genero, Date alta, Date baja, Foto foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.clave = clave;
        this.rol = rol;
        this.genero = genero;
        this.alta = alta;
        this.baja = baja;
        this.foto = foto;
    }

    public Usuario() {
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Date getBaja() {
        return baja;
    }

    public void setBaja(Date baja) {
        this.baja = baja;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", mail=" + mail + ", clave=" + clave + ", rol=" + rol + ", genero=" + genero + ", alta=" + alta + ", baja=" + baja + ", foto=" + foto + '}';
    }

}
