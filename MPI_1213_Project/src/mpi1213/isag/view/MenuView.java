package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.Button;
import mpi1213.isag.model.GamingModel;

public class MenuView {
	
	private MainApplet applet;
	
	public MenuView(MainApplet applet){
		this.applet = applet;
	}
	
	public static void drawMainMenu(MainApplet applet, GamingModel model){
		applet.text("I.S.A.G", applet.getWidth() / 2 - 20, 50);
		applet.text("Please Shoot into the Target", applet.getWidth() / 2 - 60, 100);

		//draw buttons
		int factor = model.getPlayerButtons().size() + 1;
		for(Button btn:model.getPlayerButtons()){
			applet.image(applet.getZielscheibeRot(),btn.getPosition().x, btn.getPosition().y) ;
//			applet.rect(btn.getPosition().x, btn.getPosition().y, btn.getWidth(), btn.getHeight());
			applet.text(btn.getText(), btn.getPosition().x, btn.getPosition().y);
		}
	}
	
	

}
