import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Centipede extends Creature 
{
	private SmartRectangle _head;
	private ArrayList<SmartRectangle> _bodies; // segments
	private String _direction;
	private boolean playerAreaSwtich;
	
	public Centipede(int x, int y, int segments, String direction, String bodies_location, java.awt.Color color) 
	{
		super(x, y, color);
		_head = super._creature;
		playerAreaSwtich = CentipedeConstants.CENTIPEDE_INITIAL_PLAYER_AREA_SWITCH;
		_direction = direction;
		_head.setBorderColor(CentipedeConstants.CENTIPEDE_HEAD_BORDER_COLOR);

		_bodies = new ArrayList<>();
		// initialize bodies/segments
		for(int i = 0; i < segments; i++)
		{
			SmartRectangle body = new SmartRectangle(CentipedeConstants.CENTIPEDE_BODY_COLOR);
			body.setSize(CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE);
			body.setBorderColor(CentipedeConstants.CENTIPEDE_BODY_BORDER_COLOR);
			_bodies.add(body);
		}
		setLocation(x, y, bodies_location);
	}


	public void setLocation(int x, int y, String bodies_location) 
	{
		_head.setLocation(x, y);
		switch(bodies_location.toLowerCase())
		{
			case CentipedeConstants.LEFT:
				for(int i = 0; i < _bodies.size(); i++)
					_bodies.get(i).setLocation(_head.getX() - (CentipedeConstants.BLOCK_SIZE * (i+1)),  _head.getY());
				
				break;
			case CentipedeConstants.RIGHT:
				for(int i = 0; i < _bodies.size(); i++)
					_bodies.get(i).setLocation(_head.getX() + (CentipedeConstants.BLOCK_SIZE * (i+1)),  _head.getY());
			
				break;
			default: // Face down
				for(int i = 0; i < _bodies.size(); i++)
					_bodies.get(i).setLocation(_head.getX(),  _head.getY() - (CentipedeConstants.BLOCK_SIZE * (i+1)));
		}
		
	}
	
	@Override
	public boolean moveLeft()
    {
		int prev_head_x = (int) _head.getX();
		int prev_head_y = (int) _head.getY();
		int newX = (int)_head.getX() - CentipedeConstants.BLOCK_SIZE;
		_head.setLocation(newX, _head.getY());
 
		moveBodies(prev_head_x, prev_head_y);
    	return true;
    }
    
	@Override
    public boolean moveRight()
    {
		int prev_head_x = (int) _head.getX();
		int prev_head_y = (int) _head.getY();
		int newX = (int)_head.getX() + CentipedeConstants.BLOCK_SIZE;
		_head.setLocation(newX, _head.getY());
 
		moveBodies(prev_head_x, prev_head_y);
    	return true;
    }
    
	@Override
    public boolean moveDown()
    {
		int prev_head_x = (int) _head.getX();
		int prev_head_y = (int) _head.getY();
		
		int newY = (int)_head.getY() + CentipedeConstants.BLOCK_SIZE;
		_head.setLocation(_head.getX(), newY);
 
		moveBodies(prev_head_x, prev_head_y);
		
    	return true;
    }
	
	@Override
    public boolean moveUp()
    {
		int prev_head_x = (int) _head.getX();
		int prev_head_y = (int) _head.getY();
		
		int newY = (int)_head.getY() - CentipedeConstants.BLOCK_SIZE;
		_head.setLocation(_head.getX(), newY);
 
		moveBodies(prev_head_x, prev_head_y);
		
    	return true;
    }

	
	public void moveBodies(int prev_head_x, int prev_head_y)
	{
		if(_bodies.size() > 0) // at least 1 segment
		{
			// move bodies
	    	for(int i = 0; i < _bodies.size(); i++)
	    	{ 		
	    		int next_body_x = prev_head_x;
	    		int next_body_y = prev_head_y;
	    		prev_head_x = (int) _bodies.get(i).getX();
	    		prev_head_y = (int) _bodies.get(i).getY();
	    		_bodies.get(i).setLocation(next_body_x, next_body_y);	
	    	}
		}
	}
	
	public ArrayList<SmartRectangle> getBodies()
	{
		return this._bodies;
	}
	
	public SmartRectangle getHead()
	{
		return this._head;
	}
	
	public void setHead(SmartRectangle head)
	{
		head.setColor(CentipedeConstants.CENTIPEDE_HEAD_COLOR);
		head.setBorderColor(CentipedeConstants.CENTIPEDE_HEAD_BORDER_COLOR);
		this._head = head;
	}

	public void setBodies(ArrayList<SmartRectangle> bodies)
	{
		this._bodies = bodies;
	}
	
	public SmartRectangle removeBodyAt(int index)
	{
		return this._bodies.remove(index);
	}
	
	public String getDirection()
	{
		return this._direction;
	}
	
	public void setDirection(String dir)
	{
		this._direction = dir;
	}
	
	public boolean getPlayerAreaSwtich()
	{
		return this.playerAreaSwtich;
	}
	
	public void setPlayerAreaSwtich(boolean b)
	{
		this.playerAreaSwtich = b;
	}
	
	public void setBodiesAndBorderColor(Color body_color, Color border_color)
	{
		for(SmartRectangle body : this._bodies)
		{
			body.setColor(body_color);
			body.setBorderColor(border_color);
		}
	}
}



































