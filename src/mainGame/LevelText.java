package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * This is the text you see before each set of 10 levels
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class LevelText extends GameObject {

	private String text;
	private int timer;
	
	Color customBlue = new Color(0,54,130);
	Color customYellow = new Color(186,151,39);
	Color customRed = new Color(142,11,52);
	
	private Color[] color = { Color.WHITE, new Color(186,151,39), new Color(0,54,130), Color.WHITE };
	private Random r = new Random();
	private int index;
	private double x1, y1;

	public LevelText(double x, double y, String text, ID id,boolean dif) {
		super(x, y, id,dif);
		this.text = text;
		AffineTransform at = new AffineTransform();
		timer = 15;
		x1 = x;
		y1 = y;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		timer--;

		Font font = new Font("Amoebic", 1, 60);
		g.setFont(font);
		g.setColor(color[index]);// set the new random color
		g.drawString(this.text, (int) x1, (int) y1);

		if (timer == 0) {
			index = r.nextInt(4);// get a new random color
			timer = 15;
		}

	}

	public int getTextWidth(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
		int textWidth = (int) (font.getStringBounds(text, frc).getWidth());
		return textWidth;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
