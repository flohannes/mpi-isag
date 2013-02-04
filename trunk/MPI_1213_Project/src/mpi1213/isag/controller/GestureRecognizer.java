package mpi1213.isag.controller;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

public class GestureRecognizer {
	private static final long Z_TRESHOLD = 70;
	private static final long XY_THRESHOLD = 50;
	private static final long TIME_THRESHOLD = 200;
	private List<PVector> pushHistory = new ArrayList<PVector>();
	private long pushFirst, pushLast;
	
	private List<PVector> jumpHistory = new ArrayList<PVector>();
	private long jFirst, jLast;

	public GestureRecognizer() {
		pushFirst = pushLast = 0;
		jFirst = jLast = 0;
	}

	public boolean isPushGesture(PVector vector) {
		if (pushHistory.isEmpty()) {
			pushFirst = pushLast = System.currentTimeMillis();
			pushHistory.add(vector);
		} else {
			if (checkPossiblePush(vector, pushHistory.get(pushHistory.size() - 1))) {
				pushHistory.add(vector);
			} else {
				reset();
			}
			pushLast = System.currentTimeMillis();
			return checkPush();
		}
		return false;
	}

	private boolean checkPush() {
		if (pushLast - pushFirst < TIME_THRESHOLD) {
			if (Math.abs(pushHistory.get(0).z - pushHistory.get(pushHistory.size() - 1).z) > Z_TRESHOLD) {
				reset();
				return true;
			}
		}
		return false;
	}

	private boolean checkPossiblePush(PVector current, PVector previous) {
		// check whether z direction and other axes nearly steady
		if (previous.z > current.z && Math.abs(previous.x - current.x) < XY_THRESHOLD && Math.abs(previous.y - current.y) < XY_THRESHOLD) {
			return true;
		}
		return false;
	}

	private void reset() {
		pushFirst = pushLast = 0;
		pushHistory = new ArrayList<PVector>();
	}
	
	public boolean isUpAndDownGesture(PVector position){
		return false;
	}
}
