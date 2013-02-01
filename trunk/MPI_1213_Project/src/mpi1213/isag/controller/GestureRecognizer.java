package mpi1213.isag.controller;

import java.util.ArrayList;
import java.util.List;

import processing.core.PVector;

public class GestureRecognizer {
	private static final long Z_TRESHOLD = 70;
	private static final long XY_THRESHOLD = 50;
	private static final long TIME_THRESHOLD = 200;
	private List<PVector> history = new ArrayList<PVector>();
	private long first, last;

	public GestureRecognizer() {
		first = last = 0;
	}

	public boolean isPushGesture(PVector vector) {
		if (history.isEmpty()) {
			first = last = System.currentTimeMillis();
			history.add(vector);
		} else {
			if (checkPossiblePush(vector, history.get(history.size() - 1))) {
				history.add(vector);
			} else {
				reset();
			}
			last = System.currentTimeMillis();
			return checkPush();
		}
		return false;
	}

	private boolean checkPush() {
		if (last - first < TIME_THRESHOLD) {
			if (Math.abs(history.get(0).z - history.get(history.size() - 1).z) > Z_TRESHOLD) {
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
		first = last = 0;
		history = new ArrayList<PVector>();
	}
}
