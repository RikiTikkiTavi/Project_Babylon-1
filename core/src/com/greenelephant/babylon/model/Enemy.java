package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;

public abstract class Enemy extends  GameObject{
    Enemy(Texture texture, float x, float y, float width, float height){
        super(texture,x,y,width,height);
    }
}
