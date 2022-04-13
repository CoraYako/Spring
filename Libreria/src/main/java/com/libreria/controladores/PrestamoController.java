/*
TODO crear las siguiente vistas:

prestamo-lista
prestamo-registro
*/
package com.libreria.controladores;

import com.libreria.entidades.Cliente;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Prestamo;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.ClienteServicio;
import com.libreria.servicios.LibroServicio;
import com.libreria.servicios.PrestamoServicio;
import java.util.Date;
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
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoServicio prestamoServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo, @RequestParam(required = false) String id) {
        List<Prestamo> prestamos = prestamoServicio.listarTodos();
        
        modelo.put("prestamos", prestamos);
        
        return "prestamo-lista.html";
    }

    @GetMapping("/registrar-editar")
    public String registro(ModelMap modelo, @RequestParam(required = false) String id) {
        Prestamo prestamo = new Prestamo();
        
        try {
            if (id != null && !id.trim().isEmpty()) {
                prestamo = prestamoServicio.buscarPorId(id);
            }
        
            modelo.put("prestamo", prestamo);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
        }
        
        return "prestamo-registro.html";
    }

    @PostMapping("/registrar-editar")
    public String registroEdicion(ModelMap modelo, @RequestParam(required = false) String id,
            @RequestParam(required = false) String idCliente, @RequestParam(required = false) String idLibro,
            @RequestParam Date fechaPrestamo, @RequestParam Date fechaDevolucion) {
        Prestamo prestamo = new Prestamo();
        Libro libro = new Libro();
        Cliente cliente = new Cliente();
        
        try {
            libro = libroServicio.buscarPorId(idLibro);
            cliente = clienteServicio.buscarPorId(idCliente);
            
            if (id == null || id.trim().isEmpty()) {
                prestamo = prestamoServicio.crearYGuardar(fechaPrestamo, fechaDevolucion, idLibro, idCliente);
            } else {
                prestamo = prestamoServicio.modificar(id, idCliente, idLibro, fechaPrestamo, fechaDevolucion);
            }

            modelo.put("prestamo", prestamo);
            modelo.put("titulo", "!Perfecto!");
            modelo.put("descripcion", "El Prestamo fue registrado satisfactoriamente en la base de datos.");
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            prestamo.setId(id);
            prestamo.setPrestamo(fechaPrestamo);
            prestamo.setDevolucion(fechaDevolucion);
            prestamo.setLibro(libro);
            prestamo.setCliente(cliente);

            modelo.put("prestamo", prestamo);
            modelo.put("error", ex.getMessage());
        }

        return "prestamo-regitro.html";
    }

}
