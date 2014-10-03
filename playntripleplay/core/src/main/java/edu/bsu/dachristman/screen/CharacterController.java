package edu.bsu.dachristman.screen;

import static com.google.common.base.Preconditions.checkNotNull;
import edu.bsu.dachristman.entity.Direction;
import edu.bsu.dachristman.entity.Player;

public final class CharacterController {

	private final Player player;
	private boolean RIGHT = false;
	private boolean LEFT = false;

	// private boolean JUMP = false;

	public CharacterController(Player player) {
		this.player = checkNotNull(player);
	}

	public void setInputStateOn(Direction direction) {
		if (direction == Direction.RIGHT)
			RIGHT = true;
		if (direction == Direction.LEFT)
			LEFT = true;
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
	}

}
