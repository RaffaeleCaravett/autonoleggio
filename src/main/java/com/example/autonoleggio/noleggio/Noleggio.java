package com.example.autonoleggio.noleggio;

import com.example.autonoleggio.auto.Auto;
import com.example.autonoleggio.cliente.Cliente;
import com.example.autonoleggio.enums.Durata;
import com.example.autonoleggio.enums.Stato;
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
@Table(name="noleggi")
public class Noleggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate dataInizioNoleggio;
    private Durata durata;
    private LocalDate dataFineNoleggio;
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name="auto_id")
    private Auto auto;


    public Noleggio(Durata durata, Cliente cliente, Auto auto) {
        this.dataInizioNoleggio = LocalDate.now();
        this.durata = durata;
        if(durata==Durata.SETTIMANALE){
            this.dataFineNoleggio = dataInizioNoleggio.plusDays(7);
        }else if(durata==Durata.MENSILE){
            this.dataFineNoleggio = dataInizioNoleggio.plusMonths(1);
        }else{
            this.dataFineNoleggio = dataInizioNoleggio.plusYears(1);
        }
        this.dataFineNoleggio = dataFineNoleggio;
        this.stato = Stato.IN_CORSO;
        this.cliente = cliente;
        this.auto = auto;
    }
}
