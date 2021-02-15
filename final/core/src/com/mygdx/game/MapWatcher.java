package com.mygdx.game;

/**
 * Read-only interface-je a GameMap-nak.
 * 
 * A Character osztály alkalmazza
 * @author mark
 *
 */
public interface MapWatcher {
	/**
	 * ellenőrzi egy pozíció ürességét
	 * @param x a pozíció x koordinátája
	 * @param y a pozíció y koordinátája
	 * @return igaz, ha van ott valami (pontosabban föld, entitásokat nem ellenőriz)
	 */
	public boolean check(float x,float y);//returns true if the specified position has land 

	/**
	 * lekéri a gravitációt
	 * @return a gravitáció
	 */
	public float getGravity();
}
