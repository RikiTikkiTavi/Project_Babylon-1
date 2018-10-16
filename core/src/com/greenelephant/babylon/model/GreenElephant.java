package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenelephant.babylon.controller.GreenElephantController;

public class GreenElephant extends GameObject {

    private GreenElephantController greenElephantController;

    public GreenElephant(Texture texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        greenElephantController = new GreenElephantController(bounds);
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
        greenElephantController.handle();
    }
}
