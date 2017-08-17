package br.com.expressobits.games.plataformer;

import br.com.expressobits.games.plataformer.world.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Plataformer extends ApplicationAdapter {

	private static Plataformer instance;

	public static final boolean DEBUG = true;

	SpriteBatch batch;

	protected OrthographicCamera camera;

	protected Viewport viewport;

	protected World world;

	private Plataformer(){}
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
		viewport = new FillViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),camera);

		world = new World(camera);
		world.regenerate();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.update(Gdx.graphics.getDeltaTime());

		batch.begin();
		//world.render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width,height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		world.dispose();
	}

	public static Plataformer getInstance() {
		if(instance == null){
			instance = new Plataformer();
		}
		return instance;
	}
}
