package com.example.autonoleggio.noleggio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoleggioRepository extends JpaRepository<Noleggio,Long> {

}
