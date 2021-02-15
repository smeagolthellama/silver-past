package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Mesterséges inteligenciával rendelkező szereplő.
 * Minden NPC (nem-játékos karakter) mozgását egy szöveges állomány irányitja, 
 * aminek a neve (kiterjesztés nélkül) megegyezik a képének a nevével.
 * @author mark
 *
 */
public class NonPlayer extends Character implements Runnable {
	/**
	 * a "program" amit követ a szereplő. jelenleg három utasítást ismer:
	 * 
	 * '>' menjen egy másodpercig jobbra
	 * '<' menjen egy másodpercig balra
	 * '^' ugráljon egy másodpercig
	 * 
	 * bármi más karaktert úgy értelmez, hogy várjon egy másodpercet. amiután végére ér a programnak, ismétli előrről.
	 *   
	 */
	FileHandle script;
	/**
	 * jelenlegi utasítás
	 */
	byte current;
	/**
	 * vezérlőszál. irányítja a current-en keresztül a karaktert.
	 */
	Thread thrd;
	/**
	 * ciklusváltozó, arra való, hogy leállítsa a vezérlőszálat.
	 */
	boolean running=true;
	
	/**
	 * konstruktor.
	 * 
	 * @param filename a kiterjesztés nélküli neve a képnek, és az utasításoknak.
	 * @param map a térkép.
	 */
	public NonPlayer(String filename, MapWatcher map) {
		super(filename, map);
		script=Gdx.files.internal(filename+".txt");
		thrd=new Thread(this);
		thrd.start();
	}

	/**
	 * konstruktor
	 * 
	 * @param filename a kiterjesztés nélküli neve a képnek, és az utasításoknak.
	 * @param spd a sebesség
	 * @param map a térkép.
	 */
	public NonPlayer(String filename, float spd, MapWatcher map) {
		super(filename, spd, map);
		script=Gdx.files.internal(filename+".txt");
		thrd=new Thread(this);
	}

	/**
	 * a vezérlőszál futása
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		//beolvassa az összes utasítást
		byte[] instructions=script.readBytes();
		//leelenőrzi a stopp-változót 
		while(running) {
			//következő
			current=instructions[(i++)%instructions.length];
			//debug
			System.out.println(current);
			//vár
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
	}
	
	/**
	 * a fizika mellett, a mozgást is intézi.
	 */
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
	/**
	 * leállítja a szálat, a többi mellett.
	 */
	@Override
	public void dispose() {
		super.dispose();
		running=false;
	}

}
