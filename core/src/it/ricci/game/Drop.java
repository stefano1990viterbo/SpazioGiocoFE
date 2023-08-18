package it.ricci.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import it.ricci.game.attori.*;
import it.ricci.game.backend.infrastructure.InputProcessorCustom;
import it.ricci.game.backend.stomp.WebSocketClient;
import java.util.logging.Logger;

public class Drop extends ApplicationAdapter {

  public static final Integer LARGHEZZA_SCHERMO_GIOCO = 800;
  public static final Integer ALTEZZA_SCHERMO_GIOCO = 480;

  static final Logger logger = Logger.getLogger(Drop.class.getName());
  private SpriteBatch batch;
  private OrthographicCamera camera;

  //  private Sound dropSound;
  //  private Music rainMusic;

  private Integer score;
  private String scoreName;

  private String vite;
  BitmapFont bitmapFontName;

  private Navicella navicella;
  private Nemico nemico;

  private Energia energia;
  private Cuore cuore;


  @Override
  public void create() {

    WebSocketClient.getInstance().connectToWebSocket();

    initAssets();

    //    playBackgroundSound();

    camera = new OrthographicCamera();
    camera.setToOrtho(false, LARGHEZZA_SCHERMO_GIOCO, Drop.ALTEZZA_SCHERMO_GIOCO);
    batch = new SpriteBatch();

    initScore();

    navicella = new Navicella();
    nemico = new Nemico(navicella);
    energia = new Energia(navicella);
    cuore = new Cuore(navicella);
  }

  private void initScore() {
    score = 0;
    scoreName = "score: 0";
    vite = " Vite: 3";
    bitmapFontName = new BitmapFont();
  }

  private void playBackgroundSound() {
    //    rainMusic.setLooping(true);
    //    rainMusic.play();
  }

  private void initAssets() {
    //    dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
    //    rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.wav"));
  }

  @Override
  public void render() {
    setSfondo();
    cicloVitaBatch();

    navicella.elementiRender(energia.getActors(), nemico.getActors());
    nemico.elementiRender();
    energia.elementiRender();
    cuore.elementiRender();

    Integer punteggioDagliSpari =
        Proiettile.movimentoProiettile(
            navicella.getProiettili(), energia.getActors(), nemico.getActors());
    Integer scoreNemico = nemico.movimentoNemico();
    Integer scoreEnergia = energia.movimentoEnergia();
    cuore.movimentoCuore();

    int punteggio = punteggioDagliSpari + scoreNemico + scoreEnergia;

    aggiornaPunteggio(punteggio);

    InputProcessorCustom inputProcessor= new InputProcessorCustom();
    Gdx.input.setInputProcessor(inputProcessor);
  }

  @Override
  public void dispose() {
    //    dropSound.dispose();
    //    rainMusic.dispose();
    batch.dispose();
    navicella.dispose();
    energia.dispose();
    nemico.dispose();
    cuore.dispose();
  }

  private static void setSfondo() {
    ScreenUtils.clear(0, 0, 0.2f, 1);
  }

  private void cicloVitaBatch() {
    batch.setProjectionMatrix(camera.combined);
    batch.begin();

    navicella.eseguiDraw(batch);
    nemico.eseguiDraw(batch);
    energia.eseguiDraw(batch);
    cuore.eseguiDraw(batch);

    renderScore();
    batch.end();
  }

  private void renderScore() {
    bitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    bitmapFontName.draw(batch, scoreName + " " + vite, 25, 100);
    //    bitmapFontName.draw(batch, vite, 25, 100);
  }

  private void aggiornaPunteggio(int valoreDaAggiungereAlPunteggio) {
    score = score + valoreDaAggiungereAlPunteggio;
    //    if (score <= 0) {
    //      scoreName = "GAME OVER";
    //      this.resume();
    //      return;
    //    }

    scoreName = "score: " + score;
    vite = " Vite: " + String.valueOf(navicella.getVite());
  }
}