package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyFast extends GameObject {

	private Handler handler;

	public EnemyFast(double x, double y, ID id, Handler handler,boolean dif) {
		super(x, y, id,dif);
		this.setDifficulty();
		this.handler = handler;
		velX = 2;
		velY = 9;
	}

	public void tick() {
		if(dif)
		{
			this.x += velX/2;
			this.y += velY/2;
		}
		else
		{
			this.x += velX;
			this.y += velY;
		}
		if (this.y <= 0 || this.y >= Game.HEIGHT - 40) {
			velY *= -1;
		}
		if (this.x <= 0 || this.x >= Game.WIDTH - 16) {
			velX *= -1;
		}
		
		//x = Game.clamp(x, 0, Game.WIDTH - 674);
		//y = Game.clamp(y, 0, Game.HEIGHT - 453);

		handler.addObject(new Trail(x, y, ID.Trail, Color.cyan, 16, 16, 0.025, this.handler));

	}

	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int) x, (int) y, 16, 16);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
