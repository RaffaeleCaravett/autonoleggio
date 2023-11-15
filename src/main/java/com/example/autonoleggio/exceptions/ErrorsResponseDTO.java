package com.example.autonoleggio.exceptions;

import java.util.Date;

public record ErrorsResponseDTO(String message, Date timestamp) {
}
