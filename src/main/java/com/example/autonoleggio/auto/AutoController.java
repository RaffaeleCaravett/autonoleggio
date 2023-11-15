package com.example.autonoleggio.auto;

import com.example.autonoleggio.auth.AuthService;
import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.exceptions.BadRequestException;
import com.example.autonoleggio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auto")
public class AutoController {
    @Autowired
    private AutoService autoService;
    @Autowired
    private AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('AUTONOLEGGIO','USER')")
    public Page<Auto> getAuto(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String orderBy){
        return autoService.getAuto(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    @PreAuthorize("hasAuthority('AUTONOLEGGIO')")
    public Auto saveAuto(@RequestBody @Validated Auto body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return autoService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('AUTONOLEGGIO','USER')")
    public Auto findById(@PathVariable int id)  {
        return autoService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Auto findByIdAndUpdate(@PathVariable int id, @RequestBody Auto body) throws NotFoundException {
        return autoService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AUTONOLEGGIO')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        autoService.findByIdAndDelete(id);
    }

}

