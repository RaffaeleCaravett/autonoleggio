package com.example.autonoleggio.noleggio;

import com.example.autonoleggio.enums.Durata;
import com.example.autonoleggio.enums.Stato;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record NoleggioDTO(
        Durata durata,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate dataInizioNoleggio,
        Stato stato,
        Long auto,
        Long cliente
) {
}
