package edu.bsu.dachristman.backend;

import static playn.core.PlayN.assets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import playn.core.Image;

public class ImageLoader {

	public Image IMAGEGameOver;
	public Image IMAGEShapeBallBlack;
	public Image IMAGEShapeBallRed;
	public Image IMAGEShapeSquareBlue;
	public Image IMAGEShapeThreePoint;
	public Image IMAGEShapeStar;

	private static String[] imageNames = { "bg", "gameOver", "shapeBallBlack",
			"shapeBallRed", "shapeSquareBlue", "shapeThreePoint", "star" };
	private static Image[] images = new Image[imageNames.length];

	private static boolean loaded = false;
	private static boolean initialized = false;

	public static void initialize() {
		initialized = true;
		for (int i = 0; i < imageNames.length; i++) {
			images[i] = assets().getImage("images/" + imageNames[i] + ".png");
		}
		createTimer();
	}
	
	private static void createTimer(){
		final ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loaded = true;
			}
		};
		Timer timer = new Timer(2000, listener);
		timer.start();
	}

	public static boolean loaded() {
		return loaded;
	}

	public static boolean initialized() {
		return initialized;
	}

	public static Image getImage(String name) {
		int i;
		for (i = 0; i < imageNames.length; i++) {
			if (imageNames[i].equals(name))
				break;
		}
		return images[i];
	}

}