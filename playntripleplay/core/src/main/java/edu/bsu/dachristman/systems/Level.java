package edu.bsu.dachristman.systems;

import static playn.core.PlayN.assets;
import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.components.TypeMask;
import edu.bsu.dachristman.screens.ScreenManager;
import playn.core.PlayN;
import playn.core.util.Callback;

public class Level {

	private final GravinationWorld world;
	private final ScreenManager screenManager;

	private int numberOfHUBs = 0;
	private int HUBsFilled = 0;
	private boolean loaded = false;
	private boolean gameOver = false;

	private int levelNumber;

	public Level(String levelFileLocation, GravinationWorld world,
			ScreenManager screenManager) {
		this.screenManager = screenManager;
		this.world = world;
		this.levelNumber = Integer.parseInt(levelFileLocation.substring(8,
				levelFileLocation.indexOf('.')));
		newLevel();
		createNewLevel(levelFileLocation);
	}

	public void newLevel() {
		numberOfHUBs = 0;
		HUBsFilled = 0;
		loaded = false;
		gameOver = false;
	}

	public int getLevelNumber() {
		return levelNumber;
	}

	public void createNewLevel(String levelFileLocation) {
		assets().getText(levelFileLocation, new Callback<String>() {

			@Override
			public void onSuccess(String result) {
				int j = 0;
				for (String s : result.split("\n")) {
					for (int i = 0; i < s.length(); i++) {
						add(s.charAt(i), //
								((i * 32) * world.getScalingFactor()),//
								((j * 32) * world.getScalingFactor()));
					}
					j++;
				}
			}

			@Override
			public void onFailure(Throwable cause) {
				PlayN.log().error("Level file failed to load! ", cause);
			}

		});
		loaded = true;
	}

	private void add(char c, float x, float y) {
		switch (c) {
		case '1':
			world.createBlock(x, y);
			break;
		case '2':
			world.createBall(x, y, Images.REDBALL, TypeMask.REDBALL);
			break;
		case '3':
			world.createBall(x, y, Images.BLUEBALL, TypeMask.BLUEBALL);
			break;
		case '4':
			world.createBall(x, y, Images.GREENBALL, TypeMask.GREENBALL);
			break;
		case '5':
			world.createHub(x, y, Images.REDHUB, TypeMask.REDHUB);
			numberOfHUBs++;
			break;
		case '6':
			world.createHub(x, y, Images.BLUEHUB, TypeMask.BLUEHUB);
			numberOfHUBs++;
			break;
		case '7':
			world.createHub(x, y, Images.GREENHUB, TypeMask.GREENHUB);
			numberOfHUBs++;
			break;
		case '^':
			world.createTrap(x, y, Images.TRAPUP);
			break;
		case 'v':
			world.createTrap(x, y, Images.TRAPDOWN);
			break;
		case '<':
			world.createTrap(x, y, Images.TRAPLEFT);
			break;
		case '>':
			world.createTrap(x, y, Images.TRAPRIGHT);
			break;
		}
	}

	public void HUBFilled() {
		HUBsFilled++;
		if (HUBsFilled == numberOfHUBs) {
			gameOver();
		}
	}

	public void gameOver() {
		screenManager.getGameScreen().gameOver("Level Completed");
		gameOver = true;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public boolean loaded() {
		return loaded;
	}

}
