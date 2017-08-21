package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.entity.component.CollidableComponent;
import br.com.expressobits.games.plataformer.entity.component.RigidBodyComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import br.com.expressobits.games.plataformer.world.World;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.*;

public class CollisionDebugSystem extends IteratingSystem{

    private ComponentMapper<TransformComponent> mTransform;

    private ComponentMapper<RigidBodyComponent> mRigidBody;

    private ComponentMapper<CollidableComponent> mCollidable;

    Camera camera;

    ShapeRenderer shapeRenderer;

    World world;

    public CollisionDebugSystem(Camera camera, World world) {
        super(Aspect.all(TransformComponent.class, RigidBodyComponent.class, CollidableComponent.class));
        this.camera = camera;
        shapeRenderer = new ShapeRenderer();
        this.world = world;
    }

    @Override
    protected void begin() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeType.Line);

        shapeRenderer.setColor(Color.YELLOW);

        Array<Rectangle> rects = new Array<Rectangle>();
        Rectangle collisionBox = world.getArtemis().getEntity(world.getPlayer()).getComponent(CollidableComponent.class).collisionBox;

        world.getTilesRectangle(collisionBox.x,collisionBox.y,collisionBox.x+collisionBox.width, collisionBox.y+collisionBox.height, rects);


        Rectangle rectangle;
        for(int x = 0; x < world.getWidth(); x++){
            for(int y = 0; y < world.getHeight(); y++){
                rectangle = world.getTileRectangle(x, y);
                if(rectangle!=null){

                    shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                }
            }
        }

    }

    @Override
    protected void end() {
        shapeRenderer.end();

    }

    @Override
    protected void process(int entityId) {
        TransformComponent transformComponent = mTransform.get(entityId);
        RigidBodyComponent rigidBodyComponent = mRigidBody.get(entityId);
        CollidableComponent collidableComponent = mCollidable.get(entityId);

        Vector2 min = collidableComponent.collisionBox.getPosition(new Vector2());
        Vector2 max = collidableComponent.collisionBox.getSize(new Vector2()).add(min);
        Vector2 size = collidableComponent.collisionBox.getSize(new Vector2());

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(min.x,min.y,size.x,size.y);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.line(transformComponent.position.x,transformComponent.position.y,
                transformComponent.position.x + rigidBodyComponent.velocity.x,
                transformComponent.position.y + rigidBodyComponent.velocity.y);

        shapeRenderer.setColor(Color.RED);
        if(collidableComponent.onGround){
            shapeRenderer.line(min.x,min.y,max.x,min.y);
        }
        if(collidableComponent.onCeiling){
            shapeRenderer.line(min.x,max.y,max.x,max.y);
        }
        if(collidableComponent.onLeftWall){
            shapeRenderer.line(min.x,min.y,min.x,max.y);
        }
        if(collidableComponent.onRightWall){
            shapeRenderer.line(max.x,min.y,max.x,max.y);
        }

    }

    @Override
    protected void dispose() {
        shapeRenderer.dispose();
    }
}
