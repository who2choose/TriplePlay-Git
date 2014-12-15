package edu.bsu.dachristman.screens;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.components.Images;
import playn.core.util.Clock;
import react.Slot;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

public class StartScreen extends UIScreen {

	private Root root;
	private ScreenStack screenStack;

	public StartScreen(ScreenStack screenStack) {
		this.screenStack = screenStack;
		createUIRoot();
		createButtons();
	}

	private void createUIRoot() {
		root = iface.createRoot(new AxisLayout.Vertical().gap(40), SimpleStyles.newSheet(), layer);
		root.setBounds(0, 0, graphics().width(), graphics().height());
		root.addStyles(Style.BACKGROUND.is(Background.image((Images.BACKGROUNDMAIN.getImage()))));
	}

	private void createButtons() {
		Button playButton = new Button("Play");
		root.add(playButton);

		playButton.clicked().connect(new Slot<Button>() {
			@Override
			public void onEmit(Button event) {
				screenStack.remove(screenStack.top());
				screenStack.push(new GameScreen(screenStack));
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
