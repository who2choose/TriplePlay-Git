package edu.bsu.dachristman.screens;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.systems.SoundManager;
import playn.core.util.Clock;
import react.Slot;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

public class StartScreen extends UIScreen {

	private Root root;
	private final ScreenManager screenManager;
	private SoundManager soundManager;

	public StartScreen(ScreenManager screenManager, SoundManager soundManager) {
		this.soundManager = soundManager;
		this.screenManager = screenManager;
		createUIRoot();
		createButtons();
		this.soundManager.loopMusic();
	}

	private void createUIRoot() {
		root = iface.createRoot(new AxisLayout.Vertical().gap(40),
				SimpleStyles.newSheet(), layer);
		root.setBounds(0, 0, graphics().width(), graphics().height());
		root.addStyles(Style.BACKGROUND.is(Background.image((Images.HOME
				.getImage()))));
	}

	private void createButtons() {
		Button playButton = new Button("Play");
		Button highScoreButton = new Button("High Scores");
		Button creditsButton = new Button("Rules and Credits");
		root.add(playButton).add(highScoreButton).add(creditsButton);

		playButton.clicked().connect(new Slot<Button>() {
			@Override
			public void onEmit(Button event) {
				screenManager.screenToLevelSelect();
			}
		});

		highScoreButton.clicked().connect(new Slot<Button>() {
			@Override
			public void onEmit(Button event) {
				screenManager.screenToHighScore();
			}
		});

		creditsButton.clicked().connect(new Slot<Button>() {
			@Override
			public void onEmit(Button event) {
				screenManager.screenToCredits();
			}
		});
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
	}
}
