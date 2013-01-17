package mpi1213.isag.model;

import mpi1213.isag.controller.GestureRecognizer;
import processing.core.PVector;

public class Player{
	
	private static int MAX_MUNITION = 10;
	
	private PVector targetPosition = new PVector();
	private GestureRecognizer gestureRecognizer = new GestureRecognizer();
	private int points;
	private int munition;
	
	public PVector getPosition(){
		return targetPosition;
	}

	public void setPosition(PVector hand2d) {
		targetPosition = hand2d;
	}

	public boolean recognizeGesture(PVector hand3d) {
		return gestureRecognizer.isPushGesture(hand3d);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getMunition() {
		return munition;
	}

	public void setMunition(int munition) {
		this.munition = munition;
	}
	
	public void increasePoints(int pointsToAdd){
		points += pointsToAdd;
	}
	
	public boolean isMunitionEmpty(){
		if(munition == 0){
			return true;
		}
		return false;
	}
}