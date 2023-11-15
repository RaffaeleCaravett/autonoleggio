package com.example.autonoleggio.auth;

import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.cliente.ClienteRepository;
import com.example.autonoleggio.cliente.ClienteService;
import com.example.autonoleggio.enums.Role;
import com.example.autonoleggio.exceptions.BadRequestException;
import com.example.autonoleggio.exceptions.UnauthorizedException;
import com.example.autonoleggio.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private ClienteRepository usersRepository;

    public String authenticateUser(Cliente body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        Cliente user = clienteService.findByEmail(body.getEmail());

        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(bcrypt.matches(body.getPassword(), user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public Cliente registerUser(Cliente body) throws IOException {

        // verifico se l'email è già utilizzata
        usersRepository.findByEmail(body.getEmail()).ifPresent( user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        Cliente newUser = new Cliente();
        newUser.setNome(body.getNome());
        newUser.setCognome(body.getCognome());
        newUser.setPassword(bcrypt.encode(body.getPassword())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUser.setEmail(body.getEmail());
        newUser.setRole(Role.USER);
        Cliente savedUser = clienteRepository.save(newUser);
        return savedUser;
    }
}