package edu.bsu.dachristman.screen;

import static playn.core.PlayN.graphics;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import com.google.common.collect.Lists;

import edu.bsu.dachristman.core.Floor;
import edu.bsu.dachristman.entity.CharacterController;
import edu.bsu.dachristman.entity.Charlie;
import edu.bsu.dachristman.entity.Direction;
import edu.bsu.dachristman.entity.Grid;
import edu.bsu.dachristman.entity.Player;
import playn.core.Layer;
import playn.core.Pointer.Event;

public class GridScreen extends CustomScreen {

	public static World box2dWorld;
	private Grid grid;
	private Player player;
	private CharacterController characterController;
	private final float GRAVITY = 15f;

	private List<Charlie> charlies = Lists.newArrayList();

	public GridScreen() {
		createWorld();
		new Floor(box2dWorld);
		createGrid();
		createPlayer();
	}

	private void createWorld() {
		box2dWorld = new World(new Vec2(0, GRAVITY));
		box2dWorld.setWarmStarting(true);
		box2dWorld.setAutoClearForces(true);
	}

	private void createGrid() {
		grid = new Grid(this, box2dWorld);
	}

	private void createPlayer() {
		player = Player.createPlayer(box2dWorld).position(60, graphics().height() / 2);
		layer.add(player.layer);
		characterController = new CharacterController(player);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		box2dWorld.step(delta / 1000f, 10, 10);
		grid.update(delta);
		characterController.update(delta);

		for (Charlie charlie : charlies) {
			charlie.update(delta);
		}
	}

	@Override
	public void onClickDown(Event e) {
		Charlie charlie = Charlie.createCharlie(box2dWorld)//
				.position(e.x(), e.y());
		layer.add(charlie.layer);
		charlies.add(charlie);
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
		case SPACE:
			characterController.setInputStateOn(Direction.JUMP);
			break;
		case D:
			box2dWorld.setGravity(new Vec2(GRAVITY, 0));
			break;
		case W:
			box2dWorld.setGravity(new Vec2(0, -GRAVITY));
			break;
		case A:
			box2dWorld.setGravity(new Vec2(-GRAVITY, 0));
			break;
		case S:
			box2dWorld.setGravity(new Vec2(0, GRAVITY));
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
		default:
			break;
		}
	}

	public void add(Layer newlayer) {
		layer.add(newlayer);
	}

}
