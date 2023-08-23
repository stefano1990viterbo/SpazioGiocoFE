package it.ricci.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import it.ricci.game.Drop;
//import it.ricci.game.Drop;

public class HtmlLauncher extends GwtApplication {


        /**
         * lanciare da shell sotto la cartella del progetto questo comando
         * ./gradlew html:superDev
         *
         */

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
//                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                //return new GwtApplicationConfiguration(480, 320);



                return new GwtApplicationConfiguration(Drop.LARGHEZZA_SCHERMO_GIOCO, Drop.ALTEZZA_SCHERMO_GIOCO);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new Drop();
        }
}