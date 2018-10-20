package com.greenelephant.babylon.view.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.greenelephant.babylon.model.TestTower;
import com.greenelephant.babylon.model.Tower;
import com.greenelephant.babylon.utils.Constants;

public class LoadScreen implements Screen {

    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Texture testTowerTexture;
    private Tower testTower;
    private OrthographicCamera camera;

    // Time between render calls
    public static float deltaCff;

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

        TiledMap tiledMap = new TmxMapLoader().load("test-map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        batch = new SpriteBatch();
}

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();


//        deltaCff = delta;
//
//        // Применяем матрицу проекции к отрисовщику
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        testTower.draw(batch);
        batch.end();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        float aspectRation = (float) height / width;
        camera = new OrthographicCamera(Constants.RESOLUTION.value, Constants.RESOLUTION.value * aspectRation);
        camera.translate(Constants.RESOLUTION.value >> 1, (Constants.RESOLUTION.value >> 1) * aspectRation );
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
        batch.dispose();
        testTowerTexture.dispose();
    }
}
