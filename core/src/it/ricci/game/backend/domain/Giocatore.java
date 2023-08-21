package it.ricci.game.backend.domain;

import it.ricci.game.attori.Navicella;
import it.ricci.game.entities.GiocatoreResource;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@ToString
@Log4j2
public class Giocatore {

  private UUID id;
  private UUID username;

  private Navicella navicella;

  private Double x;
  private Double y;
  private Double angoloDiDirezione;

  public Giocatore(UUID username) {
    this.username = username;
  }

  public Giocatore(GiocatoreResource resource) {

    this.id = resource.getId();
    this.username = resource.getUsername();

    navicella = new Navicella();

    float angolo =
        resource.getAngoloDiDirezione() == null ? 90 : resource.getAngoloDiDirezione().floatValue();

    navicella.setRotazioneInGradi(angolo);
    navicella.setX(resource.getX().floatValue());
    navicella.setY(resource.getY().floatValue());
  }

}
