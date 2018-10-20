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

import java.lang.reflect.Constructor;
import java.util.ArrayList;

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
    private ArrayList <Tower> towers;

    public Map (String mapName){
        level = 0;
        tiledMap = new TmxMapLoader().load(mapName);
        mapLayers = tiledMap.getLayers();
        towers = new ArrayList<Tower>();
        destroyLevel = new String[]{"House", "HouseD-1", "HouseD-2", "House-Upgrade"};
        dots.add(new Vector3(235,186,0));
        dots.add(new Vector3(327,45,0));
    }

    public void show(){
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
    }

    public void render (OrthographicCamera camera) {
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
        for(Tower tower:towers)
            tower.draw(batch);
        batch.end();

        update(camera);
    }

    private void update(OrthographicCamera camera){
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            touchPos = camera.unproject(touchPos);
            for(Vector3 dot:dots){
                if(touchPos.sub(dot).len() <= 24) {
                    towers.add(new TestTower(dot.x - 24, dot.y - 24));
                    dots.remove(dot);
                    break;
                }
            }
            Gdx.app.log("Info, coordinates |","X:" + touchPos.x + "Y:" + touchPos.y);
            checkIfTower();
        }
        if (level < 3) {
            try { Thread.sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            level++;
        }
    }

    private boolean checkIfTower() {
        return true;
    }

    public void dispose() {
        batch.dispose();
        for (Tower tower : towers) tower.dispose();
    }
}
