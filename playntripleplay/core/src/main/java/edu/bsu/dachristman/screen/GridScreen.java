package edu.bsu.dachristman.screen;

import edu.bsu.dachristman.entity.Grid;
import edu.bsu.dachristman.entity.Player;
import playn.core.Pointer.Event;
import playn.core.util.Clock;

public class GridScreen extends CustomScreen {
	
	private Grid grid;
	private Player player;
	
	public GridScreen(){
		grid = new Grid();
		player = new Player();
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		player.update(delta);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		grid.draw();
		player.draw();
	}

	@Override
	public void onClickDown(Event e) {
//		grid = new Grid();
		player.jump();
	}

	@Override
	public void onClickUp(Event e) {

	}

	@Override
	public void onPressDown(playn.core.Keyboard.Event e) {

	}

	@Override
	public void onPressUp(playn.core.Keyboard.Event e) {

	}

}
