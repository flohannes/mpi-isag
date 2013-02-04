package mpi1213.isag.model.bonus;

import mpi1213.isag.main.ImageContainer;

public class FreezeItem extends BonusItem{

	public static long FREEZE_TIME = 2000;
	
	private long freezeTime = FREEZE_TIME;
	
	public FreezeItem(int windowWidth, int windowHeight) {
		super(windowWidth, windowHeight);
		imageInit(ImageContainer.freezeItem, windowWidth, windowHeight);
	}

	public long getFreezeTime() {
		return freezeTime;
	}
	
	public void setFreezeTime(long time){
		freezeTime = time;
	}

}
