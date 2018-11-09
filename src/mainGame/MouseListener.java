package mainGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import mainGame.Game.STATE;

/**
 * Handles all mouse input
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class MouseListener extends MouseAdapter {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private Upgrades upgrades;
	private Player player;
	private String upgradeText;

	public MouseListener(Game game, Handler handler, HUD hud, Spawn1to10 spawner, Spawn10to20 spawner2,
			UpgradeScreen upgradeScreen, Player player, Upgrades upgrades) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		this.spawner2 = spawner2;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.upgrades = upgrades;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (game.gameState == STATE.GameOver) {
			handler.object.clear();
			upgrades.resetUpgrades();
			hud.health = 100;
			hud.setScore(0);
			hud.setLevel(1);
			spawner.restart();
			spawner.addLevels();
			spawner2.restart();
			spawner2.addLevels();
			Spawn1to10.LEVEL_SET = 1;
			game.back.play(); // Plays "Back" sound when you click on GameOver screen to go back to menu
			game.gameState = STATE.Menu;
		}

		else if (game.gameState == STATE.Game) {

		}

		else if (game.gameState == STATE.Upgrade) {
			if (mouseOver(mx, my, 100, 150, 1092, 112)) {
				upgradeText = upgradeScreen.getPath(1);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(1);

				game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 300, 1092, 112)) {
				upgradeText = upgradeScreen.getPath(2);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(2);

				game.gameState = STATE.Game;
			} else if (mouseOver(mx, my, 100, 450, 1092, 112)) {
				upgradeText = upgradeScreen.getPath(3);

				upgrades.activateUpgrade(upgradeText);

				upgradeScreen.removeUpgradeOption(3);

				game.gameState = STATE.Game;
			}

		}

		else if (game.gameState == STATE.Menu) {
			// Waves Button
			if (mouseOver(mx, my, 800, 125, 400, 240)) {
				game.select.play();
				game.gameState = STATE.Difficulty;
				// handler.addObject(player);
				// handler.addPickup(new PickupHealth(100, 100, ID.PickupHealth,
				// "images/PickupHealth.png", handler));
			}
			
			else if (mouseOver(mx, my,800, 370, 400, 240)) {
				game.select.play();
				game.gameState = STATE.Skins;
				SkinsWindow skins=new SkinsWindow(handler,player);
			}

			// Help Button
			else if (mouseOver(mx, my, 80, 125, 600, 135)) {
				game.select.play();
				game.gameState = STATE.Help;
			}

			// Credits
			else if (mouseOver(mx, my, 80, 300, 600, 135)) {
				game.select.play();
				JOptionPane.showMessageDialog(game,
						"Made by Brandon Loehle for his "
								+ "final project in AP Computer Science senior year, 2015 - 2016."
								+ "\n\nThis game is grossly unfinished with minor bugs. However,"
								+ " it is 100% playable, enjoy!");
			}

			// Quit Button
			else if (mouseOver(mx, my, 80, 479, 600, 135)) {
				game.hit.play();
				System.exit(1);
			}
		}

		// Back Button for Help screen
		else if (game.gameState == STATE.Help) {
			if (mouseOver(mx, my, 551, 540, 200, 64)) {
				game.back.play(); // "Back" sound
				game.gameState = STATE.Menu;
				return;
			}
		}
		
		else if (game.gameState== STATE.Difficulty)
		{
			if(mouseOver(mx,my,400, 400, 200, 64))//Easy
			{
				game.select.play();
				game.gameState=STATE.Game;
				game.setDif(true);//Sets to easy mode
				handler.addObject(player);
				//game.gameState = STATE.Upgrade;
			}
			
			else if(mouseOver(mx,my,700, 400, 200, 64))//Hard
			{
				game.select.play();
				game.gameState=STATE.Game;
				game.setDif(false);;//Sets to hard mode
				handler.addObject(player);		
			}
			
			else if(mouseOver(mx,my,551, 540, 200, 64))//Back
			{
				game.back.play();
				game.gameState=STATE.Menu;		
			}
		}
	}

	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Helper method to detect is the mouse is over a "button" drawn via Graphics
	 * 
	 * @param mx
	 *            mouse x position
	 * @param my
	 *            mouse y position
	 * @param x
	 *            button x position
	 * @param y
	 *            button y position
	 * @param width
	 *            button width
	 * @param height
	 *            button height
	 * @return boolean, true if the mouse is contained within the button
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
}
