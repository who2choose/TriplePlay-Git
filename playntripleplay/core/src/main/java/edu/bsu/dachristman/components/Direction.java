package edu.bsu.dachristman.components;

import org.jbox2d.common.Vec2;

public enum Direction {

	LEFT(-1, 0), //
	RIGHT(1, 0), //
	UP(0, -1), //
	DOWN(0, 1), //
	NONE(0, 0);

	private float gravity = 2f;
	private Vec2 vector;

	private Direction(float x, float y) {
		vector = new Vec2((x), (y));
	}

	public Vec2 vectorOfGravity() {
		return new Vec2(vector.x * gravity, vector.y * gravity);
	}

}