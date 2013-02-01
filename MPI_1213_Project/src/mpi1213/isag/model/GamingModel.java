package mpi1213.isag.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpi1213.isag.main.MainApplet;
import processing.core.PImage;
import processing.core.PVector;

public class GamingModel implements PushListener {
	private static final int MAX_PLAYERS = 2;

	private Map<Integer, Player> players;
	private List<Enemy> enemies;
	private Map<Integer, Button> playerButtons;
	private Map<Integer, ReloadButton> reloadButtons;
	private List<Button> multiplayerButtons;
	private int width, height;

	public GamingModel(int width, int height) {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
		playerButtons = new HashMap<Integer, Button>();
		reloadButtons = new HashMap<Integer, ReloadButton>();
		multiplayerButtons = new ArrayList<Button>();
		multiplayerButtons.add(new Button(0, 0, 100, 100, "Co-op", null));
		multiplayerButtons.add(new Button(0, 0, 100, 100, "PvP", null));
		this.width = width;
		this.height = height;
		updateMultiplayerButtonLayout();
	}

	public void removePlayer(int id) {
		players.remove(id);
		playerButtons.remove(id);
		reloadButtons.remove(id);
	}

	public boolean addPlayer(int id) {
		if (players.size() < MAX_PLAYERS) {
			players.put(id, new Player());
			// player button
			PImage buttonImage;
			if(players.size() == 1){
				buttonImage = MainApplet.getZielscheibeRot();
			} else {
				buttonImage = MainApplet.getZielscheibeBlau();
			}
			PlayerButton pButton = new PlayerButton(0, 0, 100, 100, "Player " + players.size(), buttonImage);
			pButton.setOnClickListener(players.get(id));
			playerButtons.put(id, pButton);

			// reload button
			ReloadButton rButton;
			String text = "Reload (P" + players.size() + ")";
			if (players.get(id).getPosition().x < width / 2) {
				rButton = new ReloadButton(10, height - 50, 40, 40, text, null);
			} else {
				rButton = new ReloadButton(width - 50, height - 50, 40, 40, text, null);
			}
			rButton.setOnClickListener(players.get(id));
			reloadButtons.put(id, rButton);
			updatePlayerButtonLayout();

			return true;
		}
		return false;
	}

	public Map<Integer, Player> getPlayers() {
		return players;
	}

	@Override
	public void pushed(PVector vector, Player player) {
		int counter = 0;
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isHit((int) vector.x, (int) vector.y) && player.getMunition() > 0) {
				enemies.remove(i);
				// points von player hinzufuegen
				player.setPoints(player.getPoints() + 10);
				// add enemy
				enemies.add(new Enemy(this.width, this.height));
			}
			counter++;
		}

		if (counter == enemies.size() && player.getMunition() > 0) {
			player.setShoot(vector);
		}

		player.setMunition(player.getMunition() - 1);

		for (Button btn : playerButtons.values()) {
			btn.evaluateClick(vector);
			if(player.isReady()){
				btn.setImage(MainApplet.getZielscheibeGruen());
			}
		}

		for (Button btn : reloadButtons.values()) {
			btn.evaluateClick(vector);
		}
		
		for (Button btn : multiplayerButtons) {
			btn.evaluateClick(vector);
		}
	}

	public void addDemoEnemies(int windowWidth, int windowHeight) {

		for (int i = 0; i < 5; i++) {
			enemies.add(new Enemy(this.width, this.height));
		}
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public Collection<Button> getPlayerButtons() {
		return playerButtons.values();
	}

	public boolean allPlayerReady() {
		boolean result = true;
		for (Player player : players.values()) {
			result = result && player.isReady();
		}
		return result;
	}

	private void updatePlayerButtonLayout() {
		int factor = playerButtons.size() + 1;
		int counter = 1;
		for (Button btn : playerButtons.values()) {
			btn.setPosition(new PVector(counter * (width / factor) - (btn.getWidth() / 2), 2 * height / 3));
			counter++;
		}
	}

	private void updateMultiplayerButtonLayout() {
		int factor = multiplayerButtons.size() + 1;
		int counter = 1;
		for (Button btn : multiplayerButtons) {
			btn.setPosition(new PVector(counter * (width / factor) - (btn.getWidth() / 2), 2 * height / 3));
			counter++;
		}
	}
	
	public Map<Integer, ReloadButton> getReloadButtons() {
		return reloadButtons;
	}

	public List<Button> getMulitplayerButtons() {
		return multiplayerButtons;
	}
}
