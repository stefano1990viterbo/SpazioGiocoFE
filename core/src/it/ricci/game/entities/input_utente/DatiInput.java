package it.ricci.game.entities.input_utente;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DatiInput {
  private String usernameKeyboard;
  private KeyDown keyDown;
  private MouseMoved mouseMoved;
  private TouchDown touchDown;
}
