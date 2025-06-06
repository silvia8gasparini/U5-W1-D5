package it.epicode.GestionePrenotazioni.repository;

import it.epicode.GestionePrenotazioni.entities.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EdificioRepository extends JpaRepository<Edificio, Long> {

    Optional<Edificio> findByNome(String nome);
    List<Edificio> findByCitta(String citta);
    Optional<Edificio> findByNomeAndCitta(String nome, String citta);
    boolean existsByNomeAndCitta(String nome, String citta);
    List<Edificio> findAllByOrderByNomeAsc();

    @Query("select e from Edificio e where e in (select p.edificio from Postazione p)")
    List<Edificio> findEdificiConPostazioni();

    @Query("select count(e) from Edificio e where e.citta = :citta")
    long countEdificiInCitta(String citta);
}