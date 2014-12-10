package edu.bsu.dachristman.screens;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.systems.LevelData;
import playn.core.PlayN;
import react.Slot;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Button;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.layout.AxisLayout;

public class HighScoreInputScreen extends UIAnimScreen {

	private ScreenManager screenManager;
	private LevelData level;
	private float score;
	private HighScoreObject newHighScore;
	private HighScoreObject[] scores = new HighScoreObject[3];
	private Label firstPlace, secondPlace, thirdPlace;
	private Field inputField;

	public HighScoreInputScreen(int levelNumber, HighScoreScreen highScores,
			float score, ScreenManager screenManager) {
		this.level = highScores.getLevelData(levelNumber);
		this.score = score;
		this.screenManager = screenManager;
		determineScorePosition();
		createLayers();
	}

	private void determineScorePosition() {
		String separatedScores[] = level.highScores.trim().split(",");
		scores[0] = new HighScoreObject(separatedScores[0], 1);
		scores[1] = new HighScoreObject(separatedScores[1], 2);
		scores[2] = new HighScoreObject(separatedScores[2], 3);
		if (score >= scores[0].score) {
			scores[2] = scores[1];
			scores[1] = scores[0];
			newHighScore = new HighScoreObject(scores[0]);
			scores[2].position++;
			scores[1].position++;
		} else if (score >= scores[1].score) {
			scores[2] = scores[1];
			newHighScore = new HighScoreObject(scores[1]);
			scores[2].position++;
		} else {
			newHighScore = new HighScoreObject(scores[2]);
		}
		newHighScore.score = score;
	}

	private void createLayers() {
		Root root = iface.createRoot(AxisLayout.vertical().gap(20),
				SimpleStyles.newSheet(), layer).setSize(graphics().width(),
				graphics().height());
		root.add(new Label("NEW HIGH SCORE!!!"));
		Group inputLayer = new Group(AxisLayout.horizontal());
		inputLayer.add(new Label(newHighScore.position + ". "));
		inputField = new Field("Enter Name Here");
		inputLayer.add(inputField);
		inputLayer.add(new Label(" " + (int) (newHighScore.score)));
		firstPlace = new Label(scores[0].position + ". " + scores[0].name + " "
				+ (int) scores[0].score);
		secondPlace = new Label(scores[1].position + ". " + scores[1].name
				+ " " + (int) scores[1].score);
		thirdPlace = new Label(scores[2].position + ". " + scores[2].name + " "
				+ (int) scores[2].score);
		switch (newHighScore.position) {
		case 1:
			root.add(inputLayer);
			root.add(secondPlace);
			root.add(thirdPlace);
			break;
		case 2:
			root.add(firstPlace);
			root.add(inputLayer);
			root.add(thirdPlace);
			break;
		case 3:
			root.add(firstPlace);
			root.add(secondPlace);
			root.add(inputLayer);
		}

		Button submit = new Button("Submit");
		submit.onClick(new Slot<Button>() {

			@Override
			public void onEmit(Button event) {
				saveHighScore();
			}
		});
		root.add(submit);
	}

	protected void saveHighScore() {
		String lineToSave = level.levelNumber + ".";
		String nameInput = inputField.text.get();
		nameInput = nameInput.replaceAll("\\.|,", "");
		switch (newHighScore.position) {
		case 1:
			lineToSave += nameInput + "." + (int) newHighScore.score + ","
					+ scores[1].name + "." + (int) scores[1].score + ","
					+ scores[2].name + "." + (int) scores[2].score;
			break;
		case 2:
			lineToSave += scores[0].name + "." + (int) scores[0].score + ","
					+ nameInput + "." + (int) newHighScore.score + ","
					+ scores[2].name + "." + (int) scores[2].score;
			break;
		case 3:
			lineToSave += scores[0].name + "." + (int) scores[0].score + ","
					+ scores[2].name + "." + (int) scores[2].score + ","
					+ nameInput + "." + (int) newHighScore.score;
		}
		String finalScores = "";
		String levelHighScores[] = screenManager.getSaveData().split("\n");
		for (String levelScore : levelHighScores) {
			if (levelScore.substring(0, levelScore.indexOf('.')).equals(
					level.levelNumber)) {
				levelScore = lineToSave;
			}
			finalScores += levelScore + "\n";
		}
		PlayN.storage().setItem(ScreenManager.saveDataKey, finalScores);
		screenManager.updateHighScores();
		screenManager.screenToStart();
	}

	private class HighScoreObject {
		private int position;
		private float score;
		private String name;

		public HighScoreObject(String highScores, int position) {
			String sections[] = highScores.split("     |\n");
			this.name = sections[0];
			this.score = Float.parseFloat(sections[1]);
			this.position = position;
		}

		public HighScoreObject(HighScoreObject score) {
			this.name = score.name;
			this.score = score.score;
			this.position = score.position;
		}
	}

}
