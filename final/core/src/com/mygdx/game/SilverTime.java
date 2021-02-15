package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * a főosztály. ez indít el mindent.
 * @author mark
 *
 */
public class SilverTime extends Game {
	/**
	 * ennek adjuk a képek rajzolásának feladatát
	 */
	public SpriteBatch batch;
	/**
	 * zene
	 */
	public Music music;
	/**
	 * két másodperces a hang
	 */
	public static final int AUDIOLENGTH=2;
	/**
	 * 44.1 kilohertz, ha jól emlékszem. de nem biztos hogy ez a mértékegység
	 */
	public static final int FREQ=44100;

	/**
	 * maga az audió, PCM formátumban.
	 */
	public short[] pcmaudio;
	
	/**
	 * kb színtéma és általános kinézet. letölthető. amit ín használok, az CCO, kivéve a "hack" fontot
	 */
	public Skin myskin;
	/**
	 * ide kerülnek az UI és HUD komponensek
	 */
	public Stage stage;
	
	/**
	 * konstruktormegfelelő
	 */
	@Override
	public void create () {
		//betölti a színtémát
		myskin=new Skin(Gdx.files.internal("lml/skin/skin.json"));
		//előkészíti a színpadot a komponenseknek
		stage=new Stage(new ScreenViewport());
		//és az adagolót is
		batch = new SpriteBatch();
		//a zenével együtt (én csináltam, egy nap alatt, az LMMS-t használva (annak az állományai is megtalálhatóak az assets mappában)). 
		music=Gdx.audio.newMusic(Gdx.files.internal("music.wav"));
		//a főmenüre vált
		this.setScreen(new MainMenuScreen(this));
		//a zene ciklikus
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
