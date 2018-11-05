package mainGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.*;

public class SkinsPanel extends JPanel{
	
	private Handler _handler;
	private Player _player;
	private Trail _trail;

	public SkinsPanel(Handler handler, Player player, Trail trail) {
		_handler=handler;
		_player=player;
		_trail=trail;
		this.setVisible(true);
		this.setBackground(Color.black);
		handler.addObject(player);
		GridLayout grid=new GridLayout(3,11,10,10);
		this.setLayout(grid);
		
		this.add(new JTextField("Character Colors"));
		SkinsButton red=new SkinsButton(_handler,_player,_trail,Color.red,"Red",true);
		this.add(red);
		SkinsButton orange=new SkinsButton(_handler,_player,_trail,Color.orange,"Orange",true);
		this.add(orange);
		SkinsButton yellow=new SkinsButton(_handler,_player,_trail,Color.yellow,"Yellow",true);
		this.add(yellow);
		SkinsButton green=new SkinsButton(_handler,_player,_trail,Color.green,"Green",true);
		this.add(green);
		SkinsButton blue=new SkinsButton(_handler,_player,_trail,Color.blue,"blue",true);
		this.add(blue);
		SkinsButton magenta=new SkinsButton(_handler,_player,_trail,Color.magenta,"Magenta",true);
		this.add(magenta);
		SkinsButton cyan=new SkinsButton(_handler,_player,_trail,Color.cyan,"Cyan",true);
		this.add(cyan);
		SkinsButton gray=new SkinsButton(_handler,_player,_trail,Color.gray,"Gray",true);
		this.add(gray);
		SkinsButton pink=new SkinsButton(_handler,_player,_trail,Color.pink,"Pink",true);
		this.add(pink);
		SkinsButton white=new SkinsButton(_handler,_player,_trail,Color.white,"white",true);
		this.add(white);
		
		this.add(new JTextField("Trail Colors"));
		
		SkinsButton redT=new SkinsButton(_handler,_player,_trail,Color.red,"Red",false);
		this.add(redT);
		SkinsButton orangeT=new SkinsButton(_handler,_player,_trail,Color.orange,"Orange",false);
		this.add(orangeT);
		SkinsButton yellowT=new SkinsButton(_handler,_player,_trail,Color.yellow,"Yellow",false);
		this.add(yellowT);
		SkinsButton greenT=new SkinsButton(_handler,_player,_trail,Color.green,"Green",false);
		this.add(greenT);
		SkinsButton blueT=new SkinsButton(_handler,_player,_trail,Color.blue,"blue",false);
		this.add(blueT);
		SkinsButton magentaT=new SkinsButton(_handler,_player,_trail,Color.magenta,"Magenta",false);
		this.add(magentaT);
		SkinsButton cyanT=new SkinsButton(_handler,_player,_trail,Color.cyan,"Cyan",false);
		this.add(cyanT);
		SkinsButton grayT=new SkinsButton(_handler,_player,_trail,Color.gray,"Gray",false);
		this.add(grayT);
		SkinsButton pinkT=new SkinsButton(_handler,_player,_trail,Color.pink,"Pink",false);
		this.add(pinkT);
		SkinsButton whiteT=new SkinsButton(_handler,_player,_trail,Color.white,"white",false);
		this.add(whiteT);
		
		this.add(new JTextField("Character Skins"));
		
		SkinsButton boss=new SkinsButton(_player,getImage("images/EnemyBoss.png"),"Boss1");
		this.add(boss);
		SkinsButton hoff=new SkinsButton(_player,getImage("images/Hoffman.jpg"),"Hoffman");
		this.add(hoff);
		SkinsButton blake=new SkinsButton(_player,getImage("images/Blake.jpg"),"Blake");
		this.add(blake);
		SkinsButton duncan=new SkinsButton(_player,getImage("images/Duncan.jpg"),"Duncan");
		this.add(duncan);
		SkinsButton zach=new SkinsButton(_player,getImage("images/Zach.jpg"),"Zach");
		this.add(zach);
		SkinsButton pika=new SkinsButton(_player,getImage("images/Pikachu.png"),"Pikachu");
		this.add(pika);
		SkinsButton boom=new SkinsButton(_player,getImage("images/Boomer.jpg"),"Boomer the bobcat");
		this.add(boom);
		SkinsButton fox=new SkinsButton(_player,getImage("images/Fox.png"),"Fox");
		this.add(fox);
		SkinsButton qu=new SkinsButton(_player,getImage("images/QU.png"),"QU");
		this.add(qu);
		SkinsButton god=new SkinsButton(_player,getImage("images/Hoffos.jpg"),"God");
		this.add(god);
		
		
		
		
		
		this.repaint();
		this.revalidate();
		
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
	
	

}
