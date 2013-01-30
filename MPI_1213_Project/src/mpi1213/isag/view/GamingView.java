package mpi1213.isag.view;

import mpi1213.isag.main.MainApplet;
import mpi1213.isag.model.GamingModel;

public class GamingView {

	private ViewState viewState;
	private MainApplet mainApplet;
	private int time;
	private GamingModel model;
	
	public GamingView(ViewState viewState, MainApplet mainApplet, GamingModel model){
		this.viewState = viewState;
		this.mainApplet = mainApplet;
		this.time = 60*60; //60fps * 60sec Spielzeit
		this.model = model;
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
			viewState = ViewState.STARTMENU;
		}
			
	}
	
	private void drawSingleplayer(){
		//Player 1
		mainApplet.text("Player 1", 20, 20);
		//Bullets
		for(int i =0; i< model.getPlayers().get(0).getMunition();i++){
			int x1 = i * 10;
			int y1 = 30;
			int x2 = x1+8;
			int y2 = y1+15;
			mainApplet.rect(x1,y1,x2,y2);
		}
		//Points
		mainApplet.text(model.getPlayers().get(0).getPoints(),  20, 100);
		//Time
		mainApplet.text("0 : " + (int)(time/60), mainApplet.getWidth()/2-20, 50);
		time--;
		//Reload
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, mainApplet.getHeight()-42, 40, 20);
	}
	
	

	private void drawCOOP(){
		//Player 1
		mainApplet.text("Player 1", 20, 20);
		
		//Player 2
		mainApplet.text("Player 2", 550, 20);
		
		//Bullets P1
		//Bullets P2
		
		//Points P1
		mainApplet.text(model.getPlayers().get(0).getPoints(),  20, 50);
		//Points P2
		mainApplet.text(model.getPlayers().get(1).getPoints(),  550, 50);
				
		//Time
		mainApplet.text("0 : " + (int)(time/60), mainApplet.getWidth()/2-20, 50);
		time--;//Reload P1

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
		mainApplet.text(model.getPlayers().get(0).getPoints(),  20, 50);
		//Points P2
		mainApplet.text(model.getPlayers().get(1).getPoints(),  550, 50);
				
		//Time
		mainApplet.text("0 : " + (int)(time/60), mainApplet.getWidth()/2-20, 50);
		time--;
		//Trennline
		
		//Reload P1
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
		//Reload P2
		mainApplet.color(150 * 75+25, 100+25, 0+25);
		mainApplet.rect(2, 2, 40, 20);
	}
	
	private void drawFadenkreuzP1(){
		
	}
	private void drawFadenkreuzP2(){
		
	}

}
