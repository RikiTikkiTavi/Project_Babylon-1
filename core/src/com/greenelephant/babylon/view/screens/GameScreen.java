package com.greenelephant.babylon.view.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.greenelephant.babylon.model.Map;
import com.greenelephant.babylon.utils.Constants;


public class GameScreen implements Screen {

    private Map map;
    private FitViewport viewport;
    // Time between render calls
    // public static float deltaCff;
    private OrthographicCamera camera;

    public GameScreen(String mapName){
        map = new Map(mapName);
        viewport = new FitViewport(Constants.RESOLUTION.value >> 1, Constants.RESOLUTION.value >> 1, camera);
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        map.show();
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.5f, .7f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        map.render(camera);
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

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        map.dispose();
    }
}
