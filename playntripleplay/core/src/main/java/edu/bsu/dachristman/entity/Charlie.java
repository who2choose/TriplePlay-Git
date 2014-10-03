package edu.bsu.dachristman.entity;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.backend.ImageLoader;
import playn.core.Layer;

public class Charlie {

	public static Charlie createCharlie(World world) {
		Charlie charlie = new Charlie();
		charlie.body = createBodyIn(world);
		FixtureDef fixtureDef = createFixtureDef();
		charlie.body.createFixture(fixtureDef);
		return charlie;
	}

	private static Body createBodyIn(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		return world.createBody(bodyDef);
	}

	private static FixtureDef createFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = createCharlieCircle();
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 1f;
		fixtureDef.userData = "Charlie";
		return fixtureDef;
	}

//	private static PolygonShape createCharlieBox() {
//		PolygonShape shape = new PolygonShape();
//		float size = 1.6f;
//		System.out.println(size);
//		shape.setAsBox(size, size);
//		return shape;
//	}

	private static CircleShape createCharlieCircle() {
		CircleShape shape = new CircleShape();
		shape.setRadius(1.6f);
		// shape.setAsBox(32 / 2 / 10, 32 / 2 / 10);
		return shape;
	}

	private Body body;
	public final Layer layer;

	private Charlie() {
		this.layer = graphics().createImageLayer(
				ImageLoader.getImage("shapeBallRed"))//
				.setOrigin(32 / 2, 32 / 2);
	}

	public Charlie position(float screenX, float screenY) {
		Vec2 worldPosition = new Vec2(screenX / 10, //
				screenY / 10);
		body.setTransform(worldPosition, body.getAngle());
		updateLayerPosition();
		return this;
	}

	private void updateLayerPosition() {
		Vec2 bodyPosition = body.getPosition();
		layer.setTranslation(bodyPosition.x * 10, bodyPosition.y * 10);
		layer.setRotation(body.getAngle());
	}

	public void update(int deltaMS) {
		updateLayerPosition();
	}

}
