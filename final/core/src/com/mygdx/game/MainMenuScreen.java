package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MainMenuScreen implements Screen {
	final SilverTime game;
	private AudioRecorder recorder;

	public MainMenuScreen(final SilverTime game) {
		this.game = game;
		recorder = Gdx.audio.newAudioRecorder(SilverTime.FREQ, true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		//ScreenUtils.clear(0, 0, 0.2f, 1);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.begin();
		game.font.draw(game.batch, "katintson, hogy vegye fel az ugras hangjat, utána nyomjon entert.", 300, 300);
		game.batch.end();
		
		if(Gdx.input.isTouched()){
			recorder.read(game.pcmaudio, 0, game.pcmaudio.length);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
			game.setScreen(new GameScreen(game));
			dispose();
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
		recorder.dispose();
	}

}
