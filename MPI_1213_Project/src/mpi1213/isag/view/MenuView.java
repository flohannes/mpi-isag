package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.GamingModel;

public class MenuView {
	
	private MainApplet applet;
	
	public MenuView(MainApplet applet){
		this.applet = applet;
	}
	
	public static void drawMainMenu(MainApplet applet, GamingModel model){
		applet.text("I.S.A.G", applet.getWidth() / 2 - 20, 50);
		applet.text("Please Shoot into the Target", applet.getWidth() / 2 - 60, 100);
		applet.rect(10, 200, 80, 80);
		applet.rect(300, 200, 80, 80);
		applet.text("Player 1", 220, 270);
		applet.text("Player 2", 315, 270);
	}
	
	

}
