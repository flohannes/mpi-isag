package mpi1213.isag.model;

import processing.core.PVector;

public class Player {
	private PVector targetPosition = new PVector();
	
	public PVector getPosition(){
		return targetPosition;
	}

	public void setPosition(PVector hand2d) {
		targetPosition = hand2d;
	}
}
