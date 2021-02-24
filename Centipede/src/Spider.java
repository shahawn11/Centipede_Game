import java.awt.Color;

public class Spider extends Creature {
	private int _horizontal_direction;
	private int _vertical_direction;
	
	public Spider(int x, int y, int h_direction, int v_direction, java.awt.Color color)
	{
		super(x, y, color);
		this._horizontal_direction = h_direction;
		this._vertical_direction = v_direction;
		super._creature.setBorderColor(CentipedeConstants.SPIDER_BORDER_COLOR);
		super.setLocation(x, y);
	}

	public boolean moveZigZag()
	{
		int newX = (int)_creature.getX() + _horizontal_direction;
		int newY = (int)_creature.getY() + _vertical_direction;
    	
    	_creature.setLocation(newX, newY);
        return true;
	}

	public int getHorizontalDirection() 
	{
		return this._horizontal_direction;
	}
	
	public int getVerticalDirection() 
	{
		return this._vertical_direction;
	}

	public void reverseHorizontalDirection()
	{
		this._horizontal_direction *= -1;
	}
	
	public void reverseVerticalDirection()
	{
		this._vertical_direction *= -1;
	}
}
















