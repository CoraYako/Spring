package com.libreria.repositorios;

import com.libreria.entidades.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>{
    
}
