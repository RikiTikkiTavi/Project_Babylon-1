package com.greenelephant.babylon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greenelephant.babylon.Babylon;
import com.greenelephant.babylon.utils.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = Constants.APP_HEIGHT;
        config.width = Constants.APP_WIDTH;
        new LwjglApplication(new Babylon(), config);
    }
}
