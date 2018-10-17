package com.greenelephant.babylon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.greenelephant.babylon.view.GameScreen;

public class Babylon extends Game {

    // Is called only once on game start -> Create and set game start screen
    @Override
    public void create() {
        Screen gameScreen = new GameScreen();
        setScreen(gameScreen);
    }

}
