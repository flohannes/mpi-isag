package mpi1213.isag.model;

import processing.core.PImage;
import processing.core.PVector;

public class Button {
	private PVector position;
	private int width, height;
	private OnClickListener listener;
	private String text;
	private boolean isVisible = true;
	private PImage image;

	public Button(int x, int y, int width, int height, String text, PImage image) {
		this.position = new PVector(x, y);
		this.width = width;
		this.height = height;
		this.text = text;
		this.image = image;
		if (image != null) {
			image.resize(width, height);
		}
	}

	public void setOnClickListener(OnClickListener listener) {
		this.listener = listener;
	}

	public boolean evaluateClick(PVector vector) {
		if (isVisible && isHit(vector)) {
			click();
			return true;
		} else {
			return false;
		}
	}

	private boolean isHit(PVector vector) {
		return vector.x >= position.x && vector.x <= (position.x + width) && vector.y >= position.y && vector.y <= (position.y + height);
	}

	private void click() {
		if (listener != null) {
			listener.onClick(this);
		}
	}

	public String getText() {
		return this.text;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public PVector getPosition() {
		return position;
	}

	public void setPosition(PVector position) {
		this.position = position;
	}

	public PImage getImage() {
		return image;
	}

	public void setImage(PImage image) {
		this.image = image;
		image.resize(width, height);
	}
}
