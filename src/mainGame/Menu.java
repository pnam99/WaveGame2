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
	private BufferedImage img, img2, img3, img4, img5;
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
			img = ImageIO.read(new File("images/background.jpg")); // Green misty background
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		img2 = null;
		try {
			img2 = ImageIO.read(new File("images/backgroundHelp.jpg")); // QU Blue Solid
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		img3 = null;
		try {
			img3 = ImageIO.read(new File("images/bobcat.png")); // Bobcat (transparent)
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		img4 = null;
		try {
			img4 = ImageIO.read(new File("images/background2.jpg")); // Library (Outside)
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		img5 = null;
		try {
			img5 = ImageIO.read(new File("images/background3.png")); // Aerial Drone View
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(4)), ID.Firework, this.handler,true));
	}

	public void addColors() { // Determines the firework colors
		Color customBlue = new Color(0,54,130);
		Color customYellow = new Color(186,151,39);
		Color customRed = new Color(142,11,52);
		colorPick.add(customBlue); //QU Blue (RGB)
		colorPick.add(customYellow); //QU Yellow (RGB)
		colorPick.add(Color.WHITE); //QU White
		colorPick.add(customRed); //QU Red (Bobcat's mouth) (RGB)
	}

	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(4); // Cycles through the firework colors
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
					colorPick.get(colorIndex), ID.Firework, this.handler,true));
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
//			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null); //Green misty background
			g.drawImage(img5, 0, 0, 1300, 660, null); // Drone View
			handler.render(g);
			Font font = new Font("Amoebic", 1, 60);
			Font font2 = new Font("Amoebic", 1, 60);

			//bottom boundary rectangle
			g.setColor(Color.WHITE);
			g.fillRect(0, 659, 1600, 800); //(X,Y,Width,Height)
			
			//right boundary rectangle
			g.setColor(Color.WHITE);
			g.fillRect(1278, 0, 800, 1600); //(X,Y,Width,Height)
			
			
			g.setFont(new Font("Century Gothic", 1, 60));
			g.setColor(new Color(225,175,39)); //Custom Color
			g.drawString("QU: The Legend Of The Bobcat", 200, 90); // Title text displayed at the top of the main menu

			g.setColor(Color.RED);
			g.drawRect(800, 125, 400, 240);//(X,Y,Width,Height)
			g.setFont(font2);
			g.setColor(Color.RED);
			g.drawString("Waves",905,270);
			
			g.setColor(Color.MAGENTA);
			g.drawRect(800, 370, 400, 240);//(X,Y,Width,Height)
			g.setFont(font2);
			g.setColor(Color.MAGENTA);
			g.drawString("Skins",905,510);

			
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
			g.drawImage(img2, 0, 0, 1280, 700, null); //Sets the background for help menu
			g.drawImage(img3, 425, 80, 450, 450, null); //Bobcat picture
			handler.render(g); //Sets the background for help menu
			Font font = new Font("Amoebic", 1, 55);
			Font font2 = new Font("Amoebic", 5, 20);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 590, 85);

			g.setFont(font2);
			
			// Controls Explanation
			g.drawString("Controls:", 950, 150);
			g.drawString("_______", 950, 155); // "Underline" text
			g.drawString("W/â†‘: Move Up", 950, 190);
			g.drawString("S/â†“: Move Down", 950, 220);
			g.drawString("A/â†�: Move Left", 950, 250);
			g.drawString("D/â†’: Move Right", 950, 280);
			g.drawString("ENTER: Use Upgrade", 950, 310);
			g.drawString("ESC: Quit Game", 950, 340);
			
			// "How To Play" Explanation
			g.drawString("How To Play:", 100, 150);
			g.drawString("___________", 100, 155); // "Underline" text
			g.drawString("-Avoid enemies as long as possible", 100, 190);
			g.drawString(" by moving around with the keys.", 100, 210);
			g.drawString("-As you advance through the stages,", 100, 250);
			g.drawString(" the difficulty will slightly increase.", 100, 270);
			g.drawString("-Each time you beat the boss stage,", 100, 310);
			g.drawString(" you obtain a new upgrade.", 100, 330);
			g.drawString("-Your health bar is displayed on the top left.", 100, 370);
			g.drawString(" You lose health when enemies hit you.", 100, 390);
			g.drawString(" Once it depletes, the game is over.", 100, 410);
			

			g.setFont(font2);
			g.setColor(Color.white);
			g.drawRect(551, 540, 200, 64);
			g.drawString("Back", 630, 580);
		}
		
		else if (game.gameState == STATE.Difficulty) {// if the user clicks on "waves"
			Font font = new Font("impact", 1, 100);
			Font font2 = new Font("impact", 1, 30);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Select Your Difficulty", 170 , 150);
			

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
