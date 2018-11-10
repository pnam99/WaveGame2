package mainGame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.image.BufferStrategy;
import java.io.File;

import javafx.embed.swing.JFXPanel;
import mainGame.audio.SoundPlayer; //SOUND
import mainGame.Game.STATE;
import mainGame.audio.SoundClip; //SOUND


/**
 * Main game class. This class is the driver class and it follows the Holder
 * pattern. It houses references to ALL of the components of the game
 * 
 * @author Brandon Loehle 5/30/16
 */

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 1920, HEIGHT = 1080;
	public static boolean isEasy=true;
	private Thread thread;
	private boolean running = false;
	private Handler handler;
	private HUD hud;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private Menu menu;
	private GameOver gameOver;
	private UpgradeScreen upgradeScreen;
	private MouseListener mouseListener;
	private Upgrades upgrades;
	private Player player;
	public static STATE gameState = STATE.Menu;
	public static int TEMP_COUNTER;
	
	public SoundPlayer soundPlayer; //Music Player (that can loop)
	public SoundClip select, back, hit, die, pause; //SFX (only plays once)
	
	private boolean isPaused = false; //SOUND
	private boolean isMusicPlaying = true; //SOUND

	/**
	 * Used to switch between each of the screens shown to the user
	 */
	public enum STATE {
		Menu, Help, Game, GameOver, Upgrade, Difficulty, Skins,
	};

	/**
	 * Initialize the core mechanics of the game
	 */
	public Game() {
		handler = new Handler();
		hud = new HUD();
		spawner = new Spawn1to10(this.handler, this.hud, this, isEasy);
		spawner2 = new Spawn10to20(this.handler, this.hud, this.spawner, this,isEasy);
		menu = new Menu(this, this.handler, this.hud, this.spawner);
		upgradeScreen = new UpgradeScreen(this, this.handler, this.hud);
		player = new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, this.hud, this,isEasy);
		upgrades = new Upgrades(this, this.handler, this.hud, this.upgradeScreen, this.player, this.spawner,
				this.spawner2);
		gameOver = new GameOver(this, this.handler, this.hud);
		mouseListener = new MouseListener(this, this.handler, this.hud, this.spawner, this.spawner2, this.upgradeScreen,
				this.player, this.upgrades);
		this.addKeyListener(new KeyInput(this.handler, this, this.hud, this.player, this.spawner, this.upgrades));
		this.addMouseListener(mouseListener);
		new Window((int) 1292, (int) 695, "QU: The Legend Of The Bobcat", this);
//		isEasy=true;//Default
		
		// Setting up the music player
		JFXPanel jfxp = new JFXPanel(); // trust
		soundPlayer = new SoundPlayer("music/smashTheme.wav", true);
		soundPlayer.start();
		
