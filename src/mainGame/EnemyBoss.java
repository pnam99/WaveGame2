package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.Random;

/**
 * The first boss in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBoss extends GameObject {

	private Handler handler;
	private int timer = 80;
	private int timer2 = 50;
	Random r = new Random();
	private Image img;
	private int spawn;

	public EnemyBoss(ID id, Handler handler, boolean dif) {
		super(Game.WIDTH / 2 - 48, -120, id, dif);
		this.handler = handler;
		velX = 0;
		img = getImage("images/EnemyBoss.png");
		this.health = 1000;// full health is 1000
		this.setDifficulty();
		if (Game.isEasy) {
			velY = 4;
		} else{
			velY = 2;
		}

	}

	public void tick() {
		if (dif) {
			this.x += velX / 2;
			this.y += velY / 2;
		} else {
			this.x += velX;
			this.y += velY;
		}

		if (timer <= 0)
			velY = 0;
		else
			timer--;
		drawFirstBullet();
		if (timer <= 0)
			timer2--;
		if (timer2 <= 0) {
			if (velX == 0)
				velX = 8;
			this.isMoving = true;
			if (dif) {
				spawn = r.nextInt(20);// Sets oftenness of the bosses bullet
			} else {
				spawn = r.nextInt(5);// Sets oftenness of the bosses bullet
			}

			if (spawn == 0) {
				handler.addObject(
						new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler, dif));
				this.health -= 1;
			}
			else if (spawn <=4 && dif)
			{
				this.health -= 3;
			}
		}

		// if (this.y <= 0 || this.y >= Game.HEIGHT - 40) velY *= -1;
		if (this.x <= 0 || this.x >= Game.WIDTH - 742)
			velX *= -1;

		// handler.addObject(new Trail(x, y, ID.Trail, Color.red, 96, 96, 0.025,
		// this.handler));

	}

	public Image getImage(String path) {
		Image image = null;
		try {
			URL imageURL = Game.class.getResource(path);
			image = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return image;
	}

	public void render(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.drawLine(0, 138, Game.WIDTH, 138);
		g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);

		// HEALTH BAR
		g.setColor(Color.GRAY);
		g.fillRect(146,625,1000,25);
		g.setColor(Color.RED);
		g.fillRect(146,625,this.health,25);
		g.setColor(Color.WHITE);
		g.drawRect(146,625,1000,25);

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 96, 96);
	}

	// allows for grey line to be drawn, as well as first bullet shot
	public void drawFirstBullet() {
		if (timer2 == 1)
			handler.addObject(
					new EnemyBossBullet((int) this.x + 48, (int) this.y + 96, ID.EnemyBossBullet, handler, dif));
	}

}
