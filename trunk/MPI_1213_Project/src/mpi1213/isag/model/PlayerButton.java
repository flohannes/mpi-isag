package mpi1213.isag.model;

import mpi1213.isag.main.ImageContainer;
import processing.core.PImage;

public class PlayerButton extends Button {
		
	private boolean isReady = false;
	
	public PlayerButton(int x, int y, int width, int height, String text, PImage image) {
		super(x, y, width, height, text, image);
	}

	public void setReady(boolean value) {
		isReady = value;
	}
	
	public PImage getStateImage(){
		if(isReady){
			if(ImageContainer.zielscheibeGruen.width != this.getWidth()){
				ImageContainer.zielscheibeGruen.resize(this.getWidth(), 0);
			}
			return ImageContainer.zielscheibeGruen;
		}
		return this.getImage();
		
	}
}
