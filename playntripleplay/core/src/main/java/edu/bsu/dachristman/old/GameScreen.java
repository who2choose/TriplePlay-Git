package edu.bsu.dachristman.old;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.core.SystemWorld;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.Keyboard.TypedEvent;
import playn.core.PlayN;
import playn.core.util.Clock;
import pythagoras.f.Point;
import react.ValueView.Listener;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Colors;

public class GameScreen extends UIAnimScreen {

	private ScreenStack screenStack;
	private Label scoreLabel;

	private boolean gameOver = false;

	private boolean keyIsDown = false;

	private SystemWorld world = new SystemWorld();

	public GameScreen() {
		createHUD();
		createBall();
		createBall2();
		setKeyboardListener();
		layer.add(world.layer);
	}

	private void createBall() {
		world.addBall(0, 300);
	}

	private void createBall2() {
		world.addBall(300, 400, 100, 100);
	}

	private void setKeyboardListener() {
		PlayN.keyboard().setListener(new Keyboard.Listener() {

			@Override
			public void onKeyDown(Event event) {
				switch (event.key()) {
				case UP:
					world.move(0, -10, 1);
					break;
				case DOWN:
					world.move(0, 10, 1);
					break;
				case LEFT:
					world.move(-10, 0, 1);
					break;
				case RIGHT:
					world.move(10, 0, 1);
					break;
				default:
					break;
				}
			}

			@Override
			public void onKeyTyped(TypedEvent event) {
			}

			@Override
			public void onKeyUp(Event event) {
				keyIsDown = false;
			}

		});
	}

	private void createHUD() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer)//
				.setSize(graphics().width(), graphics().height());
		scoreLabel = new Label("Score: " + world.score.get());
		scoreLabel.setStyles(Style.COLOR.is(Colors.WHITE));
		world.score.connect(new Listener<Float>() {

			@Override
			public void onChange(Float value, Float oldValue) {
				String rounded = "Score : " + value.intValue();
				if (world.isGameOver()) {
					rounded = "Final Score : " + value.intValue();
				}
				scoreLabel.text.update(rounded);
			}

		});
		root.add(AbsoluteLayout.at(scoreLabel, new Point(
				graphics().width() / 2, 15)));
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		world.update(delta);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		world.paint(clock);
	}

}
