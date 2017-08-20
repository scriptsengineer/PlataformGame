package br.com.expressobits.games.plataformer.screen;

import br.com.expressobits.games.plataformer.Plataformer;
import br.com.expressobits.games.plataformer.resource.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;

public class PreloadScreen extends ScreenAdapter {

    @Override
    public void show() {
        Assets.load();
    }

    @Override
    public void render(float delta) {
        Gdx.app.log("Progress", Assets.manager.getProgress() * 100 + "%");
        if(Assets.manager.update()){
            Plataformer.getInstance().setScreen(new GameScreen());
        }
    }
}
