package com.libreria.servicios;

import com.libreria.entidades.Cliente;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.ClienteRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public Cliente crearYGuardar(Integer documento, String nombre, String apellido, String telefono) throws ErrorInputException {
        validacion(documento, nombre, apellido, telefono);
        Cliente cliente = new Cliente();

        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setAlta(new Date());
        cliente.setActivo(true);

        return clienteRepositorio.save(cliente);
    }

    @Transactional(rollbackFor = Exception.class)
    public Cliente modificar(String id, Integer documento, String Nombre, String apellido, String telefono) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe de indicar el identificador correcto para el Cliente.");
        }
        validacion(documento, Nombre, apellido, telefono);

        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();

            cliente.setDocumento(documento);
            cliente.setNombre(Nombre);
            cliente.setApellido(apellido);
            cliente.setTelefono(telefono);
            return clienteRepositorio.save(cliente);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Cliente solicitado.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Cliente deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe de indicar un identificador correcto para el Cliente.");
        }
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setActivo(false);
            return clienteRepositorio.save(cliente);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Cliente solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe de indicar el identificador correcto para el Cliente.");
        }
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Cliente solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarActivos() {
        return clienteRepositorio.buscarActivos();
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarPorNombre(String nombre) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre válido para el Cliente.");
        }
        return clienteRepositorio.buscarPorNombre(nombre);
    }

    @Transactional(readOnly = true)
    public List<Cliente> listarPorApellido(String apellido) throws ErrorInputException {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un apellido válido para el Cliente.");
        }
        return clienteRepositorio.buscarPorApellido(apellido);
    }

    @Transactional(readOnly = true)
    public Cliente buscarPorTelefono(String telefono) throws ErrorInputException {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un teléfono válido para el Cliente.");
        }
        return clienteRepositorio.buscarPorTelefono(telefono);
    }

    private void validacion(Integer documento, String nombre, String apellido, String telefono) throws ErrorInputException {
        if (documento == null || documento < 1000000) {
            throw new ErrorInputException("Debe indicar un documento válido para el Cliente.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un nombre para el Cliente.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ErrorInputException("Debe indicar un apellido para el Cliente.");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new ErrorInputException("Debe proporcionar un teléfono de contacto del Cliente.");
        }
    }

}
