package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Entity {
	///teglalap, amiben talalhato a dolog
	public Rectangle rect;
	///képe a dolognak
	public Texture image;
	///az allomany neve, kiterjesztes nelkul.
	protected String basename;
	///ha animalt, akkor itt talalhatoak az egyedi kepek.
	protected Texture[] frames;
	///alapertelmezetten nem animalt.
	protected boolean animated=false;
	///az animacio sebessege. egy kepet milyen sokaig mutat?
	public float animationstep;
	///jelenlegi kep indexe
	protected int currentframe;
	///oda-vissza, vagy ciklikus az animacio?
	public boolean zigzag=false;
	///menyi idő telt el az utolsó animációkép óta
	protected float time;
	
	/**alapertelmezett konstruktor
	 * ad egy alapertelmezett kepet (badlogic_small.jpg, 64x64) , es a 0,0 koordinatara helyezi az entitast.
	 * nem lesz alapertelmezett allomanynev.
	 */
	public Entity() {
		image=new Texture(Gdx.files.internal("badlogic_small.jpg"));//dummy image
		rect=new Rectangle();
		rect.x=rect.y=0;
		rect.height=image.getHeight();
		rect.width=image.getWidth();
		basename=null;
	}
	
	/**Konstruktor
	 * betolti a kepet, az adott allomanybol. 0,0 koordinatakra helyezi az entitast
	 * @param filename az allomany neve *kiterjesztes nelkul*. ez fontos. 
	 * 
	 * Az allomany neve kotelezoen legyen filename+".png", es legyen az assets mappaba elhelyezve.
	 * Ezen felul, a konyvtar megkotesei miatt a kep meretei kettonem valamilyen hatvanyai kell legyenek.
	 * Ezek lesznek a meretek a jatekon belul is.
	 */
	public Entity(String filename) {
		rect=new Rectangle();
		rect.x=rect.y=0;
		basename=filename;
		image=new Texture(Gdx.files.internal(filename+".png"));
		rect.height=image.getHeight();
		rect.width=image.getWidth();
	}
	/**
	 * 
	 * Rajzolo fuggveny.
	 * 
	 * @param batch Ebbe a SpriteBatch-ba rajzolja bele a képét az entitás. A könyvtár ajánlott módszere a rajzolásra, hardware-gyorsítással. 
	 * @param x eltolás x-el (ajánlott ehelyett a kamera használata, és ide 0 érték kerüljön)
	 * @param y eltolás y-al (kamera használata ajánlott, ide 0-t)
	 */
	public void draw(SpriteBatch batch, float x, float y) {
		batch.draw(image, rect.x+x, rect.y+y);
	}
	
	/**
	 * destruktor-féle.
	 * habár a jáva nem használ destruktorokat, a használt könyvtár által erősen ajánlott a manuális törlés, 
	 * mivel sokan az adott osztályok közül rendszeri erőforrásokat kezelnek egyenesen, és a szemétgyűjtő
	 * nem kezelné őket megfelelő gyorsasággal/módon.
	 */
	public void dispose() {
		image.dispose();
		if(frames!=null) {
			for(Texture t:frames) {
				t.dispose();
			}
		}
	}

	/**
	 * Az animáció előkészítése. Az állományok 1-től indexelve, négy karakteres számkóddal kell rendelkezzenek.
	 * például, ha a basename="anim", és 2 frame van, akkor 2 fpsel fog valtani az anim0001.png és az anim0002.png között.
	 * 
	 * @param basename az állományok nevei, szám és kiterjesztés nélkül
	 * @param frame_number hány képkeret lesz az animációban
	 * @throws Exception ha hiba van a képek olvasása vagy betöltése közben (valószínű nem illik az álománynév)
	 */
	public void animate(String basename,int frame_number) throws Exception{
		frames=new Texture[frame_number];
		int i;
		for(i=1;i<=frame_number;i++) {
			System.err.println(basename+String.format("%04d.png", i));
			frames[i-1]=new Texture(Gdx.files.internal(basename+String.format("%04d.png", i)));
		}
		animated=true;
		animationstep=1f/(float)frame_number;
	}
	
	/**
	 * idő szerinti lépés. Entitás esetében, ez csak az animációt jelenti.
	 * @param delta menyi másodperc telt el azóta, hogy legutóbb meg lett hívva ez a függvény
	 */
	public void step(float delta) {
		if(animated) {
			time+=delta;
			if(time>animationstep) {
				time-=animationstep;
				currentframe++;
				if(!zigzag) {
					image=frames[currentframe%frames.length];
				}else {
					int num=currentframe%frames.length;
					if((currentframe/frames.length)%2==0) {
						image=frames[num];
					}else {
						image=frames[frames.length-num-1];
					}
				}
				rect.height=image.getHeight();
				rect.width=image.getWidth();
			}
		}
	}
}
