package mpi1213.isag.view;

import mpi1213.isag.main.ImageContainer;
import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.GamingModel;
import mpi1213.isag.model.Player;
import processing.core.PVector;

public class GamingView {

	public static void drawGame(ViewState viewState, GamingModel model, MainApplet applet) {
		// Je nach viewState das Game starten
		if (ViewState.SINGLEPLAYER == viewState || viewState == ViewState.COOP) {
			drawCommonScreen(applet, model);
		} else if (ViewState.PVP == viewState) {
			drawPVP(applet, model);
		}
	}

	private static void drawCommonScreen(MainApplet applet, GamingModel model) {
		drawHUD(applet, model);

		// Time
		if (model.getGameTime() < 6) {
			applet.fill(255, 0, 0);
			applet.text("0 : " + model.getGameTime(), applet.getWidth() / 2, 50);
		} else {
			applet.text("0 : " + model.getGameTime(), applet.getWidth() / 2, 50);
		}
		applet.fill(255);
	}

	private static void drawHUD(MainApplet applet, GamingModel model) {
		// determine position
		Player leftPlayer = null;
		for (Player player : model.getPlayers().values()) {
			if (leftPlayer == null) {
				leftPlayer = player;
			} else {
				if (player.getTargetPosition().x <= leftPlayer.getTargetPosition().x) {
					leftPlayer = player;
				}
			}
		}

		// draw player-huds
		for (Integer key : model.getPlayers().keySet()) {
			if (model.getPlayers().get(key) == leftPlayer) {
				applet.fill(leftPlayer.getShapeColor());
				applet.text("Player" + key, 20, 20);
				applet.text("Points: " + model.getPlayers().get(key).getPoints(), 100, 20);
				// Bullets
				for (int j = 0; j < model.getPlayers().get(key).getMunition(); j++) {
					int x1 = j * (ImageContainer.ammoImage.width + 1);
					int y1 = 28;
					applet.image(ImageContainer.ammoImage, x1, y1);
				}
				if (model.getPlayers().get(key).getMunition() <= 0 && applet.millis() % 3 == 0) {
					applet.textSize(30);
					applet.fill(255, 0, 0);
					applet.text("R E L O A D", 100, 55);
					applet.textSize(12);
				}
				// Reload
				model.getReloadButtons()
						.get(key)
						.setPosition(
								new PVector(applet.getWidth() / 2 - model.getReloadButtons().get(key).getWidth(), applet.getHeight()
										- model.getReloadButtons().get(key).getHeight()));
				applet.rect(model.getReloadButtons().get(key).getPosition().x, model.getReloadButtons().get(key).getPosition().y, model
						.getReloadButtons().get(key).getWidth(), model.getReloadButtons().get(key).getHeight());

			} else {
				applet.fill(model.getPlayers().get(key).getShapeColor());
				applet.text("Player" + key, applet.getWidth() - 20, 20);
				applet.text("Points: " + model.getPlayers().get(key).getPoints(), applet.getWidth() - 100, 20);
				// Bullets
				for (int j = model.getPlayers().get(key).getMunition(); j > 0; j--) {
					int x1 = applet.getWidth() - j * (ImageContainer.ammoImage.width + 1);
					int y1 = 28;
					applet.image(ImageContainer.ammoImage, x1, y1);
				}
				if (model.getPlayers().get(key).getMunition() <= 0 && applet.millis() % 3 == 0) {
					applet.textSize(30);
					applet.fill(255, 0, 0);
					applet.text("R E L O A D", applet.getWidth() - 100, 55);
					applet.textSize(12);
				}
				// Reload
				model.getReloadButtons()
						.get(key)
						.setPosition(
								new PVector(applet.getWidth() - model.getReloadButtons().get(key).getWidth(), applet.getHeight()
										- model.getReloadButtons().get(key).getHeight()));
				applet.rect(model.getReloadButtons().get(key).getPosition().x, model.getReloadButtons().get(key).getPosition().y, model
						.getReloadButtons().get(key).getWidth(), model.getReloadButtons().get(key).getHeight());
			}
			// Missed shoots
			if (model.getPlayers().get(key).getShoot() != null) {
				if (model.getPlayers().get(key).getShoot().z > 0) {
					applet.fill(255, 0, 0);
					applet.rect(model.getPlayers().get(key).getShoot().x, model.getPlayers().get(key).getShoot().y, 3, 3);
					model.getPlayers().get(key).decreaseShoot();
				}
			}
			applet.fill(255);
		}
	}

	private static void drawPVP(MainApplet applet, GamingModel model) {
		// Player 1
		applet.text("Player 1", 20, 20);

		// Player 2
		applet.text("Player 2", 550, 20);

		// Bullets P1
		// Bullets P2

		// Points P1
		applet.text(model.getPlayers().get(0).getPoints(), 20, 50);
		// Points P2
		applet.text(model.getPlayers().get(1).getPoints(), 550, 50);

		// Time
		applet.text("0 : " + model.getGameTime(), applet.getWidth() / 2 - 20, 50);
		// Trennline

		// Reload P1
		applet.color(150 * 75 + 25, 100 + 25, 0 + 25);
		applet.rect(2, 2, 40, 20);
		// Reload P2
		applet.color(150 * 75 + 25, 100 + 25, 0 + 25);
		applet.rect(2, 2, 40, 20);
	}
}
