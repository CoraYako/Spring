package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.excepciones.ErrorServicio;
import com.libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    public Autor crearYGuardar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un nombre válido para el Autor.");
        }
        Autor a = null;
        try {
            a = new Autor();
            a.setNombre(nombre);
            a.setActivo(true);
            a = autorRepositorio.save(a);
        } catch (Exception e) {
            throw e;
        }
        return a;
    }

    public void modificar(String id, String nombre) throws ErrorServicio {
        validacion(id, nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setNombre(nombre);
            autorRepositorio.save(a);
        } else {
            throw new ErrorServicio("No se encontró el Autor solicitado.");
        }
    }

    public void deshabilitar(String id) throws ErrorServicio {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un identificador válido para el Autor.");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setActivo(false);
            autorRepositorio.save(a);
        } else {
            throw new ErrorServicio("No se encontró el Autor solicitado.");
        }
    }

    public List<Autor> buscarPorNombre(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un nombre válido para el Autor");
        }
        return autorRepositorio.buscarPorNombre(nombre);
    }

    public List<Autor> listarActivos() {
        return autorRepositorio.buscarActivos();
    }

    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }

    private void validacion(String id, String nombre) throws ErrorServicio {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un identificador válido para el Autor.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un nombre válido para el Autor.");
        }
    }

}
