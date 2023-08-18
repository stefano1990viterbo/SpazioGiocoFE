package it.ricci.game.backend.application.ports.input;

import it.ricci.game.entities.StatoGiocoResource;

public interface AggiornaStatoGiocoUseCase {
  void aggiornaStatoGioco(StatoGiocoResource resource);

}
