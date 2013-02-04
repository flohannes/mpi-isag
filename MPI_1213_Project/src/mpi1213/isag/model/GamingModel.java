package mpi1213.isag.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mpi1213.isag.main.ImageContainer;
import mpi1213.isag.model.bonus.BonusItem;
import mpi1213.isag.model.bonus.FreezeItem;
import mpi1213.isag.model.bonus.UnlimitedAmmoItem;
import mpi1213.isag.view.ViewState;
import processing.core.PImage;
import processing.core.PVector;

public class GamingModel implements PushListener {
	private static final int MAX_PLAYERS = 2;
	private static final long GAME_SESSION_TIME = 20000;// in ms
	private static final int INITIAL_ENEMY_COUNT = 6;
	private static final int BONUS_ITEM_POINT_STEP = 400;

	private Map<Integer, Player> players;
	private List<Enemy> enemies;
	private Map<Integer, PlayerButton> playerButtons;
	private Map<Integer, ReloadButton> reloadButtons;
	private List<Button> multiplayerButtons;
	private int width, height;
	private long startTime = 0;
	private float gamePointSlope;
	private float gamePointAbs = 100f;
	private int bonusItemPointLimit = BONUS_ITEM_POINT_STEP;

	private ViewState viewState = ViewState.STARTMENU;

	public GamingModel(int width, int height) {
		players = new HashMap<Integer, Player>();
		enemies = new ArrayList<Enemy>();
		playerButtons = new HashMap<Integer, PlayerButton>();
		reloadButtons = new HashMap<Integer, ReloadButton>();
		multiplayerButtons = new ArrayList<Button>();
		multiplayerButtons.add(new Button(0, 0, 150, 100, "Co-op", ImageContainer.coop));
		multiplayerButtons.add(new Button(0, 0, 150, 100, "PvP", ImageContainer.pvp));
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
				rButton = new ReloadButton(10, height - 50, 40, 40, text, ImageContainer.reload);
			} else {
				rButton = new ReloadButton(width - 50, height - 50, 40, 40, text, ImageContainer.reload);
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
			if (enemies.get(i).isHit((int) vector.x, (int) vector.y) && player.getMunition() > 0 && enemies.get(i).getState() == EnemyState.ALIVE) {
				removeEnemy(player, enemies.get(i));
			}
			counter++;
		}

		if (counter == enemies.size() && player.getMunition() > 0) {
			player.setShoot(vector);
		}

		if(isGameRunning()){
			player.decreaseMunition();
		}

		for (PlayerButton btn : playerButtons.values()) {
			if (btn.isListener(player)) {
				btn.evaluateClick(vector);
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
					if (players.get(key).getPositionForSideDecision().x <= tempPlayer.getPositionForSideDecision().x) {
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
			btn.setPosition(new PVector(counter * (width / factor) - (btn.getWidth() / 2), height / 2));
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

	private void removeEnemy(Player player, Enemy enemy) {
		enemy.destroy();
		player.increasePoints(getGamePoints(enemy.getWidth(), enemy.getHeight()));
		if(player.getPoints() > bonusItemPointLimit){
			enemies.add(BonusItem.getRandomInstance(width, height));
			bonusItemPointLimit += BONUS_ITEM_POINT_STEP;
		}
		// BonusItem?
		if (enemy instanceof FreezeItem) {
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).freeze(((BonusItem) enemy).getTime());
			}
		} else if(enemy instanceof UnlimitedAmmoItem){
			player.unlimitedAmmo(((BonusItem)enemy).getTime());
		}
	}

	public void update() {

		switch (viewState) {
		case STARTMENU:
			break;
		case MULTIPLAYERMENU:
			break;
		default:
			if (!isGameRunning()) {
				setVisibilityPlayerButtons(true);
				viewState = ViewState.STARTMENU;
				resetPlayers();
			} else {
				for (int i = 0; i < enemies.size(); i++) {
					if (enemies.get(i).getState() == EnemyState.TO_BE_REMOVED) {
						if(!(enemies.get(i) instanceof BonusItem)){
							enemies.add(new Enemy(this.width, this.height));
						}
						enemies.remove(i);						
					} else {
						enemies.get(i).update(this.width, this.height);
					}
				}
			}
			break;
		}
	}

	private void resetPlayers() {
		// reset playerReady-flag and munition
		for (Integer key : players.keySet()) {
			players.get(key).setReady(false);
			playerButtons.get(key).setReady(false);
			players.get(key).reloadMunition();
			players.get(key).setPoints(0);
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
