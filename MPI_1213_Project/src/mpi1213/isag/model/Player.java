package mpi1213.isag.model;

import mpi1213.isag.controller.GestureRecognizer;
import processing.core.PVector;

public class Player implements OnClickListener {

	private static int MAX_MUNITION = 10;

	private PVector targetPosition = new PVector();
	private PVector hipPosition = null;
	private GestureRecognizer gestureRecognizer = new GestureRecognizer();
	private PVector shoot;
	private int points;
	private int munition;
	private boolean isReady = false;
	private boolean noMorePoints = false;
	private int shapeColor = 1;
	private int crossColor = 1;
	
	private boolean isUnlimited = false;

	private long unlimitedAmmoTime;

	public Player() {
		munition = 10;
	}

	public PVector getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(PVector hand2d) {
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

	public void reloadMunition() {
		this.munition = MAX_MUNITION;
	}

	public void increasePoints(int pointsToAdd) {
		if(!noMorePoints){
			points += pointsToAdd;
		}
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
			((PlayerButton)button).setReady(isReady);
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
	
//	public void setShapeColor(int color){
//		shapeColor = color;
//	}
	
	public void setColors(int shape, int cross){
		shapeColor = shape;
		crossColor = cross;
	}

	public int getShapeColor() {
		return shapeColor;
	}

	public PVector getHipPosition() {
		return hipPosition;
	}

	public void setHipPosition(PVector hipPosition) {
		this.hipPosition = hipPosition;
	}

	public void unlimitedAmmo(long time) {
		isUnlimited = true;
		unlimitedAmmoTime = System.currentTimeMillis() + time;
	}

	public void decreaseMunition() {
		if(!isUnlimited){
			munition--;
		} else {
			if(System.currentTimeMillis() > unlimitedAmmoTime){
				isUnlimited = false;
			}
		}
	}
	
	public boolean isUnlimited(){
		return isUnlimited;
	}
	
	public PVector getPositionForSideDecision(){
		if (hipPosition == null){
			return targetPosition;
		} else {
			return hipPosition;
		}
	}
	
	public int getCrossColor(){
		return crossColor;
	}
	
	public void setNoMorePoints(boolean value){
		noMorePoints = value;
	}
}
