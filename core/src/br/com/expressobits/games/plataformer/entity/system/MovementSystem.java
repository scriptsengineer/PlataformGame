package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.block.Block;
import br.com.expressobits.games.plataformer.entity.component.CollidableComponent;
import br.com.expressobits.games.plataformer.entity.component.RigidBodyComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import br.com.expressobits.games.plataformer.world.World;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;

    private ComponentMapper<RigidBodyComponent> mRigidBody;

    private ComponentMapper<CollidableComponent> mCollidable;

    private World world;

    Array<Rectangle> tiles = new Array<Rectangle>();

    public MovementSystem(World world){
        super(Aspect.all(TransformComponent.class, RigidBodyComponent.class));
        this.world = world;
    }

    @Override
    protected void process(int entityId) {
        TransformComponent transformComponent = mTransform.get(entityId);
        RigidBodyComponent rigidBodyComponent = mRigidBody.get(entityId);
        CollidableComponent collidableComponent = mCollidable.get(entityId);

        //Sistema simples de gravidade
        //Método de euler
        float delta = super.world.getDelta();

        //Delta insignifcante
        //float newY = transformComponent.position.y + rigidBodyComponent.velocity.y * delta + 0.5f * world.getGravity() * delta * delta;

        rigidBodyComponent.velocity.y +=  world.getGravity() * delta;

        if(mCollidable.has(entityId)) {
            collidableComponent.onGround = false;
            collidableComponent.onCeiling = false;
            collidableComponent.onLeftWall = false;
            collidableComponent.onLeftWall = false;

            Vector2 velocity = new Vector2(rigidBodyComponent.velocity);
            velocity.scl(delta);
            Rectangle rectangle = collidableComponent.collisionBox;
            rectangle.setPosition(transformComponent.position);

            float startX, startY, endX, endY;

            //X
            if(velocity.x > 0){
                startX = endX = transformComponent.position.x + rectangle.width + velocity.x;
            }else{
                startX = endX = transformComponent.position.x + velocity.x;
            }
            startY = transformComponent.position.y;
            endY = transformComponent.position.y + rectangle.height;
            tiles.clear();
            world.getTilesRectangle(startX, startY, endX, endY, tiles);
            rectangle.x += velocity.x;
            for(Rectangle tile:tiles){
                if(rectangle.overlaps(tile)){
                    if(velocity.x > 0){
                        collidableComponent.onRightWall = true;
                    }else if(velocity.x < 0){
                        collidableComponent.onLeftWall = true;
                    }
                    velocity.x = 0;
                    break;
                }
            }
            rectangle.x = transformComponent.position.x;


            if(velocity.y > 0){
                startY = endY = transformComponent.position.y + rectangle.height + velocity.y;
            }else{
                startY = endY = transformComponent.position.y + velocity.y;
            }
            startX = transformComponent.position.x;
            endX = transformComponent.position.x + rectangle.width;

            tiles.clear();
            world.getTilesRectangle(startX, startY, endX, endY, tiles);

            rectangle.y += velocity.y;
            for(Rectangle tile : tiles){
                if(rectangle.overlaps(tile)){
                    if(velocity.y > 0){
                        transformComponent.position.y = tile.y -rectangle.height;
                        collidableComponent.onCeiling = true;
                    }else {
                        transformComponent.position.y = tile.y + tile.height;
                        collidableComponent.onGround = true;
                    }
                    velocity.y = 0;
                    break;
                }
            }
            transformComponent.position.add(velocity);

            velocity.scl(1/delta);
            rigidBodyComponent.velocity.set(velocity);

        }else{
            transformComponent.position.mulAdd(rigidBodyComponent.velocity, delta);
        }
    }

}
