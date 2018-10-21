package com.greenelephant.babylon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.greenelephant.babylon.view.ScreenEnum;
import com.greenelephant.babylon.view.ScreenManager;
import com.greenelephant.babylon.view.screen.GameScreen;
import com.greenelephant.babylon.view.screen.MainMenuScreen;

public class Babylon extends Game {

    // Is called only once on game start -> Create and set game start screen
    @Override
    public void create() {
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU.getScreen(""));
    }

}
