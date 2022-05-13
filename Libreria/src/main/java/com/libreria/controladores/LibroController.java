package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.entidades.Editorial;
import com.libreria.entidades.Libro;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.AutorServicio;
import com.libreria.servicios.EditorialServicio;
import com.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarTodos();

        modelo.put("libros", libros);

        return "libro-lista.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam(required = false) String id) {
        List<Autor> autores = autorServicio.listarTodos();
        List<Editorial> editoriales = editorialServicio.listarTodos();

        Libro libro = new Libro();

        try {
            if (id != null && !id.trim().isEmpty()) {
                libro = libroServicio.buscarPorId(id);
            }

            modelo.put("libro", libro);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);

        return "libro-registro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id) {
        List<Autor> autores = autorServicio.listarTodos();
        List<Editorial> editoriales = editorialServicio.listarTodos();

        Libro libro = new Libro();

        try {
            if (id != null && !id.trim().isEmpty()) {
                libro = libroServicio.buscarPorId(id);
            }

            modelo.put("libro", libro);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);

        return "libro-registro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/deshabilitar/{id}")
    public String deshabilitarGet(ModelMap modelo, @PathVariable String id) {
        List<Autor> autores = autorServicio.listarTodos();
        List<Editorial> editoriales = editorialServicio.listarTodos();

        Libro libro = new Libro();

        try {
            if (id != null && !id.trim().isEmpty()) {
                libro = libroServicio.buscarPorId(id);
            }

            modelo.put("libro", libro);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);

        return "libro-registro.html";
    }

    @PostMapping("/registrar-editar")
    public String registroEdicion(ModelMap modelo, @RequestParam(required = false) String id,
            @RequestParam String isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam(required = false) Integer prestados,
            @RequestParam(required = false) Integer restantes, @RequestParam String idAutor, @RequestParam String idEditorial) {
        List<Autor> autores = autorServicio.listarTodos();
        List<Editorial> editoriales = editorialServicio.listarTodos();

        Libro libro = new Libro();
        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        try {
            autor = autorServicio.buscarPorId(idAutor);
            editorial = editorialServicio.buscarPorId(idEditorial);

            if (id == null || id.trim().isEmpty()) {
                libro = libroServicio.crearYGuardar(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
            } else {
                libro = libroServicio.modificar(id, idAutor, idEditorial, isbn,
                        titulo, anio, ejemplares, prestados, restantes);
            }

            modelo.put("libro", libro);
            modelo.put("guardado", "!Perfecto!");
            modelo.put("descripcion", "El Libro fue guardado satisfactoriamente en la base de datos.");
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            libro.setId(id);
            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            libro.setPrestados(prestados);
            libro.setRestantes(restantes);

            modelo.put("libro", libro);
            modelo.put("error", ex.getMessage());
            modelo.put("error", ex.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("prestados", prestados);
            modelo.put("restantes", restantes);
        }

        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);

        return "libro-registro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/deshabilitar")
    public String deshabilitarPost(ModelMap modelo, @RequestParam String id,
            @RequestParam String idAutor, @RequestParam String idEditorial) {
        List<Autor> autores = autorServicio.listarTodos();
        List<Editorial> editoriales = editorialServicio.listarTodos();

        try {
            autorServicio.buscarPorId(idAutor);
            editorialServicio.buscarPorId(idEditorial);

            modelo.put("autores", autores);
            modelo.put("editoriales", editoriales);

            if (id == null || id.trim().isEmpty()) {
                libroServicio.deshabilitar(id);
            }

        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "redirect:/libro/lista";
    }

}
