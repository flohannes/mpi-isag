package mpi1213.isag.main;

import mpi1213.isag.controller.InputControl;
import mpi1213.isag.model.Model;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class MainApplet extends PApplet {
	private static final long serialVersionUID = 3497175479855519829L;
	int windowWidth = 640;
	int windowHeight = 480;
	PImage soniImage;
	InputControl input;
	Model model;

	public void setup() {
		model = new Model();
		input = new InputControl(this, model);
		size(windowWidth, windowHeight);
		fill(255, 0, 0, 128);
		smooth();
		noStroke();
	}

	public void draw() {
		background(0);
		fill(255);
		input.update();
		image(input.getDepthImage(), 0, 0);
		tint(100);
		paintPlayers();
	}

	private void paintPlayers() {
		for(Integer key:model.getPlayers().keySet()){
			PVector pos = model.getPlayers().get(key).getPosition();
			fill(key*75, 100, 0);
			ellipse(pos.x, pos.y, 100, 100);
		}
	}

	public void onNewUser(int userId) {
		input.newPlayer(userId);
	}
	
	public void onLostUser(int userId){
		input.removePlayer(userId);
	}

	public void onEndCalibration(int id, boolean successfull) {
		input.endCalibration(id, successfull);
	}
}
