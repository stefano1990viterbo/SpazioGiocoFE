package it.ricci.game.entities;

import java.time.Duration;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProiettileResource {
  private UUID id;
  private Duration durata;
  private Double xPartenza;
  private Double yPartenza;
  private Double xDirezione;
  private Double yDirezione;
  private Double angoloDiDirezione;
  private int velocita;
}
