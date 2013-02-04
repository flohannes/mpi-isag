package mpi1213.isag.model.bonus;

import mpi1213.isag.model.Enemy;
import processing.core.PImage;

public class BonusItem extends Enemy{
	private static final long STANDARD_TIME = 2000;
	private long time = STANDARD_TIME;
	
	public BonusItem(int windowWidth, int windowHeight) {
		double deltaX, deltaY;
		deltaX = (Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1)) * getRandomDirection();
		deltaY = (Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1)) * getRandomDirection();

		this.width = windowWidth/8;
		this.height = width;
		this.deltaX = (int) deltaX;
		this.deltaY = (int) deltaY;
	}
	
	public void imageInit(PImage image, int windowWidth, int windowHeight){
		try {
			PImage img = (PImage) image.clone();
			img.resize(this.width, 0);
			this.height = img.height;
			this.setImage(img);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		this.xPos = (int)(Math.random() * (windowWidth - this.width/2));
		this.yPos = (int)(Math.random() * (windowHeight - this.height/2));	
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time){
		this.time = time;
	}

	public static Enemy getRandomInstance(int windowWidth, int windowHeight) {
		int random = (int) (1 + Math.random() * (2 - 1 + 1));

		switch (random) {
		case 1:
			return new FreezeItem(windowWidth, windowHeight);
		case 2:
			return new UnlimitedAmmoItem(windowWidth, windowHeight);
		default:
			return null;
		}
	}

}
