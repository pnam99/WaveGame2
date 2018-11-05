package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {

	private Game game;
	private Handler handler;
	private HUD hud;
	private BufferedImage img;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	private Spawn1to10 spawner;

	public Menu(Game game, Handler handler, HUD hud, Spawn1to10 spawner) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		timer = 10;
		r = new Random();
		addColors();

		img = null;
		try {
			img = ImageIO.read(new File("images/background.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(6)), ID.Firework, this.handler,true));
	}

	public void addColors() {
		colorPick.add(Color.blue);
		colorPick.add(Color.white);
		colorPick.add(Color.green);
		colorPick.add(Color.red);
		colorPick.add(Color.cyan);
		colorPick.add(Color.magenta);
		colorPick.add(Color.yellow);
	}

	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
					colorPick.get(colorIndex), ID.Firework, this.handler,true));
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);
			Font font = new Font("Amoebic", 1, 60);
			Font font2 = new Font("Amoebic", 1, 60);

			//bottom boundary rectangle
			g.setColor(Color.WHITE);
			g.fillRect(0, 659, 1600, 800); //(X,Y,Width,Height)
			
			//right boundary rectangle
			g.setColor(Color.WHITE);
			g.fillRect(1278, 0, 800, 1600); //(X,Y,Width,Height)
			
			
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Wave Game", 500, 90);

			g.setColor(Color.RED);
			g.drawRect(800, 125, 400, 490);//(X,Y,Width,Height)
			g.setFont(font2);
			g.setColor(Color.RED);
			g.drawString("Waves",905,384);

			
			g.setColor(Color.green);
			g.drawRect(80, 125, 600, 135);
			g.setFont(font);
			g.setColor(Color.green);
			g.drawString("Help", 310, 216);
			
			g.setColor(Color.CYAN);
			g.drawRect(80, 300, 600, 135);
			g.setFont(font);
			g.setColor(Color.CYAN);
			g.drawString("Credits", 270, 384);
			
			g.setColor(Color.YELLOW);
			g.drawRect(80, 479, 600, 135);
			g.setFont(font);
			g.setColor(Color.YELLOW);
			g.drawString("Quit", 310, 570);
		

		} else if (game.gameState == STATE.Help) {// if the user clicks on "help"
			Font font = new Font("impact", 1, 50);
			Font font2 = new Font("impact", 1, 30);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 600, 100);

			g.setFont(font2);
			g.drawString("Waves: Simply use WASD to avoid enemies.", 370, 200);
			g.drawString("Once you avoid" + " \n"
					+ "them long enough, a new batch will spawn in! Defeat each boss to win!", 60, 300);

			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(551, 540, 200, 64);
			g.drawString("Back", 620, 580);
		}
		
		else if (game.gameState == STATE.Difficulty) {// if the user clicks on "waves"
			Font font = new Font("impact", 1, 100);
			Font font2 = new Font("impact", 1, 30);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Select your difficulty", 200 , 150);
			

			g.setFont(font2);
			
			g.setColor(Color.blue);
			g.drawRect(400, 400, 200, 64);
			g.drawString("Easy", 470, 443);
			
			
			g.setColor(Color.red);
			g.drawRect(700, 400, 200, 64);
			g.drawString("Hard", 770, 443);
			
			
		
	}
	}
}
