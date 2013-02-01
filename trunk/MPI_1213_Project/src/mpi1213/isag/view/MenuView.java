package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.Button;
import mpi1213.isag.model.GamingModel;
import processing.core.PApplet;

public class MenuView {

	public static void drawMainMenu(MainApplet applet, GamingModel model) {
		applet.text("I.S.A.G", applet.getWidth() / 2 - 20, 50);
		applet.text("Please Shoot into the Target", applet.getWidth() / 2 - 60, 100);

		// draw buttons
		for (Button btn : model.getPlayerButtons()) {
			if (btn.getImage() != null) {
				applet.image(btn.getImage(), btn.getPosition().x, btn.getPosition().y);
			} else {
				applet.rect(btn.getPosition().x, btn.getPosition().y, (float) btn.getWidth(), (float) btn.getHeight());
			}
			applet.text(btn.getText(), btn.getPosition().x + btn.getWidth() / 2, btn.getPosition().y);
			applet.textAlign(PApplet.CENTER);
		}
	}

	public static void drawMultiplayerMenu(MainApplet applet, GamingModel model) {
		applet.text("I.S.A.G", applet.getWidth() / 2 - 20, 50);
		applet.text("Please Shoot into the Target", applet.getWidth() / 2 - 60, 100);
		for (Button btn : model.getMulitplayerButtons()) {
			if (btn.getImage() != null) {
				applet.image(btn.getImage(), btn.getPosition().x, btn.getPosition().y);
			} else {
				applet.rect(btn.getPosition().x, btn.getPosition().y, (float) btn.getWidth(), (float) btn.getHeight());
			}
			applet.text(btn.getText(), btn.getPosition().x + btn.getWidth() / 2, btn.getPosition().y);
			applet.textAlign(PApplet.CENTER);
		}
	}

}
