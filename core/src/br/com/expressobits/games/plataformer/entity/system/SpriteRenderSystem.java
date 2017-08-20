package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.entity.component.SpriteComponent;
import br.com.expressobits.games.plataformer.entity.component.TransformComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

        Sprite sprite = spriteComponent.sprite;

        if(transformComponent.originCenter){
            sprite.setOriginCenter();
        }else {
            sprite.setOrigin(transformComponent.origin.x, transformComponent.origin.y);
        }
        sprite.setScale(transformComponent.scaleX, transformComponent.scaleY);
        sprite.setRotation(transformComponent.rotation);
        sprite.setPosition(transformComponent.position.x, transformComponent.position.y);

        batch.draw(sprite.getTexture(),
                sprite.getX() - sprite.getOriginX(), sprite.getY() - sprite.getOriginY(),
                sprite.getOriginX(), sprite.getOriginY(),
                sprite.getWidth(), sprite.getHeight(),
                sprite.getScaleX(), sprite.getScaleY(),
                sprite.getRotation(),
                sprite.getRegionX(), sprite.getRegionY(),
                sprite.getRegionWidth(), sprite.getRegionHeight(),
                spriteComponent.flipX, spriteComponent.flipY);

    }

    @Override
    protected void dispose() {
        batch.dispose();
    }
}
