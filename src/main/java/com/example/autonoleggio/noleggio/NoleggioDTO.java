package com.example.autonoleggio.noleggio;

import com.example.autonoleggio.enums.Durata;
import com.example.autonoleggio.enums.Stato;

public record NoleggioDTO(
        Durata durata,
        Stato stato,
        Long auto,
        Long cliente
) {
}
