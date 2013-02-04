package mpi1213.isag.model;

import mpi1213.isag.main.ImageContainer;
import mpi1213.isag.model.bonus.FreezeItem;
import processing.core.PImage;

public class Enemy {

	public static final int MIN_WIDTH = 50;
	public static final int MIN_HEIGHT = 50;
	public static final int MAX_WIDTH = 250;
	public static final int MAX_HEIGHT = 200;
	public static final int MIN_DELTA = 1;
	public static final int MAX_DELTA = 3;
	public static final int DESTROY_TIMESPAN = 500;

	public int xPos;
	public int yPos;

	public int width;
	public int height;

	public double deltaX;
	public double deltaY;

	private PImage image;
	
	private long destroyTime = 0;
	
	private EnemyState state = EnemyState.ALIVE;

	private boolean isFrozen = false;
	private long unfreezeTime = 0;

	public Enemy(){
		super();
	}
	
	public Enemy(int windowWidth, int windowHeight) {
		double deltaX, deltaY;
		deltaX = (Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1)) * getRandomDirection();
		deltaY = (Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1)) * getRandomDirection();

		init(Enemy.MIN_WIDTH + Math.random() * (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1),
				Enemy.MIN_WIDTH + Math.random() * (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1), deltaX, deltaY, 10 + (float) Math.random() * (255 - 10),
				(float) Math.random() * (255 - 10), (float) Math.random() * (255 - 10));
		this.xPos = (int)(Math.random() * (windowWidth - this.width/2));
		this.yPos = (int)(Math.random() * (windowHeight - this.height/2));		
	}

	public double getRandomDirection() {
		if (Math.random() < 0.5) {
			return -1;
		} else {
			return 1;
		}
	}

	private void init(double width, double height, double deltaX, double deltaY, float r, float g, float b) {
		this.width = (int) width;
		this.height = (int) height;
		this.deltaX = (int) deltaX;
		this.deltaY = (int) deltaY;
		try {
			this.image = (PImage) ImageContainer.getRandomAlienImage().clone();
			image.resize(this.width, 0);
			this.height = image.height;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void move(int windowWidth, int windowHeight) {
		//changeDirection();
		
		xPos += deltaX;
		yPos += deltaY;

		if (xPos < -(width/2) || xPos > (windowWidth - width/2)) {
			xPos -= deltaX;
			deltaX = -deltaX;
		}
		if (yPos < -(height/2) || yPos > (windowHeight - height/2)) {
			yPos -= deltaY;
			deltaY = -deltaY;
		}
	}

	private void changeDirection() {
		if(Math.random() < 0.1){
			deltaX += ((Math.random() > 0.5) ? 1:-1) * Math.random() / 2;
			deltaY += ((Math.random() > 0.5) ? 1:-1) * Math.random() / 2;
			
			deltaX = (deltaX > MAX_DELTA) ? MAX_DELTA : deltaX;
			deltaX = (deltaX < -MAX_DELTA) ? -MAX_DELTA : deltaX;
			deltaX = (deltaX < MIN_DELTA && deltaX > 0) ? MIN_DELTA : deltaX;
			deltaX = (deltaX > -MIN_DELTA && deltaX < 0) ? -MIN_DELTA : deltaX;
			
			deltaY = (deltaY > MAX_DELTA) ? MAX_DELTA : deltaY;
			deltaY = (deltaY < -MAX_DELTA) ? -MAX_DELTA : deltaY;
			deltaY = (deltaY < MIN_DELTA && deltaY > 0) ? MIN_DELTA : deltaY;
			deltaY = (deltaY > -MIN_DELTA && deltaY < 0) ? -MIN_DELTA : deltaY;
		}
	}

	public boolean isHit(int xPos, int yPos) {
		return xPos >= this.xPos && xPos <= (this.xPos + width) && yPos >= this.yPos && yPos <= (this.yPos + height);
	}

	public float getX() {
		return xPos;
	}

	public float getY() {
		return yPos;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public PImage getImage() {
		return this.image;
	}
	
	public void setImage(PImage image) {
		this.image = image;
		if(image.width != width || image.height != height){
			image.resize(width, height);
		}
	}
	
	public void destroy(){
		state = EnemyState.DESTROYED;
		freeze(-1);
		destroyTime = System.currentTimeMillis();
		try {
			this.setImage((PImage) ImageContainer.explosion.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
	
	public void markToBeRemoved(){
		state = EnemyState.TO_BE_REMOVED;
	}
	
	public EnemyState getState(){
		return state;
	}

	public void update(int width, int height) {
		if(!isFrozen){
			move(width, height);
		} else if (unfreezeTime != -1){
			if(unfreezeTime < System.currentTimeMillis()){
				isFrozen = false;
			}
		}
		if(state == EnemyState.DESTROYED){
			if(System.currentTimeMillis() > (destroyTime + DESTROY_TIMESPAN)){
				state = EnemyState.TO_BE_REMOVED;
			}
		}
	}

	public void freeze(long freezeTime) {
		isFrozen  = true;
		if(freezeTime == -1){
			unfreezeTime = -1;
		} else {
			unfreezeTime = System.currentTimeMillis() + freezeTime;
		}
	}
}
