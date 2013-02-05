package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.Button;
import mpi1213.isag.model.GamingModel;
import mpi1213.isag.model.PlayerButton;
import processing.core.PApplet;

public class MenuView {

	public static void drawMainMenu(MainApplet applet, GamingModel model) {
		applet.textSize(18);

		applet.text("I.S.A.G", applet.getWidth() / 2, 50);
		applet.text("Please Shoot into the Target", applet.getWidth() / 2, 100);
		applet.text("<PUSH> your target to start", applet.getWidth() / 2, 4 * applet.getHeight() / 5);

		// draw buttons
		for (PlayerButton btn : model.getPlayerButtons()) {
			if (btn.getImage() != null) {
				applet.image(btn.getStateImage(), btn.getPosition().x, btn.getPosition().y);
			} else {
				applet.rect(btn.getPosition().x, btn.getPosition().y, (float) btn.getWidth(), (float) btn.getHeight());
			}
			applet.text(btn.getText(), btn.getPosition().x + btn.getWidth() / 2, btn.getPosition().y);
			applet.textAlign(PApplet.CENTER);
		}
	}

	public static void drawMultiplayerMenu(MainApplet applet, GamingModel model) {
		applet.text("I.S.A.G", applet.getWidth() / 2, 50);
		applet.text("Please Shoot into the Target", applet.getWidth() / 2, 100);
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

	public static void drawHighScore(MainApplet applet, GamingModel model) {
		applet.textAlign(PApplet.CENTER);
		applet.textSize(30);
		applet.text("SCORE", applet.getWidth() / 2, applet.getHeight() / 5);
		applet.textSize(18);
		int counter = 3;
		for (Integer key : model.getPlayers().keySet()) {
			applet.text("Player " + key + ":     " + model.getPlayers().get(key).getPoints(), applet.getWidth() / 2, counter * applet.getHeight() / 8);
			counter++;
		}
		
		applet.textSize(10);
		String result = "";
		for (int i = 0; i < ((model.getSingleHighscoreList().size() < 6) ? model.getSingleHighscoreList().size() : 5); i++) {
			result += (i + 1) + ". " + model.getSingleHighscoreList().get(i).text + "  -  " + model.getSingleHighscoreList().get(i).score + "\n";
		}
		applet.text(result, applet.getWidth() / 2, 3 * applet.getHeight() / 5);
		
		if (model.getHighscoreTime() < System.currentTimeMillis()) {
			applet.textSize(18);
			applet.fill(255, 20, 20);
			applet.text("<PUSH> to return to menu", applet.getWidth() / 2, 5 * applet.getHeight() / 6);
		}
	}

	public static void drawHighScoreCoop(MainApplet applet, GamingModel model) {
		applet.textAlign(PApplet.CENTER);
		applet.textSize(30);
		applet.text("SCORE", applet.getWidth() / 2, applet.getHeight() / 5);
		applet.textSize(18);
		int score = 0;
		for (Integer key : model.getPlayers().keySet()) {
			score += model.getPlayers().get(key).getPoints();
		}
		applet.text("Players:     " + score, applet.getWidth() / 2, 2 * applet.getHeight() / 5);

		
		applet.textSize(10);
		String result = "";
		for (int i = 0; i < ((model.getCoopHighscoreList().size() < 6) ? model.getCoopHighscoreList().size() : 5); i++) {
			result += (i + 1) + ". " + model.getCoopHighscoreList().get(i).text + "  -  " + model.getCoopHighscoreList().get(i).score + "\n";
		}
		applet.text(result, applet.getWidth() / 2, 3 * applet.getHeight() / 5);
		
		if (model.getHighscoreTime() < System.currentTimeMillis()) {
			applet.textSize(18);
			applet.fill(255, 20, 20);
			applet.text("<PUSH> to return to menu", applet.getWidth() / 2, 5 * applet.getHeight() / 6);
		}
	}

}
