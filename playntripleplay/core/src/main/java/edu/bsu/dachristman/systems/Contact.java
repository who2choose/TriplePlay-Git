package edu.bsu.dachristman.systems;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;

public class Contact implements ContactListener{

	@Override
	public void beginContact(org.jbox2d.dynamics.contacts.Contact contact) {
//		java.lang.System.out.printf("begin[%s, %s]\n", contact.getFixtureA().getUserData(), contact.getFixtureB().getUserData());
	}

	@Override
	public void endContact(org.jbox2d.dynamics.contacts.Contact contact) {
//		java.lang.System.out.printf("end[%s, %s]\n", contact.getFixtureA().getUserData(), contact.getFixtureB().getUserData());
	}

	@Override
	public void preSolve(org.jbox2d.dynamics.contacts.Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(org.jbox2d.dynamics.contacts.Contact contact, ContactImpulse impulse) {
		
	}

}
