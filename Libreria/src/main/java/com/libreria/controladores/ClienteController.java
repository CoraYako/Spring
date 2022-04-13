/*
TODO crear las siguiente vistas:

cliente-lista
cliente-registro
 */
package com.libreria.controladores;

import com.libreria.entidades.Cliente;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.ClienteServicio;
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
@RequestMapping("/cleinte")
public class ClienteController {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Cliente> clientes = clienteServicio.listarTodos();

        modelo.put("clientes", clientes);

        return "cliente-lista.html";
    }

    @GetMapping("/registrar-editar")
    public String registro(ModelMap modelo, @RequestParam(required = false) String id) {
        Cliente cliente = new Cliente();

        try {
            if (id != null && !id.trim().isEmpty()) {
                cliente = clienteServicio.buscarPorId(id);
            }
            modelo.put("cliente", cliente);
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            modelo.put("error", ex.getMessage());
        }
        return "cliente-registro.html";
    }

    @PostMapping("/registrar-editar")
    public String registroEdicion(ModelMap modelo, @RequestParam(required = false) String id, @RequestParam Integer documento,
            @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String telefono) {
        Cliente cliente = new Cliente();

        try {
            if (id == null || id.trim().isEmpty()) {
                cliente = clienteServicio.crearYGuardar(documento, nombre, apellido, telefono);
            } else {
                cliente = clienteServicio.modificar(id, documento, nombre, apellido, telefono);
            }

            modelo.put("cliente", cliente);
            modelo.put("titulo", "!Perfecto!");
            modelo.put("descripcion", "El Cliente fue guardado satisfactoriamente en la base de datos.");
        } catch (ErrorInputException | ElementoNoEncontradoException ex) {
            cliente.setId(id);
            cliente.setDocumento(documento);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);

            modelo.put("cliente", cliente);
            modelo.put("error", ex.getMessage());
            modelo.put("documento", documento);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("telefono", telefono);
        }

        return "cliente-registro.html";
    }

}
