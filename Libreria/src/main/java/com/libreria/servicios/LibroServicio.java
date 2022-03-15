package com.libreria.servicios;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.excepciones.ErrorServicio;
import com.libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;

    public Libro crearYGuardar(Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            Autor autor, Editorial editorial) throws ErrorServicio {
        validacion(isbn, titulo, anio, ejemplares, prestados, restantes, autor, editorial);

        Libro libro = null;
        try {
            libro = new Libro();
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setPrestados(prestados);
            libro.setRestantes(restantes);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setActivo(true);
            libroRepositorio.save(libro);
        } catch (Exception e) {
            throw e;
        }
        return libro;
    }

    public void modificar(String id, Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            Autor autor, Editorial editorial) throws ErrorServicio {
        validacion(isbn, titulo, anio, ejemplares, prestados, restantes, autor, editorial);

        Optional<Libro> respuesta = libroRepositorio.findById(id);
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
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el Libro solicitado.");
        }
    }

    public void deshabilitar(String id) throws ErrorServicio {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorServicio("El identificador proporcionado no es correcto.");
        }
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setActivo(false);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("No se encontró el Libro solicitado.");
        }
    }

    public List<Libro> listarTodos() {
        return libroRepositorio.findAll();
    }

    public List<Libro> listarActivos() {
        return libroRepositorio.buscarActivos();
    }

    private void validacion(Integer isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            Autor autor, Editorial editorial) throws ErrorServicio {
        if (isbn == null || isbn < 1) {
            throw new ErrorServicio("Debe indicar el ISBN correcto.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErrorServicio("Debe indicar un título válido.");
        }
        if (anio == null || anio < 1000) {
            throw new ErrorServicio("Debe indicar un año válido.");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new ErrorServicio("Debe indicar una cantidad válidad de ejempares disponibles.");
        }
        if (prestados == null || prestados < 0) {
            throw new ErrorServicio("Debe indicar una cantidad igual o mayor a cero (0)");
        }
        if (restantes == null || restantes < 0) {
            throw new ErrorServicio("Debe indicar una cantidad igual o mayor a cero (0).");
        }
        if (autor == null) {
            throw new ErrorServicio("Debe indicar un Autor existente o válido.");
        }
        if (editorial == null) {
            throw new ErrorServicio("Debe indicar una Editorial existente o válida.");
        }
    }

}
