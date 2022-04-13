package com.libreria.servicios;

import com.libreria.entidades.Foto;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.FotoRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public Foto guardar(MultipartFile archivo) throws ErrorInputException {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                Foto foto = new Foto();

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public Foto actualizar(String idFoto, MultipartFile archivo) throws ErrorInputException {
        if (archivo != null) {
            try {
                Foto foto = new Foto();

                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }
                }

                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return null;
    }

    @Transactional(readOnly = true)
    public Foto buscarPorId(String id) throws ElementoNoEncontradoException {
        Optional<Foto> respuesta = fotoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new ElementoNoEncontradoException("No se encontró el archivo solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Foto> listarTodos() {
        return fotoRepositorio.findAll();
    }

}
