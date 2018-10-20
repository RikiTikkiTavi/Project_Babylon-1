package com.greenelephant.babylon.controller;

import com.badlogic.gdx.math.Polygon;


public class TowerController {

    private Polygon towerBounds;
    private float width;
    private float height;
    private long shootingFrequency;
    private long lastShoot;


    public TowerController(long shootingFrequency) {
        this.shootingFrequency = shootingFrequency;
        lastShoot = System.currentTimeMillis();
    }

    public void setShootingFrequency(long shootingFrequency) {
        this.shootingFrequency = shootingFrequency;
    }

    /*public void handle(OrthographicCamera camera) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            towerBounds.setPosition(touchPos.x - width / 2,
                    touchPos.y - height /2 );
        }
    }*/


    public boolean shoot(long multiplier) {
        if (System.currentTimeMillis() - lastShoot < shootingFrequency / multiplier)
            return false;
        else {
            lastShoot = System.currentTimeMillis();
            return true;
        }
    }
}
