package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector2;

class TestEnemy extends Enemy {

    public static final String texturePath = "enemy-default.gif";
    public static final String damagedTexturePath = "green-elephant.png";

    public TestEnemy(float x, float y) {
        super(texturePath,damagedTexturePath, x, y);
        speed = new Vector2(2, 0);
        HP = 30;
    }

}
