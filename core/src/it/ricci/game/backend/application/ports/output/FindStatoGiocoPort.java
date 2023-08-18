package it.ricci.game.backend.application.ports.output;

import it.ricci.game.backend.application.services.StatoGiocoApplicationService;

public interface FindStatoGiocoPort {
  StatoGiocoApplicationService caricaStatoGioco();
}
