package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.greenelephant.babylon.utils.Constants;

abstract class Enemy extends GameObject {

    protected int HP;
    protected Vector2 speed;
    Texture damagedTexture;
    boolean damaged;
    long lastDamage;
    int animationInterval = 500;

    public Enemy(String texturePath,String damagedTexturePath, float x, float y) {
        super(texturePath, x, y, Constants.ENEMY_WIDTH.value, Constants.ENEMY_HEIGHT.value);
        damagedTexture = new Texture(damagedTexturePath);
        damagedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        damaged = false;
        lastDamage = 0;
    }

    public boolean isDead() {
        return HP <= 0;
    }

    public void move() {
        bounds.translate(speed.x, speed.y);
    }


    public void turn(int i) {
        speed.rotate90(i);
        sprite.rotate90(i < 0);
    }

    public float getSpeed() {
        return speed.len2();
    }

    public void damage(int d) {
        if (d >= 0)
            HP -= d;
        sprite.setTexture(damagedTexture);
        damaged = true;
        lastDamage = System.currentTimeMillis();
    }

    public boolean isDamaged(){
        return damaged;
    }

    public boolean checkTimeInterval(){
        return System.currentTimeMillis() - lastDamage >= animationInterval;
    }

    public void returnTexture(){
        sprite.setTexture(texture);
        damaged = false;
    }
}
