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

    @GetMapping("/registrar-editar")
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
