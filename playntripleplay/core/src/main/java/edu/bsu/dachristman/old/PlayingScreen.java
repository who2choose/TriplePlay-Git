package edu.bsu.dachristman.old;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

import java.util.List;

import com.google.common.collect.Lists;

import edu.bsu.dachristman.core.Console;
import edu.bsu.dachristman.core.World;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Pointer.Event;
import playn.core.util.Clock;
import pythagoras.f.Point;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.layout.AbsoluteLayout;

public class PlayingScreen extends UIAnimScreen {

	private List<Entity> entities = Lists.newArrayList();
	private List<Entity> toDelete = Lists.newArrayList();
	private final World world = new World();
	private Entity target = new Target(world);
	private final Label scoreLabel = new Label("Score: " + world.score.get());
	private final Label lifeLabel = new Label("Lives: " + world.lives.get());
	private final Label consoleLabel = new Label("");
	private final Console console = new Console(5);
	private boolean playable = true;
	private int numBalls = 0;

	public PlayingScreen() {
		createBG();
		configureListener();
		createHUD();
		setNewTarget();
	}

	private void createBG() {
		Image bgImage = assets().getImage("images/bg.png");
		ImageLayer bgLayer = graphics().createImageLayer(bgImage);
		graphics().rootLayer().add(bgLayer);
	}

	private void configureListener() {
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Event event) {
				addBallAt(event.x(), event.y());
			}
		});
	}

	private void addBallAt(float x, float y) {
		if (playable) {
			entities.add(new Ball(x, y, world, ++numBalls));
		}
	}

	private void createHUD() {
		Root root = iface.createRoot(//
				new AbsoluteLayout(), SimpleStyles.newSheet(), layer)//
				.setSize(world.right(), world.bottom());
		root.add(AbsoluteLayout.at(lifeLabel,//
				new Point(world.left() + 10, world.top() + 10)));
		root.add(AbsoluteLayout.at(scoreLabel,//
				new Point(world.right() - 100, world.top() + 10)));
		root.add(AbsoluteLayout.at(consoleLabel,//
				new Point(world.left() + 10,//
						world.bottom() - console.textSize())));
	}

	private void setNewTarget() {
		target.clear();
		target = new Target(world.randX(), world.randY(), world,
				world.score.get());
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		updateEntites(delta);
		detectCollision();
		removeEntities();
		updateLabels();
	}

	private void updateEntites(int delta) {
		if (playable) {
			for (Entity e : entities) {
				e.update(delta);
			}
		}
	}

	private void detectCollision() {
		for (Entity e : entities) {
			if (e.offMap(world.right(), world.bottom())) {
				toDelete.add(e);
				console.addMinusLifeMessage(e, "off map");
				updateLives(-1);
				continue;
			}
			if (e.connectsWith(target)) {
				updateScore(1);
				setNewTarget();
				console.addPlusScoreMessage(e, target);
				toDelete.add(e);
			}
		}
	}

	private void updateLives(int i) {
		world.loseLife(i);
		checkGameOver();
	}

	private void checkGameOver() {
		if (world.lives.get() == 0) {
			startNewGame();
		}
	}

	private void startNewGame() {
		playable = false;
		console.addGameOverMessage(world.score.get(), numBalls);
		final ImageLayer gameOver = graphics().createImageLayer(
				assets().getImage("images/gameOver.png"));
		graphics().rootLayer().add(gameOver);
		gameOver.setTranslation(0, 0);

		anim.tweenTranslation(gameOver)//
				.to(70, world.bottom() / 2).in(1000).easeIn()//
				.then().tweenScale(gameOver).to(5f).in(1000)//
				.then().shake(gameOver)//
				.then().action(new Runnable() {
					@Override
					public void run() {
						setNewTarget();
						world.reset();
						toDelete.addAll(entities);
						entities.clear();
						playable = true;
						gameOver.destroy();
						numBalls = 0;
					}
				});
	}

	private void updateScore(int i) {
		world.addScore(i);
	}

	private void removeEntities() {
		for (Entity d : toDelete) {
			entities.remove(d);
			d.clear();
		}
		toDelete.clear();
	}

	private void updateLabels() {
		lifeLabel.text.update("Lives : " + world.lives.get());
		scoreLabel.text.update("Score : " + world.score.get());
		consoleLabel.text.update(console.text());
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		paintEntites();
	}

	private void paintEntites() {
		for (Entity e : entities) {
			e.draw();
		}
		target.draw();
	}

}