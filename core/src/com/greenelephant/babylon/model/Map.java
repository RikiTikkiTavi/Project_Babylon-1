package com.greenelephant.babylon.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.utils.Constants;

import com.greenelephant.babylon.utils.Pair;


import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Map {

    MapConfig mapConfig = new MapConfig();

    private int level;
    private SpriteBatch batch;
    private TiledMap tiledMap;
    private MapLayers mapLayers;
    private String[] destroyLevel;
    private int[] decorationLayersIndices;
    private TiledMapTileLayer tiledMapTileLayer;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private ArrayList<Tower> towers;
    private ArrayList<Enemy> enemies;

    private long frequency = 2000;
    private long lastEnemy;


    public Map(String mapName) {
        level = 0;

        tiledMap = new TmxMapLoader().load(mapName);
        mapLayers = tiledMap.getLayers();
        towers = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();
        destroyLevel = new String[]{"House", "HouseD-1", "HouseD-2", "House-Upgrade"};




    }

    public void show() {
        tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("tree"),
                mapLayers.getIndex("Water"),
                mapLayers.getIndex("tower-layer"),
                mapLayers.getIndex("road"),
                mapLayers.getIndex("cave"),
                mapLayers.getIndex("gover")
        };
        batch = new SpriteBatch();
        try {
            Class towerClass = Class.forName(Tower.Types.TestTower.getName());
            Constructor towerConstructor = towerClass.getConstructor(float.class, float.class);
            towers.add((Tower) towerConstructor.newInstance(Constants.RESOLUTION.value >> 1, Constants.RESOLUTION.value >> 2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        enemies.add(new TestEnemy(mapConfig.getSpawnPoint().x, mapConfig.getSpawnPoint().y));
        lastEnemy = System.currentTimeMillis();
    }

    public void render(OrthographicCamera camera) {
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

        batch.begin();
        for (Tower tower : towers)
            tower.draw(batch);
        for (Enemy enemy : enemies)
            enemy.draw(batch);
        batch.end();

        update(camera);
    }

    private void update(OrthographicCamera camera) {
        for (Pair<Vector3, Integer> dot : mapConfig.getTurnPoints()) {
            for (Enemy enemy : enemies) {
                if (enemy.getVector().dst(dot.getKey()) < 1/enemy.getSpeed()) {
                    if(dot.getValue() != 0)
                        enemy.turn(dot.getValue());
                    else {
                        enemies.remove(enemy);
                        break;
                    }
                }
            }
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos = camera.unproject(touchPos);
            Gdx.app.log("Info, coordinates ", "X:" + touchPos.x + "Y:" + touchPos.y);
            for (Vector3 dot : mapConfig.getTowerDots()) {
                if (new Vector3(touchPos).sub(dot).len() <= 24) {
                    towers.add(new TestTower(dot.x - 24, dot.y - 24));
                    mapConfig.getTowerDots().remove(dot);
                    break;
                }
            }
        }
        /*if (level < 3) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            level++;
        }*/

        for (Enemy enemy : enemies) {
            enemy.move();
        }
        if (System.currentTimeMillis() - lastEnemy >= frequency) {
            enemies.add(new TestEnemy(mapConfig.getSpawnPoint().x, mapConfig.getSpawnPoint().y));
            lastEnemy = System.currentTimeMillis();
        }


    }

    public void dispose() {
        batch.dispose();
        for (Tower tower : towers) tower.dispose();
    }
}
