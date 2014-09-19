package edu.bsu.dachristman.core;

import static playn.core.PlayN.pointer;
import edu.bsu.dachristman.screen.CustomScreen;
import edu.bsu.dachristman.screen.LoadingScreen;
import playn.core.Game;
import playn.core.Keyboard;
import playn.core.Pointer;
import playn.core.Keyboard.TypedEvent;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class ProgramMain extends Game.Default {

	private static final int UPDATE_DELAY = 33;

	private final Clock.Source clock = new Clock.Source(UPDATE_DELAY);
	public final static ScreenStack screenStack = new ScreenStack();

	public ProgramMain() {
		super(UPDATE_DELAY);
		iniListeners();
	}

	private void iniListeners() {
		iniKeyboardListener();
		iniPointerListener();
	}

	private void iniKeyboardListener() {
		PlayN.keyboard().setListener(new Keyboard.Listener() {
			@Override
			public void onKeyDown(Keyboard.Event event) {
				((CustomScreen) screenStack.top()).onPressDown(event);
			}

			@Override
			public void onKeyTyped(TypedEvent event) {
				
			}

			@Override
			public void onKeyUp(Keyboard.Event event) {
				((CustomScreen) screenStack.top()).onPressUp(event);
			}
		});
	}

	private void iniPointerListener() {
		pointer().setListener(new Pointer.Adapter() {
			@Override
			public void onPointerStart(Pointer.Event event) {
				((CustomScreen) screenStack.top()).onClickDown(event);
			}

			@Override
			public void onPointerEnd(Pointer.Event event) {
				((CustomScreen) screenStack.top()).onClickUp(event);
			}

			@Override
			public void onPointerDrag(Pointer.Event event) {
			}

			@Override
			public void onPointerCancel(Pointer.Event event) {
			}
		});
	}

	@Override
	public void init() {
		screenStack.push(new LoadingScreen());
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

	public static void changeScreen(Screen screen) {
		screenStack.push(screen);
	}

}