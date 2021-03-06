package com.greenelephant.babylon.model;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.greenelephant.babylon.controller.CoolClickerController;
import com.greenelephant.babylon.utils.Constants;
import com.greenelephant.babylon.utils.Pair;
import com.greenelephant.babylon.view.ScreenEnum;
import com.greenelephant.babylon.view.ScreenManager;
import com.greenelephant.babylon.view.screen.GameOverScreen;

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
    private CoolClickerController coolClickerController;
    int HP = 75;

    private long started;

    private long frequency = 6000;
    private long waveFrequency = 4000;
    private long lastEnemy = 0;
    private long lastWave = 0;


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

       // enemies.add(new TestEnemy(mapConfig.getSpawnPoint().x, mapConfig.getSpawnPoint().y));
        lastEnemy = System.currentTimeMillis() - 3000;

        // Clicker Controller
        coolClickerController = new CoolClickerController();

        // HUD
        hudBatch = new SpriteBatch();

        lastWave = System.currentTimeMillis();
        started = lastWave;
    }

    public void handleHudButch(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f,  0.2f);
        shapeRenderer.rect(0, 980f, 1920, 50);
        shapeRenderer.end();
        hudBatch.begin();
        int gold = bank.getGold();
        double rateModifier = coolClickerController.getRateModifier();
        coolClickerController.handleClicks();
        String health = HP + " /75";
        font.draw(hudBatch, "Gold: "+gold, 20, 1005);
        font.draw(hudBatch, "Health: "+health, 1790, 1005);
        font.draw(hudBatch, "Rate: "+rateModifier, 900, 1005);
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
        for (Tower tower : towers) {
            tower.draw(batch);
            if(tower.areUpgradesShown())
                tower.drawUpgrades(batch);
        }
        for (Enemy enemy : enemies)
            enemy.draw(batch);
        batch.end();

        handleHudButch();

        update(camera);
    }

    private void turnEnemies() {
        for (Pair<Vector3, Integer> dot : mapConfig.getTurnPoints()) {
            for (Enemy enemy : enemies) {
                if (!enemy.isDeathAnimationPlaying() && enemy.getVector().dst(dot.getKey()) < 1 / enemy.getSpeed()) {
                    if (dot.getValue() != 0)
                        enemy.turn(dot.getValue());
                    else {
                        enemyAmount++;
                        HP-=5;
                        if(level < 3 && enemyAmount >= 5){
                            enemyAmount = 0;
                            level++;

                        }
                        else if (level >= 3) {
                            ScreenManager.getInstance().initialize((Game) Gdx.app.getApplicationListener());
                            ScreenManager.getInstance().showScreen(ScreenEnum.GAME_OVER_SCREEN.getScreen("test-map.tmx"));
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
            for(Tower tower:towers)
                if(tower.areUpgradesShown())
                    if (tower.isUpgradeHere(touchPos.x,touchPos.y)) {
                        bank.sub(200);
                        tower.disposeUpgrades();
                    }

            for (Pair<Vector3,Boolean> pair : mapConfig.getTowerDots()) {
                Vector3 dot = pair.getKey();
                if (new Vector3(touchPos).sub(dot).len() <= 24 && bank.canBuy(TestTower.price)) {
                    if (!pair.getValue()) {
                        bank.sub(TestTower.price);
                        towers.add(new TestTower(dot.x - 24, dot.y - 24));
                        pair.setValue(true);
                        return;
                    }
                    else{
                        for(Tower tower:towers) {
                            if (tower.isHere(dot.x, dot.y)) {
                                tower.setupUpgrades(dot.x, dot.y);
                                return;
                            }
                        }
                    }
                }

            }
            for(Tower tower:towers) {
                tower.disposeUpgrades();
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
            if(!enemy.isDeathAnimationPlaying())
                enemy.move();
        }
    }

    private void shoot() {
        double mult = coolClickerController.getRateModifier();
        for (Tower tower : towers) {
            for (int i = 0, enemiesSize = enemies.size(); i < enemiesSize; i++) {
                Enemy enemy = enemies.get(i);
                if (!enemy.isDeathAnimationPlaying() &&
                        new Vector3(tower.getVector()).dst(enemy.getVector()) <= tower.getRange())
                    if (tower.shoot(mult)) { //CLICKER
                            enemy.damage(tower.getPower());
                    }
            }

        }
    }

    public void checkDead(){
        for (int i = 0, enemiesSize = enemies.size(); i < enemiesSize; i++) {
            Enemy enemy = enemies.get(i);
            if (enemy.isDead()) {
                bank.add(enemy.getReward());
                enemies.remove(enemy);
                --enemiesSize;

            }
        }
    }

    private void enemiesAnimation(){
        for(Enemy enemy:enemies)
            if(!enemy.isDeathAnimationPlaying() &&
                    enemy.isDamaged() && enemy.checkTimeInterval())
                enemy.returnTexture();
    }

    private void checkWawe(){
        if(System.currentTimeMillis() - lastWave >= waveFrequency){
            if(frequency >= 700 )
                frequency/=1.3;
            TestEnemy.moreHP();
            lastWave = System.currentTimeMillis();

        }
    }

    private void update(OrthographicCamera camera) {

            checkDead();
            turnEnemies();
            placeTowers(camera);
            spawnEnemies();
            moveEnemies();
            shoot();
            enemiesAnimation();
            checkWawe();

    }

    public int getLevel(){return level;}

    public void dispose() {
        batch.dispose();
        for (Tower tower : towers) tower.dispose();
        hudBatch.dispose();
    }
}
