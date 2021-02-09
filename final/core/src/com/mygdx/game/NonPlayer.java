package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class NonPlayer extends Character implements Runnable {
	
	FileHandle script;
	byte current;
	Thread thrd;
	boolean running=true;
	
	public NonPlayer(String filename, MapWatcher map) {
		super(filename, map);
		script=Gdx.files.internal(filename+".txt");
		thrd=new Thread(this);
		thrd.start();
	}

	public NonPlayer(String filename, float spd, MapWatcher map) {
		super(filename, spd, map);
		script=Gdx.files.internal(filename+".txt");
		thrd=new Thread(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		byte[] instructions=script.readBytes();
		while(running) {
			current=instructions[(i++)%instructions.length];
			System.out.println(current);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void step(float delta) {
		super.step(delta);
		switch(current) {
		case '>':
			moveright(delta);
			break;
		case '<':
			moveleft(delta);
			break;
		case '^':
			jump();
			break;
		default:
			//wait
		}
	}
	@Override
	public void dispose() {
		super.dispose();
		running=false;
	}

}
