package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.block.Block;
import br.com.expressobits.games.plataformer.world.World;
import com.artemis.BaseSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileRenderSystem extends BaseSystem{

    private World world;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Color tileColor = new Color();

    public TileRenderSystem(World world, OrthographicCamera camera){
        this.world = world;
        this.camera = camera;
        this.batch = new SpriteBatch();
    }


    @Override
    protected void begin() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    }

    @Override
    protected void end() {
        batch.end();
    }


    @Override
    protected void processSystem() {
        tileColor.set(0xCCCCCCFF);
        batch.setColor(tileColor);
        renderBackground(batch);

        tileColor.set(Color.WHITE);
        batch.setColor(tileColor);
        renderForeground(batch);
    }

    private void renderBackground(Batch batch) {
        Texture texture = null;

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                texture = world.getBlock(x,y,0).texture;

                if (texture != null) {
                    batch.draw(texture, World.mapToWorld(x), World.mapToWorld(y));
                }
            }
        }
    }

    private void renderForeground(Batch batch) {
        Texture texture = null;

        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                texture = world.getBlock(x,y,1).texture;

                if (texture != null) {
                    batch.draw(texture,World.mapToWorld(x), World.mapToWorld(y));
                }
            }
        }
    }

    @Override
    protected void dispose() {
        batch.dispose();
    }
}
