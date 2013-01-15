package mpi1213.isag.model;


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
	}
	
	public Enemy() {
		this.width = 70;
		this.height = 70;
		this.xPos = 0;
		this.yPos = 200;
		this.deltaX = 2;
		this.deltaY = 0;
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
}
