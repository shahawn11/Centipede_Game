import java.awt.Color;

public class Flea extends Creature {
	
	public Flea(int x, int y, java.awt.Color color) 
	{
		super(x, y, color);
		super._creature.setBorderColor(CentipedeConstants.FLEA_BORDER_COLOR);
		super.setLocation(x, y);
	}

}
