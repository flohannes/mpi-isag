package mpi1213.isag.main;

import mpi1213.isag.controller.InputControl;
import mpi1213.isag.model.Enemy;
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
		model.addDemoEnemies();
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
		paintKinectImage();
		paintPlayerShapes();
		paintEnemies();
		paintPlayerCrosshairs();
	}

	private void paintEnemies() {
		for(Enemy enemy:model.getEnemies()){
			enemy.move(this.getWidth(), this.getHeight());
			rect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
		}
	}

	private void paintKinectImage() {
		if (input.isKinect()) {
			image(input.getDepthImage(), 0, 0);
			tint(100);
		}
	}
	
	private void paintPlayerShapes() {
		for (Integer key : model.getPlayers().keySet()) {
			if (input.isKinect()) {
				int playerPixels[] = input.getPlayerPixels(key);
				loadPixels();
				for (int i = 0; i < playerPixels.length; i++) {
					if (playerPixels[i] != 0 && playerPixels[i] == key) {
						pixels[i] = color(key * 75+25, 100+25, 0+25);
					}
				}
				updatePixels();
			}
		}
	}
	
	private void paintPlayerCrosshairs() {
		for (Integer key : model.getPlayers().keySet()) {
			PVector pos = model.getPlayers().get(key).getPosition();
			fill(key * 75, 100, 0);
			rect(pos.x - 50, pos.y - 5, 100, 10);
			rect(pos.x - 5, pos.y - 50, 10, 100);
		}
	}

	public void onNewUser(int userId) {
		input.newPlayer(userId);
	}

	public void onLostUser(int userId) {
		input.removePlayer(userId);
	}

	public void onEndCalibration(int id, boolean successfull) {
		input.endCalibration(id, successfull);
	}
}
