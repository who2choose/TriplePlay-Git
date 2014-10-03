package edu.bsu.dachristman.entity;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.backend.ImageLoader;
import playn.core.Image;
import playn.core.ImageLayer;

public class Player {

	public ImageLayer layer;
	private float x, y, vx, vy, ax, ay;
	private Body body;

	public Player(World world) {
		layer = graphics().createImageLayer(
				ImageLoader.getImage("player").subImage(0, 0, 32, 32));
		layer.setOrigin(32 / 2, 32 / 2);
		// x = 40;
		// y = 300;
		// vx = 0;
		// vy = 0;
		// ax = 0;
		// ay = 50f;

		body = createBodyIn(world);
		updatePhysicsBasedOnLayer();
	}

	private Body createBodyIn(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		body.createFixture(createFixtureDef());
		return body;
	}

	private static FixtureDef createFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 1.0f;
		fixtureDef.shape = makeImageBox();
		return fixtureDef;
	}

	private static Shape makeImageBox() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(32 / 2 / 10,//
				32 / 2 / 10);
		return shape;
	}

	private void updatePhysicsBasedOnLayer() {
		Vec2 newPos = new Vec2(layer.tx() / 10,//
				layer.ty() / 10);
		body.setTransform(newPos, body.getAngle());
	}

	public void draw() {
		graphics().rootLayer().add(layer);
	}

	public void update(int deltaMS) {
		// vx += ax * deltaMS / 1000f;
		// vy += ay * deltaMS / 1000f;
		// x = x + vx * deltaMS / 1000f;
		// y = y + vy * deltaMS / 1000f;
		//
		// layer.setTx(x);
		// layer.setTy(y);
		updatePhysicsBasedOnLayer();
	}

	public boolean collision(int width, int height) {
		if (x < 0) {
			return true;
		} else if (x > width || y > height) {
			return true;
		}
		return false;
	}

	public void jump() {
		// vy = -100;
		updatePhysicsBasedOnLayer();
	}

}
