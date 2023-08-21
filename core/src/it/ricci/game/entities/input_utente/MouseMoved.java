package it.ricci.game.entities.input_utente;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
@Builder
public record MouseMoved (int screenX, int screenY){
}
