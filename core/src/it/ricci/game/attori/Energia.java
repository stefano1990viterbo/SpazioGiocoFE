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

public class Energia {

  public static final int WIDTH = 64;
  public static final int HEIGHT = 64;
  private Texture image;

  private Array<Rectangle> actors = new Array<>();

  private long lastEnergiaTime;

  private Navicella navicella;


  public Energia(Navicella navicella) {
    this.navicella = navicella;
    initAsset();
    spawnEnergia();
  }

  private void spawnEnergia() {
    Rectangle energy = new Rectangle();
    energy.x = MathUtils.random(0, LARGHEZZA_SCHERMO_GIOCO - WIDTH);
    energy.y = Drop.ALTEZZA_SCHERMO_GIOCO;
    energy.width = WIDTH;
    energy.height = HEIGHT;
    actors.add(energy);
    lastEnergiaTime = TimeUtils.nanoTime();
  }

  private void initAsset() {
    image = new Texture(Gdx.files.internal("energia.png"));
  }

  public void eseguiDraw(SpriteBatch batch) {
    for (Rectangle n : actors) {
      batch.draw(this.getImage(), n.getX(), n.getY());
    }
  }

  public Texture getImage() {
    return image;
  }

  public Integer movimentoEnergia() {
    for (Iterator<Rectangle> energy = actors.iterator(); energy.hasNext(); ) {
      Rectangle energyR = energy.next();
      energyR.y -= 200 * Gdx.graphics.getDeltaTime();

      if (energyR.y + HEIGHT < 0) {
        energy.remove();
        }
      if (energyR.overlaps(navicella)) {
        energy.remove();
        //        aggiornaPunteggio(1);
        navicella.setTempImage(navicella.getImage());
        return 1;
      }
    }
    return 0;
  }

  private void controlloIntervalloCreazione() {
    if (TimeUtils.nanoTime() - lastEnergiaTime > 1000000000) {
      spawnEnergia();
    }
  }

  public void elementiRender() {
//    this.movimentoEnergia();
    this.controlloIntervalloCreazione();
  }

  public Array<Rectangle> getActors() {
    return actors;
  }

  public void dispose() {
    this.getImage().dispose();
  }


}
