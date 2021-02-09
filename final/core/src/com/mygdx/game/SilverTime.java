package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SilverTime extends Game {
	public SpriteBatch batch;
	public SpriteBatch hudbatch;
	public Music music;
	public static final int AUDIOLENGTH=2;
	public static final int FREQ=44100;
	public short[] pcmaudio;
	public BitmapFont font;
	
	@Override
	public void create () {
		font=new BitmapFont();
		batch = new SpriteBatch();
		hudbatch=new SpriteBatch();
		music=Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		this.setScreen(new MainMenuScreen(this));
		music.setLooping(true);
		pcmaudio=new short[AUDIOLENGTH*FREQ];
	}

	@Override
	public void render () {
		super.render();		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();
	}
}
