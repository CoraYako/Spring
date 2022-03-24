package com.libreria.repositorios;

import com.libreria.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.activo = true")
    public List<Cliente> buscarActivos();

    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE :nombre")
    public List<Cliente> buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Cliente c WHERE c.apellido LIKE :apellido")
    public List<Cliente> buscarPorApellido(@Param("apellido") String apellido);

    @Query("SELECT c FROM Cliente c WHERE c.telefono LIKE :telefono")
    public Cliente buscarPorTelefono(@Param("telefono") String telefono);

}
