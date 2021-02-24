import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener
{
    private Player _player;
    private Mushroom[][] _mushrooms;
    private Laser _laser;
    private ArrayList<Centipede> _centipedes;
    private Spider _spider;
    private Flea _flea;
    private Scorpion _scorpion;
    
    private Timer _timer;
    private Random _generator;
    private KeyLeftListener _leftKey;
    private KeyRightListener _rightKey;
    private KeyUpListener _upKey;
    private KeyDownListener _downKey;
    private KeySpaceListener _spaceKey;
    private JLabel _scoreJLabel;
    
    private GameSingleton _gameSingleton;
    private boolean _isGameOver;
    private int _mush_i_index_to_remove;
    private int _mush_j_index_to_remove;
    
    private int _centipede_timer_delay;
    private int _spider_timer_delay;
    private int _flea_timer_delay;
    private int _scorpion_timer_delay;

    
	public GamePanel() 
	{

		// player
		_player = new Player(CentipedeConstants.PLAYER_SPAWN_X, CentipedeConstants.PLAYER_SPAWN_Y, CentipedeConstants.PLAYER_COLOR);
		
		// mushroom
		_mushrooms = new Mushroom[CentipedeConstants.WIDTH][CentipedeConstants.HEIGHT];
		
		// centipede
		_centipedes = new ArrayList<>();
		Centipede centipede = new Centipede(CentipedeConstants.CENTIPEDE_SPAWN_X, CentipedeConstants.CENTIPEDE_SPAWN_Y, 
				CentipedeConstants.CENTIPEDE_INITIAL_SEGMENTS, CentipedeConstants.CENTIPEDE_INITIAL_DIRECTION, 
				CentipedeConstants.CENTIPEDE_INITIAL_BODIES_LOCATION, CentipedeConstants.CENTIPEDE_HEAD_COLOR);
		_centipedes.add(centipede);
		_centipede_timer_delay = 0;
		
		// flea
		_flea_timer_delay = 0;

		// spider
		_spider_timer_delay = 0;
		
		// scorpion
		_scorpion_timer_delay = 0;
		
		// keys
		_upKey = new KeyUpListener(this);
        _downKey = new KeyDownListener(this);
        _leftKey = new KeyLeftListener(this);
        _rightKey = new KeyRightListener(this);
        _spaceKey = new KeySpaceListener(this);
        
        _mush_i_index_to_remove = -1;
        _mush_j_index_to_remove = -1;
        
        setLayout(new GridLayout(1,1));
        _scoreJLabel = new JLabel();
        _scoreJLabel.setText("");
        _scoreJLabel.setFont(new Font(CentipedeConstants.FONT, CentipedeConstants.FONT_STYLE , CentipedeConstants.FONT_SIZE));
        _scoreJLabel.setForeground(CentipedeConstants.SCORE_COLOR);
        _scoreJLabel.setHorizontalAlignment(JLabel.RIGHT);
        _scoreJLabel.setVerticalAlignment(JLabel.BOTTOM);
        spawnMushrooms();
        
        _timer = new Timer(CentipedeConstants.TIMER_DELAY, this);
        _timer.start();
        
        _gameSingleton = new GameSingleton();

        this.setBackground(Color.BLACK);
        add(_scoreJLabel);
        _scoreJLabel.setVisible(true);
	}
	
	public void paintComponent(java.awt.Graphics aBrush) 
	{
        super.paintComponent(aBrush);
        java.awt.Graphics2D aBetterBrush = (java.awt.Graphics2D) aBrush;
        
        _player.fill(aBetterBrush);
        _player.draw(aBetterBrush);

        for(Centipede c : _centipedes)
        {
        	if(c != null)
        	{
	        	c.getHead().fill(aBetterBrush);
	            c.getHead().draw(aBetterBrush);
	            for(SmartRectangle body : c.getBodies())
	            {
	            	if(body != null)
	            	{
	    	        	body.fill(aBetterBrush);
	    	            body.draw(aBetterBrush);
	            	}
	            }
        	}
        }
               
        for(Mushroom[] row : _mushrooms)
    	{
    		for(Mushroom mush : row)
    		{
        		if(mush != null)
        		{
        			mush.fill(aBetterBrush);
        			mush.draw(aBetterBrush);
        		}
        	}
        }
        
        if (_laser != null) 
        {
    		if (_laser._creature.y < 0) 
    		{
				_laser = null;
			}
    		else 
    		{
    			// The Laser spawns in the same "block" as the player, this code only shows the laser if it is not onto of the player
    			if (_player._creature.x == _laser._creature.x - (CentipedeConstants.BLOCK_SIZE/2) & _player._creature.y == _laser._creature.y) {
					
				}else {
					_laser.fill(aBetterBrush);
				}
    		}
		}
        
        if (_flea != null) 
        {
        	_flea.fill(aBetterBrush);
        	_flea.draw(aBetterBrush);
		}

        if (_spider != null) 
        {
        	_spider.fill(aBetterBrush);
        	_spider.draw(aBetterBrush);
		}
        
        if (_scorpion != null) 
        {
        	_scorpion.fill(aBetterBrush);
        	_scorpion.draw(aBetterBrush);
		}
        
        if (_gameSingleton.getLivesRemaining() >= 1) {
        	_scoreJLabel.setText(get_score_lives_string());
		}
        if (_gameSingleton.getLivesRemaining() < 1) {
	        _scoreJLabel.setText(CentipedeConstants.END_GAME_STRING + Integer.toString(_gameSingleton.getScore()));
		}

	}
	
    public Creature creatureFactory()
    {
        Creature newPiece = null;
        int randomNumber;
        
        int x = 0;
        int y = 0;
        randomNumber = (int) (Math.floor(Math.random()*7)+1);
        switch(randomNumber) {
        //    default: newPiece = new T(x,y);     break;
        }
        return newPiece;
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(_centipede_timer_delay == CentipedeConstants.CENTIPEDE_TIMER_DELAY)
		{
			moveCentipede();
			_centipede_timer_delay = 0;
		}
		_centipede_timer_delay++;
		

		if (_laser != null)
		{
			if(!isCentipedeHit())
				_laser.moveUp();
		}
		
		
		if(_flea == null)
		{
			spawnFleaByChance();
		}
		else
		{
			if(_flea_timer_delay == CentipedeConstants.FLEA_TIMER_DELAY)
			{
				moveFlea();
				_flea_timer_delay = 0;
			}	
			_flea_timer_delay++;
		}
		
		
		if(_spider == null)
		{
			spawnSpiderByChance();
		}
		else
		{
			if(_spider_timer_delay == CentipedeConstants.SPIDER_TIMER_DELAY)
			{
				moveSpider();
				_spider_timer_delay = 0;
			}
			_spider_timer_delay++;
			
		}
		
		
		if(_scorpion == null)
		{
			spawnScorpionByChance();
		}
		else
		{
			if(_scorpion_timer_delay == CentipedeConstants.SCORPION_TIMER_DELAY)
			{
				moveScorpion();
				_scorpion_timer_delay = 0;
			}
			_scorpion_timer_delay++;
		}
		
		// spawn one-segmented "head" centipedes periodically
		spawnOneSegmentHeadCentipedeByChance();
		
		update_index_of_mushroom_to_remove();
		
		remove_mushroom_if_needed();
		
		checkEndLevel();
		
		// creature_was_shot must be present for the creatures to disappear and reward the player
		creature_was_shot();
		
		if (playerWasHit()) {
			_gameSingleton.setLife(-1);
			// reset the level
			if (_gameSingleton.getLivesRemaining() > 0) {
				// remove all creatures
				nullAllCreatures();
				// return any poisonous mushrooms to regular mushrooms 
				returnAllMushroomsToRegular();
				// renew centipedes
				_centipedes = new ArrayList<>();
				// re-spawn centipede
				Centipede centipede = new Centipede(CentipedeConstants.CENTIPEDE_SPAWN_X, CentipedeConstants.CENTIPEDE_SPAWN_Y, 
						CentipedeConstants.CENTIPEDE_INITIAL_SEGMENTS - _gameSingleton.getLevel(), CentipedeConstants.CENTIPEDE_INITIAL_DIRECTION, 
						CentipedeConstants.CENTIPEDE_INITIAL_BODIES_LOCATION, CentipedeConstants.CENTIPEDE_HEAD_COLOR);
				_centipedes.add(centipede);
				
				_player = new Player(CentipedeConstants.PLAYER_SPAWN_X, CentipedeConstants.PLAYER_SPAWN_Y, CentipedeConstants.PLAYER_COLOR);

			}
			else {
				_timer.stop();
			}
		}
		
		repaint();
		
	}
	
	/**
     * Checks if player is hit by one of the following creatures
	 * 1. Flea
	 * 2. Spider
	 * 3. Scorpion
	 * 4. Centipede
     *
     * @param none
     * @return true if player is hit false otherwise
     */
	public boolean playerWasHit() {
		return (fleaCollidedWithPlayer() | spiderCollidedWithPlayer() | scorpionCollidedWithPlayer() | centipedeCollidedWithPlayer());
	}
	
	/**
     * Nulls the following creatures
	 * 1. Flea
	 * 2. Spider
	 * 3. Scorpion
	 * 4. Centipede
     *
     * @param none
     * @return void
     */
	public void nullAllCreatures() {
		_centipedes = null;
		_spider = null;
		_scorpion = null;
		_flea = null;
	}
	
	/**
     * Turns all mushrooms to initial state
     *
     * @param none
     * @return void
     */
	public void returnAllMushroomsToRegular() {
        for(Mushroom[] row : _mushrooms)
    	{
    		for(Mushroom mush : row)
    		{
        		if(mush != null)
        		{
					mush.changeToRegular();
					_gameSingleton.setScore(CentipedeConstants.MUSHROOM_REGEN_SCORE);					
        		}
        	}
        }
	}
	
	/**
     * Spawns an one-segment 'head' centipede in the PLAYER_AREA periodically 
     * once any one of the original centipedes has entered the PLAYER_AREA zone.
     * 
     * @param none
     * @return void
     */
	public void spawnOneSegmentHeadCentipedeByChance()
	{
		int rand = (int)(Math.random() * CentipedeConstants.ONE_SEGMENT_HEAD_CENTIPEDE_CHANCE);
		
		if(rand == 0)
		{
			for(int i=0; i<_centipedes.size(); i++)
			{
				if(_centipedes.get(i).getPlayerAreaSwtich())
				{
					rand = (int)(Math.random() * 2);
					
					Centipede one_segment_centipede_head = new Centipede(0, CentipedeConstants.PLAYER_AREA * CentipedeConstants.BLOCK_SIZE, 
								0, CentipedeConstants.RIGHT, CentipedeConstants.CENTIPEDE_INITIAL_BODIES_LOCATION, CentipedeConstants.CENTIPEDE_HEAD_COLOR);
					
					if(rand == 0)
					{
						one_segment_centipede_head = new Centipede(CentipedeConstants.BOARD_WIDTH, CentipedeConstants.PLAYER_AREA * CentipedeConstants.BLOCK_SIZE, 
								0, CentipedeConstants.LEFT, CentipedeConstants.CENTIPEDE_INITIAL_BODIES_LOCATION, CentipedeConstants.CENTIPEDE_HEAD_COLOR);
					}

					_centipedes.add(one_segment_centipede_head);
					break;
				}
			}
		}
	}
	
	/**
     * Spawns a scorpion periodically from either the left wall or the right wall.
     * 
     * @param none
     * @return void
     */
	public void spawnScorpionByChance()
	{
		int rand = (int)(Math.random() * CentipedeConstants.SCORPION_SPAWN_CHANCE);
		
		if(rand == 0)
		{
			// spawning from either left or right
			rand = (int)(Math.random() * 2);
			if(rand == 0)
			{
				rand = (int)(Math.random() * CentipedeConstants.HEIGHT);
				_scorpion = new Scorpion(0, rand * CentipedeConstants.BLOCK_SIZE, CentipedeConstants.RIGHT, CentipedeConstants.SCORPION_COLOR);
			}
			else
			{
				rand = (int)(Math.random() * CentipedeConstants.HEIGHT);
				_scorpion = new Scorpion(CentipedeConstants.BOARD_WIDTH, rand * CentipedeConstants.BLOCK_SIZE, CentipedeConstants.LEFT, CentipedeConstants.SCORPION_COLOR);
			}
			
		}
	}
	
	/**
     * Moves existing scorpion horizontally based on scorpion's
     * horizontal direction across the screen, turning every 
     * mushroom it touch into poisonous mushrooms.
     * Scorpion is set to null once it has reached the other side
     * of the screen.
     * 
     * @param none
     * @return void
     */
	public void moveScorpion()
	{
		if(_scorpion != null)
		{
			int x = (int) (_scorpion._creature.getX() / CentipedeConstants.BLOCK_SIZE);
			int y = (int) (_scorpion._creature.getY() / CentipedeConstants.BLOCK_SIZE);
			
			if(_scorpion.getDirection().equalsIgnoreCase(CentipedeConstants.RIGHT))
			{
				_scorpion.moveRight();	
			}
			else
			{
				_scorpion.moveLeft();
			}
			
			// change mushrooms to poisonous
			if(x >= 0 && x < _mushrooms.length && y >= 0 && y < _mushrooms[x].length)
			{
				if(_mushrooms[x][y] != null)
				{
					_mushrooms[x][y].changeToPoison();
				}
			}
			
			if(x < 0 || x > CentipedeConstants.WIDTH) // scorpion goes over the board
			{
				_scorpion = null; // destroy scorpion
			}
		}
	}
	
	/**
     * Spawns a spider periodically at SPIDER_AREA
     * 
     * @param none
     * @return void
     */
	public void spawnSpiderByChance()
	{
		int rand = (int)(Math.random() * CentipedeConstants.SPIDER_SPAWN_CHANCE); 
		
		if(_spider == null && rand == 0)
		{
			_spider = new Spider(CentipedeConstants.BOARD_WIDTH - CentipedeConstants.BLOCK_SIZE, CentipedeConstants.SPIDER_AREA * CentipedeConstants.BLOCK_SIZE, 
					CentipedeConstants.SPIDERE_INITIAL_HORIZONTAL_DIRECTION, CentipedeConstants.SPIDERE_INITIAL_VERTICAL_DIRECTION, 
					CentipedeConstants.SPIDER_COLOR);	
		}
	}	
	
	/**
     * Moves spider in three different patterns:
     * 1. up - equal likely as 'down'
     * 2. down - equal likely as 'up'
     * 3. zigzag (diagonal) - more likely than 'up' and 'down'
     * 
     * Spider only travels within the SPIDER_AREA and eats
     * some of the mushrooms.
     * 
     * @param none
     * @return void
     */
	public void moveSpider() 
	{
		if(_spider != null)
		{
			int rand = (int)(Math.random() * 4); 
			int x = (int) _spider._creature.getX() / CentipedeConstants.BLOCK_SIZE;
			int y = (int) _spider._creature.getY() / CentipedeConstants.BLOCK_SIZE;
			
			switch(rand)
			{
				case 0:
					y--;
					if(isValid(x, y) && y >= CentipedeConstants.SPIDER_AREA)
						_spider.moveUp();
					break;
				case 1:	
					y++;
					if(isValid(x, y))
						_spider.moveDown();
					break;
				default:
					rand = (int)(Math.random() * 2);
					// reverse vertical direction by chance
					if(rand == 0)
						_spider.reverseVerticalDirection();
					// reverse horizontal direction when spider reaches both left and right walls
					if(x == 0 || x == CentipedeConstants.WIDTH-1)
						_spider.reverseHorizontalDirection();
					
					x += _spider.getHorizontalDirection() / CentipedeConstants.BLOCK_SIZE;
					y += _spider.getVerticalDirection() / CentipedeConstants.BLOCK_SIZE;
					
					if(isValid(x, y) && y >= CentipedeConstants.SPIDER_AREA)
						_spider.moveZigZag();
			}
			
			// eat mushroom
			if(x >= 0 && x < _mushrooms.length && y >= 0 && y < _mushrooms[x].length)
			{
				if(_mushrooms[x][y] != null)
				{
					rand = (int)(Math.random() * CentipedeConstants.SPIDER_EATS_MUSHROOM_CHANCE);
					if(rand == 0)
						_mushrooms[x][y] = null;
				}
			}
		}
	}

	/**
     * Moves flea vertically and disappear(null) upon touching the bottom
     * of the screen, occasionally leaving a trail of mushrooms in 
     * its path.
     * 
     * @param none
     * @return void
     */
	public void moveFlea()
	{
		if(_flea != null)
		{
			int x = (int) _flea._creature.getX();
			int y = (int) _flea._creature.getY();
			if(y < CentipedeConstants.BOARD_HEIGHT)
			{
				_flea.moveDown();
				// flea spawn mushroom chance
				int rand = (int)(Math.random() * CentipedeConstants.FLEA_SPAWN_MUSHROOM_CHANCE);
				if(rand == 0)
					spawnMushroomByLocation(x, y);
			}
			else // reached the bottom
			{		
				_flea = null; // destroy flea
			}
		}
	}
	
	/**
     * Spawns a mushroom at the given coordinates.
     * 
     * @param i x coordinate
     * @param j y coordinate
     * @return void
     */
	public void spawnMushroomByLocation(int i, int j) 
	{
		int x = (int) (i/CentipedeConstants.BLOCK_SIZE);
		int y = (int) (j/CentipedeConstants.BLOCK_SIZE);
		
		if(x >= 0 && x < _mushrooms.length && y >= 0 && y < _mushrooms[x].length)
			if(_mushrooms[x][y] == null)
				_mushrooms[x][y] = new Mushroom(CentipedeConstants.BLOCK_SIZE * x, CentipedeConstants.BLOCK_SIZE * y, CentipedeConstants.MUSHROOM_START_COLOR);
	}
	
	
	/**
     * Spawns a flea periodically from the top of the screen.
     * 
     * @param none
     * @return void
     */
	public void spawnFleaByChance()
	{
		// frequency of spawning the flea
		int rand = (int)(Math.random() * CentipedeConstants.FLEA_SPAWN_CHANCE);
		if(_flea == null && rand == 0)
		{
			// spawn flea randomly across the board width
			rand = (int)(Math.random() * CentipedeConstants.WIDTH) * CentipedeConstants.BLOCK_SIZE;
			_flea = new Flea(rand, 0, CentipedeConstants.FLEA_COLOR);
		}
	}
	
	
	
	
	/**
     *  checks if there is any centipede left, 
	 *  if all centipedes are destroyed, (goes to next level)
	 *    re-spawn mushrooms
	 *    re-spawn new centipede with one segment shorter on top of the screen
	 *    new centipede is accompanied by one additional "head" centipede 
     * 
     * @param none
     * @return void
     */
	public void checkEndLevel()
	{
		if (there_is_a_centipede() == false) {
			// reset mushrooms
			_mushrooms = new Mushroom[CentipedeConstants.WIDTH][CentipedeConstants.HEIGHT];
			
			spawnMushrooms();
			// reset _centipedes
			this._centipedes = new ArrayList<>();
			
			// centipede is one segment shorter next level
			Centipede centipede = new Centipede(CentipedeConstants.CENTIPEDE_SPAWN_X, CentipedeConstants.CENTIPEDE_SPAWN_Y, 
					CentipedeConstants.CENTIPEDE_INITIAL_SEGMENTS - _gameSingleton.getLevel(), CentipedeConstants.CENTIPEDE_INITIAL_DIRECTION, 
					CentipedeConstants.CENTIPEDE_INITIAL_BODIES_LOCATION, CentipedeConstants.CENTIPEDE_HEAD_COLOR);
			_centipedes.add(centipede);
			_gameSingleton.setLevel(1);
			
			// is accompanied by one additional "head" centipede
			for(int i = 0; i < _gameSingleton.getLevel(); i++)
			{
				Centipede one_segment_centipede_head = new Centipede(CentipedeConstants.CENTIPEDE_SPAWN_X, CentipedeConstants.CENTIPEDE_SPAWN_Y, 
						0, reverseDirection(CentipedeConstants.CENTIPEDE_INITIAL_DIRECTION), 
						CentipedeConstants.CENTIPEDE_INITIAL_BODIES_LOCATION, CentipedeConstants.CENTIPEDE_HEAD_COLOR);
				_centipedes.add(one_segment_centipede_head);
			}
		}
	}
	
	/**
     * Checks centipede-player collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean centipedeCollidedWithPlayer() {
        for(Centipede c : _centipedes)
        {
        	if(c != null)
        	{
	        	if ((int)c.getHead().getY() == _player._creature.y && (int)c.getHead().getX() == _player._creature.x) {
					return true;
				}
	        	
	            for(SmartRectangle body : c.getBodies())
	            {
	            	if(body != null)
	            	{
	    	        	if ((int)body.x == _player._creature.x && (int)body.y == _player._creature.y) {
	    					return true;
	    				}
	            	}
	            }
        	}
        }
		
		return false;
	}
	
	/**
     * Checks scorpion-player collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean scorpionCollidedWithPlayer() {
		if (_scorpion != null) {
			if (_scorpion._creature.x == _player._creature.x & _scorpion._creature.y == _player._creature.y) {
				//System.out.println("A Scorpion hit the player");
				return true;
			}
		}
		return false;
	}
	
	/**
     * Checks flea-player collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean fleaCollidedWithPlayer() {
		if (_flea != null) {
			if (_flea._creature.x == _player._creature.x & _flea._creature.y == _player._creature.y) {
				//System.out.println("A Flea hit the player");
				return true;
			}
		}
		return false;
	}
	
	/**
     * Checks spider-player collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean spiderCollidedWithPlayer() {
		if (_spider != null) {
			if (_spider._creature.x == _player._creature.x & _spider._creature.y == _player._creature.y) {
				//System.out.println("A Spider hit the player");
				return true;
			}
		}
		return false;
	}

	/**
     * Checks centipede-laser collision.
     * Shooting any segment of the centipede creates a mushroom; 
     * shooting one of the middle segments split the centipede into two pieces at that point. 
     * Each piece then continues independently on its way down the screen, 
     * with the rear piece growing its own head. 
     * If the head is destroyed, the segment behind it becomes the next head.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean isCentipedeHit()
	{
		for(int i = 0; i < _centipedes.size(); i++)
		{
			Centipede centipede = _centipedes.get(i);
			SmartRectangle head = centipede.getHead();
			ArrayList<SmartRectangle> bodies = centipede.getBodies();
			
			int laser_x = (int) _laser._creature.getX();
			int laser_y = (int) _laser._creature.getY();
			
			// the head is hit
			if(laser_x == head.getX() + CentipedeConstants.BLOCK_SIZE / 2  && laser_y == head.getY())
			{
				// destroy laser
				_laser = null;
				
				// Updated the score
				_gameSingleton.setScore(CentipedeConstants.CENTIPEDE_HEAD_SCORE);
				// the head becomes a mushroom			
				int mush_x = (int)head.getX() / CentipedeConstants.BLOCK_SIZE;
				int mush_y = (int)head.getY() / CentipedeConstants.BLOCK_SIZE;
				if(mush_x >= 0 && mush_x < _mushrooms.length && mush_y >= 0 && mush_y < _mushrooms[mush_x].length)
					_mushrooms[mush_x][mush_y] = new Mushroom(CentipedeConstants.BLOCK_SIZE * mush_x, CentipedeConstants.BLOCK_SIZE * mush_y , 
							CentipedeConstants.MUSHROOM_START_COLOR);
				
				if(bodies.size() > 0) // if the centipede has at least one body segment
				{
					// remove the head and the immediate body segment becomes the head
					centipede.setHead(centipede.removeBodyAt(0));
					
					centipede.moveDown();
					centipede.setDirection(reverseDirection(centipede.getDirection()));
				}
				else // if centipede has no body segment
				{
					_centipedes.remove(i);
				}
				
				return true;
			}
			
			// one of the bodies is hit
			for(int j=0; j<bodies.size(); j++)
			{
				if(laser_x == bodies.get(j).getX() + CentipedeConstants.BLOCK_SIZE / 2  && laser_y == bodies.get(j).getY())
				{
					// destroy laser
					_laser = null;
					
					// Updated the score
					_gameSingleton.setScore( CentipedeConstants.CENTIPEDE_BODY_SCORE);
					
					// the hit body segment becomes a mushroom
					int mush_x = (int)bodies.get(j).getX() / CentipedeConstants.BLOCK_SIZE;
					int mush_y = (int)bodies.get(j).getY() / CentipedeConstants.BLOCK_SIZE;
					if(mush_x >= 0 && mush_x < _mushrooms.length && mush_y >= 0 && mush_y < _mushrooms[mush_x].length)
						_mushrooms[mush_x][mush_y] = new Mushroom(CentipedeConstants.BLOCK_SIZE * mush_x, CentipedeConstants.BLOCK_SIZE * mush_y , 
								CentipedeConstants.MUSHROOM_START_COLOR);
					
					if(j != bodies.size()-1) // not the last body segment
					{
						// adjust new centipede spawn location with an offset
						int offset = CentipedeConstants.BLOCK_SIZE;
						if(centipede.getDirection().equalsIgnoreCase(CentipedeConstants.RIGHT))
							offset = -CentipedeConstants.BLOCK_SIZE;
						
						// split the centipede into two centipedes by spawning another one			
						Centipede newCentipede = new Centipede((int)bodies.get(j).getX() + offset, (int)bodies.get(j).getY(), 
								bodies.size() - j - 2, reverseDirection(centipede.getDirection()), reverseDirection(centipede.getDirection()), CentipedeConstants.CENTIPEDE_HEAD_COLOR);
						
						newCentipede.moveDown();
						_centipedes.add(newCentipede);
						
						// remove all the following body segments after the body segment that was hit
						while(bodies.size() > j)
							centipede.removeBodyAt(bodies.size()-1);
					}
					else // if the last body segment is hit
					{
						centipede.removeBodyAt(bodies.size()-1);
					}
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
     * Moves centipedes either left or right.
     * When it touches a mushroom or reached the edge of the screen, 
     * it descends one level and reverses direction.
     * Once centipedes have entered the PLAYER_AREA,
     * they continue travel within the PLAYER_AREA.
     * 
     * A centipede touching a poisonous mushroom will change color 
     * and hurtle straight down toward the PLAYER_AREA, 
     * then returns to normal behavior upon reaching it.
     * 
     * @param none
     * @return void
     */
	public void moveCentipede()
	{
		for(int i = 0; i < _centipedes.size(); i++)
		{
			Centipede centipede = _centipedes.get(i);
			centipede.setColor(CentipedeConstants.CENTIPEDE_HEAD_COLOR);
    		centipede.setBodiesAndBorderColor(CentipedeConstants.CENTIPEDE_BODY_COLOR, CentipedeConstants.CENTIPEDE_BODY_BORDER_COLOR);
			int x = (int)Math.round(centipede.getHead().getX() / CentipedeConstants.BLOCK_SIZE);
	        int y = (int)Math.round(centipede.getHead().getY() / CentipedeConstants.BLOCK_SIZE);
			if(canMove(centipede, centipede.getDirection()))
			{
				switch(centipede.getDirection().toLowerCase())
				{
					case CentipedeConstants.RIGHT: 
						centipede.moveRight(); 
						break;
					case CentipedeConstants.LEFT: 
						centipede.moveLeft(); 
						break;
					case CentipedeConstants.DOWN: 
						centipede.moveDown();
				}
			}
			else
			{	
		        // centipede reaches the bottom while moving downward
				if(y >= CentipedeConstants.HEIGHT)
				{
					centipede.setPlayerAreaSwtich(true);
				}
				// centipede is about to move out of the PLAYER_AREA while moving upward
				if(y <= CentipedeConstants.PLAYER_AREA)
				{
					centipede.setPlayerAreaSwtich(false);
				}
				// centipede bumps into another centipede
				for(int j = 0; j < _centipedes.size(); j++)
				{
					// skip current centipede
					if(i == j) continue;
					
					// reverse both collided centipedes' directions
					if((int) _centipedes.get(j).getHead().getX() == x && (int) _centipedes.get(j).getHead().getY() == y)
					{
						centipede.setDirection(reverseDirection(centipede.getDirection()));
						_centipedes.get(j).setDirection(reverseDirection(centipede.getDirection()));
						break;
					}
				}
				if(centipede.getPlayerAreaSwtich())
				{
					centipede.moveUp();
					centipede.setDirection(reverseDirection(centipede.getDirection()));
				}
				else
				{
					
					// touching poisionous mushroom
					if(centipede.getDirection().equalsIgnoreCase(CentipedeConstants.RIGHT))
					{
						x++;
					}
					else if(centipede.getDirection().equalsIgnoreCase(CentipedeConstants.LEFT))
					{
						x--;
					}
					
					// centipede rushes down to PLAYER_AREA upon touching a poisonous mushroom
					if(x >= 0 && x < _mushrooms.length && y >= 0 && y < _mushrooms[x].length)
			        {
			        	if(y < CentipedeConstants.PLAYER_AREA && _mushrooms[x][y] != null && _mushrooms[x][y].isPoison())
			        	{
			        		centipede.setColor(CentipedeConstants.CENTIPEDE_POISONOUS_HEAD_COLOR);
			        		centipede.setBodiesAndBorderColor(CentipedeConstants.CENTIPEDE_POISONOUS_BODY_COLOR, CentipedeConstants.CENTIPEDE_BODY_BORDER_COLOR);
			        		while(y < CentipedeConstants.PLAYER_AREA - 1)
			        		{
			        			y++;
			        			centipede.moveDown();
			        		}
			        	}
			        }

					centipede.moveDown();
					centipede.setDirection(reverseDirection(centipede.getDirection()));
				}
				
				
			}
		}
	}
	
	/**
     * Reverses horizontal directions.
     * Left -> Right or Right -> Left
     * 
     * @param dir direction
     * @return the reversed direction.
     */
	public String reverseDirection(String dir)
	{
		if(dir.equalsIgnoreCase(CentipedeConstants.LEFT))
			return CentipedeConstants.RIGHT;
		else if(dir.equalsIgnoreCase(CentipedeConstants.RIGHT))
			return CentipedeConstants.LEFT;
		else
			return dir;
	}
	
	/**
     * Updates index of mushroom to remove.
     * 
     * @param none
     * @return void
     */
	public void update_index_of_mushroom_to_remove() {
		for (int i = 0; i < _mushrooms.length; i++) {
			for (int j = 0; j < _mushrooms[i].length; j++) {
				if(_mushrooms[i][j] != null)
        		{
        			if (_laser != null) {
        				if (mushroomlaserCollision (_mushrooms[i][j])) {
        					_laser = null;
        					_mushrooms[i][j].mushroomWasShot();
        					}
        				if (_mushrooms[i][j]._health <= 0) {
        					_mush_i_index_to_remove = i;
        					_mush_j_index_to_remove = j;
        					break;
        				}
        			}
        		}
			}
		}
	}
	
	/**
     * Checks mushroom-laser collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean mushroomlaserCollision(Mushroom mushroom) {
		if(mushroom._creature.x == _laser._creature.x - (CentipedeConstants.BLOCK_SIZE/2) & mushroom._creature.y == _laser._creature.y){
			return true;
		}
		return false;
	}
	
	/**
     * Removes mushroom if needed.
     * 
     * @param none
     * @return void
     */
	public void remove_mushroom_if_needed() {
		if (_mush_i_index_to_remove != -1) {
			_gameSingleton.setScore(CentipedeConstants.MUSHROOM_DESTROY_SCORE);
			_mushrooms[_mush_i_index_to_remove][_mush_j_index_to_remove] = null;
			_mush_i_index_to_remove = -1;
			_mush_j_index_to_remove = -1;
		}
	}
	
	/**
     * Checks flea-laser collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean flealaserCollision() {
		if (_flea != null & _laser != null) {
			
			if(_flea._creature.x == _laser._creature.x - (CentipedeConstants.BLOCK_SIZE/2) & _flea._creature.y == _laser._creature.y){
				_gameSingleton.setScore(CentipedeConstants.FLEA_SCORE);	
			_flea = null;
			return true;
				}
			}
		return false;
	}
	
	/**
     * Checks scorpion-laser collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean scorpinlaserCollision() {
		if (_scorpion != null & _laser != null) {
			
			if(_scorpion._creature.x == _laser._creature.x - (CentipedeConstants.BLOCK_SIZE/2) & _scorpion._creature.y == _laser._creature.y){
				_gameSingleton.setScore(CentipedeConstants.SCORPION_SCORE);
			_scorpion = null;
			return true;
				}
			}
		return false;
	}
	
	/**
     * Checks spider-laser collision.
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean spiderlaserCollision() {
		if (_spider != null & _laser != null) {
			
			if(_spider._creature.x == _laser._creature.x - (CentipedeConstants.BLOCK_SIZE/2) & _spider._creature.y == _laser._creature.y){
			int scoreToAdd = getScoreOfSpiderKill();
			_gameSingleton.setScore(scoreToAdd);
			_spider = null;
			return true;
				}
			}
		return false;
	}
	
	/**
     * TBA
     * 
     * @param none
     * @return 
     */
	public int getScoreOfSpiderKill() {
		// The instruction state that different points are assigned based on how close the spider is to the player when it is killed.
		int multiplier = CentipedeConstants.SPIDER_CLOSENESS_MULTIPLIER;
		double distance = getEuclidianDistance(_player._creature.x, _player._creature.y, _spider._creature.x, _spider._creature.y);
		if (distance <= CentipedeConstants.BLOCK_SIZE) {
			return CentipedeConstants.SPIDER_SCORE_THREE;
		}
		else if (distance <= multiplier * CentipedeConstants.BLOCK_SIZE) {
			return CentipedeConstants.SPIDER_SCORE_TWO;
		}
		else {
			return CentipedeConstants.SPIDER_SCORE_ONE;
		}
	}
	
	/**
     * TBA
     * 
     * @param none
     * @return 
     */
	public double getEuclidianDistance(double x1, double y1, double x2, double y2) {
		 return (double)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	/**
     * Checks the following collisions:
     * 1. flea-laser
     * 2. scorpion-laser
     * 3. spider-laser
     * 
     * @param none
     * @return true if there is a collision, false otherwise.
     */
	public boolean creature_was_shot() {
		if (flealaserCollision() | scorpinlaserCollision() | spiderlaserCollision()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
     * Spawns mushrooms randomly across the screen with an exception of
     * top first three rows that are free of mushrooms.
     * 
     * Mushrooms are less likely to be spawned in the PLAYER_AREA.
     * 
     * @param none
     * @return void
     */
	public void spawnMushrooms()
	{
		for(int i = 0; i<_mushrooms.length; i++)
		{
			for(int j = 0; j<_mushrooms[i].length; j++)
			{
				// reserved top 3 rows free of mushrooms for centipede to spawn
				if(j < 3) continue;
				int rand = (int)(Math.random() * CentipedeConstants.MUSHROOM_SPAWN_CHANCE); 
				
				// mushrooms have lower chance to spawn in PLAYER_AREA, last row is always free of mushrooms
				if(j >= CentipedeConstants.PLAYER_AREA) 
				{
					// reserved last row free of mushrooms
					if(j >= CentipedeConstants.HEIGHT - 2) break;
					
					// prevent mushroom spawn on top of the player
					if(i == (CentipedeConstants.PLAYER_SPAWN_X / CentipedeConstants.BLOCK_SIZE) 
							&& j == (CentipedeConstants.PLAYER_SPAWN_Y / CentipedeConstants.BLOCK_SIZE))
						continue;
					rand = (int)(Math.random() * CentipedeConstants.PLAYER_AREA_MUSHROOM_SPAWN_CHANCE);
				}
				
				if(0 == rand) 
				{
					// (x.position, y.position, color)
					_mushrooms[i][j] = new Mushroom(CentipedeConstants.BLOCK_SIZE * i, CentipedeConstants.BLOCK_SIZE * j, CentipedeConstants.MUSHROOM_START_COLOR);
				}
			}
		}
		
	}
	
	/**
     * Checks whether the given coordinates are within the game board.
     * 
     * @param i x coordinate
     * @param j y coordinate
     * @return true if the coordinates are valid.
     */
	public boolean isValid(int i, int j)
	{
		if(i < 0 || i >= CentipedeConstants.WIDTH || j < 0 || j >= CentipedeConstants.HEIGHT-1) {
            return false;
        }
		return true;
	}
	
	/**
     * Checks whether there is a mushroom at the given coordinates.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return true if there is no mushroom at the given coordinates.
     */
	public boolean mushroomCollision(int x, int y)
	{
		if(x >= 0 && x < _mushrooms.length && y >= 0 && y < _mushrooms[x].length)
			if(_mushrooms[x][y] != null)
				return false;
		return true;
		
	}
	
	/**
     * Checks whether such creature can move to its next location.
     * 
     * @param creature 
     * @param dir direction
     * @return true if it is possible to move to its next location.
     */
	public boolean canMove(Creature creature, String dir)
	{
		int x = 0, y = 0;
		if(creature instanceof Player)
		{
			x = (int)Math.round(((Player)creature)._creature.getX() / CentipedeConstants.BLOCK_SIZE);
	        y = (int)Math.round(((Player)creature)._creature.getY() / CentipedeConstants.BLOCK_SIZE);
		}
		else if(creature instanceof Centipede)
		{
			x = (int)Math.round(((Centipede)creature).getHead().getX() / CentipedeConstants.BLOCK_SIZE);
	        y = (int)Math.round(((Centipede)creature).getHead().getY() / CentipedeConstants.BLOCK_SIZE);
		}
		else
		{
			System.out.println("Unknown creature.");
			return false;
		}

        switch(dir.toLowerCase())
        {
        	case CentipedeConstants.LEFT:
        		x--;
        		break;
        	case CentipedeConstants.RIGHT: 
        		x++;
        		break;
        	case CentipedeConstants.UP:
        		y--;
        		if(y < CentipedeConstants.PLAYER_AREA)
        			return false;
        		break;
        	case CentipedeConstants.DOWN:
        		y++;    
        }     
         
        if(!isValid(x, y))
        	return false;
        
       
        if(!mushroomCollision(x, y)) 
        	return false;
	        	

    	return true;
	}
	
	/**
     * Fetches current score and lives from GameSingleton class instance.
     * 
     * @param none 
     * @return a String of current score and lives
     */
	public String get_score_lives_string() {
		int temp_score = _gameSingleton.getScore();
		if (_gameSingleton.getScore() > CentipedeConstants.MAX_SCORE) {
			temp_score = CentipedeConstants.MAX_SCORE;
		}
		String myString = "Level: " + Integer.toString(_gameSingleton.getLevel()) + "		     Lives Remaining: " + Integer.toString(_gameSingleton.getLivesRemaining()) + "     Score: " + Integer.toString(temp_score);
		return myString;
	}
	
	/**
     * Checks if there is a centipede.
     * 
     * @param none 
     * @return true if there is a centipede, false otherwise.
     */
	private boolean there_is_a_centipede() {
        for(Centipede c : _centipedes)
        {
        	if(c != null)
        	{
	        	return true;
        	}
        }
		return false;
	}
	
	private class KeyLeftListener extends KeyInteractor 
    {
        public KeyLeftListener(JPanel p)
        {
            super(p,KeyEvent.VK_LEFT);
        }
        
        public  void actionPerformed (ActionEvent e) {
        	if(canMove(_player, CentipedeConstants.LEFT))
        	{
				_player.moveLeft();
				repaint();
        	}
        }
    } 
	
    private class KeyRightListener extends KeyInteractor 
    {
        public KeyRightListener(JPanel p)
        {
            super(p,KeyEvent.VK_RIGHT);
        }
        
        public  void actionPerformed (ActionEvent e) {
        	if(canMove(_player, CentipedeConstants.RIGHT))
        	{
        		_player.moveRight();
                repaint();
        	}
        }
    }
    
    private class KeyUpListener extends KeyInteractor 
    {
        public KeyUpListener(JPanel p)
        {
            super(p,KeyEvent.VK_UP);
        }
        
        public  void actionPerformed (ActionEvent e) {
        	if(canMove(_player, CentipedeConstants.UP))
        	{
        		_player.moveUp();
                repaint();
        	}
        }
    }
    
    private class KeyDownListener extends KeyInteractor 
    {
        public KeyDownListener(JPanel p)
        {
            super(p,KeyEvent.VK_DOWN);
        }
        
        public  void actionPerformed (ActionEvent e) {
        	if(canMove(_player, CentipedeConstants.DOWN))
        	{
        		_player.moveDown();
                repaint();
        	}
        }
    }

    private class KeySpaceListener extends KeyInteractor 
    {
        public KeySpaceListener(JPanel p)
        {
            super(p,KeyEvent.VK_SPACE);
        }
        
        public  void actionPerformed (ActionEvent e) {
        	if (_laser == null) {
        		int x_pos = (int)_player._creature.getX();
        		int y_pos = (int)_player._creature.getY();
        		_laser = new Laser(x_pos, y_pos, Color.MAGENTA);
        		repaint();
        	}

        }
    } 
}

