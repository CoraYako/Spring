package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AutorController {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/autor/lista")
    public String inicio(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarTodos();
        modelo.put("autores", autores);
        return "autor-lista.html";
    }

    @GetMapping("/autor/registro")
    public String registro() {
        return "autor-registro.html";
    }

    @PostMapping("/autor/registro")
    public String registroYPersistencia(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido) {
        try {
            autorServicio.crearYGuardar(nombre, apellido);
        } catch (ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "autor-registro.html";
    }

}
