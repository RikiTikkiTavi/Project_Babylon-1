package com.greenelephant.babylon.model;

public abstract class Upgrade {
    private String texturePath = "green-elephant.png";
    private int range;
    private int health;
    private long shootingFrequency;
    private int price;

    Upgrade(String texturePath, int range, int health, int shootingFrequency, int price) {
        this.texturePath = texturePath;
        this.health = health;
        this.shootingFrequency = shootingFrequency;
        this.price = price;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public int getRange() {
        return range;
    }

    public int getHealth() {
        return health;
    }

    public int getPrice() {
        return price;
    }

    public long getShootingFrequency() {
        return shootingFrequency;
    }

    boolean isEmpty() {
        return false;
    }
}
