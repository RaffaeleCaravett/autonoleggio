package com.example.autonoleggio.cliente;

import com.example.autonoleggio.auth.AuthService;
import com.example.autonoleggio.exceptions.BadRequestException;
import com.example.autonoleggio.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Page<Cliente> getCliente(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy){
        return clienteService.getClienti(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    @PreAuthorize("hasAuthority('USER')")
    public long saveCliente(@RequestBody @Validated Cliente body, BindingResult validation){
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


    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Cliente findById(@PathVariable int id)  {
        return clienteService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Cliente findByIdAndUpdate(@PathVariable int id, @RequestBody Cliente body) throws NotFoundException {
        return clienteService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        clienteService.findByIdAndDelete(id);
    }

}