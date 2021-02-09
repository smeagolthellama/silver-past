package com.mygdx.game;


import java.util.ArrayList;
import java.util.Scanner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameMap implements MapWatcher {
	
	private boolean land[][];
	private Texture landImage; //just one image will do for now
	private FileHandle fh;
	private int height;
	private int width;
	private float gravity;
	private boolean checked[][];
	ArrayList<Rectangle> finishLine;

	public GameMap() {
		finishLine=new ArrayList<Rectangle>();
		System.err.println("GameMap constructor called");
		landImage=new Texture(Gdx.files.internal("badlogic_small.jpg"));
		fh=Gdx.files.internal("map.pbm");//cannot load into a texture 
		Scanner sc=new Scanner(fh.readString());
		String tmp;
		while(!sc.hasNextInt()) {
			tmp=sc.next();
			System.err.println("read \""+tmp+"\"");
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
		
		//assuming plain pbm, not normal (P1). P4 is no supported.
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
				if(java.lang.Character.isWhitespace((char)(b&0xff))) {
					j--;
				}
			}
		}
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
				if(java.lang.Character.isWhitespace((char)(b&0xff))) {
					j--;
				}
			}
		}
		System.err.println(bytearray);
		
		gravity=100;

	}

	@Override
	public boolean check(float x, float y) {
		int lastX=(int) Math.floor(x/landImage.getWidth());
		int lastY=(int) Math.floor(y/landImage.getHeight());
		checked[lastY][lastX]=true;
		return land[lastY][lastX];
	}

	public boolean finished(Rectangle test) {
		return finishLine.stream().anyMatch(r -> r.overlaps(test));
	}
	
	public void draw(SpriteBatch batch,float x, float y) {
		for(int i=0;i<land.length;i++) {
			for(int j=0;j<land[0].length;j++) {
				if(land[i][j]) {
					batch.draw(landImage, j*landImage.getWidth() + x, i*landImage.getHeight() + y);
				}
			}
		}
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
