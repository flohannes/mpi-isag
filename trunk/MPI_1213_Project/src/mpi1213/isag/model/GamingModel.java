package mpi1213.isag.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpi1213.isag.main.ImageContainer;
import mpi1213.isag.view.ViewState;
import processing.core.PImage;
import processing.core.PVector;

public class GamingModel implements PushListener {
	private static final int MAX_PLAYERS = 2;
	private static final long GAME_SESSION_TIME = 10000;// in ms
	private static final int INITIAL_ENEMY_COUNT = 6;

	private Map<Integer, Player> players;
	private List<Enemy> enemies;
	private Map<Integer, PlayerButton> playerButtons;
	private Map<Integer, ReloadButton> reloadButtons;
	private List<Button> multiplayerButtons;
	private int width, height;
	private long startTime = 0;
	private float gamePointSlope;
	private float gamePointAbs = 100f;

	private ViewState viewState = ViewState.STARTMENU;

	public GamingModel(int width, int height) {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
		playerButtons = new HashMap<Integer, PlayerButton>();
		reloadButtons = new HashMap<Integer, ReloadButton>();
		multiplayerButtons = new ArrayList<Button>();
		multiplayerButtons.add(new Button(0, 0, 100, 100, "Co-op", null));
		multiplayerButtons.add(new Button(0, 0, 100, 100, "PvP", null));
		this.width = width;
		this.height = height;
		updateMultiplayerButtonLayout();
		setVisibilityMultiplayerButtons(false);

		// game points function
		gamePointSlope = (0f - 100f) / ((float) width / 2f * (float) height / 2f - 0f);
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
			if (players.size() == 1) {
				buttonImage = ImageContainer.zielscheibeRot;
				players.get(id).setShapeColor(-65536);
			} else {
				buttonImage = ImageContainer.zielscheibeBlau;
				players.get(id).setShapeColor(-16776961);
			}
			PlayerButton pButton = new PlayerButton(0, 0, 100, 100, "Player " + players.size(), buttonImage);
			pButton.setOnClickListener(players.get(id));
			playerButtons.put(id, pButton);

			// reload button
			ReloadButton rButton;
			String text = "Reload (P" + players.size() + ")";
			if (players.get(id).getTargetPosition().x < width / 2) {
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
				removeEnemy(enemies.get(i));
				// points von player hinzufuegen
				player.increasePoints(getGamePoints(enemies.get(i).getWidth(), enemies.get(i).getHeight()));
			}
			counter++;
		}

		if (counter == enemies.size() && player.getMunition() > 0) {
			player.setShoot(vector);
		}

		player.setMunition(player.getMunition() - 1);

		for (PlayerButton btn : playerButtons.values()) {
			if (btn.isListener(player)) {
				btn.evaluateClick(vector);
				if (player.isReady()) {
					btn.checkButton();
				}
			}
		}

		for (Button btn : reloadButtons.values()) {
			btn.evaluateClick(vector);
		}

		for (Button btn : multiplayerButtons) {
			btn.evaluateClick(vector);
		}
	}

	public void addInitialEnemies(int windowWidth, int windowHeight) {
		enemies = new ArrayList<Enemy>();
		for (int i = 0; i < INITIAL_ENEMY_COUNT; i++) {
			enemies.add(new Enemy(this.width, this.height));
		}
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public Collection<PlayerButton> getPlayerButtons() {
		return playerButtons.values();
	}

	public boolean allPlayersReady() {
		boolean result = true;
		for (Player player : players.values()) {
			result = result && player.isReady();
		}
		return result;
	}

	public void updatePlayerButtonLayout() {
		int factor = playerButtons.size() + 1;
		int counter = 1;

		if (players.size() == 2) {
			PlayerButton leftButton = null;
			PlayerButton rightButton = null;
			Player tempPlayer = null;
			for (Integer key : players.keySet()) {
				if (tempPlayer == null) {
					tempPlayer = players.get(key);
					leftButton = playerButtons.get(key);
				} else {
					if (players.get(key).getTargetPosition().x <= tempPlayer.getTargetPosition().x) {
						rightButton = leftButton;
						leftButton = playerButtons.get(key);
					} else {
						rightButton = playerButtons.get(key);
					}
				}
			}
			leftButton.setPosition(new PVector(1 * (width / factor) - (leftButton.getWidth() / 2), height / 2));
			rightButton.setPosition(new PVector(2 * (width / factor) - (rightButton.getWidth() / 2), height / 2));
		} else {
			for (Button btn : playerButtons.values()) {
				btn.setPosition(new PVector(counter * (width / factor) - (btn.getWidth() / 2), height / 2));
				counter++;
			}
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

	public void setVisibilityMultiplayerButtons(boolean visibility) {
		for (Button btn : multiplayerButtons) {
			btn.setVisible(visibility);
		}
	}

	public void setVisibilityPlayerButtons(boolean visibility) {
		for (Button btn : playerButtons.values()) {
			btn.setVisible(visibility);
		}
	}

	private void removeEnemy(Enemy enemy) {
		enemy.destroy();
		try {
			enemy.setImage((PImage) ImageContainer.explosion.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getState() == EnemyState.TO_BE_REMOVED) {
				enemies.remove(i);
				// add enemy
				enemies.add(new Enemy(this.width, this.height));
			} else {
				enemies.get(i).update(this.width, this.height);
			}
		}
		switch (viewState) {
		case SINGLEPLAYER:
			if (!isGameRunning()) {
				setVisibilityPlayerButtons(true);
				viewState = ViewState.STARTMENU;
			}
			break;
		default:
			break;
		}
	}

	public boolean isGameRunning() {
		if ((GAME_SESSION_TIME - (System.currentTimeMillis() - startTime)) > 0) {
			return true;
		}
		return false;
	}

	public void startGame() {
		startTime = System.currentTimeMillis();
		addInitialEnemies(width, height);
		// reset playerReady-flag and munition
		for (Integer key : players.keySet()) {
			players.get(key).setReady(false);
			playerButtons.get(key).uncheckButton();
			players.get(key).reloadMunition();
			players.get(key).setPoints(0);
		}
	}

	/**
	 * 
	 * @return time in s
	 */
	public long getGameTime() {
		return (GAME_SESSION_TIME - (System.currentTimeMillis() - startTime)) / 1000;
	}

	private int getGamePoints(float width, float height) {
		return (int) (gamePointSlope * (float) (width * height) + gamePointAbs);
	}

	public ViewState getViewState() {
		return viewState;
	}

	public void setViewState(ViewState viewState) {
		this.viewState = viewState;
	}
}