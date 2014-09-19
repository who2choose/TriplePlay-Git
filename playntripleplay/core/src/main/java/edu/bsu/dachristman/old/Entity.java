package edu.bsu.dachristman.old;

import playn.core.Layer;

public abstract class Entity {

	public enum Type {
		BALL, TARGET
	}

	public Entity(float x, float y) {}

	public void update(int deltaMS) {}
	
	public Layer getLayer() {return null;}
	
	public void draw() {}
	public void clear() {}
	
	public void move(float dx, float dy) {}
	public void changeDirection(Entity w) {}

	public boolean connectsWith(Entity w) {return false;}
	public boolean offMap(int width, int height) {return false;}

	public float centerX() {return 0;}
	public float centerY() {return 0;}
	public float getX() {return 0;}
	public float getY() {return 0;}
	public float getHeight() {return 0;}
	public float getWidth() {return 0;}
	
	public Type type() {return null;}
	public int number(){return -1;}

}