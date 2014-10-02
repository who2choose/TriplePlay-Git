package edu.bsu.dachristman.entity;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.assets;
import edu.bsu.dachristman.backend.ImageLoader;
import playn.core.Image;
import playn.core.ImageLayer;

public class Grid {

	private Tile[][] IMAGES;
	private static final int WIDTH = 20;
	private static final int HEIGHT = 15;
	private static String file;

	public Grid() {
		readInFile();
	}

	// private void createTemp() {
	// temp = new int[WIDTH][HEIGHT];
	// for (int i = 0; i < WIDTH; i++) {
	// for (int j = 0; j < HEIGHT; j++) {
	// temp[i][j] = (int) World.randNum(2);
	// }
	// }
	// }

	private void readInFile() {
		readText();
		// TODO change this to actually read a file and not temp
		Image image = ImageLoader.getImage("map_tiles");
		Image black = image.subImage(0, 64, 32, 32);
		Image white = image.subImage(33, 64, 32, 32);

		IMAGES = new Tile[HEIGHT][WIDTH];
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				int a = Integer.parseInt(nextString());
				if (a == 0) {
					IMAGES[i][j] = new Tile(a, graphics().createImageLayer(
							black));
				} else if (a == 1) {
					IMAGES[i][j] = new Tile(a, graphics().createImageLayer(
							white));
				}
				IMAGES[i][j].setXY(j * 32, i * 32);
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

	public void draw() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				graphics().rootLayer().add(IMAGES[i][j].getImage());
			}
		}
	}

	public int getType(int x, int y) {
		return IMAGES[x][y].getType();
	}

	private class Tile {

		private ImageLayer layer;
		private int type;
		private int x, y;

		public Tile(int a, ImageLayer imageLayer) {
			layer = imageLayer;
			type = a;
		}

		public void setXY(int i, int j) {
			x = i;
			y = j;
			layer.setTx(i);
			layer.setTy(j);
		}

		public int getType() {
			return type;
		}

		public ImageLayer getImage() {
			return layer;
		}

	}

}
