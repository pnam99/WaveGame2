package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBurst extends GameObject {

	private Handler handler;
	private int timer;
	private int size;
	private String side;
	private Random r = new Random();

	public EnemyBurst(double x, double y, double velX, double velY, int size, String side, ID id, Handler handler,boolean dif) {
		super(x, y, id,dif);
		this.setDifficulty();
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.timer = 60;
		this.side = side;
		this.size = size;
		if (this.side.equals("left")) {
			handler.object.add(new EnemyBurstWarning(0, 0, 20, 659, ID.EnemyBurstWarning, handler,dif));
			setPos();
			setVel();
		} else if (this.side.equals("right")) {
			handler.object.add(
					new EnemyBurstWarning(1258, 0, 20, 659, ID.EnemyBurstWarning, handler,dif));
			setPos();
			setVel();

		} else if (this.side.equals("top")) {
			handler.object.add(new EnemyBurstWarning(0, 0, 1278, 20, ID.EnemyBurstWarning, handler,dif));
			setPos();
			setVel();

		} else if (this.side.equals("bottom")) {
			handler.object.add(
					new EnemyBurstWarning(0, 639, 1278, 20, ID.EnemyBurstWarning, handler,dif));
			setPos();
			setVel();

		}

	}

	public void tick() {

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		// if (this.x <= 0 || this.x >= Game.WIDTH - 16) velX *= -1;

		
		handler.addObject(new Trail(x, y, ID.Trail, Color.ORANGE, this.size, this.size, 0.025, this.handler));

		timer--;
		if (timer <= 0) {
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
			

		}

	}

	public void setPos() {
		if (this.side.equals("left")) {
			this.y = r.nextInt(((Game.HEIGHT - 325 - size) - 0) + 1) + 0;
		} else if (this.side.equals("right")) {
			this.x = 1278;
			this.y = 100;

		} else if (this.side.equals("top")) {
			this.y = -(size) - 325;
			this.x = r.nextInt(((Game.WIDTH - 425 - size) - 0) + 1) + 0;

		} else if (this.side.equals("bottom")) {
			this.y = 659;
			this.x = r.nextInt(((Game.WIDTH - 325 - size) - 0) + 1) + 0;

		}
	}

	public void setVel() {
		if (this.side.equals("left")) {
			this.velY = 0;
		} else if (this.side.equals("right")) {
			this.velX = -(this.velX);
			this.velY = 0;

		} else if (this.side.equals("top")) {
			this.velX = 0;

		} else if (this.side.equals("bottom")) {
			this.velX = 0;
			this.velY = -(this.velY);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect((int) x, (int) y, 1, 1);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 200, 200);
	}

}
