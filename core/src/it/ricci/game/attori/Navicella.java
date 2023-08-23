package it.ricci.game.attori;

import static it.ricci.game.Drop.ALTEZZA_SCHERMO_GIOCO;
import static it.ricci.game.Drop.LARGHEZZA_SCHERMO_GIOCO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.logging.Logger;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Navicella extends Rectangle {

  static final Logger logger = Logger.getLogger(Navicella.class.getName());
  public static final int WIDTH = 32;
  public static final int HEIGHT = 32;
  public static final int VELOCITA_NAVICELLA = 200;
  public static final int SUPER_VELOCITA_NAVICELLA = 400;

  private int velocitaNavicella = VELOCITA_NAVICELLA;
  private Texture image;
  private Texture imageDestory;

  private Texture tempImage;

  private float rotazioneInGradi;

  private Array<ProiettileActor> proiettili = new Array<>();
  private long lastProiettileTime;
  private boolean puoiSparare = true;

  private int vite = 3;

  public Navicella() {
    initAsset();
    initDimensione();
  }

  private void initDimensione() {
    this.x = LARGHEZZA_SCHERMO_GIOCO / 2 - WIDTH / 2;
    this.y = 20;
    this.width = WIDTH;
    this.height = HEIGHT;
  }

  private void limiti() {
    if (this.x < 0) {
      this.x = 0;
    }
    if (this.x > LARGHEZZA_SCHERMO_GIOCO - WIDTH) {
      this.x = LARGHEZZA_SCHERMO_GIOCO - WIDTH;
    }

    if (this.y < 0) {
      this.y = 0;
    }
    if (this.y > ALTEZZA_SCHERMO_GIOCO - HEIGHT) {
      this.y = ALTEZZA_SCHERMO_GIOCO - HEIGHT;
    }
  }

  void initAsset() {
      image = new Texture(Gdx.files.internal("rocket.png"));
      //    laserImage = new Texture(Gdx.files.internal("laser.png"));
      imageDestory = new Texture(Gdx.files.internal("esplode.png"));
      tempImage = image;
  }

//  private void spostamenti() {
//    configuraMovimentoConFrecce();
//    configuraMovimentoConWASD();
//    setRotazioneInGradi(calcoloAngoloDiRotazione());
//    configuraSparo();
//  }

  private void configuraSparo() {
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && puoiSparare) {
      //      creazioneProiettile();
      ProiettileActor proiettile = new ProiettileActor();
      proiettile.creazioneProiettile(this);
      proiettili.add(proiettile);
    }
  }




  public void controlloIntervalloCreazioneProiettile() {
    if (TimeUtils.nanoTime() - lastProiettileTime > 100000000) {
      puoiSparare = true;
    }
  }

  public void eseguiDraw(SpriteBatch batch) {
    //    batch.draw(tempImage, actor.getX(), actor.getY());

    drawNavicella(batch);

    for (ProiettileActor p : this.proiettili) {
      //      batch.draw(laserImage, p.x, p.y);
      p.eseguiDraw(batch);
    }
  }

  private void drawNavicella(SpriteBatch batch) {
    //    TextureRegion textureRegion = new TextureRegion(tempImage);
    TextureRegion textureRegion = new TextureRegion(image);
    float originX = textureRegion.getRegionWidth() / 2f; // Punto centrale della texture sull'asse x
    float originY =
        textureRegion.getRegionHeight() / 2f; // Punto centrale della texture sull'asse y
    float rotationDegrees = getRotazioneInGradi(); // Angolo di rotazione in gradi
//    float rotationDegrees =calcoloAngoloDiRotazione(); // Angolo di rotazione in gradi

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
        rotationDegrees);
  }

  public float getRotazioneInGradi() {
    return rotazioneInGradi;
  }

  public void setRotazioneInGradi(float rotazioneInGradi) {
    this.rotazioneInGradi = rotazioneInGradi;
  }

  public boolean isCollidono(Rectangle ostacolo) {
    return this.overlaps(ostacolo);
  }

  public boolean isCollidono(Array<Rectangle> ostacoli) {
    boolean collidono = false;
    for (Rectangle ostacolo : ostacoli) {
      if (this.overlaps(ostacolo)) {
        collidono = true;
        break;
      }
    }
    return collidono;
  }

  public Texture getImage() {
    return image;
  }

  public Rectangle getthis() {
    return this;
  }

  public void dispose() {
    this.getImage().dispose();
  }

  public Array<ProiettileActor> getProiettili() {
    return proiettili;
  }

//  public void elementiRender(Array<Rectangle> gocce, Array<Rectangle> asteroidi) {
//    this.spostamenti();
//    this.limiti();
//    //    this.movimentoProiettile(gocce, asteroidi);
//    this.controlloIntervalloCreazioneProiettile();
//  }

  public void setImage(Texture image) {
    this.image = image;
  }

  public Texture getImageDestory() {
    return imageDestory;
  }

  public void setTempImage(Texture tempImage) {
    logger.info("IMPOSTATA immagine di esplosione ");
    this.tempImage = tempImage;
  }

  public int getVite() {
    return vite;
  }

  public void setVite(int vite) {
    this.vite = vite;
  }

  public void aggiornaVite(int vita) {
    this.setVite(this.getVite() + vita);
    //    int viteRimaste = navicella.getVite();
    System.out.println("Vite rimaste: " + this.getVite());
    if (this.getVite() <= 0) {
      this.setTempImage(this.getImageDestory());
    }
  }

  public void setPuoiSparare(boolean puoiSparare) {
    this.puoiSparare = puoiSparare;
  }

  public int getVelocitaNavicella() {
    return velocitaNavicella;
  }

  public void setVelocitaNavicella(int velocitaNavicella) {
    this.velocitaNavicella = velocitaNavicella;
  }

  public void setLastProiettileTime(long lastProiettileTime) {
    this.lastProiettileTime = lastProiettileTime;
  }

  public void impostaImmagineDistruzione(){
    this.setImage(imageDestory);
  }
}
