package it.ricci.game.entities;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatoGiocoResource {

  private List<GiocatoreResource> giocatori = new ArrayList<>();

}
