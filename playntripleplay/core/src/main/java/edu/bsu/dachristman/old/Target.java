package edu.bsu.dachristman.old;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import playn.core.Image;
import playn.core.ImageLayer;
import edu.bsu.dachristman.core.World;

public class Target extends Entity{

	private final int WIDTH = 32, HEIGHT = 32;
	private float x, y;
	public ImageLayer layer;
	public static final Image IMAGE = assets().getImage(
			"images/shapeSquareBlue.png");
	private int number;
	
	public Target(float x, float y, World world, int number) {
		super(x, y);
		
		this.x = x;
		this.y = y;
		
		layer = graphics().createImageLayer(IMAGE);
		layer.setTx(x);
		layer.setTy(y);
		
		this.number = number;
	}
	
	public Target(World world){
		super(0, 0);
		
		this.x = 0;
		this.y = 0;
		
		layer = graphics().createImageLayer(IMAGE);
		layer.setTx(x);
		layer.setTy(y);
	}

	@Override
	public void draw() {
		graphics().rootLayer().add(layer);
	}

	@Override
	public void update(int deltaMS) {
		super.update(deltaMS);
	}

	@Override
	public void clear() {
		layer.destroy();
	}

	@Override
	public float getHeight() {
		return HEIGHT;
	}

	@Override
	public float getWidth() {
		return WIDTH;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
	
	public Type type(){
		return Type.TARGET;
	}
	
	public int number() {
		return number;
	}
	
}
