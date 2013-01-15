package mpi1213.isag.controller;

import java.util.ArrayList;
import java.util.List;

import mpi1213.isag.model.Model;
import mpi1213.isag.model.PushListener;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class InputControl {	
	private InputMode inputMode = InputMode.KINECT;
	private SimpleOpenNI context;
	private Model model;
	private List<PushListener> listeners;
	

	public InputControl(PApplet applet, Model model) {
		context = new SimpleOpenNI(applet);
		this.model = model;
		listeners = new ArrayList<PushListener>();
		listeners.add(model);
		
		if (SimpleOpenNI.deviceCount() < 1) {
			inputMode = InputMode.MOUSE;
			model.addPlayer(0);
			applet.addMouseMotionListener(model.getPlayers().get(0));
		} else {
			context.enableDepth();
			context.enableRGB();
			context.setMirror(true);
			context.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);		}
	}

	public void update() {
		if (inputMode == InputMode.KINECT) {
			context.update();
			
			for(Integer key:model.getPlayers().keySet()){
				if (context.isTrackingSkeleton(key)) {
					PVector hand3d = new PVector();
					PVector hand2d = new PVector();
					context.getJointPositionSkeleton(key,SimpleOpenNI.SKEL_RIGHT_HAND, hand3d);
					context.convertRealWorldToProjective(hand3d, hand2d);
					model.getPlayers().get(key).setPosition(hand2d);
					if(model.getPlayers().get(key).recognizeGesture(hand3d)){
						System.out.println("pushed! " + System.currentTimeMillis());
						notifyPushListeners(hand2d);
					}
				}	
			}
		} else {
		}
	}
	
	public PImage getDepthImage(){
		if(inputMode == InputMode.KINECT){
			return (PImage) context.depthImage();
		} else {
			return null;
		}
	}

	public void newPlayer(int userId) {
		context.requestCalibrationSkeleton(userId, true);
		System.out.println("user: " + userId);
	}

	public void endCalibration(int id, boolean successfull) {
		if (successfull) {
			System.out.println("successful, id: " + id);
			context.startTrackingSkeleton(id);
			model.addPlayer(id);			
		}
	}

	public void removePlayer(int userId) {
		model.removePlayer(userId);
		context.stopTrackingSkeleton(userId);
		System.out.println("player " + userId + " lost.");
	}

	public boolean isKinect() {
		if(inputMode == InputMode.KINECT){
			return true;
		}
		return false;
	}
	
	public int[] getPlayerPixels(int id){
		return context.getUsersPixels(id);
	}

	public void addPushListener(PushListener listener){
		listeners.add(listener);
	}
	
	private void notifyPushListeners(PVector vector){
		for(PushListener listener:listeners){
			listener.pushed(vector);
		}
	}
}
