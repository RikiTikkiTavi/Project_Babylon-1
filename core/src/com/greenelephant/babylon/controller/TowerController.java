package com.greenelephant.babylon.controller;

public class TowerController {

    private long shootingFrequency;
    private long lastShoot;


    public TowerController(long shootingFrequency) {
        this.shootingFrequency = shootingFrequency;
        lastShoot = System.currentTimeMillis();
    }

    public void setShootingFrequency(long shootingFrequency) {
        this.shootingFrequency = shootingFrequency;
    }


    public boolean shoot(long multiplier) {
        if (System.currentTimeMillis() - lastShoot < shootingFrequency / multiplier)
            return false;
        else {
            lastShoot = System.currentTimeMillis();
            return true;
        }
    }
}
