import java.awt.Color;

import javax.swing.text.StyleConstants.ColorConstants;

public class Laser extends Creature {

	public Laser(int x, int y, Color color) {
		super(x, y, color);
		super.setColor(color);
		super._creature.setSize(CentipedeConstants.BLOCK_SIZE/10, CentipedeConstants.BLOCK_SIZE);
		super.setLocation(x + (CentipedeConstants.BLOCK_SIZE/2), y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean moveUp()
    {
		int oldX = (int)_creature.getX();
		int oldY = (int)_creature.getY();
		
		
		_creature.setLocation(oldX, oldY - CentipedeConstants.BLOCK_SIZE);
		return true;
    }

}
