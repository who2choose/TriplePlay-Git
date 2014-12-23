package edu.bsu.dachristman.systems;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;

public class Contact implements ContactListener{

	private World world;
	
	public Contact(World world) {
		this.world = world;
	}

	@Override
	public void beginContact(org.jbox2d.dynamics.contacts.Contact contact) {
//		java.lang.System.out.printf("end[%s, %s]\n", contact.getFixtureA().getUserData(), contact.getFixtureB().getUserData());
		int idA = (Integer) contact.m_fixtureA.m_userData;
		if (world.hasContact(idA))
			world.setInContact(idA, true);
		
		int idB = (Integer) contact.m_fixtureB.m_userData;
		if (world.hasContact(idB))
			world.setInContact(idB, true);
	}

	@Override
	public void endContact(org.jbox2d.dynamics.contacts.Contact contact) {
		int idA = (Integer) contact.m_fixtureA.m_userData;
		if (world.hasContact(idA))
			world.setInContact(idA, false);
		
		int idB = (Integer) contact.m_fixtureB.m_userData;
		if (world.hasContact(idB))
			world.setInContact(idB, false);
	}

	@Override
	public void preSolve(org.jbox2d.dynamics.contacts.Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(org.jbox2d.dynamics.contacts.Contact contact, ContactImpulse impulse) {
		
	}

}
