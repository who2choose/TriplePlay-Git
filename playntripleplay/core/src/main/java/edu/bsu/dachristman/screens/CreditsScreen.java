package edu.bsu.dachristman.screens;

import static playn.core.PlayN.graphics;
import edu.bsu.dachristman.components.Images;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import tripleplay.game.UIAnimScreen;
import tripleplay.util.Colors;

public class CreditsScreen extends UIAnimScreen {

	private CanvasImage textLayer;
	private Canvas canvas;
	private final int yDifference = 20;
	private final int creditsPositionX = 350;
	private final int initialCreditsPositionY = 300;
	private final int rulesPositionX = 20;
	private final int initialRulesPositionY = 100;
	private int[] creditsPosY;
	private int[] rulesPosY;
	private String[] credits;
	private String[] rules;

	public CreditsScreen() {
		refresh();
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		canvas.clear();
		for (int i = 0; i < credits.length; i++) {
			canvas.drawText(credits[i], creditsPositionX, creditsPosY[i]);
		}
		for (int i = 0; i < rules.length; i++) {
			canvas.drawText(rules[i], rulesPositionX, rulesPosY[i]);
		}
	}

	public void refresh() {
		ImageLayer background = graphics().createImageLayer(
				Images.BACKGROUNDMAIN.getImage());
		background.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(background);

		ImageLayer foreground = graphics().createImageLayer(
				Images.BACKGROUNDMAIN.getImage());
		foreground.setSize(graphics().width(), graphics().height());
		graphics().rootLayer().add(foreground);

		textLayer = graphics().createImage(graphics().width(),
				graphics().height());
		foreground.setImage(textLayer);
		canvas = textLayer.canvas();
		canvas.setFillColor(Colors.WHITE);
	}

	public void createCreditsCanvas(String creditsData) {
		credits = creditsData.split("\n");
		creditsPosY = new int[credits.length];
		creditsPosY[0] = initialCreditsPositionY;
		for (int i = 1; i < credits.length; i++) {
			creditsPosY[i] = creditsPosY[i - 1] + yDifference;
		}
	}

	public void createRulesCanvas(String rulesData) {
		rules = rulesData.split("\n");
		rulesPosY = new int[rules.length];
		rulesPosY[0] = initialRulesPositionY;
		for (int i = 1; i < rules.length; i++) {
			rulesPosY[i] = rulesPosY[i - 1] + yDifference;
		}
	}

}