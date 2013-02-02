package mpi1213.isag.model;

import mpi1213.isag.main.ImageContainer;
import processing.core.PImage;

public class PlayerButton extends Button {
	
	private PImage lastImage;
	
	public PlayerButton(int x, int y, int width, int height, String text, PImage image) {
		super(x, y, width, height, text, image);
		lastImage = image;
	}

	public void checkButton(){
		lastImage = this.getImage();
		this.setImage(ImageContainer.zielscheibeGruen);
	}
	
	public void uncheckButton(){
		this.setImage(lastImage);
	}
}
