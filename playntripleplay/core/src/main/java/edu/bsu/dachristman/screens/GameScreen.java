package edu.bsu.dachristman.screens;

import static playn.core.PlayN.*;
import edu.bsu.dachristman.components.Blocks;
import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.components.Kat;
import edu.bsu.dachristman.components.Lazor;
import edu.bsu.dachristman.components.Walls;
import edu.bsu.dachristman.systems.InputSystem;
import edu.bsu.dachristman.systems.World;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;

public class GameScreen extends UIAnimScreen {

	private static final float SCALING_FACTOR = 1.0f / 32.0f;

//	private ScreenStack screenStack;
	private World world;
	private Blocks blocks;
	private Lazor lazor;
	private Kat kat;

	public GameScreen(ScreenStack screenStack) {
//		this.screenStack = screenStack;
		createBackground();
		world = new World();
		new Walls(world, SCALING_FACTOR);
		blocks = new Blocks(world);
		lazor = new Lazor(world);
		kat = new Kat(world, lazor.getEntity());
		new InputSystem(lazor);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		world.update(delta);
		blocks.update(delta);
		kat.update(delta);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (world != null) {
			world.paint(clock);
		}
	}

	private void createBackground() {
		ImageLayer imageLayer = graphics().createImageLayer(Images.BACKGROUNDMAIN.getImage());
		imageLayer.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(imageLayer);
	}

}
