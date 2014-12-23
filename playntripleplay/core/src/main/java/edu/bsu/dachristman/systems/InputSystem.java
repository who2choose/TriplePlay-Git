package edu.bsu.dachristman.systems;

import edu.bsu.dachristman.components.Kat;
import edu.bsu.dachristman.components.Lazor;
import playn.core.PlayN;

public class InputSystem implements playn.core.Pointer.Listener {

	private Lazor lazor;
	private Kat kat;
	
	public InputSystem(Lazor lazor, Kat kat) {
		PlayN.pointer().setListener(this);
		this.lazor = lazor;
		this.kat = kat;
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
//		kat.nextState();
	}

	@Override
	public void onPointerDrag(playn.core.Pointer.Event event) {
		
	}

	@Override
	public void onPointerCancel(playn.core.Pointer.Event event) {

	}

}