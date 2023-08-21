package it.ricci.game.backend.infrastructure;

import com.badlogic.gdx.InputProcessor;
import it.ricci.game.backend.application.services.DatiInputService;
import it.ricci.game.entities.input_utente.DatiInput;
import it.ricci.game.entities.input_utente.KeyDown;
import it.ricci.game.entities.input_utente.MouseMoved;
import it.ricci.game.entities.input_utente.TouchDown;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class InputProcessorCustom implements InputProcessor {

  public InputProcessorCustom() {}

  //  private ClientWebSocket client;

  private boolean stoPremendoIlTasto;

  @Override
  public boolean keyDown(int keycode) {
    // TODO rifare
    //    setStoPremendoIlTasto(true);
    //    while (stoPremendoIlTasto){
    //      client.inviaTasti(keycode);
    //    }

    DatiInputService.getInstance()
        .setInput(DatiInput.builder().keyDown(KeyDown.builder().keycode(keycode).build()).build());

    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    setStoPremendoIlTasto(false);
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    log.warn("Cliccato");
    DatiInputService.getInstance()
        .setInput(
            DatiInput.builder()
                .touchDown(
                    TouchDown.builder()
                        .button(button)
                        .pointer(pointer)
                        .screenX(screenX)
                        .screenY(screenY)
                        .build())
                .build());
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    DatiInputService.getInstance()
        .setInput(
            DatiInput.builder()
                .mouseMoved(MouseMoved.builder().screenX(screenX).screenY(screenY).build())
                .build());
    return false;
  }

  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
  }

  private void setStoPremendoIlTasto(boolean premoIlTasto) {
    this.stoPremendoIlTasto = premoIlTasto;
  }
}
