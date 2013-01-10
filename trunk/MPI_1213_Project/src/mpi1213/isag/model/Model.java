package mpi1213.isag.model;

import java.util.HashMap;
import java.util.Map;


public class Model {
	private static final int MAX_PLAYERS = 2;
	
	private Map<Integer, Player> players;
	
	public Model() {
		players = new HashMap<Integer, Player>();
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
}
