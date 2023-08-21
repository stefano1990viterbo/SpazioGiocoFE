package it.ricci.game.backend.stomp;

import it.ricci.game.SpazioGioco;
import it.ricci.game.backend.application.services.StatoGiocoApplicationService;
import it.ricci.game.entities.StatoGiocoResource;
import java.lang.reflect.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

@Log4j2
@RequiredArgsConstructor
public class RicevitoreStatoGioco extends StompSessionHandlerAdapter {

  //  private StompSession session;

  @Override
  public Type getPayloadType(StompHeaders headers) {
    return StatoGiocoResource.class;
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    StatoGiocoResource payload1 = (StatoGiocoResource) payload;

//    log.info("TEST ricevuto: "+payload1);

//    payload1.getProiettili().forEach(p-> log.info(p.toString()));

    StatoGiocoApplicationService.getInstance().aggiornaStatoGioco(payload1);
    //      aggiornaStatoGiocoUseCase.aggiornaStatoGioco(payload1);
  }

  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    //    this.session = session;

    log.info("Mi sono connesso");
    session.subscribe("/topic/aggiornagioco", this);
  }

  @Override
  public void handleException(
      StompSession session,
      StompCommand command,
      StompHeaders headers,
      byte[] payload,
      Throwable exception) {
    log.warn(String.format("Errore: %s ", exception.getMessage()));
    super.handleException(session, command, headers, payload, exception);
  }

  @Override
  public void handleTransportError(StompSession session, Throwable exception) {
    log.warn(String.format("Errore: %s ", exception.getMessage()));
    super.handleTransportError(session, exception);
  }

}
