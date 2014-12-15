package edu.bsu.dachristman.screens;

import static playn.core.PlayN.*;

import org.jbox2d.dynamics.BodyType;

import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.components.Kat;
import edu.bsu.dachristman.components.Lazor;
import edu.bsu.dachristman.systems.InputSystem;
import edu.bsu.dachristman.systems.World;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import tripleplay.entity.Entity;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;

public class GameScreen extends UIAnimScreen {

	private static final float SCALING_FACTOR = 1.0f / 32.0f;

	private ScreenStack screenStack;
	private World world;
	private Kat kat;
	private Lazor lazor;

	public GameScreen(ScreenStack screenStack) {
		this.screenStack = screenStack;
		createBackground();
		world = new World();
		kat = new Kat(world);
		lazor = new Lazor(world);
		new InputSystem(kat, lazor);
		add();
	}
	
	private void add(){
		for (int i = 0; i < 20; i++) {
			Entity e = world.createNew().addEntityComponents()
					.setXY(((i * 32) * SCALING_FACTOR), 0)
					.createBodyDef(BodyType.STATIC).createFixtureDef()
					.setShape(world.createPolygon(0.5f, 0.5f))
					.setFixtureDef(0.5f, 0.5f, 0.5f).createBody()
					.setImage(Images.BRICK.getImage()).setImageLayer()
					.getEntity();
			System.out.println(e.id);
		}
		for (int i = 0; i < 20; i++) {
			Entity e = world.createNew().addEntityComponents()
					.setXY(((i * 32) * SCALING_FACTOR), 14)
					.createBodyDef(BodyType.STATIC).createFixtureDef()
					.setShape(world.createPolygon(0.5f, 0.5f))
					.setFixtureDef(0.5f, 0.5f, 0.5f).createBody()
					.setImage(Images.BRICK.getImage()).setImageLayer()
					.getEntity();
			System.out.println(e.id);
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		world.update(delta);
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
		ImageLayer imageLayer = graphics().createImageLayer(
				Images.BACKGROUNDMAIN.getImage());
		imageLayer.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(imageLayer);
	}

}
