package it.epicode.GestionePrenotazioni.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Edificio {

    @Id
    @GeneratedValue
    private Integer id;

    private String nome;
    private String indirizzo;
    private String citta;

    public Edificio(String nome, String indirizzo, String citta){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.citta = citta;
    }

    @Override
    public String toString() {
        return "Edificio{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", citta='" + citta + '\'' +
                '}';
    }
}
