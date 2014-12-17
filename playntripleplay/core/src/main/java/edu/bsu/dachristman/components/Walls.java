package edu.bsu.dachristman.components;

import org.jbox2d.dynamics.BodyType;

import edu.bsu.dachristman.systems.World;

public class Walls {

	private float a = 0, b = 1, c = 0;
	
	public Walls(World world, float SCALING_FACTOR) {
		for (int i = 0; i < 20; i++) {
			world.createNew().addEntityComponents()
					.setXY(((i * 32) * SCALING_FACTOR), 0)
					.createBodyDef(BodyType.STATIC).createFixtureDef()
					.setShape(world.createPolygon(0.5f, 0.5f))
					.setFixtureDef(a, b, c).createBody()
					.setImage(Images.BRICK.getImage()).setImageLayer()
					.getEntity();
		}
		for (int i = 0; i < 20; i++) {
			world.createNew().addEntityComponents()
					.setXY(((i * 32) * SCALING_FACTOR), 14)
					.createBodyDef(BodyType.STATIC).createFixtureDef()
					.setShape(world.createPolygon(0.5f, 0.5f))
					.setFixtureDef(a, b, c).createBody()
					.setImage(Images.BRICK.getImage()).setImageLayer()
					.getEntity();
		}
		
		for (int i = 0; i < 14; i++) {
			world.createNew().addEntityComponents()
					.setXY(0, ((i * 32) * SCALING_FACTOR))
					.createBodyDef(BodyType.STATIC).createFixtureDef()
					.setShape(world.createPolygon(0.5f, 0.5f))
					.setFixtureDef(a, b, c).createBody()
					.setImage(Images.BRICK.getImage()).setImageLayer()
					.getEntity();
		}
		for (int i = 0; i < 14; i++) {
			world.createNew().addEntityComponents()
					.setXY(19, ((i * 32) * SCALING_FACTOR))
					.createBodyDef(BodyType.STATIC).createFixtureDef()
					.setShape(world.createPolygon(0.5f, 0.5f))
					.setFixtureDef(a, b, c).createBody()
					.setImage(Images.BRICK.getImage()).setImageLayer()
					.getEntity();
		}
	}

	
	
}
