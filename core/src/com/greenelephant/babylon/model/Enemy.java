package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector2;
import com.greenelephant.babylon.utils.Constants;

abstract class Enemy extends GameObject {

    protected int HP;
    protected Vector2 speed;

    public Enemy(String texturePath, float x, float y) {
        super(texturePath, x, y, Constants.ENEMY_WIDTH.value, Constants.ENEMY_HEIGHT.value);
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
    }

}
