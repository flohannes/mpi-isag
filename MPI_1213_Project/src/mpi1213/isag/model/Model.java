package mpi1213.isag.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PVector;


public class Model implements PushListener{
	private static final int MAX_PLAYERS = 2;
	
	private Map<Integer, Player> players;
	
	private List<Enemy> enemies;
	
	public Model() {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
	}

	public void removePlayer(int id) {
		players.remove(id);
	}

	public boolean addPlayer(int id) {
		if(players.size() <= MAX_PLAYERS) {
			players.put(id, new Player());
			return true;
		}
		return false;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	@Override
	public void pushed(PVector vector) {
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).isHit((int)vector.x, (int)vector.y)){
				enemies.remove(i);
			}
		}
	}

	public void addDemoEnemies() {
		//for(int i= 0; i< 10;i++){
			enemies.add(new Enemy());
		//}
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
}
