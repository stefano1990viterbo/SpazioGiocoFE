package it.ricci.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import it.ricci.game.attori.Cuore;
import it.ricci.game.attori.Energia;
import it.ricci.game.attori.Navicella;
import it.ricci.game.attori.Nemico;
import it.ricci.game.backend.application.services.DatiInputService;
import it.ricci.game.backend.application.services.StatoGiocoApplicationService;
import it.ricci.game.backend.domain.Giocatore;
import it.ricci.game.backend.domain.Proiettile;
import it.ricci.game.backend.infrastructure.InputProcessorCustom;
import it.ricci.game.backend.stomp.WebSocketClient;
import it.ricci.game.entities.GiocatoreResource;
import it.ricci.game.entities.ProiettileResource;
import it.ricci.game.entities.input_utente.DatiInput;
import it.ricci.game.entities.input_utente.KeyDown;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameScreen implements Screen {

  final SpazioGame game;

  OrthographicCamera camera;

  private Integer score;
  private String scoreName;

  private Integer vite;
  BitmapFont bitmapFontName;

  private Navicella navicella;
  private Nemico nemico;

  private Energia energia;
  private Cuore cuore;

  private List<Giocatore> giocatoreList = new ArrayList<>();
  private List<Proiettile> proiettileList = new ArrayList<>();

  private Giocatore giocatore;

  //  public static UUID username = UUID.randomUUID();

  private Stage stage;
  private Skin skin;
  private BitmapFont font;
  private TextButton.TextButtonStyle buttonStyle;

  public GameScreen(final SpazioGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    camera.setToOrtho(false, game.LARGHEZZA_SCHERMO_GIOCO, game.ALTEZZA_SCHERMO_GIOCO);
    game.batch = new SpriteBatch();

    //    initScore();

    this.giocatore =
        new Giocatore(
            StatoGiocoApplicationService.getInstance().getGiocatoreDiSessione().getUsername());

    Gdx.input.setInputProcessor(InputProcessorCustom.getInstance());
  }

  @Override
  public void show() {
//    menuConPulsante();
  }

  private void menuConPulsante() {
    Table table = new Table();
    table.setFillParent(true);
    table.setDebug(true);
    stage = new Stage(new ScreenViewport());
    stage.addActor(table);

    skin = new Skin();

    // Carica un font per il pulsante
    font = new BitmapFont();

    // Crea uno stile per il pulsante
    TextButtonStyle buttonStyle = new TextButtonStyle();
    buttonStyle.font = font;

    // Crea un'immagine di colore solido per lo sfondo del pulsante
    NinePatch solidColor =
        new NinePatch(new TextureRegion(new Texture(Gdx.files.internal("rocket.png"))), 4, 4, 4, 4);
    buttonStyle.up = new NinePatchDrawable(solidColor);

    // Crea il pulsante e impostane la posizione
    TextButton button = new TextButton("Inizia la partita!", buttonStyle);

    // add buttons to table
    table.add(button).fillX().uniformX();
    table.row().pad(10, 0, 10, 0);
    button.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("Ricarico il gioco!");
            game.setScreen(new GameScreen(game));
            dispose();
            stage.dispose();
          }
        });
  }

  @Override
  public void render(float delta) {
    setSfondo();
    cicloVitaBatch();
    gestioneTasti();
    informazioniDaVisualizzare();
  }

  private void informazioniDaVisualizzare() {
    camera.update();
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();
    String labelVita= String.format("Vite: %s",vite);
    game.font.draw(game.batch, labelVita, 50, 50);
    game.batch.end();
  }

  private void gestioneTasti() {
    if (Gdx.input.isKeyPressed(Keys.A)) {
      DatiInputService.getInstance()
          .setInput(DatiInput.builder().keyDown(KeyDown.builder().keycode(Keys.A).build()).build());
    }
    if (Gdx.input.isKeyPressed(Keys.D)) {
      DatiInputService.getInstance()
          .setInput(DatiInput.builder().keyDown(KeyDown.builder().keycode(Keys.D).build()).build());
    }

    if (Gdx.input.isKeyPressed(Keys.W)) {
      DatiInputService.getInstance()
          .setInput(DatiInput.builder().keyDown(KeyDown.builder().keycode(Keys.W).build()).build());
    }

    if (Gdx.input.isKeyPressed(Keys.S)) {
      DatiInputService.getInstance()
          .setInput(DatiInput.builder().keyDown(KeyDown.builder().keycode(Keys.S).build()).build());
    }

  }

  private static void setSfondo() {
    ScreenUtils.clear(0, 0, 0.2f, 1);
  }

  private void cicloVitaBatch() {
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();

    if (navicella != null) {
      navicella.eseguiDraw(game.batch);
      nemico.eseguiDraw(game.batch);
      energia.eseguiDraw(game.batch);
      cuore.eseguiDraw(game.batch);
    }

    giocatoreList.clear();


    List<ProiettileResource> proiettileResources =
        StatoGiocoApplicationService.getInstance().getProiettileResources();

    for (ProiettileResource p : proiettileResources) {
      Proiettile proiettile = new Proiettile(p);
      proiettileList.add(proiettile);
      proiettile.getActor().eseguiDraw(game.batch);
    }

    drawGiocatore();

    //    renderScore();
    game.batch.end();
  }

  private void drawGiocatore() {
    List<GiocatoreResource> player = StatoGiocoApplicationService.getInstance().getPlayer();
    for (GiocatoreResource giocatoreReource : player) {
      Giocatore giocatore = new Giocatore(giocatoreReource);
      giocatoreList.add(giocatore);
      giocatore.getNavicella().eseguiDraw(game.batch);
      if (this.giocatore != null && giocatore.getUsername().equals(this.giocatore.getUsername())) {
        this.giocatore = giocatore;
        this.vite = giocatore.getVite();
      }
    }
  }

  @Override
  public void resize(int width, int height) {}

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void hide() {}

  @Override
  public void dispose() {
    proiettileList.forEach(p -> p.getActor().dispose());
    giocatoreList.forEach(g -> g.getNavicella().dispose());
    giocatore.getNavicella().dispose();
  }

  public static void chiudiGioco() {
    Gdx.app.exit();
  }
}
