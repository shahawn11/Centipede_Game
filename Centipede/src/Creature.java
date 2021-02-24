
public abstract class Creature implements Animatable {
	protected java.awt.Color _color;
    protected SmartRectangle _creature;
    protected int _x;
    protected int _y;
    protected int _health;


	public Creature(int x, int y, java.awt.Color color) 
	{
		this._x = x;
		this._y = y;
		this._color = color;
		this._health = CentipedeConstants.BASE_HEALTH;
		_creature = new SmartRectangle(color);
		_creature.setSize(CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE);
	}
	
    public void fill(java.awt.Graphics2D aBrush)
    {
    	_creature.fill(aBrush);
    }
    
    public void draw(java.awt.Graphics2D aBrush)
    {
    	_creature.draw(aBrush);
    }
    
    public void setLocation(int x, int y)
    {
		_creature.setLocation(x, y);
	}
    
    public void setColor(java.awt.Color color)
    {
    	this._color = color;
    }

    public boolean moveLeft()
    {
    	int newX = (int)_creature.getX() - CentipedeConstants.BLOCK_SIZE;
    	_creature.setLocation(newX, _creature.getY());
    	
        return true;
    }
    
    public boolean moveRight()
    {
    	int newX = (int)_creature.getX() + CentipedeConstants.BLOCK_SIZE;
    	_creature.setLocation(newX, _creature.getY());
    	
        return true;
    }
    
    public boolean moveUp()
    {
    	int newY = (int)_creature.getY() - CentipedeConstants.BLOCK_SIZE;
    	_creature.setLocation(_creature.getX(), newY);
    
    	return true;
    }
    
    public boolean moveDown()
    {
    	int newY = (int)_creature.getY() + CentipedeConstants.BLOCK_SIZE;
    	_creature.setLocation(_creature.getX(), newY);
    	
    	return true;
    }
    

}
