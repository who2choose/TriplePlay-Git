package edu.bsu.dachristman.core;

import react.Value;

public class World {

	public final int maxLives = 3;
	public final Value<Integer> score = Value.create(0),//
			lives = Value.create(maxLives);

	public float gravity() {
		return 200;
	}

	public static int top() {
		return 0;
	}

	public static int left() {
		return 0;
	}

	public static int right() {
		return 640;
	}

	public static int bottom() {
		return 480;
	}

	public void addScore(int value) {
		score.update(score.get() + value);
	}

	public float randX() {
		return (float) (Math.random() * right());
	}

	public float randY() {
		return (float) (Math.random() * bottom());
	}

	public void loseLife(int value) {
		lives.update(lives.get() + value);
	}

	public void reset() {
		lives.update(maxLives);
		score.update(0);
	}

}
