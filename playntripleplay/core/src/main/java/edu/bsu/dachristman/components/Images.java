package edu.bsu.dachristman.components;

import static playn.core.PlayN.assets;
import playn.core.Image;

public enum Images {
	
	MAPTILES(assets().getImage("images/map_tiles.png")),//
	TRAPUP(assets().getImage("images/map_tiles.png").subImage(0, 0, 32, 32)),//
	TRAPDOWN(assets().getImage("images/map_tiles.png").subImage(32, 0, 32, 32)),//
	TRAPLEFT(assets().getImage("images/map_tiles.png").subImage(32, 32, 32, 32)),//
	TRAPRIGHT(assets().getImage("images/map_tiles.png").subImage(0, 32, 32, 32)),//
	BACKGROUNDHOME(assets().getImage("images/background.png")),//
	BACKGROUNDHIGHSCORES(assets().getImage("images/highScoreScreen.png")),//
	BACKGROUNDMAIN(assets().getImage("images/creditsScreen.png")),//
	REDBALL(assets().getImage("images/entity_ball.png").subImage(0, 32, 32, 32)),//
	BLUEBALL(assets().getImage("images/entity_ball.png").subImage(32, 0, 32, 32)),//
	GREENBALL(assets().getImage("images/entity_ball.png").subImage(32, 33, 32, 32)),//
	REDHUB(assets().getImage("images/entity_hub.png").subImage(0, 32, 32, 32)),//
	BLUEHUB(assets().getImage("images/entity_hub.png").subImage(32, 0, 32, 32)),//
	GREENHUB(assets().getImage("images/entity_hub.png").subImage(32, 33, 32, 32)),//
	HOME(assets().getImage("images/home.png"));

	private final Image image;

	private Images(Image i) {
		image = i;
	}

	public Image getImage() {
		return image;
	}

}

