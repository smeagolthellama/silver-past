package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

/**
 * a fő játék
 * @author mark
 *
 */
public class GameScreen implements Screen {
	final SilverTime game;
	/**
	 * a játékos
	 */
	Player ch;
	/**
	 * egy npc. egyszerű utasításokat követ.
	 */
	Character npc;
	/**
	 * a térkép
	 */
	GameMap map;
	/**
	 * egy kamera. követi a játékost
	 */
	OrthographicCamera camera;
	/**
	 * az eddigi idő
	 */
	float score=0;
	/**
	 * cimke, tartalmazza a pontszámot (időt).
	 */
	private Label label;

	public GameScreen(SilverTime game) {
		label=new Label("",game.myskin);
		label.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		label.setAlignment(Align.topLeft);
		//elhelyeztük a pontozást
		this.game=game;
		game.stage.addActor(label);
		map=new GameMap();
		map.setLandImage("maptile");
		//Nem elég szép az alapértelmezett kép. 
		ch=new Player(120,map);
		//új játékos, 120 "pixel"/sec sebességgel
		npc=new NonPlayer("droplet",map);
		//Npc, droplet.png és droplet.txt-t használja.
		ch.rect.x=70;
		ch.rect.y=250;
		//elhelyezzük a két szereplőt a térképen.
		npc.rect.x=300;
		npc.rect.y=250;
		//a kamera méretei és helyzete
		camera=new OrthographicCamera(300,300*Gdx.graphics.getHeight()/Gdx.graphics.getWidth());
		camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
		/**
		 * 0001...0005.png animáció a játékosnak (/core/assets)
		 */
		try {
			ch.animate("", 5);
			ch.zigzag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// indul a zene! én írtam.
		game.music.play();
	}

	@Override
	public void show() {

	}

	/**
	 * deltánként meghívva, lerajzolja az elemeket.
	 */
	@Override
	public void render(float delta) {
		//világos lila háttéren...
		Gdx.gl.glClearColor(0.5f, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//követve a kamerát...
		game.batch.setProjectionMatrix(camera.combined);
		//történik fizika...
		ch.step(delta);
		npc.step(delta);
		//és...
		game.batch.begin();
		map.draw(game.batch,0,0);
		ch.draw(game.batch, 0, 0);
		npc.draw(game.batch, 0, 0);
		game.batch.end();
		// mindent lerajzolunk.
		camera.position.set(ch.rect.x, ch.rect.y, 0);
		//a kamera követi a játékost, 
		camera.update();
		score+=delta;// és pörög az idő
		label.setText(Float.toString(score));
		//ha elértük a célt,
		if(map.finished(ch.rect)/*||Gdx.input.isKeyPressed(Input.Keys.ESCAPE)*/){
			game.setScreen(new FinishedScreen(game, score));//vége a játéknak
			game.music.stop();// és a zenének.
			this.dispose();
		}
		game.stage.act(delta);
		game.stage.draw();
	}

	/**
	 * legyen megfelelően méretezve minden
	 */
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth=300f*width/height;
		camera.viewportHeight=300f;
		camera.update();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		ch.dispose();
		npc.dispose();
		map.dispose();
		label.remove();
	}

}
