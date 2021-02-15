package com.mygdx.game;


import java.util.ArrayList;
import java.util.Scanner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * A térképe a pályának
 * @author mark
 *
 */
public class GameMap implements MapWatcher {
	/**
	 * van-e az y,x indexen föld?
	 */
	private boolean land[][];
	/**
	 * hogy néz ki egy négyzete a földnek?
	 */
	private Texture landImage; //just one image will do for now
	/**
	 * ahonnan beolvassuk a térképet.
	 */
	private FileHandle fh;
	/**
	 * self-explanatory
	 */
	private int height;
	private int width;
	private float gravity;
	/**
	 * debugging info
	 */
	private boolean checked[][];
	/**
	 * hol található a célvonal
	 */
	ArrayList<Rectangle> finishLine;
	
	/**
	 * setter a landImage-nek. ha nem működik, ugyanazz marad a kép (elméletileg) 
	 * @param basename
	 */
	public void setLandImage(String basename) {
		try {
			Texture tmp=new Texture(Gdx.files.internal(basename+".png"));
			landImage.dispose();
			landImage=tmp;
		}catch(Exception e) {
			//leave landImage as it is
		}			
	}

	/**
	 * konstruktor
	 */
	public GameMap() {
		finishLine=new ArrayList<Rectangle>();
		System.err.println("GameMap constructor called");
		landImage=new Texture(Gdx.files.internal("badlogic_small.jpg"));//alapértelmezett kép
		fh=Gdx.files.internal("map.pbm");//cannot load into a texture 
		/**
		 * Ajánlott olvasmány: a dokumentáció a ppm, pgm, és pbm képformátumokról (netpbm család)
		 */
		Scanner sc=new Scanner(fh.readString());
		String tmp;
		while(!sc.hasNextInt()) {
			tmp=sc.next();
			System.err.println("read \""+tmp+"\"");
			//Nem olvassuk be a mágikus számot, sem a kommenteket.
		}
		width=sc.nextInt();
		height=sc.nextInt();
		System.err.println("read dimensions as "+width+" "+height);
		land=new boolean[height][width];
		checked=new boolean[height][width];
		int i,j;
		byte b;
		//sc.skip("\n");
		sc.useDelimiter("");
		String str = sc.next();
		while(sc.hasNext()) {
			str+=sc.next();
		}
		//a méretek után mindent beolvasunk egy nagy sztring-be. byte-onként olvasunk, nem bittenként. P1-es mágikus szám a támogatott, nem a P4
		//assuming plain pbm, not normal (P1). P4 is not supported.
		int k=0;
		byte[] bytearray=str.getBytes();
		for(i=height-1;i>=0;i--) {
			System.err.println("entering loop(i="+i+")");
			for(j=0;j<width;j++) {
				System.err.println("entering loop(j="+j+")");
				b=bytearray[k++];
				System.err.printf("read byte: 0x%2X (%d, '%c') \n",b,b,b);
				land[i][j]=(b=='1');
				checked[i][j]=false;
				if(java.lang.Character.isWhitespace((char)(b&0xff))) {//újsor, szóköz etc.
					j--;
				}
			}
		}
		//A netpbm támogatja a több képet egyetlen állományban. ezért ugyanabban az állományban, ugyanolyan formátumban várjuk el a cél(ok) elhejyezését.
		for(i=height-1;i>=0 && k<bytearray.length;i--) {
			for(j=0;j<width && k<bytearray.length;j++) {
				b=bytearray[k++];
				if(b=='1') {
					Rectangle r=new Rectangle();
					r.height=landImage.getHeight();
					r.width=landImage.getWidth();
					r.x=j*landImage.getWidth();
					r.y=i*landImage.getHeight();
					finishLine.add(r);
				}
				if(java.lang.Character.isWhitespace((char)(b&0xff))) {//hasonlóan.
					j--;
				}
			}
		}
		System.err.println(bytearray);//debugging info
		
		gravity=100;//alapérték. setterrel felülírható.

	}

	/**
	 * leelenőrzi, ha az x,y pozíció elfoglalható. 
	 */
	@Override
	public boolean check(float x, float y) {
		int lastX=(int) Math.floor(x/landImage.getWidth());
		int lastY=(int) Math.floor(y/landImage.getHeight());
		checked[lastY][lastX]=true;
		return land[lastY][lastX];
	}

	/**
	 * leelenőrzi, hogyha az adott négyszög érinti-e a célt
	 * @param test
	 * @return
	 */
	public boolean finished(Rectangle test) {
		return finishLine.stream().anyMatch(r -> r.overlaps(test));
	}
	
	/**
	 * lerajzolja a térképet, hasonlóan az Entitáshoz.
	 * @param batch
	 * @param x
	 * @param y
	 */
	public void draw(SpriteBatch batch,float x, float y) {
		batch.setColor(Color.FIREBRICK);
		for(int i=0;i<land.length;i++) {
			for(int j=0;j<land[0].length;j++) {
				if(land[i][j]) {
					batch.draw(landImage, j*landImage.getWidth() + x, i*landImage.getHeight() + y);
				}
			}
		}
		batch.setColor(Color.CYAN);
		for(Rectangle r:finishLine) {
			batch.draw(landImage, r.x+x, r.y+y);
		}
		batch.setColor(Color.WHITE);
	}

	@Override
	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}
	
	public void dispose() {
		landImage.dispose();
	}
}
