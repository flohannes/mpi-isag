package mpi1213.isag.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PVector;

public class GamingModel implements PushListener {
	private static final int MAX_PLAYERS = 2;

	private Map<Integer, Player> players;
	private List<Enemy> enemies;
	private List<Button> buttons;

	public GamingModel() {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
		buttons = new ArrayList<Button>();
	}

	public void removePlayer(int id) {
		players.remove(id);
	}

	public boolean addPlayer(int id) {
		if (players.size() <= MAX_PLAYERS) {
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
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isHit((int) vector.x, (int) vector.y)) {
				enemies.remove(i);
			}
		}
		for(Button btn:buttons){
			btn.evaluateClick(vector);
		}
	}

	public void addDemoEnemies(int windowWidth, int windowHeight) {

		for (int i = 0; i < 5; i++) {
			enemies.add(new Enemy(Math.random() * windowWidth, Math.random()
					* windowHeight, Enemy.MIN_WIDTH + Math.random()
					* (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1), Enemy.MIN_WIDTH
					+ Math.random() * (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1),
					Enemy.MIN_DELTA + Math.random()
							* (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1),
					Enemy.MIN_DELTA + Math.random()
							* (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), 10
							+ (float) Math.random() * (255 - 10), (float) Math
							.random() * (255 - 10), (float) Math.random()
							* (255 - 10)));
		}
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
}
