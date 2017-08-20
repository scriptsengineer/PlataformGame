package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.block.Block;
import br.com.expressobits.games.plataformer.entity.component.CollidableComponent;
import br.com.expressobits.games.plataformer.entity.component.RigidBodyComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import br.com.expressobits.games.plataformer.world.World;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;

public class MovementSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> mTransform;

    private ComponentMapper<RigidBodyComponent> mRigidBody;

    private ComponentMapper<CollidableComponent> mCollidable;

    private World world;

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

        // SO + vt
        transformComponent.position.mulAdd(rigidBodyComponent.velocity, delta);

        rigidBodyComponent.velocity.y +=  world.getGravity() * delta;

        if(mCollidable.has(entityId)) {

            collidableComponent.collisionBox.setCenter(transformComponent.position);

            if(transformComponent.position.y < world.getSeaLevel() * Block.TILE_SIZE){
                rigidBodyComponent.velocity.y = 0;
                transformComponent.position.y = world.getSeaLevel() * Block.TILE_SIZE;
                collidableComponent.onGround = true;
            }else{
                collidableComponent.onGround = false;
            }
        }
    }

}
