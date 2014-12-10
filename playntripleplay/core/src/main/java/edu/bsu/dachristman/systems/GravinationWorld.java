package edu.bsu.dachristman.systems;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import edu.bsu.dachristman.components.Images;
import edu.bsu.dachristman.components.TypeMask;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Clock;
import static playn.core.PlayN.graphics;
import pythagoras.f.Point;
import tripleplay.entity.Component;
import tripleplay.entity.Entity;
import tripleplay.entity.System;
import tripleplay.entity.World;

public class GravinationWorld extends World {

	private static final float BALL_RADIUS = 0.48f;
	private static final float SCALING_FACTOR = 1.0f / 32.0f;

	private org.jbox2d.dynamics.World physicsWorld;
	private SessionManager sessionManager;
	private Level currentLevel;
	private HUD hud;
	private final CollisionSystem collisionSystem;

	public GravinationWorld(org.jbox2d.dynamics.World physicsWorld, HUD hud,
			SoundManager soundManager, SessionManager sessionManager) {
		this.sessionManager = sessionManager;
		this.collisionSystem = new CollisionSystem(soundManager);
		this.hud = hud;
		this.physicsWorld = physicsWorld;
		physicsWorld.setContactListener(collisionSystem);
	}

	private final Component.IMask type = new Component.IMask(this);
	private final Component.Generic<Body> body = //
			new Component.Generic<Body>(this);
	private final Component.XY position = new Component.XY(this);
	private final Component.XY initialPosition = new Component.XY(this);
	private final Component.Generic<ImageLayer> imageLayer = //
			new Component.Generic<ImageLayer>(this);
	private final Component.Generic<Boolean> affectsScore = //
			new Component.Generic<Boolean>(this);

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
				currentImageLayer.setTranslation((currentPosition.x() + 0.5f)
						/ SCALING_FACTOR, (currentPosition.y() + 0.5f)
						/ SCALING_FACTOR);
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
			} catch (UnsupportedOperationException e) {
			}
		}

	};

	public final System scoring = new System(this, 3) {

		@Override
		protected void update(int delta, Entities entities) {
			for (int i = 0, j = entities.size(); i < j; i++) {
				int entityId = entities.get(i);
				if (collisionSystem.contactID == type.get(entityId)) {
					reset(entityId);
					collisionSystem.contactID = 0;
				} else if (collisionSystem.redHub != null
						&& type.get(entityId) == TypeMask.REDBALL.getMask()) {
					setBallPosition(entityId, collisionSystem.redHub);
					collisionSystem.redHub = null;
				} else if (collisionSystem.blueHub != null
						&& type.get(entityId) == TypeMask.BLUEBALL.getMask()) {
					setBallPosition(entityId, collisionSystem.blueHub);
					collisionSystem.blueHub = null;
				} else if (collisionSystem.greenHub != null
						&& type.get(entityId) == TypeMask.GREENBALL.getMask()) {
					setBallPosition(entityId, collisionSystem.greenHub);
					collisionSystem.greenHub = null;
				}
			}
		}

		@Override
		protected boolean isInterested(Entity entity) {
			return entity.has(affectsScore) && entity.has(type)
					&& entity.has(body);
		}

		private void setBallPosition(int entityId, Vec2 position) {
			Body ball = body.get(entityId);
			ball.getFixtureList().setSensor(true);
			ball.setLinearVelocity(new Vec2(0, 0));
			ball.setAngularVelocity(0f);
			ball.setGravityScale(0f);
			ball.setType(BodyType.STATIC);
			ball.setTransform(position, 0f);
			ImageLayer currentImageLayer = imageLayer.get(entityId);
			currentImageLayer.setTx((position.x + 0.5f) / SCALING_FACTOR);
			currentImageLayer.setTy((position.y + 0.5f) / SCALING_FACTOR);
			imageLayer.set(entityId, currentImageLayer);
			currentLevel.HUBFilled();
		}

	};

	public final System destroyWorld = new System(this, 4) {

		@Override
		protected void update(int delta, Entities entities) {
			if (sessionManager.isGameOver()) {
				while (this.world.entities().hasNext()) {
					Entity e = this.world.entities().next();
					imageLayer.get(e.id).destroy();
					e.destroy();
				}
			}
		}

		@Override
		protected boolean isInterested(Entity entity) {
			return true;
		}

	};

	public Entity createBall(float x, float y, Images image, TypeMask typeMask) {
		Entity ball = create(true);
		ball.add(type, body, position, initialPosition, imageLayer,
				affectsScore);
		int entityId = ball.id;
		type.set(entityId, typeMask.getMask());
		generateBody(entityId, generateBodyDef(BodyType.DYNAMIC, x, y),
				generateBallFixtureDef(typeMask));
		position.set(entityId, x, y);
		initialPosition.set(entityId, x, y);
		generateImageLayer(entityId, image.getImage(), x, y);
		affectsScore.set(entityId, true);
		return ball;
	}

	public Entity createHub(float x, float y, Images image, TypeMask typeMask) {
		Entity hub = create(true);
		hub.add(type, body, position, imageLayer, affectsScore);
		int entityId = hub.id;
		type.set(entityId, typeMask.getMask());
		generateBody(entityId, generateBodyDef(BodyType.STATIC, x, y),
				generateHubFixtureDef(typeMask));
		position.set(entityId, x, y);
		generateImageLayer(entityId, image.getImage(), x, y);
		affectsScore.set(entityId, true);
		return hub;
	}

	public Entity createTrap(float x, float y, Images image) {
		Entity trap = create(true);
		trap.add(type, body, position, imageLayer, affectsScore);
		int entityId = trap.id;
		type.set(entityId, TypeMask.TRAP.getMask());
		generateBody(entityId, generateBodyDef(BodyType.STATIC, x, y),
				generateTrapFixtureDef());
		position.set(entityId, x, y);
		generateImageLayer(entityId, image.getImage(), x, y);
		affectsScore.set(entityId, true);
		return trap;
	}

	public Entity createBlock(float x, float y) {
		Entity block = create(true);
		block.add(type, body, position, imageLayer);
		int entityId = block.id;
		type.set(entityId, TypeMask.WALL.getMask());
		generateBody(entityId, generateBodyDef(BodyType.STATIC, x, y),
				generateWallFixtureDef());
		position.set(entityId, x, y);
		generateImageLayer(entityId, Images.MAPTILES.getImage().//
				subImage(0, 64, 32, 32), x, y);
		return block;
	}

	private void generateImageLayer(int entityId, Image image, float x, float y) {
		ImageLayer constructingImageLayer = graphics().createImageLayer(image);
		constructingImageLayer.setOrigin(image.width() / 2, image.height() / 2);
		constructingImageLayer.setTranslation((x + 0.5f) / SCALING_FACTOR,
				(y + 0.5f) / SCALING_FACTOR);
		graphics().rootLayer().add(constructingImageLayer);
		imageLayer.set(entityId, constructingImageLayer);
	}

	private BodyDef generateBodyDef(BodyType type, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(x, y);
		return bodyDef;
	}

	private FixtureDef generateWallFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(0.5f, 0.5f);
		fixtureDef.shape = polygon;
		fixtureDef.userData = TypeMask.WALL.getMask();
		return fixtureDef;
	}

	private FixtureDef generateTrapFixtureDef() {
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape polygon = new PolygonShape();
		polygon.setAsBox(0.5f, 0.5f);
		fixtureDef.shape = polygon;
		fixtureDef.userData = TypeMask.TRAP.getMask();
		return fixtureDef;
	}

	private FixtureDef generateBallFixtureDef(TypeMask userData) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.6f;
		CircleShape circle = new CircleShape();
		circle.setRadius(BALL_RADIUS);
		fixtureDef.shape = circle;
		fixtureDef.userData = userData.getMask();
		return fixtureDef;
	}

	private FixtureDef generateHubFixtureDef(TypeMask userData) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 5f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.6f;
		CircleShape circle = new CircleShape();
		circle.setRadius(BALL_RADIUS);
		fixtureDef.shape = circle;
		fixtureDef.userData = userData.getMask();
		fixtureDef.isSensor = true;
		return fixtureDef;
	}

	private void generateBody(int entityId, BodyDef bodyDef,
			FixtureDef fixtureDef) {
		Body currentBody = physicsWorld.createBody(bodyDef);
		currentBody.createFixture(fixtureDef);
		body.set(entityId, currentBody);
	}

	public void reset(int entityToReset) {
		Point into = new Point();
		initialPosition.get(entityToReset, into);
		Vec2 position = new Vec2(into.x, into.y);
		Body b = body.get(entityToReset);
		b.setTransform(position, b.getAngle());
		body.set(entityToReset, b);
		hud.changeScoreOnBallReset();
	}

	public void reset() {
		Body b = physicsWorld.getBodyList();
		while (b != null) {
			physicsWorld.destroyBody(b);
			b = b.getNext();
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
	}

	public void setLevel(Level newLevel) {
		currentLevel = newLevel;
	}

	public float getScalingFactor() {
		return SCALING_FACTOR;
	}

}