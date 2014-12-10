package edu.bsu.dachristman.systems;

import static playn.core.PlayN.graphics;

import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.components.Direction;
import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.screens.ScreenManager;
import playn.core.ImageLayer;
import playn.core.PlayN;

public class InputSystem implements playn.core.Keyboard.Listener,
		playn.core.Pointer.Listener {

	private Direction direction = Direction.NONE;
	private World world;
	private final ScreenManager screenManager;
	private SessionManager sessionManager;
	private HUD hud;

	public InputSystem(World world, ScreenManager screenManager, HUD hud,
			SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.hud = hud;
		this.screenManager = screenManager;
		this.world = world;
	}

	public void setListeners() {
		PlayN.keyboard().setListener(this);
		PlayN.pointer().setListener(this);
	}

	public void destroy() {
		this.world = null;
		this.direction = null;
		PlayN.keyboard().setListener(null);
		PlayN.pointer().setListener(null);
	}

	@Override
	public void onKeyDown(playn.core.Keyboard.Event event) {
		switch (event.key()) {
		case W:
			if (!screenManager.getGameScreen().isPaused()) {
				if (direction == Direction.UP) {
					break;
				}
				setGravity(Direction.UP);
			}
			break;
		case A:
			if (!screenManager.getGameScreen().isPaused()) {
				if (direction == Direction.LEFT) {
					break;
				}
				setGravity(Direction.LEFT);
			}
			break;
		case S:
			if (!screenManager.getGameScreen().isPaused()) {
				if (direction == Direction.DOWN) {
					break;
				}
				setGravity(Direction.DOWN);
			}
			break;
		case D:
			if (!screenManager.getGameScreen().isPaused()) {
				if (direction == Direction.RIGHT) {
					break;
				}
				setGravity(Direction.RIGHT);
			}
			break;
		case Q:
			if (screenManager.isActiveScreen(screenManager.getGameScreen())) {
				if (sessionManager.isLevelLoaded()) {
					if (screenManager.getGameScreen().isPaused()) {
						screenManager.getGameScreen().gameOver("game quit");
						screenManager.getGameScreen().resetGame();
						screenManager.screenToStart();
					}
				}
			}
			break;
		case ESCAPE:
			if (screenManager.isActiveScreen(screenManager.getGameScreen())) {
				if (sessionManager.isLevelLoaded()) {
					if (!sessionManager.isGameOver()) {
						screenManager.getGameScreen().pauseUnpause();
					}
				}
			} else if (!screenManager.isActiveScreen(screenManager
					.getStartScreen())) {
				screenManager.screenToStart();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onKeyUp(playn.core.Keyboard.Event event) {

	}

	@Override
	public void onKeyTyped(playn.core.Keyboard.TypedEvent event) {

	}

	@Override
	public void onPointerStart(playn.core.Pointer.Event event) {

	}

	@Override
	public void onPointerEnd(playn.core.Pointer.Event event) {
		if (screenManager.isActiveScreen(screenManager.getGameScreen())) {
			if (sessionManager.isLevelLoaded()) {
				if (!sessionManager.isGameOver()) {
					screenManager.getGameScreen().pauseUnpause();
				} else {
					ImageLayer il = graphics().createImageLayer(
							Images.BACKGROUNDHOME.getImage());
					il.setSize(graphics().width(), graphics().height());
					graphics().rootLayer().add(il);
					setGravity(Direction.NONE);
					screenManager.getGameScreen().resetGame();
					int levelNumber = screenManager.getGameScreen()
							.getCurrentLevel().getLevelNumber();
					float score = screenManager.getGameScreen().getScore();
					if (newHighScore(levelNumber, score)) {
						screenManager
								.screenToHighScoreInput(levelNumber, score);
					} else {
						screenManager.screenToStart();
					}
				}
			}
		}
	}

	private boolean newHighScore(int levelNumber, float score) {
		LevelData levelData = screenManager.getHighScoreScreen().getLevelData(
				levelNumber);
		float scoreForThird = Float.parseFloat(levelData.highScores
				.substring(levelData.highScores.length() - 3));
		if (score > scoreForThird) {
			return true;
		}
		return false;
	}

	@Override
	public void onPointerDrag(playn.core.Pointer.Event event) {

	}

	@Override
	public void onPointerCancel(playn.core.Pointer.Event event) {

	}

	private void setGravity(Direction d) {
		if (screenManager.isActiveScreen(screenManager.getGameScreen())) {
			direction = d;
			world.setGravity(direction.vectorOfGravity());
			hud.changeInputBy(1);
		}
	}

}