package edu.bsu.dachristman.components;

import tripleplay.entity.Entity;
import edu.bsu.dachristman.systems.World;

public class Lazor {

	private Entity lazor;
	private World world;

	public Lazor(World world) {
		this.world = world;
		lazor = this.world.createNew().addPosImageComponents()//
				.setXY(10, 5).setImage(Images.LAZOR.getImage())//
				.setImageLayer().getEntity();
	}

	public void move(float x, float y) {
		world.setPosition(lazor.id, x - 16, y - 16);
	}

	public Entity getEntity() {
		return lazor;
	}

}
