package com.mygdx.game;

/**
 * Szereplő, vagyis mozgó entitás. Kezeli a saját fizikáját.
 * @author mark
 *
 */
public class Character extends Entity {
	/**
	 * sebessége a szereplőnek. (virtuális) pixel/másodperc. 
	 */
	protected float speed;
	/**
	 * utalás a térképre. kell ahoz, hogy lehessen ellenőrizni a falakba való ütközéseket, és tartalmazza a gravitáció mértékét.
	 * Az interface megakadályozza a térkép módosítását a szereplők által. 
	 */
	protected final MapWatcher map;
	/**
	 * pillanatnyi függőleges sebesség. Közvetlenül ugrás után egyenlő a `speed`-el. 
	 */
	protected float velocityY; 

	/**
	 * konstruktor. Kötelező a térkép megadása.
	 * alapértelmezett képet (badlogic_small) és sebességet (200f) állít be. A pozíció 0,0-ra lesz állítva, 
	 * de mivel a rect publikus, ez könnyen orvosolható. 
	 * @param map a térkép 
	 */
	public Character(MapWatcher map) {
		super();
		speed=200f;
		this.map=map;
	}

	/**
	 * konstruktor
	 * alapértelmezett sebességet (200) és pozzíciót (0,0) állít be.
	 * @param filename A kép neve kiterjesztés nélkül. a kiterjesztés kötelezően ".png".
	 * @param map A térkép.
	 */
	public Character(String filename,MapWatcher map) {
		super(filename);
		speed=200f;		
		this.map=map;

	}
	
	/**
	 * kontruktor
	 * alapértelmezett képet állít be (badlogic_small).
	 * @param spd a sebesség. virtuális pizel/másodperc-ben van mérve.
	 * @param map a térkép
	 */
	public Character(float spd,MapWatcher map) {
		super();
		speed=spd;
		this.map=map;
	}
	
	/**
	 * konstruktor
	 * @param filename az állománynév a ".png" kiterjesztés nélkül.
	 * @param spd a sebesség virtuális pixel/sec-ben
	 * @param map a térkép
	 */
	public Character(String filename, float spd,MapWatcher map) {
		super(filename);
		speed=spd;
		this.map=map;
	}
	
	/**
	 * balra mozog ha nem ütközik falba.
	 * @param delta az időegység másodpercben mérve. a "frame" hossza.
	 */
	protected void moveleft(float delta) {
		if(!map.check(rect.x-speed*delta, rect.y) &&
				!map.check(rect.x-speed*delta, rect.y+rect.height)){//in order to not get stuck in walls
			rect.x-=speed*delta;
		}
	}
	
	/**
	 * jobbra mozog ha nincs fal az útban. 
	 * @param delta időegység
	 */
	protected void moveright(float delta) {
		if(!map.check(rect.x+rect.width+speed*delta, rect.y+rect.height) &&
				!map.check(rect.x+rect.width+speed*delta, rect.y) ){
			rect.x+=speed*delta;
		}
	}
	/**
	 * esés (vagy felfele mozgás, ha ugrott a karakter)
	 * @param delta időegység
	 */
	protected void fall(float delta) {
		if(!map.check(rect.x, rect.y-velocityY*delta) && 
				!map.check(rect.x+rect.width, rect.y-velocityY*delta) &&
				!map.check(rect.x, rect.y+rect.height-velocityY*delta) &&
				!map.check(rect.x+rect.width, rect.y+rect.height-velocityY*delta)) {
			//mivel fel is, le is mehet (előjeles a sebesség), mind négy sarkot le kell ellenőrizni.
			rect.y-=velocityY*delta;
			velocityY+=map.getGravity()*delta;
		}else {
			velocityY=0;
		}
	}
	
	/**
	 * ugrás
	 * leelenőrzi a jelenlegi sebességet, ahelyett, hogy leelenőrizze, hogy van-e amiről elrugaszkodni. Ez szándékos.
	 */
	protected void jump() {
		if(Math.abs(velocityY)<10) {
			velocityY=-speed;
		}
	}

	/**
	 * Időkezelés
	 * 
	 * Meghévja az Entity.step(delta)-t (ami az animációkat kezeli), és esik. 
	 * A postosabb irányítást (jobb, bal, ugrás) vagy a játékos (Player) vagy az AI (NonPlayer) kell biztosítsa.  
	 * @param delta időegység
	 */
	public void step(float delta) {
		super.step(delta);
		fall(delta);
	}
}
