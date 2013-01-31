package mpi1213.isag.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import mpi1213.isag.model.GamingModel;
import mpi1213.isag.model.Player;
import mpi1213.isag.model.PushListener;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;

public class InputControl implements MouseMotionListener, MouseListener{	
	private InputMode inputMode = InputMode.KINECT;
	private SimpleOpenNI context;
	private GamingModel model;
	private List<PushListener> listeners;
	
	public InputControl(PApplet applet, GamingModel model) {
		context = new SimpleOpenNI(applet);
		this.model = model;
		listeners = new ArrayList<PushListener>();
		listeners.add(model);
		
		if (SimpleOpenNI.deviceCount() < 1) {
			inputMode = InputMode.MOUSE;
			model.addPlayer(0);
			applet.addMouseMotionListener(this);
			applet.addMouseListener(this);
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
						notifyPushListeners(hand2d, model.getPlayers().get(key));
					}
				}	
			}
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
	
	private void notifyPushListeners(PVector vector, Player player){
		for(PushListener listener:listeners){
			listener.pushed(vector, player);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		notifyPushListeners(new PVector(e.getPoint().x, e.getPoint().y), model.getPlayers().get(0));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		model.getPlayers().get(0).setPosition(new PVector(e.getPoint().x, e.getPoint().y));
	}
}
