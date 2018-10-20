package com.greenelephant.babylon.view.screens;

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
import com.greenelephant.babylon.model.Map;
import com.greenelephant.babylon.utils.Constants;
import com.greenelephant.babylon.controller.MapController;

public class LevelMenuScreen implements Screen {

    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin skin;
    private TextButton testLevelButton, exitButton;

    public LevelMenuScreen() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.RESOLUTION.value >> 1, Constants.RESOLUTION.value >> 1, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    private void initializeMenuButtons() {
        //Create buttons
        testLevelButton = new TextButton("Level\nTest", skin, "default");
        exitButton = new TextButton("Exit to Menu", skin, "default");
    }

    private void initializeMenuButtonsListeners() {
        //Add listeners to buttons
        testLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen("test-map.tmx"));
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
        mainTable.add(testLevelButton).size(60f, 60f);
        mainTable.row();
        mainTable.row();
        mainTable.add(exitButton).size(150f, 60f);
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
    }
}