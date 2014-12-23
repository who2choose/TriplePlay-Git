package edu.bsu.dachristman.systems;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import pythagoras.f.Point;
import tripleplay.entity.Component;
import tripleplay.entity.Entity;
import tripleplay.entity.System;

public class World extends tripleplay.entity.World{

	private org.jbox2d.dynamics.World physicsWorld;
	private final float SCALING_FACTOR = 1.0f / 32.0f;
	private Contact contactSystem;
	
	public World(){
		physicsWorld = new org.jbox2d.dynamics.World(new Vec2(0.0f, 0.5f));
		physicsWorld.setWarmStarting(true);
		physicsWorld.setAutoClearForces(true);
		contactSystem = new Contact(this);
		physicsWorld.setContactListener(contactSystem);
	}
	
	//COMPONENTS
	private final Component.Generic<Body> body = new Component.Generic<Body>(this);
	private final Component.Generic<ImageLayer> imageLayer = new Component.Generic<ImageLayer>(this);
	private final Component.XY position = new Component.XY(this);
	private final Component.XY moving = new Component.XY(this);
	private final Component.Generic<Boolean> inContact = new Component.Generic<Boolean>(this);
	
	//SYSTEMS
	public final System physics = new System(this, 2) {

		protected final Point _position = new Point();
		
		@Override
		protected void update(int delta, Entities entities) {
			physicsWorld.step(delta / 1000f, 10, 10);
			for (int i = 0, j = entities.size(); i < j; i++) {
				int entityId = entities.get(i);
				Point currentPosition = _position;
				Body currentBody = body.get(entityId);
				currentPosition.x = currentBody.getPosition().x;
				currentPosition.y = currentBody.getPosition().y;
				position.set(entityId, currentPosition.x, currentPosition.y);
				currentBody.setAwake(true);
				body.set(entityId, currentBody);
			}
		}

		@Override
		protected boolean isInterested(Entity entity) {
			return entity.has(position) && entity.has(body);
		}

	};
	
	public final System render = new System(this, 1) {

		protected final ImageLayer _imageLayer = graphics().createImageLayer();
		protected final Point _position = new Point();

		@Override
		protected void update(int delta, Entities entities) {
			for (int i = 0, j = entities.size(); i < j; i++) {
				int entityId = entities.get(i);
				Point currentPosition = _position;
				ImageLayer currentImageLayer = _imageLayer;
				position.get(entityId, currentPosition);
				currentImageLayer = imageLayer.get(entityId);
				currentImageLayer.setTranslation((currentPosition.x() + 0.5f) / SCALING_FACTOR, (currentPosition.y() + 0.5f) / SCALING_FACTOR);
				imageLayer.set(entityId, currentImageLayer);
			}
		}

		@Override
		protected boolean isInterested(Entity entity) {
			return entity.has(position) && entity.has(imageLayer);
		}
		
		@Override
		protected void wasRemoved(Entity entity, int index) {
			super.wasRemoved(entity, index);
			try {
				graphics().rootLayer().remove(imageLayer.get(entity.id));
			} catch (UnsupportedOperationException e) {}
		}

	};
	
	public final System motion = new System(this, 3) {

		@Override
		protected void update(int delta, Entities entities) {
			for (int i = 0, j = entities.size(); i < j; i++) {
				int entityId = entities.get(i);
				Body currentBody = body.get(entityId);
				currentBody.setTransform(currentBody.getPosition().sub(new Vec2(0.1f, 0)), currentBody.getAngle());
			}
		}

		@Override
		protected boolean isInterested(Entity entity) {
			return entity.has(moving) && entity.has(body);
		}

	};
	
	//IMPLEMENTED METHODS
	@Override
	public void update(int delta) {
		super.update(delta);
		physicsWorld.step(delta / 1000f, 10, 10);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
	}
	
	//ENTITY CREATION
	private Entity _e;
	private int _id;
	private float _x, _y;
	
	public Entity getEntity(){
		Entity temp = _e;
		_e = null;
		return temp;
	}
	
