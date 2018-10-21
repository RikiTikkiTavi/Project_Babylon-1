package com.greenelephant.babylon.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.controller.Bank;
import com.greenelephant.babylon.utils.Pair;

import java.util.ArrayList;

public class Map {

    private MapConfig mapConfig = new MapConfig();

    private int level;
    private SpriteBatch batch, hudBatch;
    private TiledMap tiledMap;
    private MapLayers mapLayers;
    private String[] destroyLevel;
    private int[] decorationLayersIndices;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private ArrayList<Tower> towers;
    private ArrayList<Enemy> enemies;

    private long frequency = 300;
    private long lastEnemy = 0;

    int enemyAmount = 0;

    Bank bank = new Bank();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    BitmapFont font = new BitmapFont();

    public Map(String mapName) {
        level = 0;

        tiledMap = new TmxMapLoader().load(mapName);
        mapLayers = tiledMap.getLayers();
        towers = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();
        destroyLevel = new String[]{"gover", "gover-D1","gover-D1", "gover-D2"};


    }

    public void show() {
        tiledMapRenderer = new OrthogonalTiledMapRenderer(this.tiledMap);
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("Water-frame1"),
                mapLayers.getIndex("road"),
                mapLayers.getIndex("tower-layer"),
                mapLayers.getIndex("gover"),
                mapLayers.getIndex("House"),
                mapLayers.getIndex("decor"),
        };
        batch = new SpriteBatch();
        /*try {
            Class towerClass = Class.forName(Tower.Types.TestTower.getName());
            Constructor towerConstructor = towerClass.getConstructor(float.class, float.class);
            towers.add((Tower) towerConstructor.newInstance(Constants.RESOLUTION.value >> 1, Constants.RESOLUTION.value >> 2));
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        enemies.add(new TestEnemy(mapConfig.getSpawnPoint().x, mapConfig.getSpawnPoint().y));
        lastEnemy = System.currentTimeMillis();

        // HUD
        hudBatch = new SpriteBatch();
    }

    public void handleHudButch(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f,  0.2f);
        shapeRenderer.rect(0, 980f, 1920, 50);
        shapeRenderer.end();
        hudBatch.begin();
        int gold = bank.getGold();
        font.draw(hudBatch, "Gold: "+gold, 1800, 1005);
        hudBatch.end();
    }

    public void render(OrthographicCamera camera) {
        // Применяем матрицу проекции к отрисовщику
        batch.setProjectionMatrix(camera.combined);
        // Update the camera
        camera.update();
        tiledMapRenderer.setView(camera);

        // Rendering
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) mapLayers.get(destroyLevel[level]);
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

        handleHudButch();

        update(camera);
    }

    private void turnEnemies() {
        for (Pair<Vector3, Integer> dot : mapConfig.getTurnPoints()) {
            for (Enemy enemy : enemies) {
                if (enemy.getVector().dst(dot.getKey()) < 1 / enemy.getSpeed()) {
                    if (dot.getValue() != 0)
                        enemy.turn(dot.getValue());
                    else {
                        enemyAmount++;
                        if(level < 3 && enemyAmount >= 5){
                            enemyAmount = 0;
                            level++;
                        }

                        enemies.remove(enemy);
                        break;
                    }
                }
            }
        }
    }



    private void placeTowers(OrthographicCamera camera) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos = camera.unproject(touchPos);
            //Gdx.app.log("Info, coordinates ", "X:" + touchPos.x + "Y:" + touchPos.y);
            for (Vector3 dot : mapConfig.getTowerDots()) {
                if (new Vector3(touchPos).sub(dot).len() <= 24 && bank.canBuy(TestTower.price)) {
                    bank.sub(TestTower.price);
                    towers.add(new TestTower(dot.x - 24, dot.y - 24));
                    mapConfig.getTowerDots().remove(dot);
                    break;
                }
            }
        }
    }

    private void spawnEnemies() {
        if (System.currentTimeMillis() - lastEnemy >= frequency) {
            enemies.add(new TestEnemy(mapConfig.getSpawnPoint().x, mapConfig.getSpawnPoint().y));
            lastEnemy = System.currentTimeMillis();
        }
    }

    private void moveEnemies() {
        for (Enemy enemy : enemies) {
            enemy.move();
        }
    }

    private void shoot() {
        for (Tower tower : towers) {
            for (int i = 0, enemiesSize = enemies.size(); i < enemiesSize; i++) {
                Enemy enemy = enemies.get(i);
                if (new Vector3(tower.getVector()).dst(enemy.getVector()) <= tower.getRange())
                    if (tower.shoot(1)) { //CLICKER
                        enemy.damage(tower.getPower());
                        if (enemy.isDead()) {
                            bank.add(enemy.getReward());
                            enemies.remove(enemy);
                            --enemiesSize;

                        }
                    }
            }

        }
    }

    private void enemiesAnimation(){
        for(Enemy enemy:enemies)
            if(enemy.isDamaged() && enemy.checkTimeInterval())
                enemy.returnTexture();
    }

    private void update(OrthographicCamera camera) {
        turnEnemies();
        placeTowers(camera);
        spawnEnemies();
        moveEnemies();
        shoot();
        enemiesAnimation();
    }

    public int getLevel(){return level;}

    public void dispose() {
        batch.dispose();
        for (Tower tower : towers) tower.dispose();
        hudBatch.dispose();
    }
}
