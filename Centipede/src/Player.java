import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Creature {
	
	public Player(int x, int y, java.awt.Color color) 
	{
		super(x, y, color);
		super.setLocation(x, y);
	}

}
