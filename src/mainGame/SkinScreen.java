package mainGame;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class SkinScreen extends JPanel{
	
	private Handler _handler;
	private Player _player;
	private int timer;
	private JWindow _window;
	
	public SkinScreen(Handler handler,Player player, JWindow window)
	{
		_handler=handler;
		_window=window;
		_player=player;
		timer=10;
	}
	
	public void tick() {
		timer--;
		if (timer <= 0) {
			_handler.object.clear();
			_handler.addObject(_player);
			timer=300;
		}
		_handler.tick();
		render();
	}
	
	public void render()
	{
		
		this.setVisible(true);
		this.setSize(400,400);
		this.setBackground(Color.RED);
		JButton button=new JButton();
		this.add(button);
		_window.add(this);
		this.repaint();
		this.revalidate();
		
	}

}
