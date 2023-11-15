package com.example.autonoleggio.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByEmail(String ics);
    @Query(value = "SELECT * FROM clienti ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Cliente findRandomCliente();
}