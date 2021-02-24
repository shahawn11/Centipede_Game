import java.awt.Color;
import java.awt.Graphics2D;

public class Scorpion extends Creature {
	
	private String _direction;
	public Scorpion(int x, int y, String direction, java.awt.Color color) 
	{
		super(x, y, color);
		this._direction = direction;
		super._creature.setBorderColor(CentipedeConstants.SCORPION_BORDER_COLOR);
		super.setLocation(x, y);
	}

	public String getDirection()
	{
		return this._direction;
	}
	
	


}
