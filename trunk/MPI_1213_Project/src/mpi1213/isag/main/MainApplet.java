package mpi1213.isag.main;

import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JFrame;

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

	public void setup() {
		size(windowWidth, windowHeight);

		// Test
//		JFrame f = new JFrame();
//		f.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
//		f.setExtendedState(Frame.MAXIMIZED_BOTH);
//		f.setUndecorated(true);
//		f.add(this);
//		f.setVisible(true);

		fill(255, 0, 0, 128);
		smooth();
		noStroke();
		ImageContainer.initImages(this);

		model = new GamingModel(this.getWidth(), this.getHeight());
		input = new InputControl(this, model);

		for (Button btn : model.getMulitplayerButtons()) {
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(Button button) {
					if (button.getText().equals("Co-op")) {
						model.setViewState(ViewState.COOP);
					} else if (button.getText().equals("PvP")) {
						model.setViewState(ViewState.PVP);
					}
					model.setVisibilityMultiplayerButtons(false);
					model.startGame();
				}
			});
		}
		
		System.out.println(color(161,0,30));
		System.out.println(color(0,67,167));
	}

	public void draw() {
		input.update();
		model.update();
		
		background(0);
		if (model.getPlayers().size() == 0) {
			paintKinectImage();
			return;
		}
		
//		PImage img = ImageContainer.getBackgroundInstance();
//		paintPlayerShapes(img);
//		img.resize(this.getWidth(), this.getHeight());
//		image(img,0,0);
		
		image(ImageContainer.backgroundImage, 0, 0);
		paintPlayerShapes();
		
		fill(255);
		
		

		switch (model.getViewState()) {
		case STARTMENU:
			MenuView.drawMainMenu(this, model);
			if (model.allPlayersReady()) {
				if (model.getPlayers().size() == 1) {
					model.setViewState(ViewState.SINGLEPLAYER);
					model.startGame();
				} else if (model.getPlayers().size() == 2) {
					model.setViewState(ViewState.MULTIPLAYERMENU);
					model.refreshDelay();
					model.setVisibilityMultiplayerButtons(true);
				}
				model.setVisibilityPlayerButtons(false);
			}
			break;
		case SINGLEPLAYER:
			paintEnemies();
			GamingView.drawGame(model.getViewState(), model, this);
			break;
		case MULTIPLAYERMENU:
			model.setVisibilityMultiplayerButtons(true);
			MenuView.drawMultiplayerMenu(this, model);
			break;
		case COOP:
			paintEnemies();
			GamingView.drawGame(model.getViewState(), model, this);
			break;
		case PVP:
			paintEnemies();
			GamingView.drawGame(model.getViewState(), model, this);
			break;
		case HIGHSCORE_SINGLE:
			MenuView.drawHighScore(this, model);
			break;
		case HIGHSCORE_COOP:
			MenuView.drawHighScoreCoop(this, model);
			break;
		default:
			break;
		}

		paintPlayerCrosshairs();
	}

	private void paintEnemies() {
		for (int i = 0; i < model.getEnemies().size(); i++) {
			fill(255, 255, 255);
			// rect(model.getEnemies().get(i).getX(),
			// model.getEnemies().get(i).getY(),
			// model.getEnemies().get(i).getWidth(), model.getEnemies().get(i)
			// .getHeight());
			image(model.getEnemies().get(i).getImage(), model.getEnemies().get(i).getX(), model.getEnemies().get(i).getY());
		}
	}

	private void paintKinectImage() {
		if (input.isKinect()) {
//			try {
//				PImage img = new PImage(input.getDepthImage().getImage());
////				PImage img = (PImage) input.getDepthImage().clone();
//				img.resize(this.getWidth(), this.getHeight());
//				image(img, 0, 0);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				image(input.getDepthImage(), 0, 0);
//			}
			
			image(input.getDepthImage(), 0, 0);
			// tint(100);
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
						// pixels[i] = color(key * 75 + 25, 100 + 25, 0 + 25);
						pixels[i] = color;
					}
				}
				updatePixels();
			}
		}
	}
	
	private void paintPlayerShapes(PImage img) {		
		for (Integer key : model.getPlayers().keySet()) {
			int color = model.getPlayers().get(key).getShapeColor();
			if (input.isKinect()) {
				int playerPixels[] = input.getPlayerPixels(key);
				loadPixels();
				for (int i = 0; i < playerPixels.length; i++) {
					if (playerPixels[i] != 0 && playerPixels[i] == key) {
						// pixels[i] = color(key * 75 + 25, 100 + 25, 0 + 25);
						img.pixels[i] = color;
					}
				}
				updatePixels();
			}
		}
	}

	private void paintPlayerCrosshairs() {
		for (Integer key : model.getPlayers().keySet()) {
			PVector pos = model.getPlayers().get(key).getTargetPosition();
			fill(model.getPlayers().get(key).getCrossColor());
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
