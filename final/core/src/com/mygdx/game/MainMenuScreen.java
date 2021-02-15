package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.Gdx;
/**
 * a főmenü
 * @author mark
 *
 */
public class MainMenuScreen implements Screen {
	final SilverTime game;
	/**
	 * audiófelvelőkészülék
	 */
	private AudioRecorder recorder;

	/**
	 * konstruktor
	 * @param game
	 */
	public MainMenuScreen(final SilverTime game) {
		this.game = game;
		recorder = Gdx.audio.newAudioRecorder(SilverTime.FREQ, true);
		Gdx.input.setInputProcessor(game.stage);
		Label label;
		label=new Label("Ezüst Idő.",game.myskin);
		label.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2f);
		label.setPosition(0,Gdx.graphics.getHeight()/2f);//felső fele a képernyőnek...
		label.setAlignment(Align.center);// ... a közepén.
		game.stage.addActor(label);
		
		Label audio=new Label("nincs hang felvéve!",game.myskin);
		audio.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		audio.setPosition(0, 0);
		audio.setAlignment(Align.topLeft);
		game.stage.addActor(audio);
		//cimke arrol, hogy van-e felvett audió.
		
		Button button=new TextButton("Hang felvétele",game.myskin);
		button.setPosition(Gdx.graphics.getWidth()/2f-button.getWidth()/2f, Gdx.graphics.getHeight()/4f-button.getHeight()/2f);
		button.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
				recorder.read(game.pcmaudio, 0, game.pcmaudio.length);
				audio.setText("Van hang felvéve");
				return true;
			}
		});
		game.stage.addActor(button);
		//gomb amelynek hatására hangfelvétel készül. Az hogy mikorról van a hang, azt nem mindig értem. 
		
		Button button2=new TextButton("Hang visszajátszása",game.myskin);
		//                               ^ itt írja
		button2.setPosition(button.getX()+(button.getWidth()-button2.getWidth())/2f, button.getY()-button.getHeight());
		button2.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
				AudioDevice player=Gdx.audio.newAudioDevice(SilverTime.FREQ, true);
				player.writeSamples(game.pcmaudio, 0, game.pcmaudio.length);
				player.dispose();
				//pazarlás, de működőképes.
				return true;
			}
		});
		game.stage.addActor(button2);
	
		
		Button button3=new TextButton("játék indítása",game.myskin);
		//                               ^ itt írja
		button3.setPosition(button2.getX()+(button2.getWidth()-button3.getWidth())/2f, button2.getY()-button2.getHeight());
		button3.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent e, float x, float y, int pointer, int button) {
				dispose();//nem ideális, de másként az új elemeket is törölné.
				game.setScreen(new GameScreen(game));
				return true;
			}
		});
		game.stage.addActor(button3);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		//ScreenUtils.clear(0, 0, 0.2f, 1);
		//szurke hátteren
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//az UI komponensek
		game.stage.act(delta);
		game.stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		game.stage.getViewport().update(width, height, false);
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
		for(Actor a:game.stage.getActors()) {
			System.err.println("removed "+a);
			a.addAction(Actions.removeActor());		
		}
	}

}
