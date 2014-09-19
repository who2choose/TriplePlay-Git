package edu.bsu.dachristman.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import edu.bsu.dachristman.core.ProgramMain;

public class ProgramMainJava {

	public static void main(String[] args) {
		JavaPlatform.Config config = new JavaPlatform.Config();
		// use config to customize the Java platform, if needed
		JavaPlatform.register(config);
		PlayN.run(new ProgramMain());
	}
}
