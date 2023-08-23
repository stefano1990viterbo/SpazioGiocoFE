package it.ricci.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("Spazio Gioco");
    config.setWindowedMode(SpazioGame.LARGHEZZA_SCHERMO_GIOCO, SpazioGame.ALTEZZA_SCHERMO_GIOCO);
    config.useVsync(true);
    config.setForegroundFPS(60);

    //    GL.createCapabilities();
    //    glfwMakeContextCurrent(window);
    //    new Lwjgl3Application(new Drop(), config);
    new Lwjgl3Application(new SpazioGame(), config);
  }
}
