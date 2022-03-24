package com.libreria.servicios;

import com.libreria.entidades.Cliente;
import com.libreria.entidades.Libro;
import com.libreria.entidades.Prestamo;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.PrestamoRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private LibroServicio libroServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Transactional(rollbackFor = Exception.class)
    public Prestamo crearYGuardar(Date prestamo, Date devolucion, Libro libro, Cliente cliente) throws ErrorInputException {
        validacion(prestamo, devolucion, libro, cliente);

        Prestamo p = new Prestamo();
        p.setPrestamo(prestamo);
        p.setDevolucion(devolucion);
        p.setLibro(libro);
        p.setCliente(cliente);
        p.setActivo(true);

        return prestamoRepositorio.save(p);
    }

    @Transactional(rollbackFor = Exception.class)
    public Prestamo modificar(String idPrestamo, String idCliente, String idLibro,
            Date prestamo, Date devolucion) throws ErrorInputException, ElementoNoEncontradoException {
        Libro libro = libroServicio.buscarPorId(idLibro);
        Cliente cliente = clienteServicio.buscarPorId(idCliente);

        validacion(prestamo, devolucion, libro, cliente);

        Optional<Prestamo> respuesta = prestamoRepositorio.findById(idPrestamo);
        if (respuesta.isPresent()) {
            Prestamo p = respuesta.get();

            p.setPrestamo(prestamo);
            p.setDevolucion(devolucion);
            p.setLibro(libro);
            p.setCliente(cliente);

            return prestamoRepositorio.save(p);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Préstamo solicitado.");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Prestamo deshabilitar(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe de indicar un identificador válido para el Préstamo.");
        }
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamo p = respuesta.get();
            p.setActivo(false);
            return prestamoRepositorio.save(p);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Préstamo solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Prestamo buscarPorId(String id) throws ErrorInputException, ElementoNoEncontradoException {
        if (id == null || id.trim().isEmpty()) {
            throw new ErrorInputException("Debe de indicar un identificador válido para el Préstamo.");
        }
        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el Préstamo solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarTodos() {
        return prestamoRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Prestamo> listarActivos() {
        return prestamoRepositorio.buscarActivos();
    }

    private void validacion(Date prestamo, Date devolucion, Libro libro, Cliente cliente) throws ErrorInputException {
        if (prestamo == null) {
            throw new ErrorInputException("Debe de indicar la fecha de inicio del Préstamo.");
        }
        if (devolucion == null) {
            throw new ErrorInputException("Debe de indicar una fecha de devolución para el Préstamo.");
        }
        if (libro == null) {
            throw new ErrorInputException("Debe de indicar el Libro a prestar.");
        }
        if (cliente == null) {
            throw new ErrorInputException("Debe de indicar el Cliente.");
        }
    }

}
