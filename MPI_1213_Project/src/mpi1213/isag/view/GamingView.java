package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;

public class GamingView {

	private ViewState viewState;
	private MainApplet mainApplet;
	private int time;
	private int points;
	
	public GamingView(ViewState viewState, MainApplet mainApplet){
		this.viewState = viewState;
		this.mainApplet = mainApplet;
		this.time = 60*60; //60fps * 60sec Spielzeit
	}

	public void drawGame(){
		//Player 1
		mainApplet.text("Player 1", 20, 20);
		//Bullets
		
		//Points
		mainApplet.text(points,  20, 50);
		//Time
		mainApplet.text("0 : " + time, mainApplet.getWidth()/2-20, 50);
		time--;
		//Reload
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
		
	}
		
}
