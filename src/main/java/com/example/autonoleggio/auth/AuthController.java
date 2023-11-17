package com.example.autonoleggio.auth;

import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ClienteLoginSuccessDTO login(@RequestBody ClienteLoginDTO body) throws Exception {

        return new ClienteLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public long saveUser(@RequestBody @Validated Cliente body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                authService.registerUser(body);
                return body.getId();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
