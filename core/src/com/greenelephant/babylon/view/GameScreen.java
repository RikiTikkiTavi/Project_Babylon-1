package com.greenelephant.babylon.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenelephant.babylon.model.TestTower;
import com.greenelephant.babylon.model.Tower;

import static com.greenelephant.babylon.utils.Constants.FIELD_SIZE;

public class GameScreen implements Screen {

    private SpriteBatch batch;
    private Texture testTowerTexture;
    private Tower testTower;
    private OrthographicCamera camera;

    // Time between render calls
    public static float deltaCff;

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        batch = new SpriteBatch();

        testTowerTexture = new Texture(TestTower.TEXTURE_PATH);
        testTowerTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        testTower = new TestTower(testTowerTexture, -1f, 0, FIELD_SIZE, FIELD_SIZE);

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        deltaCff = delta;

        // Применяем матрицу проекции к отрисовщику
        batch.setProjectionMatrix(camera.combined);

        testTower.handle(camera);

        batch.begin();
        testTower.draw(batch);
        batch.end();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        float aspectRation = (float) height / width;
        camera = new OrthographicCamera(30f, 30f * aspectRation);
        /*camera.zoom = 0.6f;
        camera.update();*/
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        batch.dispose();
        testTowerTexture.dispose();
    }
}
