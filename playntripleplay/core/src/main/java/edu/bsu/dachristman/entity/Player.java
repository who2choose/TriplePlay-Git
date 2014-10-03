package edu.bsu.dachristman.entity;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import edu.bsu.dachristman.backend.ImageLoader;
import playn.core.Image;
import playn.core.Layer;

public final class Player {

	public static Player createPlayer(World world) {
		Player Player = new Player();
		Player.body = createBodyIn(world);
		FixtureDef fixtureDef = createFixtureDef();
		Player.body.createFixture(fixtureDef);
		return Player;
	}

	private static Body createBodyIn(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		return world.createBody(bodyDef);
	}

	private static FixtureDef createFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = createPlayerBox();
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 1f;
		fixtureDef.userData = "Player";
		return fixtureDef;
	}

	private static PolygonShape createPlayerBox() {
		PolygonShape shape = new PolygonShape();
		float size = 1.6f;
		System.out.println(size);
		shape.setAsBox(size, size);
		return shape;
	}

	private Body body;
	public final Layer layer;
	private static final Image IMAGE = ImageLoader.getImage("player").subImage(
			0, 0, 32, 32);

	private Player() {
		this.layer = graphics().createImageLayer(IMAGE)//
				.setOrigin(32 / 2, 32 / 2);
	}

	public Player position(float screenX, float screenY) {
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

	public void moveLeft(int deltaMS) {
		body.setTransform(new Vec2(body.getPosition().x-1, body.getPosition().y), body.getAngle());
		updateLayerPosition();
	}

	public void moveRight(int deltaMS) {
		body.setTransform(new Vec2(body.getPosition().x+1, body.getPosition().y), body.getAngle());
		updateLayerPosition();
	}

}
