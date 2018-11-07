package mainGame;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import mainGame.Game.STATE;

public class SkinsWindow extends JFrame {
	
	
	private Handler _handler;
	private Player _player;
	private Trail _trail;
	private SkinsPanel _pan;
	public SkinsWindow(Handler handler, Player player)
	{
		_handler=handler;
		_player=player;
		_trail=this._player.getTrail();
		_pan=new SkinsPanel(handler,player,_trail);
		this.setVisible(true);
		this.setSize(new Dimension(1000, 1000));
		this.setResizable(false);
		this.setLocation(150, 0);
		this.repaint();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(_pan);
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                Game.gameState=STATE.Menu;
            }
        });
		
	}

}
