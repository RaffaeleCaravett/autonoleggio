package com.example.autonoleggio.cliente;


import com.example.autonoleggio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    // @Autowired
    //private EmailSender emailSender;

    public Page<Cliente> getClienti(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
try{
    return clienteRepository.findAll(pageable);
}catch (Exception e){
    System.out.println(e.getMessage());
    return null;
}
    }
    public Cliente findById(long id) throws NotFoundException {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Cliente found = this.findById(id);
        clienteRepository.delete(found);
    }

    public Cliente findByIdAndUpdate(long id, Cliente body) throws NotFoundException {
        Cliente found = this.findById(id);
        found.setCognome(body.getCognome());
        found.setNome(body.getNome());
        found.setEmail(body.getEmail());
        return clienteRepository.save(found);
    }

    public Cliente getRandomCliente() throws NotFoundException {
        return clienteRepository.findRandomCliente();
    }

    public Cliente findByEmail(String email) throws Exception {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Utente con email "+ email + " non trovato"));
    }


}