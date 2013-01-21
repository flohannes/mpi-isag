package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;

public class GamingView {

	private ViewState viewState;
	private MainApplet mainApplet;
	private int time;
	private int pointsP1;
	private int pointsP2;
	
	public GamingView(ViewState viewState, MainApplet mainApplet){
		this.viewState = viewState;
		this.mainApplet = mainApplet;
		this.time = 60*60; //60fps * 60sec Spielzeit
	}

	public void drawGame(){
		if(time > 0){
			//Je nach viewState das Game starten
			if(ViewState.SINGLEPLAYER == viewState){
				this.drawSingleplayer();
			}else if(ViewState.COOP == viewState){
				this.drawCOOP();
			}else if(ViewState.PVP == viewState){
				this.drawPVP();
			}
		}
		else{
			//viewState aendern und zum Highscore oder Hauptmenue
			viewState = ViewState.HIGHSCORE;
		}
			
	}
	
	private void drawSingleplayer(){
		//Player 1
		mainApplet.text("Player 1", 20, 20);
		//Bullets
		
		//Points
		mainApplet.text(pointsP1,  20, 50);
		//Time
		mainApplet.text("0 : " + time, mainApplet.getWidth()/2-20, 50);
		time--;
		//Reload
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
	}
	
	private void drawCOOP(){
		//Player 1
		mainApplet.text("Player 1", 20, 20);
		
		//Player 2
		mainApplet.text("Player 2", 550, 20);
		
		//Bullets P1
		//Bullets P2
		
		//Points P1
		mainApplet.text(pointsP1,  20, 50);
		//Points P2
		mainApplet.text(pointsP2,  550, 50);
				
		//Time
		mainApplet.text("0 : " + time, mainApplet.getWidth()/2-20, 50);
		time--;
		//Reload P1
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
		//Reload P2
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
	}
	
	private void drawPVP(){
		//Player 1
		mainApplet.text("Player 1", 20, 20);
		
		//Player 2
		mainApplet.text("Player 2", 550, 20);
		
		//Bullets P1
		//Bullets P2
		
		//Points P1
		mainApplet.text(pointsP1,  20, 50);
		//Points P2
		mainApplet.text(pointsP2,  550, 50);
				
		//Time
		mainApplet.text("0 : " + time, mainApplet.getWidth()/2-20, 50);
		time--;
		//Trennline
		
		//Reload P1
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
		//Reload P2
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
	}
		
}
