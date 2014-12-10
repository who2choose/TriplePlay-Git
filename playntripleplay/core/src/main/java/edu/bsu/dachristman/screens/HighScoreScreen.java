package edu.bsu.dachristman.screens;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;

import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.systems.LevelData;
import playn.core.ImageLayer;
import pythagoras.f.Point;
import react.Slot;
import tripleplay.game.UIAnimScreen;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

public class HighScoreScreen extends UIAnimScreen {

	private ScreenManager screenManager;
	private ArrayList<LevelData> levelInfo = new ArrayList<LevelData>();
	private Group display;
	private Label firstLabel, secondLabel, thirdLabel;

	public HighScoreScreen(ScreenManager screenManager) {
		this.screenManager = screenManager;
		parse();
	}

	private void parse() {
		for (String s : screenManager.getSaveData().split("\n")) {
			levelInfo.add(new LevelData(s));
		}
		iniLabel();
		iniButtons();
	}

	private void iniLabel() {
		Root root = iface.createRoot(new AbsoluteLayout(),
				SimpleStyles.newSheet(), layer).setSize(graphics().width(),
				graphics().height());
		display = new Group(AxisLayout.vertical().gap(5));
		firstLabel = new Label("Select Level").setStyles(Style.COLOR
				.is(Colors.WHITE));
		secondLabel = new Label("").setStyles(Style.COLOR.is(Colors.WHITE));
		thirdLabel = new Label("").setStyles(Style.COLOR.is(Colors.WHITE));
		display.add(firstLabel);
		display.add(secondLabel);
		display.add(thirdLabel);
		display.setStyles(Style.COLOR.is(Colors.BLACK));
		root.add(AbsoluteLayout.at(display, new Point(350, 200)));
	}

	private void iniButtons() {
		Root root = iface.createRoot(new AxisLayout.Vertical().gap(10),
				SimpleStyles.newSheet(), layer);
		root.setBounds(100, 0, 100, 512);
		for (final LevelData levelData : levelInfo) {
			Button levelButton = new Button("Level " + levelData.levelNumber);
			root.add(levelButton);
			levelButton.clicked().connect(new Slot<Button>() {

				@Override
				public void onEmit(Button event) {
					String scores[] = levelData.highScores.split(",");
					firstLabel.text.update(scores[0]);
					secondLabel.text.update(scores[1]);
					thirdLabel.text.update(scores[2]);
				}

			});
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	public LevelData getLevelData(int levelNumber) {
		for (LevelData level : levelInfo) {
			if (Integer.parseInt(level.levelNumber) == levelNumber) {
				return level;
			}
		}
		return null;
	}

	public void refresh() {
		ImageLayer imageLayer = graphics().createImageLayer(
				Images.BACKGROUNDHIGHSCORES.getImage());
		imageLayer.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(imageLayer);
	}

}
