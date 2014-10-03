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
import playn.core.Layer;

public class Tile {

	private static final float WORLD_SCALE = 10;

	public static Tile createTile(World world) {
		Tile tile = new Tile();
		tile.body = createBodyIn(world);
		FixtureDef fixtureDef = createFixtureDef();
		tile.body.createFixture(fixtureDef);
		return tile;
	}

	private static Body createBodyIn(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		return world.createBody(bodyDef);
	}

	private static FixtureDef createFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = createTileBox();
		fixtureDef.density = 5f;
		fixtureDef.friction = 1f;
		fixtureDef.restitution = 0.2f;
		fixtureDef.userData = "Tile";
		return fixtureDef;
	}

	private static PolygonShape createTileBox() {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(32 / 2 / WORLD_SCALE, 32 / 2 / WORLD_SCALE);
		return shape;
	}

	private Body body;
	public final Layer layer;

	private Tile() {
		this.layer = graphics().createImageLayer(
				ImageLoader.getImage("map_tiles").subImage(0, 64, 32, 32))
				.setOrigin(32 / 2, 32 / 2);
	}

	public Tile position(float screenX, float screenY) {
		Vec2 worldPosition = new Vec2(screenX / WORLD_SCALE, //
				screenY / WORLD_SCALE);
		body.setTransform(worldPosition, body.getAngle());
		updateLayerPosition();
		return this;
	}

	private void updateLayerPosition() {
		Vec2 bodyPosition = body.getPosition();
		layer.setTranslation(bodyPosition.x * WORLD_SCALE, bodyPosition.y
				* WORLD_SCALE);
		layer.setRotation(body.getAngle());
	}

	public void update(int deltaMS) {
		updateLayerPosition();
	}

}
