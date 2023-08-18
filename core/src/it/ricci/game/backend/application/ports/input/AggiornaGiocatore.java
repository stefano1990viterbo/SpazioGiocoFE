package it.ricci.game.backend.application.ports.input;

import it.ricci.game.backend.domain.Giocatore;

public interface AggiornaGiocatore {

  Giocatore aggiornaGiocatore(Giocatore nuovoStatoGiocatore);

}
