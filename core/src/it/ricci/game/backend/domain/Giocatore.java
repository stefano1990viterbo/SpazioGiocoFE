package it.ricci.game.backend.domain;

import it.ricci.game.attori.Navicella;
import it.ricci.game.entities.GiocatoreResource;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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

    this.id=resource.getId();
    this.username= resource.getUsername();

    navicella = new Navicella();

    if(resource.getAngoloDiDirezione()!=null){
      navicella.setRotazioneInGradi(resource.getAngoloDiDirezione().floatValue());
      navicella.setX(resource.getX().floatValue());
      navicella.setY(resource.getY().floatValue());
    }else if (resource.getX()!=null){
      navicella.setRotazioneInGradi(0);
      navicella.setX(resource.getX().floatValue());
      navicella.setY(resource.getY().floatValue());
    }else {
      navicella.setRotazioneInGradi(2);
      navicella.setX(150);
      navicella.setY(150);

    }
  }

  public void aggiornaStatoGiocatore(GiocatoreResource nuovoStato){
    //TODO
    navicella.setRotazioneInGradi(nuovoStato.getAngoloDiDirezione().floatValue());
    navicella.setX(nuovoStato.getX().floatValue());
    navicella.setY(nuovoStato.getY().floatValue());
  }
}
