package com.libreria.repositorios;

import com.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
}
