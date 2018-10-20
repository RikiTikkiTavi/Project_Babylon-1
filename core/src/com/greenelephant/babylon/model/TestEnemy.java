package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class TestEnemy extends Enemy {

    public static final String texturePath = "green-elephant.png";


    public TestEnemy(float x, float y) {
        super(texturePath, x, y);
        speed = new Vector2(1,0);
    }

}
