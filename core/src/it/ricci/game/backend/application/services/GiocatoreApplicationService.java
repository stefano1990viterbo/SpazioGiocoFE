package it.ricci.game.backend.application.services;

import it.ricci.game.backend.application.ports.input.AggiornaGiocatore;
import it.ricci.game.backend.domain.Giocatore;

public class GiocatoreApplicationService implements AggiornaGiocatore {

  private static GiocatoreApplicationService INSTANCE;

  private GiocatoreApplicationService() {}

  public static GiocatoreApplicationService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new GiocatoreApplicationService();
    }
    return INSTANCE;
  }

  private Giocatore giocatore;

  @Override
  public Giocatore aggiornaGiocatore(Giocatore nuovoStatoGiocatore) {
    this.giocatore = nuovoStatoGiocatore;
    return giocatore;
  }

  public Giocatore getGiocatore() {
    return this.giocatore;
  }
}
