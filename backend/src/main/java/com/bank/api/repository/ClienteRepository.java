package com.bank.api.repository;

import com.bank.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByClientId(String clientId);

    Optional<Cliente> findByIdentificacion(String identificacion);

    // Búsqueda rápida (nombre / identificación / clientId)
    @Query("""
           SELECT c
           FROM Cliente c
           WHERE lower(c.nombre) LIKE lower(concat('%', :q, '%'))
              OR lower(c.identificacion) LIKE lower(concat('%', :q, '%'))
              OR lower(c.clientId) LIKE lower(concat('%', :q, '%'))
           """)
    List<Cliente> quickSearch(String q);
}
