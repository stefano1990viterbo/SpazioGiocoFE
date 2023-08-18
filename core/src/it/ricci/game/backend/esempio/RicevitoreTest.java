package it.ricci.game.backend.esempio;

import java.lang.reflect.Type;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;

@Log4j2
public class RicevitoreTest extends StompSessionHandlerAdapter {

  public RicevitoreTest() {
    super();
  }

  private StompSession session;

  @Override
  public Type getPayloadType(StompHeaders headers) {

    return super.getPayloadType(headers);
  }

  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    super.handleFrame(headers, payload);
  }

  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    this.session=session;

    log.info("Mi sono connesso");

    session.subscribe(
        "/topic/messages",
        new StompFrameHandler() {
          @Override
          public Type getPayloadType(StompHeaders headers) {
            return String.class;
          }

          @Override
          public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Received message: " + payload);
          }
        });

//    session.send("/app/sendMessage", "Hello, Server!");

  }


  @Override
  public void handleException(StompSession session, StompCommand command, StompHeaders headers,
      byte[] payload, Throwable exception) {
    log.warn("Errore "+exception.getMessage());
    super.handleException(session, command, headers, payload, exception);
  }

  @Override
  public void handleTransportError(StompSession session, Throwable exception) {
    log.warn("Errore "+exception.getMessage());
    super.handleTransportError(session, exception);
  }

}
