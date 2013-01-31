package mpi1213.isag.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PVector;

public class GamingModel implements PushListener {
	private static final int MAX_PLAYERS = 2;

	private Map<Integer, Player> players;
	private List<Enemy> enemies;
	private Map<Integer, Button> playerButtons;
	private int width, height;

	public GamingModel(int width, int height) {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
		playerButtons = new HashMap<Integer, Button>();
		
		this.width = width;
		this.height = height;
	}

	public void removePlayer(int id) {
		players.remove(id);
		playerButtons.remove(id);
	}

	public boolean addPlayer(int id) {
		if (players.size() < MAX_PLAYERS) {
			players.put(id, new Player());
			Button button = new Button(0, 0, 100, 100, "Player " + players.size());
			button.setOnClickListener(players.get(id));
			playerButtons.put(id, button);
			updatePlayerButtonLayout();
			return true;
		}
		return false;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	@Override
	public void pushed(PVector vector) {
		int counter = 0;
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isHit((int) vector.x, (int) vector.y) && players.get(0).getMunition()>0) {
				enemies.remove(i);
				//points von player hinzufuegen
				players.get(0).setPoints(players.get(0).getPoints()+10);
				enemies.add(new Enemy(Math.random() * this.width, Math.random() * this.height, Enemy.MIN_WIDTH + Math.random()
						* (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1), Enemy.MIN_WIDTH + Math.random() * (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1),
						Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), Enemy.MIN_DELTA + Math.random()
								* (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), 10 + (float) Math.random() * (255 - 10), (float) Math.random() * (255 - 10),
						(float) Math.random() * (255 - 10)));
				break;
			}
			counter++;
		}
		if(counter == enemies.size() && players.get(0).getMunition()>0){
			players.get(0).setShoot(vector);
		}
		
		players.get(0).setMunition(players.get(0).getMunition()-1);
		
		for (Button btn : playerButtons.values()) {
			btn.evaluateClick(vector);
		}
	}

	public void addDemoEnemies(int windowWidth, int windowHeight) {

		for (int i = 0; i < 5; i++) {
			enemies.add(new Enemy(Math.random() * windowWidth, Math.random() * windowHeight, Enemy.MIN_WIDTH + Math.random()
					* (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1), Enemy.MIN_WIDTH + Math.random() * (Enemy.MAX_WIDTH - Enemy.MIN_WIDTH + 1),
					Enemy.MIN_DELTA + Math.random() * (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), Enemy.MIN_DELTA + Math.random()
							* (Enemy.MAX_DELTA - Enemy.MIN_DELTA + 1), 10 + (float) Math.random() * (255 - 10), (float) Math.random() * (255 - 10),
					(float) Math.random() * (255 - 10)));
		}
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public Collection<Button> getPlayerButtons(){
		return playerButtons.values();
	}
	
	public boolean allPlayerReady(){
		boolean result = true;
		for(Player player:players.values()){
			result = result && player.isReady();
		}
		return result;
	}
	
	private void updatePlayerButtonLayout(){
		int factor = playerButtons.size() + 1;
		int counter = 1;
		for(Button btn:playerButtons.values()){
			btn.setPosition(new PVector(counter * (width / factor) - (btn.getWidth() / 2), 2* height / 3));
			counter++;
		}
	}
}
