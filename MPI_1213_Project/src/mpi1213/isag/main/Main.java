package mpi1213.isag.main;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;
import SimpleOpenNI.SimpleOpenNI;


public class Main extends PApplet {
	private static final long serialVersionUID = 3497175479855519829L;
	
	SimpleOpenNI  soni;
	int windowWidth = 640;
	int windowHeight = 480;

	PImage soniImage;

	// Text font
	PFont f;

	// Framerate
	float fr = 100;

	int countdown = 90;

	int player2Id = 2;

	public void setup() {
	  countdown = 90;
	  soni = new SimpleOpenNI(this);
	  soni.enableDepth();
	  soni.enableRGB();
	  soni.setMirror(true);
	  soni.enableUser(SimpleOpenNI.SKEL_PROFILE_ALL);
	  
	  size(windowWidth,windowHeight);
	  fill(255,0,0,128);
	  smooth();
	  noStroke();
	}

	public void draw() {
	  background(0);
	  fill(255);
	  
	  soni.update();
	  soniImage = null;
	  try {
	    soniImage = (PImage) soni.depthImage();
	    //soniImage = soni.depthImage();
	 //   soniImage.resize(windowWidth, windowHeight);
	  } catch (Exception e) {}
	  image(soniImage,0,0);
	  
	  tint(100);
	  
	  //Track player 1
	  int player_one = 1;
	//  System.out.println(soni.isTrackingSkeleton(player_one));
	  
	  if (soni.isTrackingSkeleton(player_one)) {	        
	    PVector leftHand3d = new PVector();
	    PVector leftHand2d = new PVector();
	    //float confidence = soni.getJointPositionSkeleton(player_one,SimpleOpenNI.SKEL_LEFT_HAND, leftHand3d);
	    
	    soni.convertRealWorldToProjective(leftHand3d, leftHand2d);
	  }
	  
	  //Track player 2
	  int player_two = player2Id;
	  if (soni.isTrackingSkeleton(player_two)) {
	  
	    PVector rightHand3d = new PVector();
	    PVector rightHand2d = new PVector();
	    PVector head3d = new PVector();
	    PVector head2d = new PVector();
	    
	    //float confidence = soni.getJointPositionSkeleton(player_two,SimpleOpenNI.SKEL_RIGHT_HAND, rightHand3d);
	    //confidence = soni.getJointPositionSkeleton(player_two,SimpleOpenNI.SKEL_HEAD, head3d);
	    
	    soni.convertRealWorldToProjective(rightHand3d, rightHand2d);
	    soni.convertRealWorldToProjective(head3d, head2d);
	    
	 
	    if (head2d.x > (width - 100) || head2d.x < 100) {
	      soni.stopTrackingSkeleton(player_two);
	    }
	    
	  }	  
	}
	
	void onNewUser(int userId)
	{
	  soni.requestCalibrationSkeleton(userId, true);
	  System.out.println("user: " + userId);
	  
	}

	void onEndCalibration(int id, boolean successfull)
	{
	  if (successfull){
	    System.out.println("successful, id: " + id);
	    if(soni.getUsers().length < 2){
	      soni.startTrackingSkeleton(id);
	    } else {
	      soni.startTrackingSkeleton(id);
	      player2Id = id;  
	    }
	  }
	}
}
