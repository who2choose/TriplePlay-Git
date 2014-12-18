package edu.bsu.dachristman.components;

import org.jbox2d.dynamics.BodyType;

import playn.core.PlayN;
import tripleplay.util.Timer;
import edu.bsu.dachristman.systems.World;

public class Blocks {

	private World world;
	private float a = 0, b = 1, c = 0;
	private Timer timer;

	public Blocks(World world) {
		this.world = world;
		timer();
	}

	private void timer() {
		timer = new Timer();
		timer.atThenEvery(0, 3000, new Runnable() {
			@Override
			public void run() {
				int i = (int) (PlayN.random()*4.0);
				switch (i) {
				case 0:
					//nothing spawns
					break;
				case 1:
					spawn1();
					break;
				case 2:
					spawn2();
					break;
				case 3:
					spawn3();
					break;
				default:
					break;
				}
				System.out.println(i);
			}
		});
	}

	private void spawn1() {
		for (int i = 1; i < 14; i++) {
			switch (i) {
			case 2:
			case 3:
			case 4:
				break;
			default:
				spawn(i);
				break;
			}
		}
	}

	private void spawn2() {
		for (int i = 7; i < 14; i++) {
			spawn(i);
		}
	}
	
	private void spawn3() {
		for (int i = 1; i < 14; i++) {
			switch (i) {
			case 6:
			case 7:
				break;
			default:
				spawn(i);
				break;
			}
		}
	}

	private void spawn(int y) {
		world.createNew().addEntityComponents().addMotionComponents()
				.setXY(20, y).createBodyDef(BodyType.STATIC).createFixtureDef()
				.setShape(world.createPolygon(0.5f, 0.5f))
				.setFixtureDef(a, b, c).createBody()
				.setImage(Images.BRICK.getImage()).setImageLayer().getEntity();
	}

	public void update(int deltaMS) {
		timer.update();
	}

}