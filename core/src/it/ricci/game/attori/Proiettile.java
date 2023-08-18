package it.ricci.game.attori;

import static it.ricci.game.Calcoli.Trigonometria.trovaXDaYDellaRetta;
import static it.ricci.game.Calcoli.Trigonometria.trovaYDaXDellaRetta;
import static it.ricci.game.Drop.LARGHEZZA_SCHERMO_GIOCO;
import static java.lang.Math.abs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import it.ricci.game.Drop;
import java.util.Iterator;
import java.util.logging.Logger;


public class Proiettile extends Rectangle {

  static final Logger logger = Logger.getLogger(Proiettile.class.getName());

  public static int WIDTH = 12;
  public static int HEIGHT = 12;

  public static final int VELOCITA = 25;
  private final Texture laserImage;
  private float xDirezione;
  private float yDirezione;

  private float rotazioneInGradi;


  private long lastProiettileTime;

//  private boolean puoiSparare = true;

  public Proiettile() {
    laserImage = new Texture(Gdx.files.internal("laser.png"));
  }

  public boolean collideCon(Rectangle ostacolo) {
    return this.overlaps(ostacolo);
  }

  public boolean collideCon(Array<Rectangle> ostacoli) {
    boolean collidono = false;
    for (Rectangle ostacolo : ostacoli) {
      if (this.overlaps(ostacolo)) {
        collidono = true;
        break;
      }
    }
    return collidono;
  }

  public static Integer movimentoProiettile(
      Array<Proiettile> proiettili, Array<Rectangle> gocce, Array<Rectangle> asteroidi) {

    for (Iterator<Proiettile> iterProiettile = proiettili.iterator(); iterProiettile.hasNext(); ) {

      Proiettile proiettile = null;
      try {
        proiettile = iterProiettile.next();

      } catch (Exception e) {
        logger.info(String.format("Proiettile non trovato %s ", e.getMessage()));
        return 0;
      }

      aggiornaCoordinateDelProiettile(proiettile);

      eliminaProiettileOltreLaSchermata(iterProiettile, proiettile);

      for (Iterator<Rectangle> nemico = asteroidi.iterator(); nemico.hasNext(); ) {
        Rectangle asteroide = nemico.next();
        if (proiettile.collideCon(asteroide)) {
          nemico.remove();
          rimuoviProiettile(iterProiettile);
          //                aggiornaPunteggio(2);
          return 2;
        }
      }

      for (Iterator<Rectangle> goccia = gocce.iterator(); goccia.hasNext(); ) {
        Rectangle gocciaR = goccia.next();
        if (proiettile.overlaps(gocciaR)) {
          goccia.remove();
          rimuoviProiettile(iterProiettile);
          //                aggiornaPunteggio(-1);
          return -1;
        }
      }
    }
    return 0;
  }

  private static void rimuoviProiettile(Iterator<Proiettile> iterProiettile) {
    try {
      iterProiettile.remove();
    } catch (IndexOutOfBoundsException e) {
      logger.info(String.format("Proiettile già rimosso %s ", e.getMessage()));
    }
  }

  private static void eliminaProiettileOltreLaSchermata(
      Iterator<Proiettile> iterProiettile, Proiettile proiettile) {
    if (proiettile.y + 64 < 0) {
      rimuoviProiettile(iterProiettile);
    } else if (proiettile.y + 64 > Drop.ALTEZZA_SCHERMO_GIOCO) {
      rimuoviProiettile(iterProiettile);
    }

    if (proiettile.x + 64 < 0) {
      rimuoviProiettile(iterProiettile);
    } else if (proiettile.x + 64 > Drop.LARGHEZZA_SCHERMO_GIOCO) {
      rimuoviProiettile(iterProiettile);
    }
  }

