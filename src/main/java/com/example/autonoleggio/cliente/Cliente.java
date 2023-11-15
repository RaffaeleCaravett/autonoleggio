package com.example.autonoleggio.cliente;

import com.example.autonoleggio.noleggio.Noleggio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="clienti")
public class Cliente {

    @Id
    @GeneratedValue
    private long id;
    private String nome;
    private String cognome;
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida")
    private String email;
    @OneToOne(mappedBy = "cliente")
    private Noleggio noleggio;
}
