package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.greenelephant.babylon.controller.TowerController;
import com.greenelephant.babylon.utils.Constants;

//Abstract class for all of the towers.
// There is all of the tower logic here

abstract public class Tower extends GameObject {

    private TowerController towerController;
    private int range;
    private int health;
    private long shootingFrequency;
    private float price;
    protected Upgrade[] upgradeArray;


    public Tower(String texturePath, float x, float y) {
        super(texturePath, x, y, Constants.TOWER_WIDTH.value, Constants.TOWER_HEIGHT.value);
        this.towerController = new TowerController(shootingFrequency);
        upgradeArray = new Upgrade[3];
    }


    //public void handle(OrthographicCamera camera) {
    //    towerController.handle(camera);
    //}
    public boolean shoot(long multiplier) {
        return towerController.shoot(multiplier);
    }

    public void upgrade(Upgrade upgrade) {
        if (!upgrade.isEmpty()) {
            this.range = upgrade.getRange();
            this.health = upgrade.getHealth();
            this.shootingFrequency = upgrade.getShootingFrequency();
            towerController.setShootingFrequency(this.shootingFrequency);
            Texture newTexture = new Texture(upgrade.getTexturePath());
            texture.dispose();
            newTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            texture = newTexture;
            sprite.setTexture(newTexture);
        }
    }

    public Upgrade[] getUpgradeArray() {
        return upgradeArray;
    }

    public enum Types {
        TestTower(com.greenelephant.babylon.model.TestTower.class.getName());
        private String name;

        Types(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
