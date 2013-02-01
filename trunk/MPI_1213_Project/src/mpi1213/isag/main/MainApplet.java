package mpi1213.isag.main;

import mpi1213.isag.controller.InputControl;
import mpi1213.isag.model.Button;
import mpi1213.isag.model.GamingModel;
import mpi1213.isag.model.OnClickListener;
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
	private InputControl input;
	private GamingModel model;
	private GamingView gView;
	private PImage backgroundImg;
	private PImage ammo;
	// images
	private static PImage zielscheibeRot;
	private static PImage zielscheibeGruen;
	private static PImage zielscheibeBlau;
	private static PImage alien1;
	private static PImage alien2;
	private static PImage alien3;
	private static PImage alien4;

	ViewState viewState = ViewState.STARTMENU;

	public void setup() {
		size(windowWidth, windowHeight);
		fill(255, 0, 0, 128);
		smooth();
		noStroke();
		initImages();

		model = new GamingModel(this.getWidth(), this.getHeight());
		// model.addDemoEnemies(this.width, this.height);
		input = new InputControl(this, model);
		viewState = ViewState.STARTMENU;

		for (Button btn : model.getMulitplayerButtons()) {
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(Button button) {
					if (button.getText().equals("Co-op")) {
						viewState = ViewState.COOP;
					} else if (button.getText().equals("PvP")) {
						viewState = ViewState.PVP;
					}
				}
			});
		}
	}

	private void initImages() {
		backgroundImg = loadImage("Images/sternenhimmel.jpg");
		backgroundImg.resize(windowWidth, windowHeight);
		ammo = loadImage("Images/ammo.png");
		ammo.resize(windowWidth / 45, 0);
		zielscheibeBlau = loadImage("Images/target_blue.png");
		zielscheibeRot = loadImage("Images/target_red.png");
		zielscheibeGruen = loadImage("Images/target_green.png");
		// static alien images
		alien1 = loadImage("Images/alien_01.png");
		alien2 = loadImage("Images/alien_02.png");
		alien3 = loadImage("Images/alien_03.png");
		alien4 = loadImage("Images/alien_04.png");
	}

	public void draw() {
		background(0);
		image(backgroundImg, 0, 0);

		fill(255);
		input.update();
		paintPlayerShapes();

		switch (viewState) {
		case STARTMENU:
			MenuView.drawMainMenu(this, model);
			if (model.allPlayerReady()) {
				if (model.getPlayers().size() == 1) {
					viewState = ViewState.SINGLEPLAYER;
					model.addDemoEnemies(this.getWidth(), this.getHeight());
					gView = new GamingView(viewState, this, model);
				} else if (model.getPlayers().size() == 2) {
					viewState = ViewState.MULTIPLAYERMENU;
				}
			}
			break;
		case SINGLEPLAYER:
			gView.drawGame();
			break;
		case MULTIPLAYERMENU:
			MenuView.drawMultiplayerMenu(this, model);
			break;
		default:
			break;
		}

		paintEnemies();
		paintPlayerCrosshairs();
		if (model.getPlayers().size() == 0) {
			paintKinectImage();
		}
	}

	private void paintEnemies() {
		for (int i = 0; i < model.getEnemies().size(); i++) {
			model.getEnemies().get(i).move(this.getWidth(), this.getHeight());
			fill(model.getEnemies().get(i).getR(), model.getEnemies().get(i).getG(), model.getEnemies().get(i).getB());
			rect(model.getEnemies().get(i).getX(), model.getEnemies().get(i).getY(), model.getEnemies().get(i).getWidth(), model.getEnemies().get(i)
					.getHeight());
			image(model.getEnemies().get(i).getImage(), model.getEnemies().get(i).getX(), model.getEnemies().get(i).getY());
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
						pixels[i] = color(key * 75 + 25, 100 + 25, 0 + 25);
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

	public PImage getAmmo() {
		return ammo;
	}

	public static PImage getZielscheibeRot() {
		return zielscheibeRot;
	}

	public static PImage getZielscheibeGruen() {
		return zielscheibeGruen;
	}

	public static PImage getZielscheibeBlau() {
		return zielscheibeBlau;
	}

	public static PImage getRandomAlienImage() {
		int random = (int) (1 + Math.random() * (4 - 1 + 1));

		switch (random) {
		case 1:
			return alien1;
		case 2:
			return alien2;
		case 3:
			return alien3;
		case 4:
			return alien4;
		default:
			return null;
		}
	}
}
