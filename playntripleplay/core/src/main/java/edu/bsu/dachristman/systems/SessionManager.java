package edu.bsu.dachristman.systems;

public class SessionManager {

	private boolean levelLoaded;
	private boolean gameOver;

	public SessionManager() {
		levelLoaded = false;
		gameOver = false;
	}

	public boolean isLevelLoaded() {
		return levelLoaded;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setLevelLoadedStatus(boolean status) {
		levelLoaded = status;
	}

	public void setGameOverStatus(boolean status) {
		gameOver = status;
	}
}
