package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.greenelephant.babylon.utils.Constants;

abstract class Enemy extends GameObject {

    protected static int HP;
    protected Vector2 speed;
    Texture damagedTexture;
    Texture deadTexture;
    boolean damaged;
    long lastDamage;
    int animationInterval = 100;
    int deathAnimationInterval = 200;
    long deathTime = 0;
    protected int reward = 20;
    boolean dead = false;

    public Enemy(String texturePath,String damagedTexturePath,String deadTexturePath, float x, float y) {
        super(texturePath, x, y, Constants.ENEMY_WIDTH.value, Constants.ENEMY_HEIGHT.value);
        damagedTexture = new Texture(damagedTexturePath);
        damagedTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        deadTexture = new Texture(deadTexturePath);
        deadTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        damaged = false;
        lastDamage = 0;
    }

    public boolean isDead() {
        return dead && (System.currentTimeMillis() - deathTime >= deathAnimationInterval);
    }

    public void move() {
        bounds.translate(speed.x, speed.y);
    }


    public void turn(int i) {
        if(getSpeed()!=0)
            speed.rotate90(i);
            sprite.rotate90(i < 0);
    }

    public float getSpeed() {
        return speed.len2();
    }

    public void damage(int d) {
        if (d >= 0)
            HP -= d;
        if(HP <= 0){
            dead = true;
            speed.x = 0;
            speed.y = 0;
            sprite.setTexture(deadTexture);
            deathTime = System.currentTimeMillis();
            return;
        }
        sprite.setTexture(damagedTexture);
        damaged = true;
        lastDamage = System.currentTimeMillis();
    }

    public boolean isDamaged(){
        return damaged;
    }

    public boolean isDeathAnimationPlaying(){
        return dead;
    }

    public boolean checkTimeInterval(){
        return System.currentTimeMillis() - lastDamage >= animationInterval;
    }

    public void returnTexture(){
        sprite.setTexture(texture);
        damaged = false;
    }

    public int getReward() {
        return reward;
    }
}
