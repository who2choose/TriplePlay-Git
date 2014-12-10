package edu.bsu.dachristman.screens;

import edu.bsu.dachristman.systems.GravinationWorld;
import edu.bsu.dachristman.systems.HUD;
import edu.bsu.dachristman.systems.SessionManager;
import edu.bsu.dachristman.systems.SoundManager;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class ScreenManager {

	public static final String saveDataKey = "SAVE_DATA";

	private String highScores;
	private ScreenStack screenStack = new ScreenStack();

	private final StartScreen screenStart;
	private final LevelSelectScreen screenLevelSelect;
	private final GameScreen screenGame;
	private final CreditsScreen screenCredits;
	private HighScoreScreen screenHighScore;
	private HighScoreInputScreen screenHighScoreInput;
	private Screen activeScreen;

	public ScreenManager(GravinationWorld world, HUD hud,
			SoundManager soundManager, SessionManager sessionManager) {
		screenStart = new StartScreen(this, soundManager);
		screenLevelSelect = new LevelSelectScreen(this, world);
		screenGame = new GameScreen(world, hud, sessionManager);
		screenCredits = new CreditsScreen();
		screenStack.push(screenStart);
		activeScreen = screenStart;
	}

	public void setHighScoreData(String data) {
		highScores = data;
		screenHighScore = new HighScoreScreen(this);
	}

	public void setCreditsData(String data) {
		screenCredits.createCreditsCanvas(data);
	}

	public void setRulesData(String data) {
		screenCredits.createRulesCanvas(data);
	}

	public void paintScreenStack(Clock clock) {
		screenStack.paint(clock);
	}

	public void updateScreenStack(int delta) {
		screenStack.update(delta);
	}

	public void screenToStart() {
		clearScreenStack();
		screenStack.push(screenStart);
		activeScreen = screenStart;
	}

	public void screenToLevelSelect() {
		clearScreenStack();
		screenLevelSelect.refresh();
		screenStack.push(screenLevelSelect);
		activeScreen = screenLevelSelect;
	}

	public void screenToGame() {
		clearScreenStack();
		screenStack.push(screenGame);
		activeScreen = screenGame;
	}

	public void screenToCredits() {
		clearScreenStack();
		screenCredits.refresh();
		screenStack.push(screenCredits);
		activeScreen = screenCredits;
	}

	public void screenToHighScore() {
		clearScreenStack();
		screenHighScore.refresh();
		screenStack.push(screenHighScore);
		activeScreen = screenHighScore;
	}

	public void screenToHighScoreInput(int levelNumber, float score) {
		clearScreenStack();
		screenHighScoreInput = new HighScoreInputScreen(levelNumber,
				screenHighScore, score, this);
		screenStack.push(screenHighScoreInput);
		activeScreen = screenHighScoreInput;
	}

	public StartScreen getStartScreen() {
		return screenStart;
	}

	public LevelSelectScreen getLevelSelectScreen() {
		return screenLevelSelect;
	}

	public GameScreen getGameScreen() {
		return screenGame;
	}

	public CreditsScreen getCreditsScreen() {
		return screenCredits;
	}

	public HighScoreScreen getHighScoreScreen() {
		return screenHighScore;
	}

	public String getSaveData() {
		return highScores;
	}

	public boolean isActiveScreen(Screen screen) {
		return activeScreen.equals(screen);
	}

	public void updateHighScores() {
		highScores = PlayN.storage().getItem(saveDataKey);
		screenHighScore = new HighScoreScreen(this);
	}

	private void clearScreenStack() {
		while (screenStack.top() != null) {
			screenStack.remove(screenStack.top());
		}
	}

}
