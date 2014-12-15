package edu.bsu.dachristman.core;

import edu.bsu.dachristman.screens.StartScreen;
import playn.core.Game;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class ProgramMain extends Game.Default{

	private static final int UPDATE_DELAY = 33;
	private final Clock.Source clock = new Clock.Source(UPDATE_DELAY);
	private ScreenStack screenStack = new ScreenStack();
	
	public ProgramMain() {
		super(UPDATE_DELAY);
	}

	@Override
	public void init() {
		screenStack.push(new StartScreen(screenStack));
	}
	
	@Override
	public void update(int delta) {
		super.update(delta);
		clock.update(delta);
		screenStack.update(delta);
	}

	@Override
	public void paint(float alpha) {
		super.paint(alpha);
		clock.paint(alpha);
		screenStack.paint(clock);
	}

}
