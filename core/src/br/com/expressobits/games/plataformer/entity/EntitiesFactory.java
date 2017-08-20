package br.com.expressobits.games.plataformer.entity;

import br.com.expressobits.games.plataformer.entity.component.*;
import br.com.expressobits.games.plataformer.resource.Assets;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import javax.xml.soap.Text;

public class EntitiesFactory {

    private ComponentMapper<PlayerComponent> mPlayer;
    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteComponent> mSpriteComponent;
    private ComponentMapper<RigidBodyComponent> mRigidBoddy;
    private ComponentMapper<CollidableComponent> mCollidable;
    private ComponentMapper<JumpComponent> mJump;

    public int createPlayer(World world, float x, float y){
        int entity = world.create();

        TransformComponent transformComponent = mTransform.create(entity);
        transformComponent.position.set( x, y);

        Texture texture = Assets.manager.get(Assets.player);

        SpriteComponent spriteComponent = mSpriteComponent.create(entity);
        spriteComponent.sprite = new Sprite(Assets.manager.get(Assets.player));

        PlayerComponent playerComponent = mPlayer.create(entity);

        RigidBodyComponent rigidBodyComponent = mRigidBoddy.create(entity);

        CollidableComponent collidableComponent = mCollidable.create(entity);
        collidableComponent.collisionBox.setSize(texture.getWidth(),texture.getHeight());
        collidableComponent.collisionBox.setCenter(new Vector2(x, y));

        JumpComponent jumpComponent = mJump.create(entity);

        return entity;
    }
}
