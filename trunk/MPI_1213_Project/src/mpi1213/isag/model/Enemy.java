package mpi1213.isag.model;

public class Enemy {
		
	private int xPos;
	private int yPos;
	
	private int width;
	private int height;
	
	private int deltaX;
	private int deltaY;
	
	public void move(){
		xPos += deltaX;
		yPos += deltaY;
	}
	
	public Enemy() {
		this.width = 50;
		this.height = 30;
		this.xPos = 0;
		this.yPos = 200;
		this.deltaX = 2;
		this.deltaY = 0;
	}
	
	public boolean isHit(int xPos, int yPos) {
		return xPos >= this.xPos && xPos <= (this.xPos + width)
				&& yPos >= this.yPos && yPos <= (this.yPos + height);
	}

}
