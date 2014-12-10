package edu.bsu.dachristman.systems;

public class LevelData {

	public String levelNumber = "";
	public String highScores = "";

	public LevelData(String scores) {
		String splitScores[] = scores.split(",");
		String score[][] = new String[3][];
		for (int i = 0; i < 3; i++) {
			score[i] = splitScores[i].split("\\.");
			int index = 0;
			if (i == 0) {
				levelNumber = score[i][index];
				index++;
			}
			highScores += score[i][index];
			index++;
			highScores += "     ";
			highScores += score[i][index];
			if (i != 2) {
				highScores += ",";
			}
		}
	}

}