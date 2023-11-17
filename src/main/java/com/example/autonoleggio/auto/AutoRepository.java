package com.example.autonoleggio.auto;

import com.example.autonoleggio.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutoRepository extends JpaRepository<Auto,Long> {

    @Query(value = "SELECT * FROM auto ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Auto findRandomCliente();
}
