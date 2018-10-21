package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenelephant.babylon.controller.TowerController;
import com.greenelephant.babylon.utils.Constants;

//Abstract class for all of the towers.
// There is all of the tower logic here

abstract public class Tower extends GameObject {

    TowerController towerController;
    int range;
    private int health;
    long shootingFrequency;
    int power;
    public static final int price =  500;
    Upgrade[] upgradeArray;
    protected UpgradeScreen upgrades;
    private boolean areUpgradesShown = false;
    float x,y;


    Tower(String texturePath, float x, float y) {
        super(texturePath, x, y, Constants.TOWER_WIDTH.value, Constants.TOWER_HEIGHT.value);
        this.towerController = new TowerController(shootingFrequency);
        upgradeArray = new Upgrade[3];
        this.x = x;
        this.y = y;
    }


    //public void handle(OrthographicCamera camera) {
    //    towerController.handle(camera);
    //}
    public boolean shoot(double multiplier) {
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

    public int getRange() {
        return range;
    }

    public int getPower() {
        return power;
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

    public UpgradeScreen getUpgrades() {

        return upgrades;
    }

    public void setupUpgrades(float x,float y){
        upgrades.setup(x,y);
        areUpgradesShown = true;
    }
    public boolean areUpgradesShown(){
        return  areUpgradesShown;
    }

    public void disposeUpgrades(){
        if(areUpgradesShown) {
            upgrades.dispose();
            areUpgradesShown = false;
        }
    }

    public void drawUpgrades(SpriteBatch batch){
        if(areUpgradesShown)
            upgrades.draw(batch);

    }

    public boolean isHere(float x, float y){
        return x == this.x + 24 && y == this.y + 24;
    }

    public boolean isUpgradeHere(float x, float y){
        Upgrade tempUpgrade = null;
        tempUpgrade = upgrades.isUpgradeHere(x,y);
        if(tempUpgrade != null) {
            upgrade(tempUpgrade);
            return true;
        }
        return false;
    }
}
