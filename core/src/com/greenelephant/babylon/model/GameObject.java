package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

abstract class GameObject {
    Polygon bounds;
    Sprite object;

    GameObject(Texture texture, float x, float y, float width, float height) {

        object = new Sprite(texture);
        object.setSize(width, height);
        object.setOrigin(x / 2f, y / 2f);
        object.setPosition(x, y);

        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x, y);
        bounds.setOrigin(x / 2f, y / 2f);

    }

    public void draw(SpriteBatch batch) {
        object.setPosition(bounds.getX(), bounds.getY());
        object.setRotation(bounds.getRotation());
        object.draw(batch);
    }

    public Polygon getBounds() {
        return bounds;
    }
}
