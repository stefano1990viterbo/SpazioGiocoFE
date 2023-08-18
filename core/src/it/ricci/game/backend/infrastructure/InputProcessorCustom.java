package it.ricci.game.backend.infrastructure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import it.ricci.game.backend.application.services.DatiInputService;
import it.ricci.game.backend.application.services.GiocatoreApplicationService;
import it.ricci.game.backend.domain.Giocatore;
import it.ricci.game.backend.stomp.WebSocketClient;
import it.ricci.game.entities.DatiInput;
import it.ricci.game.entities.GiocatoreResource;
import java.util.UUID;
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

    DatiInputService.getInstance().setInput(DatiInput.builder().keyDown(keycode).build());

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
    log.warn("TEST hei: "+Gdx.graphics.getHeight());
    log.warn("TEST y: "+Gdx.input.getY());
    float yInvertita = Gdx.graphics.getHeight() - Gdx.input.getY();
    log.warn("Y invertita: "+yInvertita);
    Vector3 touchPos = new Vector3();
    touchPos.set(Gdx.input.getX(), yInvertita, 0);
    log.warn("TEST touch y "+touchPos.y);
    log.warn("TEST touch x "+touchPos.x);



    DatiInputService.getInstance().setInput(DatiInput.builder().mouseX(screenX).mouseY(screenY).build());
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
