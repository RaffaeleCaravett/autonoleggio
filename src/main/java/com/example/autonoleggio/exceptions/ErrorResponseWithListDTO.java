package com.example.autonoleggio.exceptions;

import java.util.Date;
import java.util.List;

public record ErrorResponseWithListDTO(String message,
                                        Date timestamp,
                                        List<String> errorsList)
{
}