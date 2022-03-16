package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.AutorRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public Autor crearYGuardar(String nombre) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para el Autor.");
        }
        Autor a = new Autor();
        a.setNombre(nombre);
        a.setAlta(new Date());
        a.setActivo(true);
        return autorRepositorio.save(a);
    }

    @Transactional(rollbackFor = Exception.class)
    public Autor modificar(String id, String nombre) throws ErrorInputException, ElementoNoEncontradoException {
        validacion(id, nombre);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setNombre(nombre);
            return autorRepositorio.save(a);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Autor solicitado.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Autor deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un identificador válido para el Autor.");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setActivo(false);
            return autorRepositorio.save(a);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Autor solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un identificador válido para el Autor.");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Autor solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarPorNombre(String nombre) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para el Autor");
        }
        return autorRepositorio.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Autor> listarActivos() {
        return autorRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Autor> listarTodos() {
        return autorRepositorio.findAll();
    }

    private void validacion(String id, String nombre) throws ErrorInputException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un identificador válido para el Autor.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para el Autor.");
        }
    }

}
