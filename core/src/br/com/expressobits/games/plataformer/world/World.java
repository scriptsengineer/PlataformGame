package br.com.expressobits.games.plataformer.world;

import br.com.expressobits.games.plataformer.Plataformer;
import br.com.expressobits.games.plataformer.block.Block;
import br.com.expressobits.games.plataformer.dictionary.Blocks;
import br.com.expressobits.games.plataformer.entity.EntitiesFactory;
import br.com.expressobits.games.plataformer.entity.system.*;
import com.artemis.Entity;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import net.namekdev.entity_tracker.EntityTracker;
import net.namekdev.entity_tracker.ui.EntityTrackerMainWindow;

import java.util.Random;

public class World {

    public static final int BACKGROUND = 0;
    public static final int FOREGROUND = 1;

    private EntityTrackerMainWindow entityTrackerMainWindow;
    private int[][][] map = new int[80][45][2];

    private com.artemis.World artemis;

    private int seaLevel = 7;

    private float gravity = -576;

    private EntitiesFactory entitiesFactory;

    private int player;

    public World(OrthographicCamera camera){
        WorldConfigurationBuilder worldConfigurationBuilder = new WorldConfigurationBuilder();
        worldConfigurationBuilder
                .with(new PlayerControllerSystem())
                .with(new MovementSystem(this))
                .with(new TileRenderSystem(this,camera))
                .with(new SpriteRenderSystem(camera));

        if(Plataformer.DEBUG){
            worldConfigurationBuilder.with(new CollisionDebugSystem(camera,this));



            if(Gdx.app.getType().equals(Application.ApplicationType.Desktop)){
                entityTrackerMainWindow = new EntityTrackerMainWindow(false,false);
                worldConfigurationBuilder.with(new EntityTracker(entityTrackerMainWindow));
            }
        }
        WorldConfiguration configuration = worldConfigurationBuilder.build();
        this.artemis = new com.artemis.World(configuration);
        entitiesFactory = new EntitiesFactory();
        artemis.inject(entitiesFactory);
        player = entitiesFactory.createPlayer( artemis, 0, getHeight() * Block.TILE_SIZE);
    }

    /**
     * Algoritmo que gera o world
     */
    public void regenerate(){

        Random random = new Random();

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                for(int l = 0; l < getLayers(); l++){
                    Block block = null;
                    if(y < getSeaLevel() - 5){
                        block = Blocks.OBSIDIAN;
                    }else if(y< getSeaLevel() - 2){
                        block = Blocks.COBBLESTONE;
                    }else if(y < getSeaLevel()){
                        block = Blocks.DIRT;
                    }else {
                        if(l == 0){
                            block = Blocks.DIRT;
                        }else {
                            block = random.nextInt(100) < 10 ? Blocks.DIRT : Blocks.AIR;
                        }
                    }
                    map[x][y][l] = Blocks.getIdByBlock(block);
                }
            }
        }
    }

    public void update(float deltaTime) {
        artemis.setDelta(deltaTime);
        artemis.process();
    }

    public Block getBlock(int x, int y, int layer){
        return Blocks.getBlockById(map[x][y][layer]);
    }

    public Block getBlock(float x,float y,int layer) {
        return getBlock(worldToMap(x),worldToMap(y),layer);
    }

    public boolean isSolid(int x, int y) {
        return isValid(x, y) && getBlock(x, y, FOREGROUND).isSolid();
    }

    public boolean isSolid(float x, float y) {
        return isSolid(worldToMap(x), worldToMap(y));
    }

    public Rectangle getTileRectangle(int x, int y){
        Rectangle rectangle = null;

        if(isSolid(x, y)){
            rectangle = new Rectangle(mapToWorld(x), mapToWorld(y),Block.TILE_SIZE, Block.TILE_SIZE);
        }

        return rectangle;
    }

    public void getTilesRectangle(float startX, float startY, float endX, float endY, Array<Rectangle> tileRectangles){
        getTilesRectangle(worldToMap(startX),worldToMap(startY),worldToMap(endX),worldToMap(endY),tileRectangles);
    }

    public void getTilesRectangle(int startX, int startY, int endX, int endY, Array<Rectangle> tileRectangles){
        Rectangle rectangle;

        for(int y = startY; y <= endY; y++ ){
            for(int x = startX; x <= endX; x++ ){
                rectangle = getTileRectangle(x,y);
                if(rectangle !=null){
                    tileRectangles.add(rectangle);
                }
            }
        }
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

    public int getSeaLevel() {
        return seaLevel;
    }

    /**
     * Desaloca recursos
     */
    public void dispose(){
        artemis.dispose();
    }

    public boolean isValid(int x, int y){
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

    public EntityTrackerMainWindow getEntityTrackerMainWindow() {
        return entityTrackerMainWindow;
    }

    public float getGravity() {
        return gravity;
    }

    public int getPlayer() {
        return player;
    }

    public com.artemis.World getArtemis() {
        return artemis;
    }

    public static float mapToWorld(int mapCoordinate){
        return mapCoordinate * Block.TILE_SIZE;
    }

    public static int worldToMap(float worldCoordinate){
        return (int) worldCoordinate / Block.TILE_SIZE;
    }
}
