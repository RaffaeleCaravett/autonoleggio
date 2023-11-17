package com.example.autonoleggio.security;

import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(Cliente user){

        return Jwts.builder().setSubject(String.valueOf(user.getId()))// Subject <-- A chi appartiene il token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione (IAT - Issued At)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15 ) ) // Data di scadenza (Expiration Date)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

    } // Si utilizza al login

    public void verifyToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }

    } // Si utilizza in tutte le request autenticate

    public String extractIdFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
        // Nel body (payload) del token ci sono il subject( che è l'ID dell'utente), la data di emissione, e la data di scadenza.
        // A noi interessa l'id dell'utente
    }
}