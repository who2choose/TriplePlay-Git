package edu.bsu.dachristman.entity;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Block {

	public Block(World world, float x, float y) {
		x+=16;
		y+=16;
		BodyDef blockBodyDef = createBodyDef(x/10, y/10);
		Body blockBody = world.createBody(blockBodyDef);
		blockBody.createFixture(createFixtureDef());
	}

	private BodyDef createBodyDef(float x, float y) {
		BodyDef blockBodyDef = new BodyDef();
		blockBodyDef.type = BodyType.STATIC;
		blockBodyDef.position = new Vec2(x, y);
		return blockBodyDef;
	}

	private FixtureDef createFixtureDef() {
		FixtureDef blockFixtureDef = new FixtureDef();
		blockFixtureDef.shape = createBlockShape();
		blockFixtureDef.userData = "Block";
		return blockFixtureDef;
	}

	private PolygonShape createBlockShape() {
		PolygonShape blockShape = new PolygonShape();
		blockShape.setAsBox(1.6f, 1.6f);
		return blockShape;
	}

}