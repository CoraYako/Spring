package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.LibroRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional(rollbackFor = Exception.class)
    public Libro crearYGuardar(Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            Autor autor, Editorial editorial) throws ErrorInputException {
        validacion(isbn, titulo, anio, ejemplares, prestados, restantes);

        if (autor == null) {
            throw new ErrorInputException("Debe indicar un Autor válido.");
        }
        if (editorial == null) {
            throw new ErrorInputException("Debe indicar una Editorial válida.");
        }

        Libro l = new Libro();
        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setAnio(anio);
        l.setEjemplares(ejemplares);
        l.setPrestados(prestados);
        l.setRestantes(restantes);
        l.setAutor(autor);
        l.setEditorial(editorial);
        l.setAlta(new Date());
        l.setActivo(true);

        return libroRepositorio.save(l);
    }

    @Transactional(rollbackFor = Exception.class)
    public Libro modificar(String idLibro, String idAutor, String idEditorial,
            Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes) throws ErrorInputException, ElementoNoEncontradoException {
        validacion(isbn, titulo, anio, ejemplares, prestados, restantes);

        Autor autor = autorServicio.buscarPorId(idAutor);
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(idLibro);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setPrestados(prestados);
            libro.setRestantes(restantes);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            return libroRepositorio.save(libro);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Libro solicitado.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("El identificador proporcionado para el Libro no es correcto.");
        }
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setActivo(false);
            libroRepositorio.save(libro);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Libro solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("El identificador proporcionado para el Libro no es correcto.");
        }
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Libro solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Libro> listarActivos() {
        return libroRepositorio.buscarActivos();
    }

    private void validacion(Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes/*,
            Autor autor, Editorial editorial*/) throws ErrorInputException {
        if (isbn == null || isbn < 1) {
            throw new ErrorInputException("Debe indicar el ISBN correcto.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un título válido.");
        }
        if (anio == null || anio < 1000) {
            throw new ErrorInputException("Debe indicar un año válido.");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new ErrorInputException("Debe indicar una cantidad válidad de ejempares disponibles.");
        }
        if (prestados == null || prestados < 0) {
            throw new ErrorInputException("Debe indicar una cantidad igual o mayor a cero (0)");
        }
        if (restantes == null || restantes < 0) {
            throw new ErrorInputException("Debe indicar una cantidad igual o mayor a cero (0).");
        }
        /*if (autor == null) {
            throw new ErrorInputException("Debe indicar un Autor existente o válido.");
        }
        if (editorial == null) {
            throw new ErrorInputException("Debe indicar una Editorial existente o válida.");
        }*/
    }

}
