package it.ricci.game.entities;

import it.ricci.game.attori.Navicella;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiocatoreResource {
  public GiocatoreResource() {
  }

  private UUID id;
  private UUID username;

  private Double x;
  private Double y;
  private Double angoloDiDirezione;
}