//		// Setting up the various SFX
		select = new SoundClip("sounds/meleeMenuSelect.wav", 0.3);
		back = new SoundClip("sounds/meleeMenuBack.wav", 0.3);
		hit = new SoundClip("sounds/steveHurt.wav", 0.45);
		die = new SoundClip("sounds/robloxDeath.wav", 0.45);
		pause = new SoundClip("sounds/marioPause.wav", 0.45);

	}

	/**
	 * The thread is simply a programs path of execution. This method ensures that
	 * this thread starts properly.
	 */
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Best Java game loop out there (used by Notch!)
	 */
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();// 60 times a second, objects are being updated
				delta--;
			}
			if (running)
				render();// 60 times a second, objects are being drawn
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				System.out.println(gameState);
				System.out.println(Spawn1to10.LEVEL_SET);
				frames = 0;
			}
		}
		stop();

	}

	/**
	 * Constantly ticking (60 times per second, used for updating smoothly). Used
	 * for updating the instance variables (DATA) of each entity (location, health,
	 * appearance, etc).
	 */
	private void tick() {
		handler.tick();// ALWAYS TICK HANDLER, NO MATTER IF MENU OR GAME SCREEN
		if (gameState == STATE.Game) { // GAME state is running
			upgradeScreen.tick();
			hud.tick();
			
			if (isEasy) { // On Easy difficulty, plays "Sweden.wav"
				if(!soundPlayer.getSong().equals("music/sweden.wav")) {
					soundPlayer.stop_playing();
					soundPlayer = new SoundPlayer("music/sweden.wav", true);
					soundPlayer.start();
				}
			} else { // On Hard difficulty, plays "Megalovania.wav"
				if(!soundPlayer.getSong().equals("music/megalovania.wav")) {
					soundPlayer.stop_playing();
					soundPlayer = new SoundPlayer("music/megalovania.wav", true);
					soundPlayer.start();
				}
			}
			
			if (Spawn1to10.LEVEL_SET == 1) {// user is on levels 1 thru 10, update them
				spawner.tick();				
			}
			else if (Spawn1to10.LEVEL_SET == 2) {// user is on levels 10 thru 20, update them
				spawner2.tick();
			}
			
			
		}
		else if (gameState == STATE.Menu || gameState == STATE.Help || gameState==STATE.Difficulty) { // user is on menu, update the menu items
			menu.tick();
			
			/**
			 * THIS BLOCK HAS BEEN REPLACED WITH LINES 60-63 IN "MouseListener.java" CLASS!!!
			 * 
//			// This block of code causes the game to not "fully render", but music still plays
//			// Main Menu music plays when going back to menu/help/difficulty state
//			if (!soundPlayer.getSong().equals("music/smashTheme.wav")) {
//				soundPlayer.stop_playing();
//				soundPlayer = new SoundPlayer("music/smashTheme.wav", true);
//				soundPlayer.start();
//			}
 * 
			*/
			
		} 
		else if (gameState == STATE.Upgrade) {// user is on upgrade screen, update the upgrade screen
			upgradeScreen.tick();
		} 
		else if (gameState == STATE.GameOver) {// game is over, update the game over screen
			gameOver.tick();
			if (!soundPlayer.getSong().equals("sounds/marioDie.wav")) { // Whenever player dies, mario death music plays
				soundPlayer.stop_playing();
				soundPlayer = new SoundPlayer("sounds/marioDie.wav", false);
				soundPlayer.start();
			}
		}
		else if (gameState == STATE.Skins) {// game is over, update the game over screen
			handler.clearPlayer();
		}
		
	}
	

	/**
	 * Constantly drawing to the many buffer screens of each entity requiring the
	 * Graphics objects (entities, screens, HUD's, etc).
	 */
	private void render() {

		/*
		 * BufferStrategies are used to prevent screen tearing. In other words, this
		 * allows for all objects to be redrawn at the same time, and not individually
		 */
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		///////// Draw things bellow this/////////////
		
		g.setColor(Color.black);
		g.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);
		
		//bottom boundary rectangle
		g.setColor(Color.WHITE);
		g.fillRect(0, 659, 1600, 800); //(X,Y,Width,Height)

		//right boundary rectangle
		g.setColor(Color.WHITE);
		g.fillRect(1278, 0, 800, 1600); //(X,Y,Width,Height)
		
		//dimension of game screen
		//g.setColor(Color.RED);
		//g.fillRect(0, 0, 1278, 659); //(X,Y,Width,Height)
		
		
		handler.render(g); // ALWAYS RENDER HANDLER, NO MATTER IF MENU OR GAME SCREEN

		if (gameState == STATE.Game) {// user is playing game, draw game objects
			hud.render(g);
		} else if (gameState == STATE.Menu || gameState == STATE.Help || gameState==STATE.Difficulty) {// user is in help or the menu, draw the menu and help objects
			menu.render(g);
		} else if (gameState == STATE.Upgrade) {// user is on the upgrade screen, draw the upgrade screen
			upgradeScreen.render(g);
		} else if (gameState == STATE.GameOver) {// game is over, draw the game over screen
			gameOver.render(g);
		}

		///////// Draw things above this//////////////
		g.dispose();
		bs.show();
	}
	

	/**
	 * 
	 * Constantly checks bounds, makes sure players, enemies, and info doesn't leave
	 * screen
	 * 
	 * @param var
	 *            x or y location of entity
	 * @param min
	 *            minimum value still on the screen
	 * @param max
	 *            maximum value still on the screen
	 * @return value of the new position (x or y)
	 */
	public static double clamp(double var, double min, double max) {
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	public static void main(String[] args) {

		new Game();
	}
	
	public void setDif(boolean dif)
	{
		isEasy=dif;
	}

}
