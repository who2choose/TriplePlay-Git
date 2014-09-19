package edu.bsu.dachristman.old;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import playn.core.Image;
import playn.core.ImageLayer;

import edu.bsu.dachristman.core.World;

public class Ball extends Entity {

	private final int WIDTH = 32, HEIGHT = 32;
	private float x, y, vx, vy, ax, ay;
	public ImageLayer layer;
	public static final Image IMAGE = assets()//
			.getImage("images/shapeBallRed.png");
	private World world;
	private int number;

	public Ball(float x, float y, World world, int number) {
		super(x, y);
		this.world = world;

		layer = graphics().createImageLayer(IMAGE);
		this.x = 1;
		this.y = graphics().height() - (HEIGHT + 1);

		vx = x;
		vy = -(graphics().height() - y);

		ax = 0;
		ay = this.world.gravity();
		
		this.number = number;
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

	public boolean offMap(int width, int height) {
		if (x < 0) {
			return true;
		} else if (x > width || y > height) {
			return true;
		}
		return false;
	}

	public void clear() {
		layer.destroy();
	}

	public boolean connectsWith(Entity w) {
		if ((x + WIDTH) >= w.getX() && (x <= w.getX() + w.getWidth())) {
			if ((y + HEIGHT) >= w.getY() && y <= (w.getY() + w.getHeight())) {
				return true;
			}
		}
		return false;
	}

	public void changeDirection(Entity w) {
		float angle = angle((x + WIDTH / 2), (y + HEIGHT / 2), w.centerX(),
				w.centerY());

		if (angle > 45 || angle < -45)
			vy = -vy;
		else
			vx = -vx;
	}

	private float angle(float c1, float c2, float c3, float c4) {
		return (float) ((Math.atan((c4 - c2) / (c3 - c1))) * radians());
	}

	private double radians() {
		return 180 / Math.PI;
	}

	public float getHeight() {
		return HEIGHT;
	}

	public float getWidth() {
		return WIDTH;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Type type() {
		return Type.BALL;
	}
	
	public int number() {
		return number;
	}
}