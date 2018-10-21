package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector2;

class TestEnemy extends Enemy {

    public static final String texturePath = "enemy-default.gif";


    public TestEnemy(float x, float y) {
        super(texturePath, x, y);
        speed = new Vector2(2, 0);
        HP = 20;
    }

}
