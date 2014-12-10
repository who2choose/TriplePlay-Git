package edu.bsu.dachristman.core;

import static playn.core.PlayN.pointer;

import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.components.Direction;
import edu.bsu.dachristman.screens.ScreenManager;
import edu.bsu.dachristman.systems.GravinationWorld;
import edu.bsu.dachristman.systems.HUD;
import edu.bsu.dachristman.systems.InputSystem;
import edu.bsu.dachristman.systems.SessionManager;
import edu.bsu.dachristman.systems.SoundManager;
import playn.core.Game;
import playn.core.Keyboard;
import playn.core.Pointer;
import playn.core.Keyboard.TypedEvent;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class ProgramMain extends Game.Default {

	private static final int UPDATE_DELAY = 33;

	private final Clock.Source clock = new Clock.Source(UPDATE_DELAY);

	private String highScoreData;
	private boolean dataLoaded = false;

	private ScreenManager screenManager;
	private World physicsWorld;
	private GravinationWorld world;
	private InputSystem inputSystem;
	private HUD hud;
	private SoundManager soundManager;
	private SessionManager sessionManager;

	public ProgramMain() {
		super(UPDATE_DELAY);
	}

	@Override
	public void init() {
		initializeAll();
		getScoresData();
		getCreditsData();
		getRulesData();
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		clock.update(delta);
		if (dataLoaded)
			screenManager.updateScreenStack(delta);
	}

	@Override
	public void paint(float alpha) {
		super.paint(alpha);
		clock.paint(alpha);
		if (dataLoaded)
			screenManager.paintScreenStack(clock);
	}

	private void initializeAll() {
		sessionManager = new SessionManager();
		soundManager = new SoundManager();
		hud = new HUD();
		physicsWorld = new World(Direction.NONE.vectorOfGravity());
		world = new GravinationWorld(physicsWorld, hud, soundManager,
				sessionManager);
		screenManager = new ScreenManager(world, hud, soundManager,
				sessionManager);
		inputSystem = new InputSystem(physicsWorld, screenManager, hud,
				sessionManager);
		inputSystem.setListeners();
		dataLoaded = true;
	}

	private void getScoresData() {
		highScoreData = PlayN.storage().getItem(ScreenManager.saveDataKey);
		if (highScoreData == null) {
			PlayN.assets().getText("text/highScoreList.txt",
					new Callback<String>() {
						@Override
						public void onSuccess(String result) {
							screenManager.setHighScoreData(result);
							PlayN.storage().setItem(ScreenManager.saveDataKey,
									result);
						}

						@Override
						public void onFailure(Throwable cause) {
							screenManager.setHighScoreData(PlayN.storage()
									.getItem(ScreenManager.saveDataKey));
						}
					});
		} else {
			screenManager.setHighScoreData(PlayN.storage().getItem(
					ScreenManager.saveDataKey));
		}
	}

	private void getCreditsData() {
		PlayN.assets().getText("text/credits.txt", new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				screenManager.setCreditsData(result);
			}

			@Override
			public void onFailure(Throwable cause) {
				screenManager.setCreditsData("");
			}
		});
	}

	private void getRulesData() {
		PlayN.assets().getText("text/rules.txt", new Callback<String>() {
			@Override
			public void onSuccess(String result) {
				screenManager.setRulesData(result);
			}

			@Override
			public void onFailure(Throwable cause) {
				screenManager.setRulesData("");
			}
		});
	}

}