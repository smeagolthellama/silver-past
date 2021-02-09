package com.mygdx.game;

public class Character extends Entity {
	
	protected float speed; 
	protected MapWatcher map;
	protected float velocityY; 
	protected float time=0;

	public Character(MapWatcher map) {
		super();
		speed=200f;
		this.map=map;
	}

	public Character(String filename,MapWatcher map) {
		super(filename);
		speed=200f;		
		this.map=map;

	}
	
	public Character(float spd,MapWatcher map) {
		super();
		speed=spd;
		this.map=map;
	}
	
	public Character(String filename, float spd,MapWatcher map) {
		super(filename);
		speed=spd;
		this.map=map;
	}
	
	protected void moveleft(float delta) {
		if(!map.check(rect.x-speed*delta, rect.y) && !map.check(rect.x-speed*delta, rect.y+rect.height)){//in order to not get stuck in walls
			rect.x-=speed*delta;
		}
	}
	
	protected void moveright(float delta) {
		if(!map.check(rect.x+rect.width+speed*delta, rect.y+rect.height) &&!map.check(rect.x+rect.width+speed*delta, rect.y) ){
			rect.x+=speed*delta;
		}
	}
	
	protected void fall(float delta) {
		if(!map.check(rect.x, rect.y-velocityY*delta) && !map.check(rect.x+rect.width, rect.y-velocityY*delta)) {
			rect.y-=velocityY*delta;
			velocityY+=map.getGravity()*delta;
		}else {
			velocityY=0;
		}
	}
	
	protected void jump() {
		if(Math.abs(velocityY)<10) {
			velocityY=-speed;
		}
	}

	public void step(float delta) {
		super.step(delta);
		fall(delta);
	}
}
