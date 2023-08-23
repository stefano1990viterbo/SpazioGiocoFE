package it.ricci.game.backend.stomp;

import it.ricci.game.Drop;
import it.ricci.game.GameScreen;
import it.ricci.game.MainMenuScreen;
import it.ricci.game.SpazioGame;
import it.ricci.game.backend.application.services.StatoGiocoApplicationService;
import it.ricci.game.entities.GiocatoreResource;
import it.ricci.game.entities.input_utente.DatiInput;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSession.Receiptable;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
@Log4j2
public class WebSocketClient {

  private static WebSocketClient INSTANCE;

  private WebSocketClient() {
  }

  public static WebSocketClient getInstance(){
    if(INSTANCE== null){
      INSTANCE= new WebSocketClient();
    }
    return INSTANCE;
  }

  private StompSession stompSession;


  public void connectToWebSocket(UUID username) {

    System.out.println("Mi sto connettendo");

    String url = "ws://localhost:9191/app"; // Cambia l'URL con l'indirizzo del tuo server WebSocket

    WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());

    //    StompSessionHandler sessionHandler = new TestStomSessionHandler();
//    StompSessionHandler sessionHandler = new RicevitoreTest();
    StompSessionHandler sessionHandler = new RicevitoreStatoGioco();

    WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
    handshakeHeaders.add("username", username.toString());

    try {
      StompSession stompSession = stompClient.connect(url, handshakeHeaders, sessionHandler).get();
      this.stompSession = stompSession;

      StatoGiocoApplicationService.getInstance().inizializzaGiocatoreDiSessione(username);


    } catch (Exception e) {
      log.error("Errore durante la connessione: " + e.getMessage());
    }
  }

  public void sendmessaggio(String messaggio) {
    stompSession.send("/app/sendMessage", messaggio);
  }

  public void inviaDatiGiocatore(GiocatoreResource giocatoreResource) {
    stompSession.send("/app/inviogiocatore", giocatoreResource);

  }

  public void inviaDatiInput(DatiInput input){
//    input.setUsernameKeyboard(Drop.username.toString());
    input.setUsernameKeyboard(MainMenuScreen.username.toString());
    Receiptable send = stompSession.send("/app/key-input", input);
  }
}
