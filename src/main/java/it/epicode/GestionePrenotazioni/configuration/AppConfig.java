package it.epicode.GestionePrenotazioni.configuration;

import it.epicode.GestionePrenotazioni.entities.Edificio;
import it.epicode.GestionePrenotazioni.entities.Postazione;
import it.epicode.GestionePrenotazioni.enumeration.TipoPostazione;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("edificiovenezia")
    public Edificio edificioVenezia() {
        return new Edificio("Sede Centrale", "Rio Terà Lista di Spagna 160", "Venezia");
    }

    @Bean("edificiofirenze")
    public Edificio edificioFirenze() {
        return new Edificio("Villa Neroli", "Via Gabriele D'Annunzio 141a", "Firenze");
    }

    @Bean("edificiotorino")
    public Edificio edificioTorino() {
        return new Edificio("Sale Meeting", "Via Giovanni Giolitti 16", "Torino");
    }

    @Bean("postazioneOpenSpace")
    public Postazione postazione1(@Qualifier("edificiovenezia") Edificio edificio) {
        Postazione p = new Postazione();
        p.setCodiceUnivoco("P01");
        p.setDescrizione("Open space luminoso al terzo piano");
        p.setTipo(TipoPostazione.OPENSPACE);
        p.setMaxOccupanti(10);
        p.setEdificio(edificio);
        return p;
    }

    @Bean("salaRiunioni")
    public Postazione postazione2(@Qualifier("edificiotorino") Edificio edificio) {
        Postazione p = new Postazione();
        p.setCodiceUnivoco("P02");
        p.setDescrizione("Sala riunioni con schermo");
        p.setTipo(TipoPostazione.SALA_RIUNIONI);
        p.setMaxOccupanti(20);
        p.setEdificio(edificio);
        return p;
    }

    @Bean("postazionePrivata")
    public Postazione postazione3(@Qualifier("edificiofirenze") Edificio edificio) {
        Postazione p = new Postazione();
        p.setCodiceUnivoco("P03");
        p.setDescrizione("Ufficio privato al secondo piano");
        p.setTipo(TipoPostazione.PRIVATO);
        p.setMaxOccupanti(5);
        p.setEdificio(edificio);
        return p;
    }
}
