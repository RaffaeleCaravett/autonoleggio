package com.example.autonoleggio.auto;

import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.enums.Tipo;
import com.example.autonoleggio.noleggio.Noleggio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private LocalDate dataImmatricolazione;
    @JsonIgnore
    @OneToOne(mappedBy = "auto")
    private Noleggio noleggio;

    public Auto(Tipo tipo) {
        this.tipo = tipo;
        this.dataImmatricolazione = LocalDate.now();
    }
}
