package it.ricci.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
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
import it.ricci.game.backend.application.services.StatoGiocoApplicationService;
import it.ricci.game.backend.stomp.WebSocketClient;
import it.ricci.game.entities.input_utente.DatiInput;
import it.ricci.game.entities.input_utente.KeyDown;
import java.util.UUID;

public class MainMenuScreen implements Screen {

  final SpazioGame game;
  OrthographicCamera camera;
  private Stage stage;
  private Skin skin;
  private BitmapFont font;
  private TextButton.TextButtonStyle buttonStyle;

  public MainMenuScreen(final SpazioGame game) {
    this.game = game;
    stage = new Stage(new ScreenViewport());

    camera = new OrthographicCamera();
    camera.setToOrtho(false, 800, 480);

    WebSocketClient.getInstance().connectToWebSocket(username);

    WebSocketClient.getInstance()
        .inviaDatiInput(DatiInput.builder().keyDown(KeyDown.builder().keycode(29).build()).build());

    //    Gdx.input.setInputProcessor(InputProcessorCustom.getInstance());
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void show() {
    //    menuConPulsante();
    //    sempliceMenu();
  }

  public static UUID username = UUID.randomUUID();

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0.2f, 1);

    camera.update();
    game.batch.setProjectionMatrix(camera.combined);

    sempliceMenu();
    stage.draw();

    game.batch.begin();
    game.font.draw(game.batch, "Benvenuto nello Spazio Gioco!!! ", 200, 250);
    game.font.draw(game.batch, "Tocca un punto qualsiasi per iniziare!", 200, 200);

    String stringaGiocatoredaVisualizzare = getMessaggioNumeroDiGiocatori();
    game.font.draw(game.batch, stringaGiocatoredaVisualizzare, 200, 150);

    game.batch.end();

  }

  private void onClickAvviaPartita(){
      game.setScreen(new GameScreen(game));
      dispose();
      stage.dispose();
  }

  private static String getMessaggioNumeroDiGiocatori() {
    int size = StatoGiocoApplicationService.getInstance().getPlayer().size();

    String oneGiocatore = "Sei solo, chiama qualcuno per giocare..";
    String moreGiocatori = "Ci sono " + size + " giocatori";
    String stringaGiocatoredaVisualizzare = size > 1 ? moreGiocatori : oneGiocatore;
    return stringaGiocatoredaVisualizzare;
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
  public void dispose() {}

  private void sempliceMenu() {

    Table table = new Table();
    table.setFillParent(true);
    table.setDebug(true);
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
    TextButton iniziaPartitaButton = new TextButton("Inizia la partita!", buttonStyle);
    iniziaPartitaButton.setPosition(100, 300);
    TextButton opzioniButton = new TextButton("Opzioni", buttonStyle);
    opzioniButton.setPosition(100, 200);
    TextButton esciButton = new TextButton("Esci", buttonStyle);
    esciButton.setPosition(100, 100);

    // add buttons to table
    table.add(iniziaPartitaButton).fillX().uniformX();
    table.add(opzioniButton).fillX().uniformX();
    table.add(esciButton).fillX().uniformX();

    table.row().pad(10, 0, 10, 0);

    // Aggiungi listener per gestire i clic sui pulsanti
    iniziaPartitaButton.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("Pulsante Gioca cliccato!");
            onClickAvviaPartita();
          }
        });

    opzioniButton.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("Pulsante Opzioni cliccato!");
          }
        });

    esciButton.addListener(
        new ClickListener() {
          @Override
          public void clicked(InputEvent event, float x, float y) {
            System.out.println("Pulsante Esci cliccato!");
            Gdx.app.exit(); // Esci dall'applicazione
          }
        });

    // Aggiungi i pulsanti allo stage
    stage.addActor(iniziaPartitaButton);
    stage.addActor(opzioniButton);
    stage.addActor(esciButton);
  }
}
