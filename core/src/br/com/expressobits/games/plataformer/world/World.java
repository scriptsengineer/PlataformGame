package br.com.expressobits.games.plataformer.world;

import br.com.expressobits.games.plataformer.Plataformer;
import br.com.expressobits.games.plataformer.block.Block;
import br.com.expressobits.games.plataformer.dictionary.Blocks;
import br.com.expressobits.games.plataformer.entity.EntitiesFactory;
import br.com.expressobits.games.plataformer.entity.system.SpriteRenderSystem;
import br.com.expressobits.games.plataformer.entity.system.TileRenderSystem;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import net.namekdev.entity_tracker.EntityTracker;
import net.namekdev.entity_tracker.ui.EntityTrackerMainWindow;

public class World {

    private int[][][] map = new int[80][45][2];

    private com.artemis.World world;

    public World(OrthographicCamera camera){
        WorldConfigurationBuilder worldConfigurationBuilder = new WorldConfigurationBuilder();
        worldConfigurationBuilder
                .with(new TileRenderSystem(this,camera))
                .with(new SpriteRenderSystem(camera));

        if(Plataformer.DEBUG){
            worldConfigurationBuilder.with(new EntityTracker(new EntityTrackerMainWindow()));
        }
        WorldConfiguration configuration = worldConfigurationBuilder.build();
        this.world = new com.artemis.World(configuration);
        EntitiesFactory.createPlayer( world, 0, 0);
    }

    /**
     * Algoritmo que gera o world
     */
    public void regenerate(){
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for(int l = 0; l < getLayers(); l++){
                    if(y < 2){
                        map[x][y][l] = Blocks.getIdByBlock(Blocks.OBSIDIAN);
                    }else if(y<4){
                        map[x][y][l] = Blocks.getIdByBlock(Blocks.COBBLESTONE);
                    }else if(y<5){
                        map[x][y][l] = Blocks.getIdByBlock(Blocks.DIRT);
                    }
                }
            }
        }
    }

    public void update(float deltaTime) {
        world.setDelta(deltaTime);
        world.process();
    }

    public Block getBlock(int x, int y, int layer){
        return Blocks.getBlockById(map[x][y][layer]);
    }

    public int getWidth(){
        return map.length;
    }

    public int getHeight(){
        return map[0].length;
    }

    public int getLayers(){
        return map[0][0].length;
    }

}
