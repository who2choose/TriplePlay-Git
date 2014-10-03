package edu.bsu.dachristman.entity;

import static com.google.common.base.Preconditions.checkNotNull;

public final class CharacterController {

	private final Player player;
	private boolean RIGHT = false;
	private boolean LEFT = false;

	public CharacterController(Player player) {
		this.player = checkNotNull(player);
	}

	public void setInputStateOn(Direction direction) {
		if (direction == Direction.RIGHT)
			RIGHT = true;
		if (direction == Direction.LEFT)
			LEFT = true;
		if (direction == Direction.JUMP)
			player.jump();
	}

	public void setInputStateOff(Direction direction) {
		if (direction == Direction.RIGHT)
			RIGHT = false;
		if (direction == Direction.LEFT)
			LEFT = false;
	}

	public void update(int deltaMS) {
		if (LEFT)
			player.moveLeft(deltaMS);
		if (RIGHT)
			player.moveRight(deltaMS);
		if ((!LEFT && !RIGHT) || (LEFT && RIGHT))
			player.moveNone(deltaMS);
	}

}
