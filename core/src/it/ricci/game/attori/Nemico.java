package it.ricci.game.attori;

import static it.ricci.game.Drop.LARGHEZZA_SCHERMO_GIOCO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import it.ricci.game.Drop;

import java.util.Iterator;

public class Nemico {

  private Texture image;

  private Array<Rectangle> actors = new Array<>();

  private long lastNemicoTime;
  private Navicella navicella;

  public Nemico(Navicella navicella) {
    this.navicella = navicella;
    initAsset();
    spawnNemico();
  }

  void initAsset() {
    image = new Texture(Gdx.files.internal("nemico.png"));
  }

  public Integer movimentoNemico() {
    for (Iterator<Rectangle> nemico = actors.iterator(); nemico.hasNext(); ) {
      Rectangle nemicoRettangolo = nemico.next();
      nemicoRettangolo.y -= 225 * Gdx.graphics.getDeltaTime();

      if (nemicoRettangolo.y + 64 < 0) {
        nemico.remove();
      }
      if (nemicoRettangolo.overlaps(navicella)) {
        nemico.remove();

        navicella.aggiornaVite(-1);

         return -3;
      }
    }
    return 0;
  }

  private void controlloIntervalloCreazione() {
    if (TimeUtils.nanoTime() - lastNemicoTime > 1000000000) {
      spawnNemico();
    }
  }

  public void spawnNemico() {
    Rectangle attore = new Rectangle();
    attore.x = MathUtils.random(0, LARGHEZZA_SCHERMO_GIOCO - 64);
    attore.y = Drop.ALTEZZA_SCHERMO_GIOCO;
    attore.width = 64;
    attore.height = 64;
    actors.add(attore);
    lastNemicoTime = TimeUtils.nanoTime();
  }

  public void eseguiDraw(SpriteBatch batch) {
    for (Rectangle n : actors) {
      batch.draw(this.getImage(), n.getX(), n.getY());
    }
  }

  private Texture getImage() {
    return image;
  }


  public Array<Rectangle> getActors() {
    return actors;
  }

  public void elementiRender() {
//    this.movimentoNemico();
    this.controlloIntervalloCreazione();
  }

  public void dispose() {
    this.getImage().dispose();
  }
}
