package edu.bsu.dachristman.screens;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.systems.GravinationWorld;
import edu.bsu.dachristman.systems.Level;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.util.Callback;
import react.Slot;
import tripleplay.game.UIScreen;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.FlowLayout;

public class LevelSelectScreen extends UIScreen {

	private static final int NUMBER_OF_LEVELS = 12;

	private Root root;
	private Group grid;
	private final ScreenManager screenManager;
	private final GravinationWorld world;

	public LevelSelectScreen(ScreenManager screenManager, GravinationWorld world) {
		this.world = world;
		this.screenManager = screenManager;
		createBackground();
		createUIRoot();
		createButtons();
	}

	private void createUIRoot() {
		root = iface.createRoot(new AxisLayout.Horizontal(),
				SimpleStyles.newSheet(), layer);
		root.setBounds(graphics().width() / 2 - graphics().width() / 2.5f,
				graphics().height() / 2 - graphics().height() / 1.75f,
				graphics().width() / 1.25f, graphics().height() / 1.25f);
	}

	private void createButtons() {
		grid = new Group(new FlowLayout());
		for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
			grid.add(new Button());
		}

		for (int i = 0; i < NUMBER_OF_LEVELS; i++) {
			String path = "images/map_preview" + (i + 1) + ".png";
			final String levelPath = "text/map" + (i + 1) + ".m";
			final int position = i;
			Icon levelImage = Icons.image(PlayN.assets().getImage(path));
			levelImage.addCallback(new Callback<Icon>() {

				@Override
				public void onSuccess(Icon result) {
					Button levelButton = new Button(result);
					levelButton.onClick(new Slot<Button>() {

						@Override
						public void onEmit(Button event) {
							Level newLevel = new Level(levelPath, world,
									screenManager);
							world.setLevel(newLevel);
							screenManager.getGameScreen().newGame(newLevel);
							screenManager.screenToGame();
						}
					});
					grid.destroyAt(position);
					grid.add(position, levelButton);
				}

				@Override
				public void onFailure(Throwable cause) {
					PlayN.log().error("Level image failed to load!", cause);
					Button levelButton = new Button(
							" Level image \nfailed to load!!!");
					levelButton.onClick(new Slot<Button>() {

						@Override
						public void onEmit(Button event) {
							Level newLevel = new Level(levelPath, world,
									screenManager);
							world.setLevel(newLevel);
							screenManager.getGameScreen().newGame(newLevel);
							screenManager.screenToGame();
						}
					});
					grid.destroyAt(position);
					grid.add(position, levelButton);
				}
			});
		}
		root.add(grid);
	}

	private void createBackground() {
		ImageLayer imageLayer = graphics().createImageLayer(
				Images.BACKGROUNDMAIN.getImage());
		imageLayer.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(imageLayer);
	}

	public void refresh() {
		createBackground();
		createUIRoot();
		createButtons();
	}

}
