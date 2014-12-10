package edu.bsu.dachristman.components;

public enum TypeMask {

	WALL(0x00000), //
	TRAP(0x01000), //
	REDBALL(0x11001), //
	BLUEBALL(0x11010), //
	GREENBALL(0x11100), //
	REDHUB(0x00001), //
	BLUEHUB(0x00010), //
	GREENHUB(0x00100);

	private final int mask;

	private TypeMask(int mask) {
		this.mask = mask;
	}

	public int getMask() {
		return mask;
	}

}
