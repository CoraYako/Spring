package com.libreria.controladores;

import com.libreria.entidades.Autor;
import com.libreria.servicios.AutorServicio;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tarea")
public class AutorRestController {

    private Map<String, Object> response;

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping
    public ResponseEntity<Map<String, Object>> list() {
        response = new HashMap<>();
        response.put("autores", autorServicio.listarTodos());
        return ResponseEntity.status(200).body(response);
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(Autor a) {
        System.out.println(a);
        return null;
    }
    
}
