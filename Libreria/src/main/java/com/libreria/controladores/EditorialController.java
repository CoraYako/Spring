package com.libreria.controladores;

import com.libreria.entidades.Editorial;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialController {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/lista")
    public String inicio(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarTodos();

        modelo.put("editoriales", editoriales);

        return "editorial-lista.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/registrar")
    public String registro(ModelMap modelo, @RequestParam(required = false) String id) {
        Editorial editorial = new Editorial();

        try {
            if (id != null && !id.trim().isEmpty()) {
                editorial = editorialServicio.buscarPorId(id);
            }
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        modelo.put("editorial", editorial);
        return "editorial-registro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/editar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id) {
        Editorial editorial = new Editorial();

        try {
            if (id != null && !id.trim().isEmpty()) {
                editorial = editorialServicio.buscarPorId(id);
            }
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        modelo.put("editorial", editorial);
        return "editorial-registro.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/deshabilitar/{id}")
    public String deshabilitarGet(ModelMap modelo, @PathVariable String id) {
        Editorial editorial = new Editorial();

        try {
            if (id != null && !id.trim().isEmpty()) {
                editorial = editorialServicio.buscarPorId(id);
            }
            modelo.put("editorial", editorial);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "editorial-deshabilitar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/deshabilitar")
    public String deshabilitarPost(ModelMap modelo, @RequestParam String id) {
        try {
            editorialServicio.deshabilitar(id);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/editorial/lista";
    }

    @PostMapping("/registrar-editar")
    public String registroEdicion(ModelMap modelo, @RequestParam(required = false) String id, @RequestParam String nombre) {
        Editorial editorial = new Editorial();

        try {
            if (id == null || id.trim().isEmpty()) {
                editorial = editorialServicio.crearYGuardar(nombre);
            } else {
                editorial = editorialServicio.modificar(id, nombre);
            }

            modelo.put("editorial", editorial);
            modelo.put("titulo", "!Perfecto!");
            modelo.put("descripcion", "El Editorial fue guardado satisfactoriamente en la base de datos.");
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            editorial.setId(id);
            editorial.setNombre(nombre);

            modelo.put("editorial", editorial);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
        }

        return "editorial-registro.html";
    }

}
