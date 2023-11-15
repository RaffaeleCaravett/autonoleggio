package com.example.autonoleggio.auto;

import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.cliente.ClienteRepository;
import com.example.autonoleggio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AutoService {
    @Autowired
    private AutoRepository autoRepository;


    // @Autowired
    //private EmailSender emailSender;


    public Page<Auto> getAuto(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return autoRepository.findAll(pageable);
    }

    public Auto findById(long id) throws NotFoundException {
        return autoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Auto found = this.findById(id);
        autoRepository.delete(found);
    }

    public Auto findByIdAndUpdate(long id, Auto body) throws NotFoundException {
        Auto found = this.findById(id);
        found.setTipo(body.getTipo());
        found.setNoleggio(body.getNoleggio());
        found.setDataImmatricolazione(body.getDataImmatricolazione());
        return autoRepository.save(found);
    }

    public Auto getRandomAuto() throws NotFoundException {
        return autoRepository.findRandomCliente();
    }

    public Auto findByEmail(String email) throws Exception {
        return autoRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Utente con email "+ email + " non trovato"));
    }
    public Auto save(Auto body) throws IOException {

        Auto auto = new Auto(body.getTipo(), body.getDataImmatricolazione());
        return autoRepository.save(auto);
    }

}