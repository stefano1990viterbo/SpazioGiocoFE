package it.ricci.game;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

public class DesktopLauncher {
  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("Drop");
    config.setWindowedMode(800, 480);
    config.useVsync(true);
    config.setForegroundFPS(60);

//    GL.createCapabilities();
//    glfwMakeContextCurrent(window);
    new Lwjgl3Application(new Drop(), config);
  }
}
