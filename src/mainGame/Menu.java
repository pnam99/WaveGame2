package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
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
	private BufferedImage img, img2;
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
		
		img2 = null;
		try {
			img2 = ImageIO.read(new File("images/backgroundHelp.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
				colorPick.get(r.nextInt(4)), ID.Firework, this.handler,true));
	}

	public void addColors() { // These determine the fireworks' colors
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
			colorIndex = r.nextInt(4);
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
			g.drawString("Quinnipiac: The Legend Of The Bobcat", 80, 90);

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
		

		} else if (game.gameState == STATE.Help) {// if the user clicks on "Help"
			g.drawImage(img2, 0, 0, 1280, 700, null); //Sets the background for help menu
			handler.render(g); //Sets the background for help menu
			Font font = new Font("Amoebic", 1, 55);
			Font font2 = new Font("Amoebic", 5, 20);

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 595, 85);

			g.setFont(font2);
			
			// Controls Explanation
			g.drawString("Controls:", 900, 150);
			g.drawString("_______", 900, 155); // "Underline" text
			g.drawString("W/↑: Move Up", 900, 190);
			g.drawString("S/↓: Move Down", 900, 220);
			g.drawString("A/←: Move Left", 900, 250);
			g.drawString("D/→: Move Right", 900, 280);
			g.drawString("ENTER: Use Upgrade", 900, 310);
			g.drawString("ESC: Quit Game", 900, 340);
			
			// "How To Play" Explanation
			g.drawString("How To Play:", 100, 150);
			g.drawString("___________", 100, 155); // "Underline" text
			g.drawString("-Avoid enemies as long as possible", 100, 190);
			g.drawString("by moving around with the keys.", 100, 210);
			g.drawString("-As you advance through the stages,", 100, 240);
			g.drawString("the difficulty will slightly increase.", 100, 260);
			g.drawString("-Each time you beat the boss stage,", 100, 290);
			g.drawString("you obtain a new upgrade that can help you survive.", 100, 310);
			g.drawString("-Your health bar is displayed on the top left.", 100, 340);
			g.drawString("You lose health when enemies hit you. Once it depletes, the game is over.", 100, 360);
			

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
