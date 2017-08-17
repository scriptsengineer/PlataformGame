package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.entity.component.SpriteComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpriteRenderSystem extends IteratingSystem{

    private ComponentMapper<TransformComponent> mTransform;
    private ComponentMapper<SpriteComponent> mSprite;

    SpriteBatch batch;
    OrthographicCamera camera;

    public SpriteRenderSystem(OrthographicCamera camera) {
        super(Aspect.all(TransformComponent.class, SpriteComponent.class));
        this.camera = camera;
        batch = new SpriteBatch();
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
    protected void process(int entityId) {

        TransformComponent transformComponent = mTransform.get(entityId);
        SpriteComponent spriteComponent = mSprite.get(entityId);

        if(transformComponent.originCenter){
            spriteComponent.sprite.setOriginCenter();
        }else {
            spriteComponent.sprite.setOrigin(transformComponent.origin.x, transformComponent.origin.y);
        }
        spriteComponent.sprite.setScale(transformComponent.scaleX, transformComponent.scaleY);
        spriteComponent.sprite.setRotation(transformComponent.rotation);
        spriteComponent.sprite.setPosition(transformComponent.position.x, transformComponent.position.y);
        spriteComponent.sprite.draw(batch);

    }
}
