package com.greenelephant.babylon.view.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenelephant.babylon.utils.Constants;
import com.greenelephant.babylon.view.ScreenEnum;
import com.greenelephant.babylon.view.ScreenManager;

public class MainMenuScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin skin;
    private TextButton playButton, exitButton;

    public MainMenuScreen() {
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.RESOLUTION.value >> 1, Constants.RESOLUTION.value >> 1, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth /2 , camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    private void initializeMenuButtons() {
        //Create buttons
        playButton = new TextButton("Play", skin, "default");
        exitButton = new TextButton("Exit", skin, "default");
    }

    private void initializeMenuButtonsListeners() {
        //Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.getInstance().initialize((Game) Gdx.app.getApplicationListener());
                ScreenManager.getInstance().showScreen(ScreenEnum.GAME.getScreen("test-map.tmx"));

            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();

        initializeMenuButtons();
        initializeMenuButtonsListeners();

        //Add buttons to table
        mainTable.add(playButton).size(150, 60);
        mainTable.row();
        mainTable.row();
        mainTable.add(exitButton).size(150, 60);
        // mainTable.add(exitButton);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    /**
     * @param width  TODO:Description
     * @param height TODO:Description
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        float aspectRation = (float) height / width;
        camera = new OrthographicCamera(Constants.RESOLUTION.value, Constants.RESOLUTION.value * aspectRation);
        camera.translate(Constants.RESOLUTION.value >> 1, (Constants.RESOLUTION.value >> 1) * aspectRation);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        skin.dispose();
        batch.dispose();
        stage.dispose();
    }
}