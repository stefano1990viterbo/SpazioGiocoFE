package it.ricci.game.backend.application.services;

import it.ricci.game.backend.application.ports.input.AggiornaStatoGiocoUseCase;
import it.ricci.game.backend.domain.Giocatore;
import it.ricci.game.entities.GiocatoreResource;
import it.ricci.game.entities.StatoGiocoResource;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Getter
@Service
@Log4j2
public class StatoGiocoApplicationService implements AggiornaStatoGiocoUseCase {

  private static StatoGiocoApplicationService INSTANCE;

  private StatoGiocoApplicationService() {
  }

  public static StatoGiocoApplicationService getInstance(){
    if(INSTANCE== null){
      INSTANCE= new StatoGiocoApplicationService();
    }
    return INSTANCE;
  }

  private List<Giocatore> giocatori = new ArrayList<>();


  @Override
  public void aggiornaStatoGioco(StatoGiocoResource statoGioco) {

    pulisciGiocatori();

    List<GiocatoreResource> giocatoreResources = statoGioco.getGiocatoreResources();

    for (GiocatoreResource giocatoreReource : giocatoreResources) {
      Giocatore giocatore = new Giocatore(giocatoreReource);
      giocatori.add(giocatore);
    }
  }

  private void pulisciGiocatori() {
    for (Giocatore giocatore : giocatori) {
      giocatore.getNavicella().dispose();
    }
    giocatori.clear();
  }
}