package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * The game over screen
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class GameOver {

	private Game game;
	private Handler handler;
	private HUD hud;
	private int timer;
	private Color retryColor;
	private String text;

	public GameOver(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		timer = 90;
		this.retryColor = Color.white;
	}

	public void tick() {

		flash();

	}

	public void render(Graphics g) {

		Font font = new Font("Amoebic", 1, 100);
		Font font2 = new Font("Amoebic", 1, 60);
		g.setFont(font);
		text = "Game Over";
		g.drawString(text, Game.WIDTH / 3 - getTextWidth(font, text) / 2, Game.HEIGHT / 2 - 300);
		g.setFont(font2);
		text = "Level: " + hud.getLevel();
		g.drawString(text, Game.WIDTH / 3 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 200);
		text = "Score: " + hud.getScore();
		g.drawString(text, Game.WIDTH / 3 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2 - 100);
		g.setColor(this.retryColor);
		g.setFont(font2);
		text = "Click anywhere to go back to Menu";
		g.drawString(text, Game.WIDTH / 3 - getTextWidth(font2, text) / 2, Game.HEIGHT / 2);

	}

	public void checkHighScore() {
		int score = hud.getScore();
		String[] leaderboard = game.getLeaderboard();
		int[] scores = new int[leaderboard.length + 1];
		String[] tempLeaderboard=new String[leaderboard.length+1];
		tempLeaderboard[tempLeaderboard.length-1]="Dummy-0";
		int i = 0;
		for (String line : leaderboard) {
			scores[i] = Integer.parseInt(line.substring(line.indexOf("-") + 1, line.length()));
			tempLeaderboard[i]=line;
			i++;
		}
		scores[scores.length - 1] = score;
		boolean newHighScore = false;
		for (int k = 0; k < scores.length; k++) {
			for (int j = 0; j < scores.length - 1; j++) {
				if (scores[j] < scores[j + 1]) {
					int temp = scores[j + 1];
					String temp2= tempLeaderboard[j+1];
					scores[j + 1] = scores[j];
					tempLeaderboard[j+1]=tempLeaderboard[j];
					scores[j] = temp;
					tempLeaderboard[j]=temp2;
					newHighScore = true;
				}
			}
		}
		if (newHighScore) {
			String name = JOptionPane.showInputDialog("New Highscore! Enter your name!");
			for (int j=0;j<leaderboard.length;j++) {
				String line=tempLeaderboard[j];
				if (scores[j] == score) {
					leaderboard[j] = name + "-" + scores[j];
				} else {
					leaderboard[j] = line.substring(0, line.indexOf("-") + 1) + scores[j];
				}
				System.out.println(i);
			}
			game.rewriteLeaderboard(leaderboard);
		}
	}

	public void flash() {
		timer--;
		if (timer == 45) {
			this.retryColor = Color.black;
		} else if (timer == 0) {
			this.retryColor = Color.white;
			timer = 90;
		}
	}

	/**
	 * Function for getting the pixel width of text
	 * 
	 * @param font the Font of the test
	 * @param text the String of text
	 * @return width in pixels of text
	 */
	public int getTextWidth(Font font, String text) {
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

}
