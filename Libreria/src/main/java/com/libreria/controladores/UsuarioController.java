package com.libreria.controladores;

import com.libreria.entidades.Usuario;
import com.libreria.enumeradores.Genero;
import com.libreria.enumeradores.Rol;
import com.libreria.excepciones.ElementoNoEncontradoException;
import com.libreria.excepciones.ErrorInputException;
import com.libreria.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registro{id}")
    public String registro(ModelMap modelo, HttpSession session, @PathVariable String id) {
        Usuario usuario = new Usuario();

        modelo.put("roles", Rol.values());
        modelo.put("generos", Genero.values());

        try {
            if (id != null && !id.trim().isEmpty()) {
                usuario = usuarioServicio.buscarPorId(id);
            }

            modelo.put("perfil", usuario);
        } catch (ElementoNoEncontradoException | ErrorInputException ex) {
            
            modelo.put("error", ex.getMessage());
        }

        return "usuario-registro.html";
    }

    @PostMapping("/registro")
    public String registrarEdirar(ModelMap modelo, MultipartFile archivo, @RequestParam String nombre, @RequestParam String apellido, Genero genero, @RequestParam String mail,
            Rol rol, @RequestParam String clave1, @RequestParam String clave2) {
        modelo.put("generos", Genero.values());
        modelo.put("roles", Rol.values());

        try {
            usuarioServicio.registrarYPersistir(archivo, nombre, apellido, genero, rol, mail, clave1, clave2);
        } catch (ErrorInputException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            return "usuario-registro.html";
        }
        return "index.html";
    }

}
