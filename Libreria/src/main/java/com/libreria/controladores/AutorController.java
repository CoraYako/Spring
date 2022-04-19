package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarTodos();

        modelo.put("autores", autores);

        return "autor-lista.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam(required = false) String id) {
        Autor autor = new Autor();

        try {
            if (id != null && !id.trim().isEmpty()) {
                autor = autorServicio.buscarPorId(id);
            }
            modelo.put("autor", autor);
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "autor-registro.html";
    }

    @GetMapping("/editar/{id}")
    public String editar(ModelMap modelo, @PathVariable String id) {
        Autor autor = new Autor();

        try {
            if (id != null && !id.trim().isEmpty()) {
                autor = autorServicio.buscarPorId(id);
            }
            modelo.put("autor", autor);
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "autor-registro.html";
    }

    @GetMapping("/deshabilitar/{id}")
    public String deshabilitarGet(ModelMap modelo, @PathVariable String id) {
        Autor autor = new Autor();

        try {
            if (id != null && !id.trim().isEmpty()) {
                autor = autorServicio.buscarPorId(id);
            }
            modelo.put("autor", autor);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "autor-deshabilitar.html";
    }

    @PostMapping("/deshabilitar")
    public String deshabilitarPost(ModelMap modelo, @RequestParam String id) {
        try {
            autorServicio.deshabilitar(id);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "redirect:/autor/lista";
    }

    @PostMapping("/registrar-editar")
    public String registroEdicion(ModelMap modelo, @RequestParam(required = false) String id,
            @RequestParam String nombre, @RequestParam String apellido) {
        Autor autor = new Autor();

        try {
            if (id == null || id.trim().isEmpty()) {
                autor = autorServicio.crearYGuardar(nombre, apellido);
            } else {
                autor = autorServicio.modificar(id, nombre, apellido);
            }

            modelo.put("autor", autor);
            modelo.put("titulo", "!Perfecto!");
            modelo.put("descripcion", "El autor fue guardado satisfactoriamente.");
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            autor.setId(id);
            autor.setNombre(nombre);

            modelo.put("autor", autor);
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
        }

        return "autor-registro.html";
    }

}
