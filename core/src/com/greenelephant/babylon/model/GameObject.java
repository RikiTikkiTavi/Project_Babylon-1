package com.greenelephant.babylon.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

abstract class GameObject {
    Polygon bounds;
    Sprite sprite;

    GameObject(String texturePath, float x, float y, float width, float height) {
        Texture newTexture = new Texture(texturePath);
        newTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(newTexture);
        sprite.setSize(width, height);
        sprite.setOrigin(x / 2f, y / 2f);
        sprite.setPosition(x, y);

        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x, y);
        bounds.setOrigin(x / 2f, y / 2f);

    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(bounds.getX(), bounds.getY());
        sprite.setRotation(bounds.getRotation());
        sprite.draw(batch);
    }

    public Polygon getBounds() {
        return bounds;
    }
}
