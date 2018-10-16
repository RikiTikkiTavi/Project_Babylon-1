package com.greenelephant.babylon.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.greenelephant.babylon.view.GameScreen;

public class GreenElephantController {

    private Polygon greenElephantBounds;

    public GreenElephantController(Polygon greenElephantBounds) {
        this.greenElephantBounds = greenElephantBounds;
    }

    private float moveX = 0f;
    public void handle() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            moveX += 0.2f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            moveX -= 0.2f;
        greenElephantBounds.setPosition(
                greenElephantBounds.getX() + moveX * GameScreen.deltaCff,
                greenElephantBounds.getY()
        );
    }
}
