package br.com.expressobits.games.plataformer.entity;

import br.com.expressobits.games.plataformer.entity.component.PlayerComponent;
import br.com.expressobits.games.plataformer.entity.component.RigidBodyComponent;
import br.com.expressobits.games.plataformer.entity.component.SpriteComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EntitiesFactory {

    public static Entity createPlayer(World world, float x, float y){
        Entity entity = world.createEntity();

        EntityEdit entityEdit = entity.edit();

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.set( x, y);
        entityEdit.add(transformComponent);

        SpriteComponent spriteComponent = new SpriteComponent();
        spriteComponent.sprite = new Sprite(new Texture("player/player.png"));
        entityEdit.add(spriteComponent);

        PlayerComponent playerComponent = new PlayerComponent();
        entityEdit.add(playerComponent);

        RigidBodyComponent rigidBodyComponent = new RigidBodyComponent();
        entityEdit.add(rigidBodyComponent);

        return entity;
    }
}
