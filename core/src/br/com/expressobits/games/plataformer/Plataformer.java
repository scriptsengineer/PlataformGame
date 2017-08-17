package br.com.expressobits.games.plataformer;

import br.com.expressobits.games.plataformer.screen.GameScreen;
import br.com.expressobits.games.plataformer.world.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Plataformer extends Game {

	private static Plataformer instance;

	public static final boolean DEBUG = true;

	private Plataformer(){}

	@Override
	public void create() {
		this.setScreen(new GameScreen());
	}

	public static Plataformer getInstance() {
		if(instance == null){
			instance = new Plataformer();
		}
		return instance;
	}
}
