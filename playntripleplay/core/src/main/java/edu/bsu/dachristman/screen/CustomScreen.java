package edu.bsu.dachristman.screen;

import playn.core.Keyboard;
import playn.core.Pointer;
import tripleplay.game.UIAnimScreen;

public abstract class CustomScreen extends UIAnimScreen{
	
	public abstract void onClickDown(Pointer.Event e);
	
	public abstract void onClickUp(Pointer.Event e);
	
	public abstract void onPressDown(Keyboard.Event e);
	
	public abstract void onPressUp(Keyboard.Event e);
	
}
