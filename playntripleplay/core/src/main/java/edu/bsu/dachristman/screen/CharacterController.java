package edu.bsu.dachristman.screen;

import static com.google.common.base.Preconditions.checkNotNull;
import edu.bsu.dachristman.entity.Direction;

public final class CharacterController {

	private final edu.bsu.dachristman.entity.Character player;
//	private Direction inputState = Direction.NONE;
	private boolean RIGHT = false;
	private boolean LEFT = false;
	private boolean UP = false;
	private boolean DOWN = false;

	public CharacterController(edu.bsu.dachristman.entity.Character player) {
		this.player = checkNotNull(player);
	}

	public void setInputStateOn(Direction direction) {
		if (direction == Direction.RIGHT)
			RIGHT = true;
		if (direction == Direction.LEFT)
			LEFT = true;
		if (direction == Direction.UP)
			UP = true;
		if (direction == Direction.DOWN)
			DOWN = true;
	}

	public void setInputStateOff(Direction direction) {
		if (direction == Direction.RIGHT)
			RIGHT = false;
		if (direction == Direction.LEFT)
			LEFT = false;
		if (direction == Direction.UP)
			UP = false;
		if (direction == Direction.DOWN)
			DOWN = false;
	}

	public void update(int deltaMS) {
		if (LEFT)
			player.moveLeft(deltaMS);
		if (RIGHT)
			player.moveRight(deltaMS);
		if (UP)
			player.moveUp(deltaMS);
		if (DOWN)
			player.moveDown(deltaMS);
	}

}
