package mpi1213.isag.model;

import mpi1213.isag.controller.GestureRecognizer;
import processing.core.PVector;

public class Player implements OnClickListener {

	private static int MAX_MUNITION = 10;

	private PVector targetPosition = new PVector();
	private GestureRecognizer gestureRecognizer = new GestureRecognizer();
	private PVector shoot;
	private int points;
	private int munition;
	private boolean isReady = false;
	private int shapeColor = 1;

	public Player() {
		munition = 10;
	}

	public PVector getPosition() {
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

	public void reloadMunition() {
		this.munition = MAX_MUNITION;
	}

	public void increasePoints(int pointsToAdd) {
		points += pointsToAdd;
	}

	public boolean isMunitionEmpty() {
		if (munition == 0) {
			return true;
		}
		return false;
	}

	public boolean isReady() {
		return isReady;
	}

	@Override
	public void onClick(Button button) {
		if (button instanceof ReloadButton) {
			reloadMunition();
		} else if (button instanceof PlayerButton) {
			isReady = !isReady;
		}
	}

	public PVector getShoot() {
		return shoot;
	}

	public void setShoot(PVector shoot) {
		this.shoot = shoot;
		this.shoot.z = 100;
	}

	public void decreaseShoot() {
		this.shoot.z--;
	}

	public void setReady(boolean b) {
		isReady = b;
	}
	
	public void setShapeColor(int color){
		shapeColor = color;
	}

	public int getShapeColor() {
		return shapeColor;
	}
}
