package it.epicode.GestionePrenotazioni.repository;

import it.epicode.GestionePrenotazioni.entities.Postazione;
import it.epicode.GestionePrenotazioni.entities.Prenotazione;
import it.epicode.GestionePrenotazioni.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
    List<Prenotazione> findByUtente(Utente utente);
    List<Prenotazione> findByDataPrenotazione(LocalDate data);
    Optional<Prenotazione> findByPostazioneAndDataPrenotazione(Postazione postazione, LocalDate data);
    boolean existsByUtenteAndDataPrenotazione(Utente utente, LocalDate data);

    @Query("select p from Prenotazione p where extract(year from p.dataPrenotazione) = :anno")
    List<Prenotazione> findPrenotazioniPerAnno(int anno);

    @Query("select count(p) from Prenotazione p where p.dataPrenotazione = :data")
    long countPrenotazioniInData(LocalDate data);
}
