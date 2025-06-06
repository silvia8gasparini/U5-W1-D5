package it.epicode.GestionePrenotazioni.entities;


import it.epicode.GestionePrenotazioni.enumeration.TipoPostazione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Postazione {

    @Id
    @GeneratedValue
    private Integer Id;

    @Column
    private String codiceUnivoco;
    private String descrizione;

    @Enumerated
    private TipoPostazione tipo;
    private int maxOccupanti;

    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;

    @OneToMany(mappedBy = "postazione")
    private List<Prenotazione> prenotazioni;

    public Postazione(String codiceUnivoco, String descrizione, TipoPostazione tipo, int maxOccupanti, Edificio edificio, List<Prenotazione> prenotazioni) {
        this.codiceUnivoco = codiceUnivoco;
        this.descrizione = descrizione;
        this.tipo = tipo;
        this.maxOccupanti = maxOccupanti;
        this.edificio = edificio;
        this.prenotazioni = prenotazioni;
    }

    @Override
    public String toString() {
        return "Postazione{" +
                "Id=" + Id +
                ", codiceUnivoco='" + codiceUnivoco + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", tipo=" + tipo +
                ", maxOccupanti=" + maxOccupanti +
                ", edificio=" + edificio +
                ", prenotazioni=" + prenotazioni +
                '}';
    }
}
