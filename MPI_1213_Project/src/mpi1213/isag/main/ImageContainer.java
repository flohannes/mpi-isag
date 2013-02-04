package mpi1213.isag.main;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageContainer {
	// images
	public static PImage zielscheibeRot;
	public static PImage zielscheibeGruen;
	public static PImage zielscheibeBlau;
	public static PImage alien1;
	public static PImage alien2;
	public static PImage alien3;
	public static PImage alien4;
	public static PImage explosion;
	public static PImage backgroundImage;
	public static PImage ammoImage;
	public static PImage coop;
	public static PImage pvp;
	public static PImage freezeItem;
	
	public static void initImages(PApplet applet) {
		backgroundImage = applet.loadImage("Images/sternenhimmel.jpg");
		backgroundImage.resize(applet.getWidth(), applet.getHeight());
		ammoImage = applet.loadImage("Images/ammo.png");
		ammoImage.resize(applet.getWidth() / 45, 0);
		zielscheibeBlau = applet.loadImage("Images/target_blue.png");
		zielscheibeRot = applet.loadImage("Images/target_red.png");
		zielscheibeGruen = applet.loadImage("Images/target_green.png");
		explosion = applet.loadImage("Images/explosion.png");
		coop = applet.loadImage("Images/coop.png");
		pvp = applet.loadImage("Images/pvp.png");
		freezeItem = applet.loadImage("Images/freeze_item.png");
		alien1 = applet.loadImage("Images/alien_01.png");
		alien2 = applet.loadImage("Images/alien_02.png");
		alien3 = applet.loadImage("Images/alien_03.png");
		alien4 = applet.loadImage("Images/alien_04.png");
	}

	public static PImage getRandomAlienImage() {
		int random = (int) (1 + Math.random() * (4 - 1 + 1));

		switch (random) {
		case 1:
			return alien1;
		case 2:
			return alien2;
		case 3:
			return alien3;
		case 4:
			return alien4;
		default:
			return null;
		}
	}
}
