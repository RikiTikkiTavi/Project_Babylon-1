package com.greenelephant.babylon.view;

import com.badlogic.gdx.Screen;
import com.greenelephant.babylon.view.screen.GameOverScreen;
import com.greenelephant.babylon.view.screen.GameScreen;
import com.greenelephant.babylon.view.screen.MainMenuScreen;


public enum ScreenEnum {

    MAIN_MENU {
        @Override
        public Screen getScreen(String levelName){
            return new MainMenuScreen();
        }
    },
    /*LEVEL_SELECT {
        public AbstractScreen getScreen(Object... params) {
            return new LevelMenuScreen();
        }
    },*/
    GAME {
        @Override
        public Screen getScreen(String levelName) {
            return new GameScreen(levelName);
        }
    },

    GAME_OVER_SCREEN {
        @Override
        public Screen getScreen(String levelName){
            return new GameOverScreen();
        }
    };

    public abstract Screen getScreen(String levelName);
}
