package mpi1213.isag.model;

import processing.core.PVector;

public class Button {
	private PVector position;
	private int width, height;
	private OnClickListener listener;
	
	public Button(PVector position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public Button(int x, int y, int width, int height) {
		this.position = new PVector(x, y);
		this.width = width;
		this.height = height;
	}
	
	public void setOnClickListener(OnClickListener listener){
		this.listener = listener;
	}
	
	public boolean evaluateClick(PVector vector){
		if(isHit(vector)){
			click();
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isHit(PVector vector) {
		return vector.x >= position.x && vector.x <= (position.x + width)
				&& vector.y >= position.y && vector.y <= (position.y + height);
	}
	
	private void click(){
		if(listener != null){
			listener.onClick();
		}
	}
}
