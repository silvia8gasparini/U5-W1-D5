package it.epicode.GestionePrenotazioni.repository;

import it.epicode.GestionePrenotazioni.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
 List<Utente> findByNomeCompleto(String nomeCompleto);
 List<Utente> findByUsername(String userName);
 List<Utente> findByNomeCompletoAndEmail(String nomeCompleto, String email);
 List<Utente> findByEmailContaining(String frammento);

 @Query("select u from Utente u where u.prenotazioni is empty")
    List<Utente> findUtentiSenzaPrenotazioni();

 @Query("select count(u) from Utente u")
    long countTotaleUtenti();

 @Query("select u from Utente u join u.prenotazioni p where p.dataPrenotazione > :data")
    List<Utente> findUtentiConPrenotazioniDopo(LocalDate data);

}
