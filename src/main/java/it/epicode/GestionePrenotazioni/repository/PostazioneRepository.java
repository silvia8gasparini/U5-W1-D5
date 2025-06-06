package it.epicode.GestionePrenotazioni.repository;

import it.epicode.GestionePrenotazioni.entities.Postazione;
import it.epicode.GestionePrenotazioni.enumeration.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione, Integer> {
    List<Postazione> findByTipo(TipoPostazione tipo);
    List<Postazione> findByTipoAndEdificio_Citta(TipoPostazione tipo, String citta);
    List<Postazione> findByMaxOccupantiBetween(int min, int max);
    boolean existsByCodiceUnivoco(String codiceUnivoco);

    @Query("select p from Postazione p where p.prenotazioni is empty")
    List<Postazione> findPostazioniMaiPrenotate();

    @Query("select max(p.maxOccupanti) from Postazione p")
    Integer findCapacitaMassimaDisponibile();
}
