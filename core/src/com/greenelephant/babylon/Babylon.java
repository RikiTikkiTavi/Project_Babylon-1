package com.greenelephant.babylon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenelephant.babylon.view.GameScreen;

public class Babylon extends Game {

	// Is called only once on game start -> Create and set game start screen
	@Override
	public void create () {
		Screen gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

}
