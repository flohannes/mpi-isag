package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;

public class GamingView {

	private ViewState viewState;
	private MainApplet mainApplet;
	
	public GamingView(ViewState viewState, MainApplet mainApplet){
		this.viewState = viewState;
		this.mainApplet = mainApplet;
	}

	public void drawGame(){
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
		
	}
		
}
