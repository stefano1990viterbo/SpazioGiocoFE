package it.ricci.game.backend.esempio;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
@RequiredArgsConstructor
@Log4j2
public class ClientExample {

  private StompSession stompSession;

  public void connectToWebSocket() {

    System.out.println("Mi sto connettendo");

    String url = "ws://localhost:9191/app"; // Cambia l'URL con l'indirizzo del tuo server WebSocket

    WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    stompClient.setMessageConverter(new StringMessageConverter());

    //    StompSessionHandler sessionHandler = new TestStomSessionHandler();
    StompSessionHandler sessionHandler = new RicevitoreTest();

    WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
    handshakeHeaders.add("username", "Stefano");

    try {
      StompSession stompSession = stompClient.connect(url, handshakeHeaders, sessionHandler).get();
      this.stompSession = stompSession;

    } catch (Exception e) {
      log.error("Errore durante la connessione: " + e.getMessage());
    }
  }

  public void sendmessaggio(String messaggio){
    stompSession.send("/app/sendMessage", messaggio);
  }

}
