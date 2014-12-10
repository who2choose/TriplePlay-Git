package edu.bsu.dachristman.components;

import static playn.core.PlayN.assets;
import playn.core.Sound;

public enum Sounds {

	MUSIC(assets().getSound("audio/tempMusic")), //
	HUB(assets().getSound("audio/hub")), //
	TRAP(assets().getSound("audio/trap"));

	private final Sound sound;

	private Sounds(Sound sound) {
		this.sound = sound;
		sound.setVolume(0.8f);
	}

	public Sound getSound() {
		return sound;
	}
}
