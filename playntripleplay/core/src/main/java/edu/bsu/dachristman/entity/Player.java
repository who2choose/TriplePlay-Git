package edu.bsu.dachristman.entity;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.backend.ImageLoader;
import playn.core.Image;
import playn.core.ImageLayer;

public class Player {

	public ImageLayer layer;
	private float x, y, vx, vy, ax, ay;

	public Player() {
		layer = graphics().createImageLayer(
				ImageLoader.getImage("player").subImage(0, 0, 32, 32));

		x = 40;
		y = 300;

		vx = 0;
		vy = 0;

		ax = 0;
		ay = 50f;
	}

	public void draw() {
		graphics().rootLayer().add(layer);
	}

	public void update(int deltaMS) {
		vx += ax * deltaMS / 1000f;
		vy += ay * deltaMS / 1000f;
		x = x + vx * deltaMS / 1000f;
		y = y + vy * deltaMS / 1000f;

		layer.setTx(x);
		layer.setTy(y);
	}

	public boolean collision(int width, int height) {
		if (x < 0) {
			return true;
		} else if (x > width || y > height) {
			return true;
		}
		return false;
	}
	
	public void jump(){
		vy = -100;
	}

}
