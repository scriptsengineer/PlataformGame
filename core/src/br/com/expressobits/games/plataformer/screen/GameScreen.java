package br.com.expressobits.games.plataformer.screen;

import br.com.expressobits.games.plataformer.Plataformer;
import br.com.expressobits.games.plataformer.entity.component.RigidBodyComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import br.com.expressobits.games.plataformer.world.World;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ScreenAdapter {

    SpriteBatch batch;

    protected OrthographicCamera camera;

    protected World world;

    protected final Vector3 screenCoordinate = new Vector3();

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(Plataformer.SCREEN_WIDTH, Plataformer.SCREEN_HEIGHT);
        camera.setToOrtho(false);

        world = new World(camera);
        world.regenerate();

        /*
        if(Plataformer.DEBUG) {
            Gdx.input.setInputProcessor(new InputAdapter(){
                @Override
                public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                    screenCoordinate.set(screenX, screenY, 0);
                    //Este método modifica os valores de Vector3 de valores de mouse clicado para valores do mundo
                    camera.unproject(screenCoordinate);

                    int player = world.getPlayer();
                    world.getWorld().getEntity(player).getComponent(TransformComponent.class).position.set(screenCoordinate.x, screenCoordinate.y);
                    world.getWorld().getEntity(player).getComponent(RigidBodyComponent.class).velocity.set(0,0);
                    return true;
                }
            });
        }
        */
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);

        if(Plataformer.DEBUG){
            if(Gdx.app.getType().equals(Application.ApplicationType.Desktop)){
                if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
                    if(world.getEntityTrackerMainWindow()!=null){
                        world.getEntityTrackerMainWindow().setVisible(!world.getEntityTrackerMainWindow().isVisible());
                    }
                }
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose () {
        batch.dispose();
        world.dispose();
    }

}
