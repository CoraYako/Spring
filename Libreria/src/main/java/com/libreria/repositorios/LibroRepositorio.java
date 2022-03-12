package com.libreria.repositorios;

import com.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
}
