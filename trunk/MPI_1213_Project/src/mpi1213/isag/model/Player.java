package mpi1213.isag.model;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import mpi1213.isag.controller.GestureRecognizer;
import processing.core.PVector;

public class Player implements MouseMotionListener{
	private PVector targetPosition = new PVector();
	private GestureRecognizer gestureRecognizer = new GestureRecognizer();
	
	public PVector getPosition(){
		return targetPosition;
	}

	public void setPosition(PVector hand2d) {
		targetPosition = hand2d;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		targetPosition.x = (float)e.getX();
		targetPosition.y = (float)e.getY();
	}

	public boolean recognizeGesture(PVector hand3d) {
		return gestureRecognizer.isPushGesture(hand3d);
	}
}
