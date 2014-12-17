package edu.bsu.dachristman.components;

import org.jbox2d.dynamics.BodyType;

import tripleplay.entity.Entity;
import edu.bsu.dachristman.systems.World;

public class Lazor {

	private Entity lazor;
	private World world;

	public Lazor(World world) {
		this.world = world;
		lazor = this.world.createNew().addEntityComponents().setXY(10, 5)//
				.createBodyDef(BodyType.STATIC).createFixtureDef()//
				.setShape(world.createCircle(0.25f))//
				.setSensor(true).createBody()//
				.setImage(Images.LAZOR.getImage()).setImageLayer().getEntity();
	}

	public void move(float x, float y) {
		world.setBodyLocation(lazor.id, x - 16, y - 16);
	}
	
	public Entity getEntity(){
		return lazor;
	}

}
