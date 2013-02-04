package mpi1213.isag.model.bonus;

import mpi1213.isag.main.ImageContainer;

public class FreezeItem extends BonusItem {

	public FreezeItem(int windowWidth, int windowHeight) {
		super(windowWidth, windowHeight);
		imageInit(ImageContainer.freezeItem, windowWidth, windowHeight);
	}

}
