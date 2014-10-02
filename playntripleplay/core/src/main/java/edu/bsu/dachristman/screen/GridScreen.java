package edu.bsu.dachristman.screen;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.entity.Grid;
import edu.bsu.dachristman.entity.Player;
import playn.core.Pointer.Event;
import playn.core.util.Clock;

public class GridScreen extends CustomScreen {
	
	private World box2dWorld;
	private Grid grid;
	private Player player;
	
	public GridScreen(){
		createWorld();
		grid = new Grid();
		player = new Player();
	}
	
	private void createWorld() {
		box2dWorld = new World(new Vec2(0, 9.8f));
		box2dWorld.setWarmStarting(true);
		box2dWorld.setAutoClearForces(true);
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
