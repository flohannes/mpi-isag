package mpi1213.isag.main;

import mpi1213.isag.controller.InputControl;
import mpi1213.isag.model.Enemy;
import mpi1213.isag.model.GamingModel;
import mpi1213.isag.view.GamingView;
import mpi1213.isag.view.MenuView;
import mpi1213.isag.view.ViewState;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class MainApplet extends PApplet {
	private static final long serialVersionUID = 3497175479855519829L;
	private int windowWidth = 640;
	private int windowHeight = 480;
	private PImage soniImage;
	private InputControl input;
	private GamingModel model;
	private GamingView gView;
	
	ViewState viewState = ViewState.STARTMENU;

	public void setup() {
		size(windowWidth, windowHeight);
		fill(255, 0, 0, 128);
		smooth();
		noStroke();
		
		model = new GamingModel(this.getWidth(), this.getHeight());
		//model.addDemoEnemies(this.width, this.height);
		input = new InputControl(this, model);
		viewState = ViewState.STARTMENU;
	}

	public void draw() {
		background(0);
		fill(255);
		input.update();
		switch(viewState){
			case STARTMENU:
				MenuView.drawMainMenu(this, model);
				if(model.allPlayerReady()){
					if(model.getPlayers().size() == 1){
						viewState = ViewState.SINGLEPLAYER;
						model.addDemoEnemies(this.getWidth(), this.getHeight());
						gView = new GamingView(viewState, this);
					} else if (model.getPlayers().size() == 2){
						viewState = ViewState.MULTIPLAYERMENU;
					}
				}
				break;
			case SINGLEPLAYER:
				gView.drawGame();
				break;
			default:
				break;
		}
		
		paintKinectImage();
		paintPlayerShapes();
		paintEnemies();
		paintPlayerCrosshairs();
	}

	private void paintEnemies() {
		for(Enemy enemy:model.getEnemies()){
			enemy.move(this.getWidth(), this.getHeight());
			fill(enemy.getR(), enemy.getG(), enemy.getB());
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
