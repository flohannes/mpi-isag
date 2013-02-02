package mpi1213.isag.main;

import mpi1213.isag.controller.InputControl;
import mpi1213.isag.model.Button;
import mpi1213.isag.model.GamingModel;
import mpi1213.isag.model.OnClickListener;
import mpi1213.isag.view.GamingView;
import mpi1213.isag.view.MenuView;
import mpi1213.isag.view.ViewState;
import processing.core.PApplet;
import processing.core.PVector;

public class MainApplet extends PApplet {
	private static final long serialVersionUID = 3497175479855519829L;
	private int windowWidth = 640;
	private int windowHeight = 480;
	private InputControl input;
	private GamingModel model;
	private GamingView gView;

	ViewState viewState = ViewState.STARTMENU;

	public void setup() {
		size(windowWidth, windowHeight);
		fill(255, 0, 0, 128);
		smooth();
		noStroke();
		ImageContainer.initImages(this);

		model = new GamingModel(this.getWidth(), this.getHeight());
		//model.addDemoEnemies(this.width, this.height);
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
					model.setVisibilityMultiplayerButtons(false);
				}
			});
		}
		System.out.println(color(255,0,0));
		System.out.println(color(0,0,255));
	}

	public void draw() {
		input.update();
		background(0);
		if (model.getPlayers().size() == 0) {
			paintKinectImage();
			return;
		}
		image(ImageContainer.backgroundImage, 0, 0);

		fill(255);
		viewState = model.update(viewState);
		paintPlayerShapes();
		
		switch (viewState) {
		case STARTMENU:
			MenuView.drawMainMenu(this, model);
			if (model.allPlayersReady()) {
				if (model.getPlayers().size() == 1) {
					viewState = ViewState.SINGLEPLAYER;
					gView = new GamingView(viewState, this, model);
				} else if (model.getPlayers().size() == 2) {
					viewState = ViewState.MULTIPLAYERMENU;
					model.setVisibilityMultiplayerButtons(true);
				}
				model.startGame();
				model.setVisibilityPlayerButtons(false);
			}
			break;
		case SINGLEPLAYER:
			paintEnemies();
			gView.drawGame();
			break;
		case MULTIPLAYERMENU:
			model.setVisibilityMultiplayerButtons(true);
			MenuView.drawMultiplayerMenu(this, model);
			break;
		case COOP:
			paintEnemies();
			break;
		case PVP:
			paintEnemies();
			break;
		default:
			break;
		}

		paintPlayerCrosshairs();
	}

	private void paintEnemies() {
		for (int i = 0; i < model.getEnemies().size(); i++) {
			fill(model.getEnemies().get(i).getR(), model.getEnemies().get(i).getG(), model.getEnemies().get(i).getB());
//			rect(model.getEnemies().get(i).getX(), model.getEnemies().get(i).getY(), model.getEnemies().get(i).getWidth(), model.getEnemies().get(i)
//					.getHeight());
			image(model.getEnemies().get(i).getImage(), model.getEnemies().get(i).getX(), model.getEnemies().get(i).getY());
		}
	}

	private void paintKinectImage() {
		if (input.isKinect()) {
			image(input.getDepthImage(), 0, 0);
			//tint(100);
		}
	}

	private void paintPlayerShapes() {
		for (Integer key : model.getPlayers().keySet()) {
			int color = model.getPlayers().get(key).getShapeColor();
			if (input.isKinect()) {
				int playerPixels[] = input.getPlayerPixels(key);
				loadPixels();
				for (int i = 0; i < playerPixels.length; i++) {
					if (playerPixels[i] != 0 && playerPixels[i] == key) {
						//pixels[i] = color(key * 75 + 25, 100 + 25, 0 + 25);
						pixels[i] = color;
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
