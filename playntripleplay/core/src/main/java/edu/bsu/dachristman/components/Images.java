package edu.bsu.dachristman.components;

import static playn.core.PlayN.assets;
import playn.core.Image;

public enum Images {
	
	BACKGROUNDMAIN(assets().getImage("images/creditsScreen.png")),//
	KAT(assets().getImage("images/kat.png")),//
	LAZOR(assets().getImage("images/lazor.png")),//
	BRICK(assets().getImage("images/brick.png"));

	private final Image image;

	private Images(Image i) {
		image = i;
	}

	public Image getImage() {
		return image;
	}

}

