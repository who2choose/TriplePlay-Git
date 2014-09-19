package edu.bsu.dachristman.screen;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.backend.ImageLoader;
import edu.bsu.dachristman.core.World;
import playn.core.ImageLayer;
import playn.core.Pointer.Event;
import playn.core.util.Clock;
import pythagoras.f.Point;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.layout.AbsoluteLayout;

public class MenuScreen extends CustomScreen {

	private static final String COPYRIGHT = "DACHRISTMAN@BSU.EDU\n";
	private int number = 0;
	private final Label label = new Label(COPYRIGHT + number);

	public MenuScreen() {
		createBG();
		createHUD();
	}

	private void createBG() {
		ImageLayer bgLayer = graphics().createImageLayer(ImageLoader.getImage("bg"));
		graphics().rootLayer().add(bgLayer);
	}

	private void createHUD() {
		Root root = iface.createRoot(//
				new AbsoluteLayout(), SimpleStyles.newSheet(), layer)//
				.setSize(World.right(), World.bottom());
		root.add(AbsoluteLayout.at(label,//
				new Point(World.left() + 10, World.top() + 10)));
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		label.text.update(COPYRIGHT + number);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
	}

	@Override
	public void onClickDown(Event e) {
		number++;
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