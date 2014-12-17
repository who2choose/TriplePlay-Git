package edu.bsu.dachristman.systems;

import edu.bsu.dachristman.components.Lazor;
import playn.core.PlayN;

public class InputSystem implements playn.core.Pointer.Listener {

	private Lazor lazor;
	
	public InputSystem(Lazor lazor) {
		PlayN.pointer().setListener(this);
		this.lazor = lazor;
	}

	public void destroy() {
		PlayN.pointer().setListener(null);
	}

	@Override
	public void onPointerStart(playn.core.Pointer.Event event) {
		lazor.move(event.x(), event.y());
	}

	@Override
	public void onPointerEnd(playn.core.Pointer.Event event) {

	}

	@Override
	public void onPointerDrag(playn.core.Pointer.Event event) {
		
	}

	@Override
	public void onPointerCancel(playn.core.Pointer.Event event) {

	}

}