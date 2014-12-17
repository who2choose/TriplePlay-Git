package edu.bsu.dachristman.components;

import org.jbox2d.dynamics.BodyType;

import tripleplay.util.Timer;
import edu.bsu.dachristman.systems.World;

public class Blocks {

	private World world;
	private float a = 0, b = 1, c = 0;
	private Timer timer;

	public Blocks(World world) {
		this.world = world;
		spawn();
		timer();
	}

	private void timer() {
		timer = new Timer();
		timer.atThenEvery(0, 3000, new Runnable() {
			@Override
			public void run() {
				spawn();
				System.out.println("spawn");
			}
		});
	}

	private void spawn() {
		world.createNew().addEntityComponents().addMotionComponents()
				.setXY(20, 13).createBodyDef(BodyType.STATIC).createFixtureDef()
				.setShape(world.createPolygon(0.5f, 0.5f))
				.setFixtureDef(a, b, c).createBody()
				.setImage(Images.BRICK.getImage()).setImageLayer().getEntity();
	}
	
	public void update(int deltaMS) {
		timer.update();
	}

}