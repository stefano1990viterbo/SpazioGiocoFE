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
  private Double x;
  private Double y;
  private Double xDirezione;
  private Double yDirezione;
  private Double angoloDiDirezione;
  private int velocita;
  private int width;
  private int height;
}
