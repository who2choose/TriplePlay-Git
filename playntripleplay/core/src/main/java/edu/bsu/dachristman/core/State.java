package edu.bsu.dachristman.core;

public interface State {

	public void onEnter();
	public void update(int deltaMS);
	
}