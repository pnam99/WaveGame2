package mainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JButton;

public class SkinsButton extends JButton{
	
	
	private Handler _handler;
	private Player _player;
	private Trail _trail;
	private Color _color;
	private String _name;
	private Image _image;
	private boolean _isPlayer;
	
	public SkinsButton(Handler handler, Player player, Trail trail,Color color,String name, boolean isPlayer)
	{
		_handler=handler;
		_player=player;
		_trail=trail;
		_color=color;
		_name=name;
		_isPlayer=isPlayer;
		this.setPreferredSize(new Dimension(35,35));
		this.setEnabled(true);
		this.setText(_name);
		this.addActionListener(new SkinsListener(_player,_trail,_color,_isPlayer));
	}
	
	public SkinsButton(Player player,Image image,String name)
	{
		_player=player;
		_image=image;
		_name=name;
		this.setPreferredSize(new Dimension(35,35));
		this.setEnabled(true);
		this.setText(_name);
		this.addActionListener(new SkinsListener(_player,_image));
	}

}
