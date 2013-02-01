package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.GamingModel;
import mpi1213.isag.model.ReloadButton;

public class GamingView {

	private ViewState viewState;
	private MainApplet mainApplet;
	private int time;
	private GamingModel model;

	public GamingView(ViewState viewState, MainApplet mainApplet, GamingModel model) {
		this.viewState = viewState;
		this.mainApplet = mainApplet;
		this.time = 60 * 60; // 60fps * 60sec Spielzeit
		this.model = model;
	}

	public void drawGame() {
		if (time > 0) {
			// Je nach viewState das Game starten
			if (ViewState.SINGLEPLAYER == viewState) {
				this.drawSingleplayer();
			} else if (ViewState.COOP == viewState) {
				this.drawCOOP();
			} else if (ViewState.PVP == viewState) {
				this.drawPVP();
			}
		} else {
			// viewState aendern und zum Highscore oder Hauptmenue
			viewState = ViewState.STARTMENU;
		}

	}

	private void drawSingleplayer() {
		for (Integer key : model.getPlayers().keySet()) {
			// Player 1
			mainApplet.text("Player " + key, 20, 20);
			// Bullets
			int bulletStartX;
			if (model.getReloadButtons().get(key).getPosition().x > mainApplet.getWidth() / 2) {
				bulletStartX = mainApplet.getWidth() - 120;
			} else {
				bulletStartX = 0;
			}
			for (int j = 0; j < model.getPlayers().get(key).getMunition(); j++) {
				int x1 = bulletStartX + j * (mainApplet.getAmmo().width + 1);
				int y1 = 28;
				mainApplet.image(mainApplet.getAmmo(), x1, y1);
			}
			if (model.getPlayers().get(key).getMunition() <= 0 && mainApplet.millis() % 3 == 0) {
				mainApplet.textSize(30);
				mainApplet.fill(255, 0, 0);
				mainApplet.text("R E L O A D", bulletStartX + 26, 55);
				mainApplet.textSize(12);
			}
			// Missed shoots
			if (model.getPlayers().get(key).getShoot() != null) {
				if (model.getPlayers().get(key).getShoot().z > 0) {
					mainApplet.fill(255, 0, 0);
					mainApplet.rect(model.getPlayers().get(key).getShoot().x, model.getPlayers().get(key).getShoot().y, 3, 3);
					model.getPlayers().get(key).decreaseShoot();
				}
			}
			mainApplet.fill(255);

			// Points
			mainApplet.text(model.getPlayers().get(key).getPoints(), 20, 100);
		}
		/*
		 * //Player 1 mainApplet.text("Player 1", 20, 20); //Bullets for(int i
		 * =0; i< model.getPlayers().get(1).getMunition();i++){ int x1 = i * 10;
		 * int y1 = 30; int x2 = 8; int y2 = 15; mainApplet.rect(x1,y1,x2,y2); }
		 * //Missed shoots if(model.getPlayers().get(1).getShoot() != null){
		 * if(model.getPlayers().get(1).getShoot().z > 0){
		 * mainApplet.fill(255,0,0);
		 * mainApplet.rect(model.getPlayers().get(1).getShoot().x,
		 * model.getPlayers().get(1).getShoot().y, 3,3);
		 * model.getPlayers().get(1).decreaseShoot(); } } mainApplet.fill(255);
		 * // Points mainApplet.text(model.getPlayers().get(1).getPoints(), 20,
		 * 100);
		 */

		// Time
		if ((int) (time / 60) < 6) {
			mainApplet.fill(255, 0, 0);
			mainApplet.text("0 : " + (int) (time / 60), mainApplet.getWidth() / 2 - 20, 50);
		} else {
			mainApplet.text("0 : " + (int) (time / 60), mainApplet.getWidth() / 2 - 20, 50);
		}
		mainApplet.fill(255);
		time--;
		// Reload
		for (ReloadButton rBtn : model.getReloadButtons().values()) {
			mainApplet.rect(rBtn.getPosition().x, rBtn.getPosition().y, rBtn.getWidth(), rBtn.getHeight());
		}
	}

	private void drawCOOP() {
		// Player 1
		mainApplet.text("Player 1", 20, 20);

		// Player 2
		mainApplet.text("Player 2", 550, 20);

		// Bullets P1
		// Bullets P2

		// Points P1
		mainApplet.text(model.getPlayers().get(0).getPoints(), 20, 50);
		// Points P2
		mainApplet.text(model.getPlayers().get(1).getPoints(), 550, 50);

		// Time
		mainApplet.text("0 : " + (int) (time / 60), mainApplet.getWidth() / 2 - 20, 50);
		time--;// Reload P1

		mainApplet.color(150 * 75 + 25, 100 + 25, 0 + 25);
		mainApplet.rect(2, 2, 40, 20);
		// Reload P2
		mainApplet.color(150 * 75 + 25, 100 + 25, 0 + 25);
		mainApplet.rect(2, 2, 40, 20);
	}

	private void drawPVP() {
		// Player 1
		mainApplet.text("Player 1", 20, 20);

		// Player 2
		mainApplet.text("Player 2", 550, 20);

		// Bullets P1
		// Bullets P2

		// Points P1
		mainApplet.text(model.getPlayers().get(0).getPoints(), 20, 50);
		// Points P2
		mainApplet.text(model.getPlayers().get(1).getPoints(), 550, 50);

		// Time
		mainApplet.text("0 : " + (int) (time / 60), mainApplet.getWidth() / 2 - 20, 50);
		time--;
		// Trennline

		// Reload P1
		mainApplet.color(150 * 75 + 25, 100 + 25, 0 + 25);
		mainApplet.rect(2, 2, 40, 20);
		// Reload P2
		mainApplet.color(150 * 75 + 25, 100 + 25, 0 + 25);
		mainApplet.rect(2, 2, 40, 20);
	}

	private void drawFadenkreuzP1() {

	}

	private void drawFadenkreuzP2() {

	}

}
