package it.ricci.game.entities.input_utente;

import lombok.Builder;
import lombok.Getter;

@Builder
//@Getter
public record TouchDown(int screenX, int screenY, int pointer, int button) {

}
