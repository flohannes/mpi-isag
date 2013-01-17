package mpi1213.isag.view;

import java.awt.Point;

import mpi1213.isag.main.MainApplet;

public class MenuView {
	
	private MainApplet applet;
	
	public MenuView(MainApplet applet){
		this.applet = applet;
	}
	
	public void drawMenu(){
		applet.text("I.S.A.G", applet.getWidth() / 2 - 20, 50);
		applet.text("Please Shot into the target", applet.getWidth() / 2 - 60, 100);
		applet.rect(10, 200, 80, 250);
		applet.rect(300, 200, 370, 250);
		applet.text("Player 1", 220, 270);
		applet.text("Player 2", 315, 270);
	}
	
	

}
