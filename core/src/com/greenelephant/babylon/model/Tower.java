package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.greenelephant.babylon.controller.TowerController;

//Abstract class for all of the towers.
// There is all of the tower logic here

abstract public class Tower extends GameObject {

    private final TowerController towerController;
    public static final String TEXTURE_PATH = "green-elephant.png"; //if there is no path in the daughter class

    public Tower(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        this.towerController = new TowerController(bounds, width, height);
    }


    public void handle(OrthographicCamera camera) {
        towerController.handle(camera);

    }

}
