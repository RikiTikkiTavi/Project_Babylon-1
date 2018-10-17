package com.greenelephant.babylon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

public class TowerController {

    private Polygon towerBounds;
    private float width;
    private float height;

    public TowerController(Polygon towerBounds, float width, float height) {
        this.towerBounds = towerBounds;
        this.width = width;
        this.height = height;
    }

    public void handle(OrthographicCamera camera) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            towerBounds.setPosition(touchPos.x - width / 2,
                    touchPos.y - height / 2);
        }
    }
}
