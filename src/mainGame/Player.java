package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * The main player in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private int damage;
	private int playerWidth, playerHeight;
	public static int playerSpeed = 10;
	private Image image;
	private Color color;
	private Color trailColor;
	private Trail trail;
	private boolean isDead;

	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game, boolean dif) {
		super(x, y, id,dif);
		this.handler = handler;
		this.color=Color.white;
		this.trailColor=Color.WHITE;
		this.hud = hud;
		this.game = game;
		this.damage = 2;
		this.isDead=false;
		playerWidth = 32;
		playerHeight = 32;
		this.setDifficulty();
	}

	@Override
	public void tick() {
		if(game.isEasy)
		{
			this.x += velX*2;
			this.y += velY*2;
		}
		else
		{
			this.x += velX;
			this.y += velY;
		}
		x = Game.clamp(x, 0, Game.WIDTH - 674);
		y = Game.clamp(y, 0, Game.HEIGHT - 453);

		Trail trail=new Trail(x, y, ID.Trail, trailColor, playerWidth, playerHeight, 0.05, this.handler);
		this.trail=trail;
		handler.addObject(this.trail);
		collision();
		checkIfDead();

	}

	public void checkIfDead() {
		
		if (hud.health <= 0) {// player is dead, game over!
			game.die.play();
			if (hud.getExtraLives() == 0) {
				game.gameState = STATE.GameOver;
				 handler.clearPlayer();
				 game.checkLeaderboard();
			}

			else if (hud.getExtraLives() > 0) {// has an extra life, game continues
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
		}
	}

	/**
	 * Checks for collisions with all of the enemies, and handles it accordingly
	 */
	public void collision() {

		hud.updateScoreColor(Color.white);
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.EnemyBasic || tempObject.getId() == ID.EnemyFast
					|| tempObject.getId() == ID.EnemySmart || tempObject.getId() == ID.EnemyBossBullet
					|| tempObject.getId() == ID.EnemySweep || tempObject.getId() == ID.EnemyShooterBullet
					|| tempObject.getId() == ID.EnemyBurst || tempObject.getId() == ID.EnemyShooter
					|| tempObject.getId() == ID.BossEye) {// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) {// player hit an enemy
					hud.health -= damage;
					hud.updateScoreColor(Color.red);
					
					if (hud.health > 0) { // Only plays "hit" sound if health > 0. If health <= 0, ONLY "death" sound plays (line 70)
						game.hit.play();
					}
					
				}

			}
			if (tempObject.getId() == ID.EnemyBoss) {
				// Allows player time to get out of upper area where they will get hurt once the
				// boss starts moving
				if (this.y <= 138 && tempObject.isMoving) {
					hud.health -= 2;
					hud.updateScoreColor(Color.red);
				}
			}

		}
	}

	@Override
	public void render(Graphics g) {

		g.setColor(this.color);
		g.fillRect((int) x, (int) y, playerWidth, playerHeight);
		if(this.image!=null)
		{
			g.drawImage(this.image, (int) this.x, (int) this.y, playerWidth, playerHeight, null);
		}

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 32, 32);
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
	}

	public void insertImage(Image image) {
		this.image=image;
		
	}

	public void setColor(Color color) {
		this.color=color;
		
	}

	public void setTrailColor(Color color) {
		this.trailColor=color;
		
	}

	public Trail getTrail() {
		return this.trail;
	}

}
