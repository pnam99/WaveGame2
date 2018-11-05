package mainGame;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SkinsListener implements ActionListener {

	private Player _player;
	private Trail _trail;
	private Color _color;
	private boolean _isPlayer;
	private Image _image;
	private boolean _isImage;

	public SkinsListener(Player player, Trail trail, Color color, boolean isPlayer) {
		_player = player;
		_trail = trail;
		_color = color;
		_isPlayer = isPlayer;
		_isImage=false;
	}

	public SkinsListener(Player player, Image image) {
		_player = player;
		_image = image;
		_isImage = true;

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (_isImage) {
			_player.insertImage(_image);
		} else {
			if (_isPlayer) {

				_player.setColor(_color);
			}

			else {
				_player.setTrailColor(_color);
			}

		}

	}

}
