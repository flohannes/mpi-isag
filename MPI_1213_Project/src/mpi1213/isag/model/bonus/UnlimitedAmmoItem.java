package mpi1213.isag.model.bonus;

import mpi1213.isag.main.ImageContainer;

public class UnlimitedAmmoItem extends BonusItem{

	public UnlimitedAmmoItem(int windowWidth, int windowHeight) {
		super(windowWidth, windowHeight);
		imageInit(ImageContainer.unlimitedAmmo, windowWidth, windowHeight);
	}

}
