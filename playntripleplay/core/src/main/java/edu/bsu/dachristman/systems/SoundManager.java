package edu.bsu.dachristman.systems;

import edu.bsu.dachristman.components.Sounds;

public class SoundManager {

	private boolean soundEnabled;

	public SoundManager() {
		enableSound();
		Sounds.HUB.getSound().prepare();
		Sounds.TRAP.getSound().prepare();
		Sounds.MUSIC.getSound().prepare();
	}

	public void enableSound() {
		soundEnabled = true;
	}

	public void disableSound() {
		soundEnabled = false;
	}

	public void loopMusic() {
		if (soundEnabled && !Sounds.MUSIC.getSound().isPlaying()) {
			Sounds.MUSIC.getSound().setLooping(true);
			Sounds.MUSIC.getSound().play();
		}
	}

	public void playHubSound() {
		if (soundEnabled) {
			if (Sounds.HUB.getSound().isPlaying()) {
				Sounds.HUB.getSound().stop();
			}
			Sounds.HUB.getSound().play();
		}
	}

	public void playTrapSound() {
		if (soundEnabled) {
			if (Sounds.TRAP.getSound().isPlaying()) {
				Sounds.TRAP.getSound().stop();
			}
			Sounds.TRAP.getSound().play();
		}
	}
}
