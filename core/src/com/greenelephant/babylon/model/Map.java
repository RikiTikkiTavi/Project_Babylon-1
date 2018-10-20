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
import java.util.Random;

public class Map {

    private ArrayList<Vector3> dots = new ArrayList<Vector3>();

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
    private Vector3 spawnPoint = new Vector3(0, 200, 0);
    private long frequency = 2000;
    private long lastEnemy;
    final Random random = new Random();
    private ArrayList<Pair<Vector3, Integer>> turnPoints;

    public Map(String mapName) {
        level = 0;

        tiledMap = new TmxMapLoader().load(mapName);
        mapLayers = tiledMap.getLayers();
        towers = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();
        destroyLevel = new String[]{"House", "HouseD-1", "HouseD-2", "House-Upgrade"};

        dots.add(new Vector3(235, 186, 0));
        dots.add(new Vector3(327, 45, 0));
        turnPoints = new ArrayList<Pair<Vector3, Integer>>();
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(100, 200, 0), -1));
        turnPoints.add(new Pair<Vector3, Integer>(new Vector3(100, 100, 0), 1));
    }

    public void show() {
        tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("tree"),
                mapLayers.getIndex("Water"),
                mapLayers.getIndex("tower-layer"),
                mapLayers.getIndex("road"),
                mapLayers.getIndex("cave")
        };
        batch = new SpriteBatch();
        try {
            Class towerClass = Class.forName(Tower.Types.TestTower.getName());
            Constructor towerConstructor = towerClass.getConstructor(float.class, float.class);
            towers.add((Tower) towerConstructor.newInstance(Constants.RESOLUTION.value >> 1, Constants.RESOLUTION.value >> 2));
        } catch (Exception e) {
            e.printStackTrace();
        }

        enemies.add(new TestEnemy(spawnPoint.x, spawnPoint.y));
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
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos = camera.unproject(touchPos);
            Gdx.app.log("Info, coordinates ", "X:" + touchPos.x + "Y:" + touchPos.y);
            for (Vector3 dot : dots) {
                if (touchPos.sub(dot).len() <= 24) {
                    towers.add(new TestTower(dot.x - 24, dot.y - 24));
                    dots.remove(dot);
                    break;
                }
            }
        }
        if (level < 3) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            level++;
        }
        for (Pair<Vector3, Integer> dot : turnPoints) {
            for (Enemy enemy : enemies) {
                if (enemy.getVector().dst(dot.getKey()) < enemy.getSpeed() * 2) {
                    enemy.turn(dot.getValue());
                }
            }
        }
        for (Enemy enemy : enemies) {
            enemy.move();
        }
        if (System.currentTimeMillis() - lastEnemy >= frequency) {
            enemies.add(new TestEnemy(spawnPoint.x, spawnPoint.y));
            lastEnemy = System.currentTimeMillis();
        }


    }

    public void dispose() {
        batch.dispose();
        for (Tower tower : towers) tower.dispose();
    }
}
