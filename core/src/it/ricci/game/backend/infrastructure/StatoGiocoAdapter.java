package it.ricci.game.backend.infrastructure;

import it.ricci.game.backend.application.ports.output.FindStatoGiocoPort;
import it.ricci.game.backend.application.services.StatoGiocoApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
public class StatoGiocoAdapter implements FindStatoGiocoPort {

  private final StatoGiocoApplicationService statoGioco;
  @Override
  public StatoGiocoApplicationService caricaStatoGioco() {
    return statoGioco;
  }
}
