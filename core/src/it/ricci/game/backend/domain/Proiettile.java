package it.ricci.game.backend.domain;

import it.ricci.game.attori.ProiettileActor;
import it.ricci.game.entities.ProiettileResource;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Proiettile {

  private UUID id;
  private ProiettileActor actor;
  private double rotazioneInGradi;

  public Proiettile(ProiettileResource resource) {
    this.id = resource.getId();

    actor=new ProiettileActor();

    actor.x=resource.getX().floatValue();
    actor.y=resource.getY().floatValue();
   actor.setRotazioneInGradi(resource.getAngoloDiDirezione().floatValue());
  }
}
