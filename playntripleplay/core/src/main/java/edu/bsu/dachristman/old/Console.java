package edu.bsu.dachristman.old;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Lists;

public class Console {

	private int capacity;
	private final int TEXTSIZE = 22;
	private List<String> consoleText = Lists.newArrayList();

	public Console(int capacity) {
		this.capacity = capacity;
	}

	public int textSize() {
		return TEXTSIZE * capacity;
	}

	private void addNewMessage(String string) {
		consoleText.add(string + "\n");
	}

	public String text() {
		String text = "";

		ListIterator<String> iter = consoleText.listIterator((consoleText
				.size() > capacity ? consoleText.size() - capacity : 0));

		if (consoleText.size() < capacity) {
			int excess = capacity - consoleText.size();
			for (int i = 0; i <= excess; i++) {
				text += "\n";
			}
		}

		while (iter.hasNext()) {
			text += iter.next();
		}
		return text;
	}

	public void addMinusLifeMessage(Entity e, String string) {
		addNewMessage(String.format(
				"set player life -1 due to entity %s %d %s",
				(e.type().toString()), e.number(), string));
	}

	public void addPlusScoreMessage(Entity e, Entity target) {
		addNewMessage(String
				.format("set player score +1 due to entity %s %d collision with entity %s %d",
						(e.type().toString()), e.number(),
						(target.type().toString()), target.number()));
	}

	public void addGameOverMessage(Integer score, int numBalls) {
		addNewMessage(String.format(
				"Game Over: final score %d - numer of balls used %d", score,
				numBalls));
	}

}