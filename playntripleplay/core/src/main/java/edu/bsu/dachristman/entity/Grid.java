package edu.bsu.dachristman.entity;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.util.List;

import org.jbox2d.dynamics.World;

import com.google.common.collect.Lists;

import playn.core.Image;
import playn.core.Layer;
import edu.bsu.dachristman.backend.ImageLoader;
import edu.bsu.dachristman.screen.GridScreen;

public class Grid {

	private static final int WIDTH = 20;
	private static final int HEIGHT = 15;
	private static String file;
	private World world;
	private List<Tile> tiles = Lists.newArrayList();

	public Grid(GridScreen gridScreen, World world) {
		this.world = world;
		readInFile(gridScreen);
	}

	private void readInFile(GridScreen gridScreen) {
		readText();
		Image white = ImageLoader.getImage("map_tiles")
				.subImage(33, 64, 32, 32);

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				int a = Integer.parseInt(nextString());
				if (a == 0) {
					Tile tile = Tile.createTile(world).position(
							(j * 32) + 16, (i * 32) + 16);
					gridScreen.add(tile.layer);
					tiles.add(tile);
					// IMAGES[i][j] = new Tile(a, graphics().createImageLayer(
					// black));

				} else if (a == 1) {
					Layer whiteSpace = graphics().createImageLayer(white);
					;
					whiteSpace.setTranslation((j * 32), (i * 32));
					gridScreen.add(whiteSpace);
					// IMAGES[i][j] = new Tile(a, graphics().createImageLayer(
					// white));
				}
				// IMAGES[i][j].setXY(j * 32, i * 32);
				// System.out.printf("I: %d J: %d\n", (i * 32), (j * 32));
			}
		}
	}

	private void readText() {
		try {
			file = assets().getTextSync("text/map");
		} catch (Exception e) {
			file = "";
			e.printStackTrace();
		}
	}

	private String nextString() {
		String a = file.substring(0, 1);
		file = file.substring(1);
		return a;
	}

	public void update(int delta) {
		for (Tile t : tiles) {
			t.update(delta);
		}
	}

}
