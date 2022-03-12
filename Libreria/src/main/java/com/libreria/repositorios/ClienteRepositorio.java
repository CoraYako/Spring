package com.libreria.repositorios;

import com.libreria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
}
