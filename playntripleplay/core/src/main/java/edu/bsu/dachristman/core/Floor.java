package edu.bsu.dachristman.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Floor {

	public Floor(World world) {
		BodyDef floorBodyDef = createBodyDef(9.5f, 14.5f);
		Body floorBody = world.createBody(floorBodyDef);
		floorBody.createFixture(createFixtureDef("Floor", 100, 0));
		
		BodyDef rightWallBodyDef = createBodyDef(19, 7);
		Body rightWallBody = world.createBody(rightWallBodyDef);
		rightWallBody.createFixture(createFixtureDef("Right Wall", 0, 14));
		
		BodyDef leftWallBodyDef = createBodyDef(0, 7);
		Body leftWallBody = world.createBody(leftWallBodyDef);
		leftWallBody.createFixture(createFixtureDef("Left Wall", 0, 14));
	}

	private BodyDef createBodyDef(float x, float y) {
		BodyDef floorBodyDef = new BodyDef();
		floorBodyDef.type = BodyType.STATIC;
		floorBodyDef.position = new Vec2(x, y);
		return floorBodyDef;
	}

	private FixtureDef createFixtureDef(String s, float x, float y) {
		FixtureDef floorFixtureDef = new FixtureDef();
		floorFixtureDef.shape = createFloorShape(x, y);
		floorFixtureDef.userData = s;
		return floorFixtureDef;
	}

	private PolygonShape createFloorShape(float x, float y) {
		PolygonShape floorShape = new PolygonShape();
		floorShape.setAsBox(x, y);
		return floorShape;
	}

}
