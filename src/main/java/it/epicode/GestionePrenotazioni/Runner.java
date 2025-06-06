package it.epicode.GestionePrenotazioni;

import it.epicode.GestionePrenotazioni.entities.Edificio;
import it.epicode.GestionePrenotazioni.entities.Postazione;
import it.epicode.GestionePrenotazioni.entities.Prenotazione;
import it.epicode.GestionePrenotazioni.entities.Utente;
import it.epicode.GestionePrenotazioni.repository.EdificioRepository;
import it.epicode.GestionePrenotazioni.repository.PostazioneRepository;
import it.epicode.GestionePrenotazioni.repository.PrenotazioneRepository;
import it.epicode.GestionePrenotazioni.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    @Autowired private EdificioRepository edificioRepository;
    @Autowired private PostazioneRepository postazioneRepository;
    @Autowired private PrenotazioneRepository prenotazioneRepository;
    @Autowired private UtenteRepository utenteRepository;

    @Autowired @Qualifier("edificiovenezia") private Edificio edificioVenezia;
    @Autowired @Qualifier("edificiofirenze") private Edificio edificioFirenze;
    @Autowired @Qualifier("edificiotorino") private Edificio edificioTorino;

    @Autowired @Qualifier("postazioneOpenSpace") private Postazione p1;
    @Autowired @Qualifier("salaRiunioni") private Postazione p2;
    @Autowired @Qualifier("postazionePrivata") private Postazione p3;

    @Override
    public void run(String... args) throws Exception {


        if (!edificioRepository.existsByNomeAndCitta(edificioVenezia.getNome(), edificioVenezia.getCitta())) {
            edificioRepository.save(edificioVenezia);
        }
        if (!edificioRepository.existsByNomeAndCitta(edificioFirenze.getNome(), edificioFirenze.getCitta())) {
            edificioRepository.save(edificioFirenze);
        }
        if (!edificioRepository.existsByNomeAndCitta(edificioTorino.getNome(), edificioTorino.getCitta())) {
            edificioRepository.save(edificioTorino);
        }

        if (!postazioneRepository.existsByCodiceUnivoco(p1.getCodiceUnivoco())) {
            postazioneRepository.save(p1);
        }
        if (!postazioneRepository.existsByCodiceUnivoco(p2.getCodiceUnivoco())) {
            postazioneRepository.save(p2);
        }
        if (!postazioneRepository.existsByCodiceUnivoco(p3.getCodiceUnivoco())) {
            postazioneRepository.save(p3);
        }

        if (utenteRepository.findByUsername("eugene.choi").isEmpty()) {
            utenteRepository.save(new Utente("eugene.choi", "Eugene Choi", "e.choi@gmail.com"));
        }
        if (utenteRepository.findByUsername("ae-shin.go").isEmpty()) {
            utenteRepository.save(new Utente("ae-shin.go", "Ae-shin Go", "ae.shin.go@agmail.com"));
        }
        if (utenteRepository.findByUsername("tae-ri.kim").isEmpty()) {
            utenteRepository.save(new Utente("tae-ri.kim", "Tae-ri Kim", "tae.ri.kim@agmail.com"));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Benvenutə! Prenota qui il tuo spazio di lavoro!");

        System.out.print("Inserisci il tuo username: ");
        String username = scanner.nextLine();
        Optional<Utente> optionalUtente = utenteRepository.findByUsername(username).stream().findFirst();

        if (optionalUtente.isEmpty()) {
            System.out.println("Utente non trovato.");
            return;
        }

        Utente utente = optionalUtente.get();

        List<Postazione> postazioni = postazioneRepository.findAll();
        if (postazioni.isEmpty()) {
            System.out.println("Nessuna postazione disponibile.");
            return;
        }

        System.out.println("Postazioni disponibili:");
        for (int i = 0; i < postazioni.size(); i++) {
            Postazione p = postazioni.get(i);
            System.out.println((i + 1) + " - " + p.getDescrizione() +
                    " | Tipo: " + p.getTipo() +
                    " | Edificio: " + p.getEdificio().getNome() +
                    " - " + p.getEdificio().getCitta());
        }

        System.out.print("Scegli il numero della postazione: ");
        int scelta = Integer.parseInt(scanner.nextLine());
        if (scelta < 1 || scelta > postazioni.size()) {
            System.out.println("Scelta non valida.");
            return;
        }
        Postazione postazioneScelta = postazioni.get(scelta - 1);

        System.out.print("Inserisci la data della prenotazione (AAAA-MM-GG): ");
        LocalDate data;
        try {
            data = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Formato data non valido.");
            return;
        }

        boolean occupata = prenotazioneRepository.findByPostazioneAndDataPrenotazione(postazioneScelta, data).isPresent();
        boolean utentePrenotato = prenotazioneRepository.existsByUtenteAndDataPrenotazione(utente, data);

        if (occupata) {
            System.out.println("La postazione è già prenotata per quella data.");
        } else if (utentePrenotato) {
            System.out.println("Hai già una prenotazione per quella data.");
        } else {
            Prenotazione nuova = new Prenotazione(data, utente, postazioneScelta);
            prenotazioneRepository.save(nuova);
            System.out.println("Prenotazione effettuata con successo!");
        }

        scanner.close();
    }
}
