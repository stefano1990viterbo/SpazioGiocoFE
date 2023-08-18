package it.ricci.game.attori;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import it.ricci.game.Drop;

import java.sql.Time;
import java.util.Iterator;

import static it.ricci.game.Drop.LARGHEZZA_SCHERMO_GIOCO;

public class Cuore extends Rectangle {

  public static final int WIDTH = 64;
  public static final int HEIGHT = 64;
  private Texture image;

  private Array<Rectangle> actors = new Array<>();

  private long lastEnergiaTime;

  private Navicella navicella;

  public Cuore(Navicella navicella) {
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
    lastEnergiaTime = TimeUtils.millis();
  }

  private void initAsset() {
    image = new Texture(Gdx.files.internal("cuore.png"));
  }

  public void eseguiDraw(SpriteBatch batch) {
    for (Rectangle n : actors) {
      batch.draw(this.getImage(), n.getX(), n.getY());
    }
  }

  public Texture getImage() {
    return image;
  }

  public Integer movimentoCuore() {
    for (Iterator<Rectangle> energy = actors.iterator(); energy.hasNext(); ) {
      Rectangle energyR = energy.next();
      energyR.y -= 200 * Gdx.graphics.getDeltaTime();

      if (energyR.y + HEIGHT < 0) {
        energy.remove();
      }
      if (energyR.overlaps(navicella)) {
        energy.remove();
        //        aggiornaPunteggio(1);
        navicella.aggiornaVite(1);
        return 1;
      }
    }
    return 0;
  }

  private void controlloIntervalloCreazione() {
    if (TimeUtils.millis() - lastEnergiaTime > 10000) {
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
