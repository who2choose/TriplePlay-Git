package edu.bsu.dachristman.screen;

import static playn.core.PlayN.graphics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.entity.Direction;
import edu.bsu.dachristman.entity.Grid;
import playn.core.Layer;
import playn.core.Pointer.Event;

public class GridScreen extends CustomScreen {

	private World box2dWorld;
	private Grid grid;
	private edu.bsu.dachristman.entity.Character player;
	private CharacterController characterController;

	public GridScreen() {
		createWorld();
		createGrid();
		createPlayer();
	}

	private void createWorld() {
		box2dWorld = new World(new Vec2(0, 9.8f));
		box2dWorld.setWarmStarting(true);
		box2dWorld.setAutoClearForces(true);
	}

	private void createGrid() {
		grid = new Grid(this, box2dWorld);
	}

	private void createPlayer() {
		player = edu.bsu.dachristman.entity.Character.createIn(box2dWorld)//
				.at(graphics().width() / 2, graphics().height() / 2);
		layer.add(player.layer());
		characterController = new CharacterController(player);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		box2dWorld.step(delta / 1000f, 10, 10);
		grid.update(delta);
		characterController.update(delta);
	}

	@Override
	public void onClickDown(Event e) {

	}

	@Override
	public void onClickUp(Event e) {

	}

	@Override
	public void onPressDown(playn.core.Keyboard.Event e) {
		switch (e.key()) {
		case LEFT:
			characterController.setInputStateOn(Direction.LEFT);
			break;
		case RIGHT:
			characterController.setInputStateOn(Direction.RIGHT);
			break;
		case UP:
			characterController.setInputStateOn(Direction.UP);
			break;
		case DOWN:
			characterController.setInputStateOn(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	@Override
	public void onPressUp(playn.core.Keyboard.Event e) {
		switch (e.key()) {
		case LEFT:
			characterController.setInputStateOff(Direction.LEFT);
			break;
		case RIGHT:
			characterController.setInputStateOff(Direction.RIGHT);
			break;
		case UP:
			characterController.setInputStateOff(Direction.UP);
			break;
		case DOWN:
			characterController.setInputStateOff(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	public void add(Layer newlayer) {
		layer.add(newlayer);
	}

}
