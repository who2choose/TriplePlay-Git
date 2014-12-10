package edu.bsu.dachristman.systems;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import edu.bsu.dachristman.components.TypeMask;

public class CollisionSystem implements ContactListener {

	public Vec2 redHub, blueHub, greenHub;
	public int contactID = 0;
	private SoundManager soundManager;

	public CollisionSystem(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

	@Override
	public void beginContact(Contact contact) {
		int contactFixtureA = (Integer) contact.getFixtureA().getUserData();
		int contactFixtureB = (Integer) contact.getFixtureB().getUserData();
		int comparator = contactFixtureA & contactFixtureB;

		if (comparator == TypeMask.REDHUB.getMask()) {
			redHub = (contactFixtureA == TypeMask.REDHUB.getMask()) ? contact
					.getFixtureA().getBody().getPosition() : contact
					.getFixtureB().getBody().getPosition();
			soundManager.playHubSound();
		} else if (comparator == TypeMask.BLUEHUB.getMask()) {
			blueHub = (contactFixtureA == TypeMask.BLUEHUB.getMask()) ? contact
					.getFixtureA().getBody().getPosition() : contact
					.getFixtureB().getBody().getPosition();
			soundManager.playHubSound();
		} else if (comparator == TypeMask.GREENHUB.getMask()) {
			greenHub = (contactFixtureA == TypeMask.GREENHUB.getMask()) ? contact
					.getFixtureA().getBody().getPosition()
					: contact.getFixtureB().getBody().getPosition();
			soundManager.playHubSound();
		} else if (comparator == TypeMask.TRAP.getMask()) {
			contactID = (contactFixtureA == TypeMask.TRAP.getMask()) ? contactFixtureB
					: contactFixtureA;
			soundManager.playTrapSound();
		}
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}

}
