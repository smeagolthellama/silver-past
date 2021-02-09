package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameScreen implements Screen {
	final SilverTime game;
	Player ch;
	Character npc;
	GameMap map;
	OrthographicCamera camera;
	float score=0;
	float scoreheight;

	public GameScreen(SilverTime game) {
		this.game=game;
		map=new GameMap();
		ch=new Player(120,map);
		npc=new NonPlayer("droplet",map);
		ch.rect.x=70;
		ch.rect.y=250;
		npc.rect.x=300;
		npc.rect.y=250;
		camera=new OrthographicCamera(300,300*Gdx.graphics.getHeight()/Gdx.graphics.getWidth());
		camera.position.set(camera.viewportWidth/2f, camera.viewportHeight/2f, 0);
		scoreheight=Gdx.graphics.getHeight();
		try {
			ch.animate("", 5);
			ch.zigzag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.music.play();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(camera.combined);
		ch.step(delta);
		npc.step(delta);
		game.batch.begin();
		map.draw(game.batch,0,0);
		ch.draw(game.batch, 0, 0);
		npc.draw(game.batch, 0, 0);
		game.batch.end();
		camera.position.set(ch.rect.x, ch.rect.y, 0);
		camera.update();
		score+=delta;
		game.hudbatch.begin();
		game.font.draw(game.hudbatch, Float.toString(score), 0, scoreheight);
		game.hudbatch.end();
		if(map.finished(ch.rect)){
			game.setScreen(new FinishedScreen(game, score));
			game.music.stop();
			this.dispose();
		}
	}

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
	}

}