	public World createNew(){
		_e = create(true);
		_id = _e.id;
		return this;
	}
	
	public World addEntityComponents(){
		_e.add(body, position, imageLayer);
		return this;
	}
	
	public World addPosImageComponents(){
		_e.add(position, imageLayer);
		return this;
	}
	
	public World addMotionComponents(){
		_e.add(moving);
		return this;
	}
	
	public World addContactComponents(){
		_e.add(inContact);
		inContact.set(_id, false);
		return this;
	}
	
	public World setXY(float x, float y){
		_x = x;
		_y = y;
		position.set(_id, _x, _y);
		return this;
	}
	
	//BODY CREATION
	private Body _b;
	private BodyDef _bd;
	private FixtureDef _fd;
	
	public World createBody(){
		_b = physicsWorld.createBody(_bd);
		_b.createFixture(_fd);
		body.set(_id, _b);
		return this;
	}
	
	public World createBodyDef(BodyType type){
		_bd = new BodyDef();
		_bd.type = type;
		_bd.position.set(_x, _y);
		return this;
	}
	
	public World createFixtureDef(){
		_fd = new FixtureDef();
		_fd.userData = _id;
		return this;
	}
	
	public PolygonShape createPolygon(float width, float height){
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(width, height);
		return polygon;
	}
	
	public CircleShape createCircle(float radius){
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		return circle;
	}
	
	public World setShape(Shape shape){
		_fd.shape = shape;
		return this;
	}
	
	public World setFixtureDef(float density, float friction, float restitution){
		_fd.density = density;
		_fd.friction = friction;
		_fd.restitution = restitution;
		return this;
	}
	
	public World setSensor(boolean sensor){
		_fd.isSensor = sensor;
		return this;
	}
	
	//IMAGELAYER CREATION
	private ImageLayer _il;
	
	public World setImage(Image image){
		_il = graphics().createImageLayer(image);
		_il.setOrigin(image.width() / 2, image.height() / 2);
		_il.setTranslation((_x + 0.5f) / SCALING_FACTOR, (_y + 0.5f) / SCALING_FACTOR);
		return this;
	}
	
	public World setImageLayer(){
		graphics().rootLayer().add(_il);
		imageLayer.set(_id, _il);
		return this;
	}
	
	//IMAGE MODIFICATION
	public ImageLayer getImage(int entityId){
		return imageLayer.get(entityId);
	}
	
	public void setImage(int entityID, Image image){
		_id = entityID;
		graphics().rootLayer().remove(getImage(_id));
		setXY(getImage(_id).tx(), getImage(_id).ty());
		setImage(image);
		setImageLayer();
	}
	
	//BODY MODIFICATION
	public void setBodyLocation(int entityID, float x, float y){
		_id = entityID;
		setXY(x*SCALING_FACTOR, y*SCALING_FACTOR);
		body.get(_id).setTransform(new Vec2(_x, _y), body.get(_id).getAngle());
	}
	
	public void setPosition(int entityID, float x, float y){
		_id = entityID;
		setXY(x*SCALING_FACTOR, y*SCALING_FACTOR);
	}
	
	public void setMotion(int entityID, Vec2 velocity){
		_id = entityID;
		body.get(_id).setLinearVelocity(velocity);
	}
	
	public Vec2 getPosition(int entityID){
		return new Vec2(position.getX(entityID), position.getY(entityID));
	}
	
	public Vec2 getLinearVelocity(int entityID){
		return body.get(entityID).getLinearVelocity();
	}
	
	//CONTACT MODIFICATION
	public void setInContact(int entityID, boolean setTo){
		_id = entityID;
		inContact.set(_id, setTo);
	}
	
	public boolean hasContact(int entityID){
		return entity(entityID).has(inContact);
	}
	
	public boolean inContact(int entityID){
		_id = entityID;
		if (hasContact(_id)){
			return inContact.get(_id);
		} else {
			return false; //might need to change this
		}
	}
	
}