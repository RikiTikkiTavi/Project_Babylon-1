package com.greenelephant.babylon.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.greenelephant.babylon.model.Tower;
import com.greenelephant.babylon.utils.Constants;

import java.lang.reflect.Constructor;


public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture testTowerTexture;
    private Tower testTower;

    private OrthographicCamera camera;
    private int[] decorationLayersIndices;

    private String[] destroyLevel = {"House", "HouseD-1", "HouseD-2", "House-Upgrade"};

    private TiledMapTileLayer tiledMapTileLayer;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    TiledMap tiledMap = new TmxMapLoader().load("test-map.tmx");
    MapLayers mapLayers = tiledMap.getLayers();

    int level = 0;

    // Time between render calls
    public static float deltaCff;

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);


        decorationLayersIndices = new int[]{
                mapLayers.getIndex("tree"),
                mapLayers.getIndex("Water"),
                mapLayers.getIndex("Bridge")
        };
        batch = new SpriteBatch();
        try {
            Class towerClass = Class.forName(Tower.Types.TestTower.getName());
            Constructor towerConstructor = towerClass.getConstructor(float.class, float.class);
            testTower = (Tower) towerConstructor.newInstance(Constants.RESOLUTION.value / 2, Constants.RESOLUTION.value / 4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //testTower = new Class.forName(Tower.Types.TestTower.getName())(Constants.RESOLUTION.value / 2, Constants.RESOLUTION.value / 4);

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

        // Применяем матрицу проекции к отрисовщику
        batch.setProjectionMatrix(camera.combined);
        // Update the camera
        camera.update();
        tiledMapRenderer.setView(camera);

        // Rendering
        tiledMapTileLayer = (TiledMapTileLayer) mapLayers.get(destroyLevel[level]);
        tiledMapRenderer.render(decorationLayersIndices);
        tiledMapRenderer.getBatch().begin();
        tiledMapRenderer.renderTileLayer(tiledMapTileLayer);
        tiledMapRenderer.getBatch().end();
        if (level < 3) {
            try { Thread.sleep(6000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            level++;
        }
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
        batch.dispose();
        testTowerTexture.dispose();
    }
}
