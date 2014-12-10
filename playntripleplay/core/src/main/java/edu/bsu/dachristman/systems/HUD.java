package edu.bsu.dachristman.systems;

import static playn.core.PlayN.graphics;
import playn.core.GroupLayer;
import pythagoras.f.Point;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.layout.AbsoluteLayout;

public class HUD {

	private static final float baseScore = 100f;
	private static final float pointsLostBall = -5f;
	private static final float estimatedTimeS = 5000f;

	private Label scoreLabel, inputsLabel, timeLabel;
	private float timeMS, score;
	private int inputNumber;
	private Level currentLevel;

	public void setLevel(Level newLevel) {
		currentLevel = newLevel;
	}

	public void createHUD(Interface iface, GroupLayer layer) {
		score = baseScore;
		scoreLabel = new Label("Score: " + (int) score);
		timeMS = 0;
		timeLabel = new Label("Time: " + (timeMS / 1000));
		inputNumber = 0;
		inputsLabel = new Label("Input #: " + inputNumber);
		Root UIRoot = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer).setSize(graphics().width(),
				graphics().height());
		UIRoot.add(AbsoluteLayout.at(scoreLabel, new Point(10, 485)))
				.add(AbsoluteLayout.at(timeLabel, new Point(290, 485)))
				.add(AbsoluteLayout.at(inputsLabel, new Point(550, 485)));
	}

	public void updateTimeLabel(int deltaMS) {
		timeMS += deltaMS;
		timeLabel.text.update("Time: " + (timeMS / 1000));
		changeScoreBy((timeMS > estimatedTimeS) ? -deltaMS / 1000f : 0);
		if (score <= 0) {
			currentLevel.gameOver();
		}
	}

	public void changeInputBy(int amount) {
		inputNumber += amount;
		inputsLabel.text.update("Input #: " + inputNumber);
	}

	public void changeScoreOnBallReset() {
		changeScoreBy(pointsLostBall);
	}

	public void changeScoreBy(float amount) {
		score += amount;
		scoreLabel.text.update("Score: " + (int) score);
	}

	public void resetHUD() {
		score = baseScore;
		scoreLabel.text.update("Score: " + (int) score);
		timeMS = 0;
		timeLabel.text.update("Time: " + (timeMS / 1000));
		inputNumber = 0;
		inputsLabel.text.update("Input #: " + inputNumber);
	}

	public float getScore() {
		return score;
	}

}