package com.mygdx.game;

public interface MapWatcher {
	public boolean check(float x,float y);//returns true if the specified position has land 

	public float getGravity();
}