  private static void aggiornaCoordinateDelProiettile(Proiettile proiettile) {
    float xConosciuta = 0;
    float yCalcolata = 0;
    int unitaX = 1;
    float rapportoY = calcolaRapportoY(proiettile);
    float nSegmenti = unitaX + rapportoY;
    float veloCitaAlSegmento = VELOCITA / nSegmenti;

    if (proiettile.getxDirezione() >= proiettile.x) {
      //Gdx.graphics.getDeltaTime();
      xConosciuta = proiettile.x + veloCitaAlSegmento;
    } else {
      xConosciuta = proiettile.x - veloCitaAlSegmento;
    }

    if (proiettile.getyDirezione() >= proiettile.y) {
      yCalcolata = proiettile.y + (veloCitaAlSegmento * rapportoY);
    } else {
      yCalcolata = proiettile.y - (veloCitaAlSegmento * rapportoY);
    }

    proiettile.x = xConosciuta;
    proiettile.y = yCalcolata;
  }

  private static float calcolaRapportoY(Proiettile proiettile) {
    return abs(
        (proiettile.getyDirezione() - proiettile.y) / (proiettile.getxDirezione() - proiettile.x));
  }

  public void eseguiDraw(SpriteBatch batch) {
    TextureRegion textureRegion = new TextureRegion(laserImage);
    float originX = textureRegion.getRegionWidth() / 2f; // Punto centrale della texture sull'asse x
    float originY =
        textureRegion.getRegionHeight() / 2f; // Punto centrale della texture sull'asse y

    batch.draw(
        textureRegion,
        this.getX(),
        this.getY(),
        originX,
        originY,
        textureRegion.getRegionWidth(),
        textureRegion.getRegionHeight(),
        1,
        1,
        this.getRotazioneInGradi());
  }

  public Proiettile creazioneProiettile(Navicella riferimentoNavicella) {
    Vector3 touchPos = new Vector3();
    float yInvertita = Gdx.graphics.getHeight() - Gdx.input.getY();
    touchPos.set(Gdx.input.getX(), yInvertita, 0);

//    Proiettile proiettile = new Proiettile();
    Proiettile proiettile = this;
    proiettile.x = riferimentoNavicella.getX() + WIDTH;
    proiettile.y = riferimentoNavicella.getY();

    float yOltreSchermo =
            trovaYDaXDellaRetta(
                    proiettile.x,
                    proiettile.y,
                    touchPos.x,
                    touchPos.y,
                    setXConosciuta(proiettile.x, touchPos.x));

    float xOltreSchermo =
            trovaXDaYDellaRetta(proiettile.x, proiettile.y, touchPos.x, touchPos.y, yOltreSchermo);

    proiettile.setxDirezione(xOltreSchermo);
    proiettile.setyDirezione(yOltreSchermo);

    proiettile.width = Proiettile.WIDTH;
    proiettile.height = Proiettile.HEIGHT;
    proiettile.setRotazioneInGradi(riferimentoNavicella.getRotazioneInGradi());
//    proiettili.add(proiettile);
    riferimentoNavicella.setLastProiettileTime(TimeUtils.nanoTime());
    riferimentoNavicella.setPuoiSparare(false);

    return proiettile;
  }

  private float setXConosciuta(float xProiettileIniziale, float yDirezioneProiettile) {
    if (yDirezioneProiettile >= xProiettileIniziale) {
      return xProiettileIniziale + LARGHEZZA_SCHERMO_GIOCO;
    } else {
      return xProiettileIniziale - LARGHEZZA_SCHERMO_GIOCO;
    }
  }

//  public void controlloIntervalloCreazioneProiettile() {
//    if (TimeUtils.nanoTime() - lastProiettileTime > 100000000) {
//      riferimentoNavicella.setPuoiSparare(true);
//    }
//  }

  public float getxDirezione() {
    return xDirezione;
  }

  public void setxDirezione(float xDirezione) {
    this.xDirezione = xDirezione;
  }

  public float getyDirezione() {
    return yDirezione;
  }

  public void setyDirezione(float yDirezione) {
    this.yDirezione = yDirezione;
  }

  public float getRotazioneInGradi() {
    return rotazioneInGradi;
  }

  public void setRotazioneInGradi(float rotazioneInGradi) {
    this.rotazioneInGradi = rotazioneInGradi;
  }
}
