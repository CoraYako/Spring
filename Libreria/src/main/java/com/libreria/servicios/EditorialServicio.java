package com.libreria.servicios;

import com.libreria.entidades.Editorial;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.EditorialRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public Editorial crearYGuardar(String nombre) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para la Editorial.");
        }
        Editorial e = new Editorial();
        e.setNombre(nombre);
        e.setAlta(new Date());
        e.setActivo(true);
        return editorialRepositorio.save(e);
    }

    @Transactional(rollbackFor = Exception.class)
    public Editorial modificar(String id, String nombre) throws ErrorInputException, ElementoNoEncontradoException {
        validacion(id, nombre);

        Editorial e = buscarPorId(id);

        e.setNombre(nombre);

        return editorialRepositorio.save(e);
    }

    @Transactional(rollbackFor = Exception.class)
    public Editorial deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        Editorial e = buscarPorId(id);

        e.setActivo(false);

        return editorialRepositorio.save(e);
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un identificador válido para la Editorial.");
        }
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró la Editorial solicitada.");
        }
    }

    @Transactional(readOnly = true)
    public Editorial buscarPorNombre(String nombre) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para la Editorial.");
        }
        return editorialRepositorio.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarActivos() {
        return editorialRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarTodos() {
        return editorialRepositorio.findAll();
    }

    private void validacion(String id, String nombre) throws ErrorInputException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un identificador válido para la Editorial.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para la Editorial.");
        }
    }

}
