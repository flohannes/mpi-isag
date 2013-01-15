package mpi1213.isag.model;


public class Enemy{
		
	private int xPos;
	private int yPos;
	
	private int width;
	private int height;
	
	private int deltaX;
	private int deltaY;
		
	public void move(int width, int height){
		xPos += deltaX;
		yPos += deltaY;
		
		if(xPos < 0 || xPos > width){
			deltaX = -deltaX;
		}
		if(yPos < 0 || yPos > height){
			deltaY = -deltaY;
		}
	}
	
	public Enemy() {
		this.width = 70;
		this.height = 70;
		this.xPos = 0;
		this.yPos = 200;
		this.deltaX = 2;
		this.deltaY = 0;
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
}
