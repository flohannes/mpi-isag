package mpi1213.isag.model;

import java.io.File;

import javax.imageio.ImageIO;

import mpi1213.isag.main.MainApplet;

import processing.core.PImage;


public class Enemy{
	
	public static final int MIN_WIDTH = 50;
	public static final int MIN_HEIGHT = 50;
	public static final int MAX_WIDTH = 250;
	public static final int MAX_HEIGHT = 200;
	public static final int MIN_DELTA = 1;
	public static final int MAX_DELTA = 3;
		
	private int xPos;
	private int yPos;
	
	private int width;
	private int height;
	
	private int deltaX;
	private int deltaY;
	
	private PImage image;
	
	//temporary colors
	private float r = 100;
	private float g = 100;
	private float b = 100;
	
	public Enemy(double x, double y, double width, double height, double deltaX, double deltaY, float r, float g, float b){
		this(x, y, width, height, deltaX, deltaY);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Enemy(double x, double y, double width, double height, double deltaX, double deltaY){
		this.xPos = (int)x;
		this.yPos = (int)y;
		this.width = (int)width;
		this.height = (int)height;
		this.deltaX = (int)deltaX;
		this.deltaY = (int)deltaY;
		try {
			this.image = (PImage)MainApplet.getRandomAlienImage().clone();
			image.resize(this.width, 0);
			this.height = image.height;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Enemy(int width, int height){
		this(Math.random() * width, Math.random() * height, Enemy.MIN_WIDTH + Math.random()
				* (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1), Enemy.MIN_WIDTH + Math.random() * (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1),
				Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), Enemy.MIN_DELTA + Math.random()
						* (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), 10 + (float) Math.random() * (255 - 10), (float) Math.random()
						* (255 - 10), (float) Math.random() * (255 - 10));
	}
	
	public void move(int windowWidth, int windowHeight){
		xPos += deltaX;
		yPos += deltaY;
		
		if(xPos < 0 || xPos > windowWidth){
			deltaX = -deltaX;
		}
		if(yPos < 0 || yPos > windowHeight){
			deltaY = -deltaY;
		}
	}
	
	public boolean isHit(int xPos, int yPos) {
		return xPos >= this.xPos && xPos <= (this.xPos + width)
				&& yPos >= this.yPos && yPos <= (this.yPos + height);
	}
	
	public float getX(){
		return xPos;
	}
	
	public float getY(){
		return yPos;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}
	
	public PImage getImage(){
		return this.image;
	}
}
