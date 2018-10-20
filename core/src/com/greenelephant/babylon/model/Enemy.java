package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector3;
import com.greenelephant.babylon.controller.TowerController;
import com.greenelephant.babylon.utils.Constants;

abstract public class Enemy extends GameObject {

    private int HP;
    private Vector3 Speed;

    public Enemy(String texturePath, float x, float y) {
        super(texturePath, x, y, Constants.TOWER_WIDTH.value, Constants.TOWER_HEIGHT.value);
    }

    public boolean isDead(){
        if(HP <= 0)
            return true;
        return false;
    }

    public void move(){
        bounds.translate(Speed.x,Speed.y);
    }

    public void turnRight(){
        Speed = Speed.rotate(Speed,-90);
    }

    public void turnLeft(){
        Speed = Speed.rotate(Speed,90);
    }
}
