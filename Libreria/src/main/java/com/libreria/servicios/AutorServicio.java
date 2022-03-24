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
    public Autor crearYGuardar(String nombre, String apellido) throws ErrorInputException {
        validacion(nombre, apellido);
        Autor a = new Autor();
        a.setNombre(nombre);
        a.setApellido(apellido);
        a.setAlta(new Date());
        a.setActivo(true);
        return autorRepositorio.save(a);
    }

    @Transactional(rollbackFor = Exception.class)
    public Autor modificar(String id, String nombre, String apellido) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("El ID no puede estar vacío.");
        }
        validacion(nombre, apellido);
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setNombre(nombre);
            a.setApellido(apellido);
            return autorRepositorio.save(a);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el autor solicitado.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Autor deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("El ID no puede estar vacío.");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor a = respuesta.get();
            a.setActivo(false);
            return autorRepositorio.save(a);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el autor solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Autor buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("El ID no puede estar vacío.");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el autor solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> buscarPorNombre(String nombre) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("El nombre no puede estar vacío.");
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

    private void validacion(String nombre, String apellido) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("El nombre no puede estar vacío.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ErrorInputException("El apellido no puede estar vacío.");
        }
    }

}
