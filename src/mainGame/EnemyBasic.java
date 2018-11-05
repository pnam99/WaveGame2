package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Window;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBasic extends GameObject {

	private Handler handler;
	public EnemyBasic(double x, double y, int i, int j, boolean dif, ID id, Handler handler) {
		super(x, y, id,Game.isEasy);
		this.handler = handler;
		this.velX = velX / 2;
		this.velY = velY / 2;
		this.velX = velX;
		this.velY = velY;
		this.setDifficulty();

	}

	public void tick() {
		if (dif) {
			this.x += velX / 2;
			this.y += velY / 2;
		} else {
			this.x += velX;
			this.y += velY;
		}

		if (this.y <= 0 || this.y >= Game.HEIGHT - 453)
			velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 674)
			velX *= -1;
		
		x = Game.clamp(x, 0, Game.WIDTH - 674);
		y = Game.clamp(y, 0, Game.HEIGHT - 453);

		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 16, 16, 0.025, this.handler));

	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, 16, 16);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
