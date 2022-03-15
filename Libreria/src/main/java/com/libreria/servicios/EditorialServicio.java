package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.excepciones.ErrorServicio;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    public Editorial crearYGuardar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un nombre válido para la Editorial.");
        }
        Editorial e = null;
        try {
            e = new Editorial();
            e.setNombre(nombre);
            e.setActivo(true);
            e = editorialRepositorio.save(e);
        } catch (Exception ex) {
            throw ex;
        }
        return e;
    }

    public void modificar(String id, String nombre) throws ErrorServicio {
        validacion(id, nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial e = respuesta.get();
            e.setNombre(nombre);
            editorialRepositorio.save(e);
        } else {
            throw new ErrorServicio("No se encontró la Editorial solicitada.");
        }
    }

    public void deshabilitar(String id) throws ErrorServicio {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un identificador válido para la Editorial.");
        }

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial e = respuesta.get();
            e.setActivo(false);
            editorialRepositorio.save(e);
        } else {
            throw new ErrorServicio("No se encontró la Editorial solicitada.");
        }
    }

    public Editorial buscarPorNombre(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un nombre válido para la Editorial.");
        }
        return editorialRepositorio.buscarPorNombre(nombre);
    }

    public List<Editorial> listarActivos() {
        return editorialRepositorio.buscarActivos();
    }

    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }

    private void validacion(String id, String nombre) throws ErrorServicio {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un identificador válido para la Editorial.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un nombre válido para la Editorial.");
        }
    }

}
