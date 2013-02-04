package mpi1213.isag.model;


public class HighscoreItem implements Comparable<HighscoreItem>{

	public Integer score;
	public String text;
	
	public HighscoreItem(String text, Integer score){
		this.text = text;
		this.score = score;
	}

	@Override
	public int compareTo(HighscoreItem o) {
		return o.score.compareTo(this.score);
	}
}
