package br.com.expressobits.games.plataformer.entity.system;

import br.com.expressobits.games.plataformer.entity.component.*;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.*;

public class PlayerControllerSystem extends IteratingSystem {

    private ComponentMapper<PlayerComponent> mPlayer;

    private ComponentMapper<RigidBodyComponent> mRigidBody;

    private ComponentMapper<CollidableComponent> mCollidable;

    private ComponentMapper<JumpComponent> mJump;

    private boolean moveRight;
    private boolean moveLeft;
    private boolean jump;

    public PlayerControllerSystem() {
        super(Aspect.all(PlayerComponent.class, RigidBodyComponent.class,
                JumpComponent.class, CollidableComponent.class));

        Gdx.input.setInputProcessor(new InputMultiplexer(new GameInputAdapter()));
    }

    @Override
    protected void process(int entityId) {

        PlayerComponent playerComponent = mPlayer.get(entityId);
        RigidBodyComponent rigidBodyComponent = mRigidBody.get(entityId);
        JumpComponent jumpComponent = mJump.get(entityId);
        CollidableComponent collidableComponent = mCollidable.get(entityId);

        if(playerComponent.canWalk){
            if(moveLeft == moveRight){
                rigidBodyComponent.velocity.x = 0;
            }
            if(moveLeft){
                rigidBodyComponent.velocity.x = -playerComponent.walkSpeed;
            }else if(moveRight){
                rigidBodyComponent.velocity.x = playerComponent.walkSpeed;
            }

            if(jumpComponent.canJump && collidableComponent.onGround && jump){
                rigidBodyComponent.velocity.y = jumpComponent.jumpSpeed;
            }
        }

    }

    private class GameInputAdapter extends InputAdapter {

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode){
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = true;
                    break;
                case Input.Keys.LEFT:
                case Input.Keys.A:
                    moveLeft = true;
                    break;
                case Input.Keys.SPACE:
                    jump = true;
                    break;

            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode){
                case Input.Keys.RIGHT:
                case Input.Keys.D:
                    moveRight = false;
                    break;
                case Input.Keys.LEFT:
                case Input.Keys.A:
                    moveLeft = false;
                    break;
                case Input.Keys.SPACE:
                    jump = false;
                    break;

            }
            return true;
        }
    }
}
