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
    public Libro crearYGuardar(String isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            String idAutor, String idEditorial) throws ErrorInputException, ElementoNoEncontradoException {
        Autor autor = autorServicio.buscarPorId(idAutor);
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);

        validacion(isbn, titulo, anio, ejemplares, prestados, restantes, autor, editorial);

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
            String isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes) throws ErrorInputException, ElementoNoEncontradoException {
        Autor autor = autorServicio.buscarPorId(idAutor);
        Editorial editorial = editorialServicio.buscarPorId(idEditorial);

        validacion(isbn, titulo, anio, ejemplares, prestados, restantes, autor, editorial);

        Libro libro = buscarPorId(idLibro);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setPrestados(prestados);
        libro.setRestantes(restantes);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        return libroRepositorio.save(libro);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        Libro libro = buscarPorId(id);

        libro.setActivo(false);

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public Libro buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("El identificador proporcionado para el libro no es correcto.");
        }
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el libro solicitado.");
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

    private void validacion(String isbn, String titulo, Integer anio,
            Integer ejemplares, Integer prestados, Integer restantes,
            Autor autor, Editorial editorial) throws ErrorInputException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ErrorInputException("El código ISBN no debe estar vacío.");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new ErrorInputException("El título no debe quedar vacío.");
        }
        if (anio == null || anio < 1000) {
            throw new ErrorInputException("El año no puede estar vacío.");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new ErrorInputException("La cantidad de ejempares no puede ser inferior a cero.");
        }
        if (prestados == null || prestados < 0) {
            throw new ErrorInputException("La cantidad de ejempares prestados no puede ser inferior a cero.");
        }
        if (restantes == null || restantes < 0) {
            throw new ErrorInputException("La cantidad de ejempares restantes no puede ser inferior a cero.");
        }
        if (autor == null) {
            throw new ErrorInputException("Debe seleccionar un autor.");
        }
        if (editorial == null) {
            throw new ErrorInputException("Debe indicar una Editorial.");
        }
    }

}
