package edu.bsu.dachristman.systems;

import edu.bsu.dachristman.components.Kat;
import edu.bsu.dachristman.components.Lazor;
import playn.core.PlayN;

public class InputSystem implements playn.core.Pointer.Listener {

	private Kat kat;
	private Lazor lazor;
	
	public InputSystem(Kat kat, Lazor lazor) {
		PlayN.pointer().setListener(this);
		this.kat = kat;
		this.lazor = lazor;
	}

	public void destroy() {
		PlayN.pointer().setListener(null);
	}

	@Override
	public void onPointerStart(playn.core.Pointer.Event event) {
		kat.nextState();
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