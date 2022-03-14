package com.libreria.repositorios;

import com.libreria.entidades.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String> {

    @Query("SELECT p FROM Prestamo p WHERE p.activo = true")
    public List<Prestamo> buscarActivos();

}
