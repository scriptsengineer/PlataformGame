package br.com.expressobits.games.plataformer.entity;

import br.com.expressobits.games.plataformer.entity.component.*;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EntitiesFactory {

    private ComponentMapper<PlayerComponent> mPlayer;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteComponent> mSpriteComponent;
    private ComponentMapper<RigidBodyComponent> mRigidBoddy;
    private ComponentMapper<CollidableComponent> mCollidable;

    public int createPlayer(World world, float x, float y){
        int entity = world.create();

        TransformComponent transformComponent = mTransform.create(entity);
        transformComponent.position.set( x, y);

        SpriteComponent spriteComponent = mSpriteComponent.create(entity);
        spriteComponent.sprite = new Sprite(new Texture("player/player.png"));

        PlayerComponent playerComponent = mPlayer.create(entity);

        RigidBodyComponent rigidBodyComponent = mRigidBoddy.create(entity);

        CollidableComponent collidableComponent = mCollidable.create(entity);

        return entity;
    }
}
