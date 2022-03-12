package com.libreria.repositorios;

import com.libreria.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
}
