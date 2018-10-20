package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.controller.TowerController;
import com.greenelephant.babylon.utils.Constants;

abstract public class Enemy extends GameObject {

    protected int HP;
    protected Vector2 speed;

    public Enemy(String texturePath, float x, float y) {
        super(texturePath, x, y, Constants.ENEMY_WIDTH.value, Constants.ENEMY_HEIGHT.value);
    }

    public boolean isDead(){
        if(HP <= 0)
            return true;
        return false;
    }

    public void move(){
        bounds.translate(speed.x,speed.y);
    }

    public void turnRight(){
        speed.rotate90(-1);
    }

    public void turnLeft(){
        speed.rotate90(1);
    }
    public void turn(int i){
        speed.rotate90(i);
    }
    public float getSpeed(){
        return speed.len2();
    }
}
