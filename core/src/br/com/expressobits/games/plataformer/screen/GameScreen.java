package br.com.expressobits.games.plataformer.screen;

import br.com.expressobits.games.plataformer.Plataformer;
import br.com.expressobits.games.plataformer.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ScreenAdapter {

    SpriteBatch batch;

    protected OrthographicCamera camera;

    protected Viewport viewport;

    protected World world;

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
        viewport = new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);

        world = new World(camera);
        world.regenerate();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);

        if(Plataformer.DEBUG){
            if(Gdx.input.isKeyPressed(Input.Keys.B)){
                if(world.getEntityTrackerMainWindow()!=null){
                    world.getEntityTrackerMainWindow().setVisible(!world.getEntityTrackerMainWindow().isVisible());
                }
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width,height);
    }

    @Override
    public void dispose () {
        batch.dispose();
        world.dispose();
    }

}
