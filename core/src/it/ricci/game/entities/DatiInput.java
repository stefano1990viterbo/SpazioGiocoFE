package it.ricci.game.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class DatiInput {
  private String username;
  private int keyDown;
  private int mouseX;
  private int mouseY;
}
