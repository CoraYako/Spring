package com.libreria.servicios;

import com.libreria.entidades.Foto;
import com.libreria.entidades.Usuario;
import com.libreria.enumeradores.Genero;
import com.libreria.enumeradores.Rol;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(rollbackFor = Exception.class)
    public Usuario registrarYPersistir(MultipartFile archivo, String nombre,
            String apellido, Genero genero, Rol rol, String mail, String clave1, String clave2) throws ErrorInputException {
        validacion(nombre, apellido, genero, mail, clave1, clave2);

        if (rol == null) {
            throw new ErrorInputException("Debe seleccionar un rol para el usuario.");
        }

        Foto foto = fotoServicio.guardar(archivo);
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setGenero(genero);
        usuario.setMail(mail);
        usuario.setRol(rol);

        String claveEncriptada = new BCryptPasswordEncoder().encode(clave1);
        usuario.setClave(claveEncriptada);

        usuario.setAlta(new Date());
        usuario.setFoto(foto);

        return usuarioRepositorio.save(usuario);
    }

    @Transactional(rollbackFor = Exception.class)
    public Usuario modificar(MultipartFile archivo, String idUsuario, String nombre, String apellido,
            Genero genero, String mail, String clave1, String clave2) throws ErrorInputException, ElementoNoEncontradoException {
        if (idUsuario == null || idUsuario.trim().isEmpty()) {
            throw new ErrorInputException("El identificador del usuario no puede ser nulo.");
        }

        validacion(nombre, apellido, genero, mail, clave1, clave2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setGenero(genero);
            usuario.setMail(mail);

            String claveEncriptada = new BCryptPasswordEncoder().encode(clave1);
            usuario.setClave(claveEncriptada);

            String idFoto = null;
            if (usuario.getFoto().getId() != null) {
                idFoto = usuario.getFoto().getId();
            }

            Foto foto = fotoServicio.actualizar(idFoto, archivo);

            usuario.setFoto(foto);

            return usuarioRepositorio.save(usuario);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el usuario solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorMail(String mail) throws ErrorInputException {
        if (mail == null || mail.trim().isEmpty()) {
            throw new ErrorInputException("El email del usuario no puede ser nulo.");
        }
        return usuarioRepositorio.buscarPorMail(mail);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepositorio.findAll();
    }

    public void validacion(String nombre, String apellido, Genero genero, String mail, String clave1, String clave2) throws ErrorInputException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ErrorInputException("El nombre del usuario no puede ser nulo.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ErrorInputException("El apellido del usuario no puede ser nulo.");
        }
        if (genero == null) {
            throw new ErrorInputException("Debe seleccionar un género.");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new ErrorInputException("El mail del usuario no puede ser nulo.");
        }
        if (clave1 == null || clave1.trim().isEmpty() || clave1.length() < 6) {
            throw new ErrorInputException("La contraseña no puede ser nula y debe contener al menos 6 dígitos.");
        }
        if (!clave1.equals(clave2)) {
            throw new ErrorInputException("Las contraseñas deben ser iguales.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorMail(mail);

        if (usuario == null) {
            return null;
        }

        List<GrantedAuthority> permisos = new ArrayList<>();

        GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
        permisos.add(p1);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attr.getRequest().getSession(true);
        session.setAttribute("usuariosession", usuario);

        return new User(usuario.getMail(), usuario.getClave(), permisos);
    }
}
