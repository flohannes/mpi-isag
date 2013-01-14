package mpi1213.isag.view;

public class GamingField {

	private boolean isMultiplayer;
	private boolean isPvP;
	
	public GamingField(){
		setMultiplayer(false);
		setPvP(false);
	}

	public boolean isMultiplayer() {
		return isMultiplayer;
	}

	public void setMultiplayer(boolean isMultiplayer) {
		this.isMultiplayer = isMultiplayer;
	}

	public boolean isPvP() {
		return isPvP;
	}

	public void setPvP(boolean isPvP) {
		this.isPvP = isPvP;
	}
	
}
