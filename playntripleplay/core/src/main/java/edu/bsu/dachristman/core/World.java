package edu.bsu.dachristman.core;

public class World {

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

	public static float randNum(int range) {
		return (float) (Math.random() * range);
	}

}