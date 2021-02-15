package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * a főszereplő, vagyis játékos
 * @author mark
 *
 */
public class Player extends Character {
	
	/**
	 * konstruktorok. lásd Character, Entity
	 */
	public Player(MapWatcher map) {
		super(map);
	}

	public Player(String filename, MapWatcher map) {
		super(filename, map);
	}

	public Player(float spd, MapWatcher map) {
		super(spd, map);
	}

	public Player(String filename, float spd, MapWatcher map) {
		super(filename, spd, map);
	}
	
	/**
	 * leellenőrzi a gombok lenyomását, és azoknak megfelelően mozog.
	 */
	@Override
	public void step(float delta) {
		super.step(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			moveright(delta);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			moveleft(delta);
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			jump();
		}
	}
	
	@Override
	public void dispose() {
		super.dispose();
	} 
}
