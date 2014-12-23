package edu.bsu.dachristman.components;

import org.jbox2d.dynamics.BodyType;

import tripleplay.entity.Entity;
import tripleplay.util.Timer;
import edu.bsu.dachristman.core.State;
import edu.bsu.dachristman.systems.World;

public class Kat {

	private Entity kat;
	private Entity lazor;
	private State state;
	private World world;
	private Timer timer;

	public Kat(World world, Entity lazor) {
		this.world = world;
		this.lazor = lazor;
		kat = this.world.createNew().addEntityComponents()
				.addContactComponents().setXY(3f, 13f)//
				.createBodyDef(BodyType.DYNAMIC).createFixtureDef()//
				.setShape(world.createCircle(0.5f))//
				.setFixtureDef(0, 1f, 0f).createBody()//
				.setImage(Images.KAT.getImage()).setImageLayer().getEntity();
		state = stay;
		timer();
	}

	public void update(int deltaMS) {
		state.update(deltaMS);
		timer.update();
	}

	private void timer() {
		timer = new Timer();
		timer.atThenEvery(0, 250, new Runnable() {
			@Override
			public void run() {
				tryToJump();
			}
		});
	}

	public void tryToJump() {
		if (world.inContact(kat.id)) {
			setState(jump);
		}
		if ((world.getLinearVelocity(kat.id).abs().x < 0.1) && (world.getLinearVelocity(kat.id).abs().y < 0.1)){
			setState(jump);
		}
	}

	public void setState(State s) {
		state = s;
		state.onEnter();
	}

	public final State stay = new State() {

		@Override
		public void update(int deltaMS) {
			// dont have anything to update
		}

		@Override
		public void onEnter() {
			// does nothing. . . yet!
		}
	};

	public final State jump = new State() {

		@Override
		public void update(int deltaMS) {
			// dont have anything to update
		}

		@Override
		public void onEnter() {
			world.setMotion(kat.id, world.getPosition(lazor.id).sub(world.getPosition(kat.id)).mul(0.4f));
			setState(stay);
		}
	};

}
