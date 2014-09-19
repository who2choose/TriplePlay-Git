package edu.bsu.dachristman.core;

import static playn.core.PlayN.assets;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.core.util.Clock;
import react.Value;
import tripleplay.entity.Component;
import tripleplay.entity.Entity;
import tripleplay.entity.System;
import tripleplay.entity.World;

public class SystemWorld extends World {

	private boolean gameOver = false;
	private boolean collided = false;
	private float fadeRate = 0.025f;

	private final Component.Generic<ImageLayer> sprite = new Component//
			.Generic<ImageLayer>(this);
	private final Component.XY velocity = new Component.XY(this);
	private final Component.FScalar scoreRate = new Component.FScalar(this);

	public final Value<Float> score = Value.create(0f);

	public final GroupLayer.Clipped layer = PlayN.graphics().createGroupLayer(
			right(), bottom());

	private final System physicsSystem = new System(this, 1) {

		@Override
		protected void update(int deltaMS, Entities entities) {
			for (int i = 0; i < entities.size(); i++) {
				int ballId = entities.get(i);
				ImageLayer ballLayer = sprite.get(ballId);
				float dx = velocity.getX(ballId);
				float dy = velocity.getY(ballId);
				float accelX = 0;
				float accelY = 0;

				dx += accelX * deltaMS / 1000f;
				dy += accelY * deltaMS / 1000f;

				float oldX = ballLayer.tx();
				float oldY = ballLayer.ty();
				float newX = oldX + dx * deltaMS / 1000f;
				float newY = oldY + dy * deltaMS / 1000f;

				if (collided) {
					// The Fadeaway
					if (ballLayer.alpha() < 0) {
						ballLayer.destroy();
					}
					ballLayer.setAlpha(ballLayer.alpha() - fadeRate);
				}

				if (accelY != 0) {
					accelY += 2 * deltaMS / 1000f;
				}

				// calculateDirection();
				if (!ballLayer.destroyed()) {
					if (!detectWindowCollision(ballLayer, dx, dy, accelY,
							ballId)) {
						ballLayer.setTranslation(newX, newY);
					}
				}
			}
		}

		private boolean detectWindowCollision(ImageLayer ballLayer, float dx,
				float dy, float accelY, int ballId) {
			boolean wallCollision = false;
			if (ballLayer.tx() + ballLayer.width() > 640) {
				ballLayer.setTx(639 - ballLayer.width());
				if (dx > 0) {
					wallCollision = true;
					dx = -dx;
					velocity.setX(ballId, dx);
				}
			}
			if (ballLayer.tx() < 0) {
				ballLayer.setTx(1);
				if (dx < 0) {
					wallCollision = true;
					dx = -dx;
					velocity.setX(ballId, dx);
				}
			}
			if (ballLayer.ty() + ballLayer.height() > 480) {
				ballLayer.setTy(479 - ballLayer.height());
				if (dy > 0) {
					wallCollision = true;
					dy = -dy;
					velocity.setY(ballId, dy);
				}
			}
			if (ballLayer.ty() < 0) {
				ballLayer.setTy(1);
				if (dy < 0) {
					wallCollision = true;
					dy = -dy;
					velocity.setY(ballId, dy);
				}
			}
			return wallCollision;
		}

		@Override
		protected boolean isInterested(Entity entity) {
			return entity.has(velocity) && entity.has(sprite);
		}
	};

	public void move(float dx, float dy, int id) {
		velocity.setX(id, dx + velocity.getX(id));
		velocity.setY(id, dy + velocity.getY(id));
	}

	public void detectCollision(ImageLayer ball1, ImageLayer ball2) {

		boolean xCollideLeft = (ball1.tx() + ball1.width()) >= ball2.tx();
		boolean xCollideRight = (ball1.tx() <= ball2.tx() + ball2.width());
		boolean yCollideTop = (ball1.ty() + ball1.height()) >= ball2.ty();
		boolean yCollideBottom = ball1.ty() <= (ball2.ty() + ball2.height());

		if (xCollideLeft && xCollideRight) {
			if (yCollideTop && yCollideBottom) {
				collided = true;
			}
		}
		// collided = false;
	}

	private void addScoreForDistanceToBall() {
		float scaleMax = 10;
		float distance = (float) (Math.sqrt(Math.pow(sprite.get(1).ty()
				- sprite.get(2).ty(), 2)
				+ Math.pow(sprite.get(1).tx() - sprite.get(2).tx(), 2)));
		float points = (1 / distance) * scaleMax;
		score.update(score.get() + points);
	}

	public float gravity() {
		return 100;
	}

	public int top() {
		return 0;
	}

	public int left() {
		return 0;
	}

	public int right() {
		return 640;
	}

	public int bottom() {
		return 480;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void addBall(float x, float y) {
		Entity ball = create(true);
		ball.add(sprite, velocity, scoreRate);
		ImageLayer imageLayer = PlayN.graphics().createImageLayer(
				assets().getImage("images/shapeBallRed.png"));
		imageLayer.setTranslation(x, y);
		layer.add(imageLayer);
		sprite.set(ball.id, imageLayer);
		velocity.set(ball.id, 0, 0);
	}

	public void addBall(float x, float y, float dx, float dy) {
		Entity ball = create(true);
		ball.add(sprite, velocity, scoreRate);
		ImageLayer imageLayer = PlayN.graphics().createImageLayer(
				assets().getImage("images/shapeBallRed.png"));
		imageLayer.setTranslation(x, y);
		layer.add(imageLayer);
		sprite.set(ball.id, imageLayer);
		velocity.set(ball.id, dx, dy);
	}

	@Override
	public void update(int delta) {
		if (!gameOver) {
			if (!sprite.get(1).destroyed()) {
				detectCollision(sprite.get(1), sprite.get(2));
			}
			super.update(delta);
			if (collided) {
				gameOver = true;
				score.update(score.get());
				// animateScoreLabel();
			}
			addScoreForDistanceToBall();
		}
	}

	@Override
	public void paint(Clock clock) {
		super.paint(clock);
	}

}