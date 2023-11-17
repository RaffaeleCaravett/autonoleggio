package com.example.autonoleggio.noleggio;

import com.example.autonoleggio.auto.Auto;
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
@RequestMapping("/noleggio")
public class NoleggioController {
    @Autowired
    private NoleggioService noleggioService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Noleggio> getNoleggio(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy){
        return noleggioService.getNoleggio(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    @PreAuthorize("hasAuthority('ADMIN')")
    public Noleggio saveNoleggio(@RequestBody @Validated NoleggioDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return noleggioService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Noleggio findById(@PathVariable int id)  {
        return noleggioService.findById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Noleggio findByIdAndUpdate(@PathVariable int id, @RequestBody NoleggioDTO body) throws NotFoundException {
        return noleggioService.findByIdAndUpdate(id, body);
    }

    @PutMapping("endNoleggio/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Noleggio endNoleggio(@PathVariable int id) throws NotFoundException {
        return noleggioService.endNoleggio(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        noleggioService.findByIdAndDelete(id);
    }

}