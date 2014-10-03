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
import playn.core.Layer;

public final class Character {

	private static final Image IMAGE = ImageLoader.getImage("player").subImage(
			0, 0, 32, 32);
	private static final int HORIZONTAL_SPEED_PPS = 110;
	private static final int VERTICAL_SPEED_PPS = 110;
	private static final float WORLD_SCALE = 10;

	public static Character createIn(World world) {
		return new Character(world);
	}

	private final ImageLayer layer;
	private final Body body;

	private Character(World world) {
		layer = graphics().createImageLayer(IMAGE);
		layer.setOrigin(IMAGE.width() / 2, IMAGE.height() / 2);
		body = createBodyIn(world);
	}

	private static Body createBodyIn(World world) {
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
		shape.setAsBox(IMAGE.width() / 2 / WORLD_SCALE,//
				IMAGE.height() / 2 / WORLD_SCALE);
		return shape;
	}

	public Character at(float x, float y) {
		layer.setTranslation(x, y);
		updatePhysicsBasedOnLayer();
		return this;
	}

	private void updatePhysicsBasedOnLayer() {
		Vec2 newPos = new Vec2(layer.tx() / WORLD_SCALE,//
				layer.ty() / WORLD_SCALE);
		body.setTransform(newPos, body.getAngle());
	}

	public Layer.HasSize layer() {
		return layer;
	}

	public void moveLeft(int deltaMS) {
		layer.setTx(layer.tx() - HORIZONTAL_SPEED_PPS / deltaMS);
		updatePhysicsBasedOnLayer();
	}

	public void moveRight(int deltaMS) {
		layer.setTx(layer.tx() + HORIZONTAL_SPEED_PPS / deltaMS);
		updatePhysicsBasedOnLayer();
	}

	public void moveUp(int deltaMS) {
		layer.setTy(layer.ty() - VERTICAL_SPEED_PPS / deltaMS);
		updatePhysicsBasedOnLayer();
	}

	public void moveDown(int deltaMS) {
		layer.setTy(layer.ty() + VERTICAL_SPEED_PPS / deltaMS);
		updatePhysicsBasedOnLayer();
	}
}
