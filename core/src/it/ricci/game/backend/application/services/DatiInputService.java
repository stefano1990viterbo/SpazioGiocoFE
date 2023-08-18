package it.ricci.game.backend.application.services;

import it.ricci.game.backend.stomp.WebSocketClient;
import it.ricci.game.entities.DatiInput;

public class DatiInputService {
  private static DatiInputService INSTANCE;

  private DatiInputService() {}

  public static DatiInputService getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new DatiInputService();
    }
    return INSTANCE;
  }

  private DatiInput input;

  public DatiInput getInput() {
    return this.input;
  }

  public void setInput(DatiInput input) {
    this.input = input;
    inviaDatiInput();
  }

  private void inviaDatiInput(){
    WebSocketClient.getInstance().inviaDatiInput(this.input);
  }
}
