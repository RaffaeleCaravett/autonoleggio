package com.example.autonoleggio.noleggio;

import com.example.autonoleggio.auto.Auto;
import com.example.autonoleggio.auto.AutoRepository;
import com.example.autonoleggio.cliente.ClienteRepository;
import com.example.autonoleggio.enums.Stato;
import com.example.autonoleggio.exceptions.BadRequestException;
import com.example.autonoleggio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class NoleggioService {

    @Autowired
    private NoleggioRepository noleggioRepository;

    @Autowired
    private AutoRepository autoRepository;
    @Autowired
    private ClienteRepository clienteRepository;


    // @Autowired
    //private EmailSender emailSender;


    public Page<Noleggio> getNoleggio(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return noleggioRepository.findAll(pageable);
    }

    public Noleggio findById(long id) throws NotFoundException {
        return noleggioRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Noleggio found = this.findById(id);
        noleggioRepository.delete(found);
    }

    public Noleggio findByIdAndUpdate(long id, NoleggioDTO body) throws NotFoundException {
        Noleggio found = this.findById(id);
        found.setDurata(body.durata());
        found.setStato(body.stato());
        found.setAuto(autoRepository.findById(body.auto()).get());
        found.setCliente(clienteRepository.findById(body.cliente()).get());
        return noleggioRepository.save(found);
    }

    public Noleggio endNoleggio(long id) throws NotFoundException {
        Noleggio found = this.findById(id);
        found.setStato(Stato.TERMINATO);
        found.setAuto(null);
        found.setCliente(null);
        return noleggioRepository.save(found);
    }

    public Noleggio save(NoleggioDTO body) throws IOException {


        if (clienteRepository.findById(body.cliente()).get().getNoleggio() != null) {
            throw new BadRequestException("Il cliente ha già un noleggio.");
        }
        if (autoRepository.findById(body.auto()).get().getNoleggio() != null) {
            throw new BadRequestException("L'auto ha già un noleggio.");
        }
            Noleggio noleggio =
                    new Noleggio(
                            body.durata(),
                            clienteRepository.findById(body.cliente()).get(),
                            autoRepository.findById(body.auto()).get()
                    );

            return noleggioRepository.save(noleggio);

    }

}