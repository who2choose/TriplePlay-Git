package edu.bsu.dachristman.screens;

import static playn.core.PlayN.*;
import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.systems.GravinationWorld;
import edu.bsu.dachristman.systems.HUD;
import edu.bsu.dachristman.systems.Level;
import edu.bsu.dachristman.systems.SessionManager;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import pythagoras.f.Point;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Colors;

public class GameScreen extends UIAnimScreen {

	private boolean paused = true;
	private Label gamePausedLabel1;
	private Label gamePausedLabel2;
	private final GravinationWorld world;
	private Level currentLevel;
	private HUD hud;
	private SessionManager sessionManager;

	public GameScreen(GravinationWorld world, HUD hud,
			SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.hud = hud;
		this.world = world;
		createBackground();
		initializeHUD();
	}

	@Override
	public void wasRemoved() {
		hud.resetHUD();
		super.wasRemoved();
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (!sessionManager.isLevelLoaded()) {
			if (currentLevel.loaded()) {
				sessionManager.setLevelLoadedStatus(true);
			}
		} else if (world != null && !paused) {
			world.update(delta);
			hud.updateTimeLabel(delta);
		}
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (world != null) {
			world.paint(clock);
		}
	}

	public void newGame(Level newLevel) {
		createBackground();
		currentLevel = newLevel;
		hud.setLevel(newLevel);
	}

	private void createBackground() {
		ImageLayer imageLayer = graphics().createImageLayer(
				Images.BACKGROUNDHOME.getImage());
		imageLayer.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(imageLayer);
	}

	private void initializeHUD() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer).setSize(graphics().width(),
				graphics().height());
		gamePausedLabel1 = new Label("GAME PAUSED");
		gamePausedLabel1.setStyles(Style.COLOR.is(Colors.RED));
		root.add(AbsoluteLayout.at(gamePausedLabel1, new Point(130, 485)));
		gamePausedLabel2 = new Label("GAME PAUSED");
		gamePausedLabel2.setStyles(Style.COLOR.is(Colors.RED));
		root.add(AbsoluteLayout.at(gamePausedLabel2, new Point(400, 485)));
		hud.createHUD(iface, layer);
	}

	public void pauseUnpause() {
		if (paused) {
			gamePausedLabel1.setVisible(false);
			gamePausedLabel2.setVisible(false);
			paused = false;
		} else {
			gamePausedLabel1.setVisible(true);
			gamePausedLabel2.setVisible(true);
			paused = true;
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public float getScore() {
		return hud.getScore();
	}

	public void gameOver(String message) {
		paused = true;
		sessionManager.setGameOverStatus(true);
		gamePausedLabel1.text.update(message);
		gamePausedLabel1.setVisible(true);
		gamePausedLabel2.text.update(message);
		gamePausedLabel2.setVisible(true);
	}

	public void resetGame() {
		world.update(0);
		sessionManager.setLevelLoadedStatus(false);
		sessionManager.setGameOverStatus(false);
		gamePausedLabel1.text.update("GAME PAUSED");
		gamePausedLabel2.text.update("GAME PAUSED");
		paused = true;
		world.reset();
	}

}
