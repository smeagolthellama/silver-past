package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.graphics.GL20;

public class FinishedScreen implements Screen {
	final SilverTime game;
	protected float newscore;
	protected AudioDevice player;
	protected boolean playedsound;

	public FinishedScreen(final SilverTime game, float score) {
		this.game = game;
		newscore=score;
		player=Gdx.audio.newAudioDevice(SilverTime.FREQ, true);
		playedsound=false;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.hudbatch.begin();
		game.font.draw(game.hudbatch, "Az idod:", 400, 200);
		game.font.draw(game.hudbatch, ""+newscore, 300, 170);
		game.hudbatch.end();
		if(!playedsound) {
			player.writeSamples(game.pcmaudio, 0, game.pcmaudio.length);
			playedsound=true;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
