package it.ricci.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpazioGame extends Game {

  public static final Integer LARGHEZZA_SCHERMO_GIOCO = 800;
  public static final Integer ALTEZZA_SCHERMO_GIOCO = 480;



  public SpriteBatch batch;
  public BitmapFont font;
  public void create() {
    batch = new SpriteBatch();
    font = new BitmapFont(); // use libGDX's default Arial font
    this.setScreen(new MainMenuScreen(this));
  }
  public void render() {
    super.render(); // important!
  }

  public void dispose() {
    batch.dispose();
    font.dispose();
  }
}
