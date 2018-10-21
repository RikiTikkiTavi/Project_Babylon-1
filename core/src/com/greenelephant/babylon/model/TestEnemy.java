package com.greenelephant.babylon.model;

import com.badlogic.gdx.math.Vector2;

class TestEnemy extends Enemy {

    public static final String texturePath = "FZ.png";
    public static final String damagedTexturePath = "FZ damged lv 2_1.png";
    public static final String deadTexturePath = "BOM 2.png";


    public TestEnemy(float x, float y) {
        super(texturePath,damagedTexturePath,deadTexturePath, x, y);
        speed = new Vector2(2, 0);
        HP = 30;
        reward = 50;
    }

    public static void moreHP(){
        HP*=1.5;
    }

}
