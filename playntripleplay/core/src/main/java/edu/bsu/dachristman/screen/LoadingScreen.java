package edu.bsu.dachristman.screen;

import edu.bsu.dachristman.backend.ImageLoader;
import edu.bsu.dachristman.core.ProgramMain;
import edu.bsu.dachristman.old.GameScreen;
import pythagoras.f.Point;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.util.Colors;
import static playn.core.PlayN.graphics;
import playn.core.Pointer.Event;
import playn.core.util.Clock;

public class LoadingScreen extends CustomScreen {

	private final Label label = new Label("");
	private boolean ready = false;

	public LoadingScreen() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer).setSize(graphics().width(),
				graphics().height());
		label.setStyles(Style.COLOR.is(Colors.WHITE));
		root.add(AbsoluteLayout.at(label, new Point(10, 10)));
		ProgramMain.screenStack.push(new GameScreen());
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		if (ImageLoader.loaded()) {
			label.text.update("All Images Loaded. Click to Continue");
			ready = true;
		} else {
			label.text.update("Loading Images... Please Wait");
			if (!ImageLoader.initialized())
				ImageLoader.initialize();
		}
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
	}

	@Override
	public void onClickDown(Event e) {
		if (ready){
			ProgramMain.changeScreen(new MenuScreen());
		}
	}

	@Override
	public void onClickUp(Event e) {
		
	}

	@Override
	public void onPressDown(playn.core.Keyboard.Event e) {
		
	}

	@Override
	public void onPressUp(playn.core.Keyboard.Event e) {
		
	}

}