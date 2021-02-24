import java.awt.Color;
import java.awt.Font;
public class CentipedeConstants {
	// Game settings
	public static final int BLOCK_SIZE = 25;
	public static final int WIDTH = 25;
	public static final int HEIGHT = 30;
	public static final int BOARD_WIDTH = BLOCK_SIZE * WIDTH;
	public static final int BOARD_HEIGHT = BLOCK_SIZE * HEIGHT;
	
	public static final int STARTING_LIVES = 3;
	public static final int STARTING_SCORE = 0;
	public static final int MAX_LIFE = 6;
	public static final int MAX_SCORE = 999999;
	public static final int MIN_MUSHROOM = 30;
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String ZIGZAG = "zigzag";
	public static final java.awt.Color SCORE_COLOR = Color.WHITE;
	public static final String FONT = "Helvetica";
	public static final int FONT_SIZE = 16;
	public static final int FONT_STYLE = Font.BOLD;
	public static final int NEW_LIFE_THRESHOLD = 10000;
	public static final int INITIAL_LEVEL = 1;
	public static final String END_GAME_STRING = "The Game is Over!		Final score:";
	
	// Timer delay
	public static final int TIMER_DELAY = 15; // 10
	public static final int CENTIPEDE_TIMER_DELAY = 10; // 10
	public static final int SPIDER_TIMER_DELAY = 10; // 10
	public static final int FLEA_TIMER_DELAY = 10;
	public static final int SCORPION_TIMER_DELAY = 10;
	
	
	// Creature
	public static final int BASE_HEALTH = 1;
	
	// Player
	public static final int PLAYER_SPAWN_X = BLOCK_SIZE * (WIDTH / 2);
	public static final int PLAYER_SPAWN_Y = BLOCK_SIZE * (HEIGHT - 4);
	public static final int PLAYER_AREA = 24;
	public static final java.awt.Color PLAYER_COLOR = new Color(255, 255, 102);
	
	// Mushroom
	public static final java.awt.Color MUSHROOM_START_COLOR = new Color(0, 128, 0);
	public static final java.awt.Color MUSHROOM_ONE_HIT_COLOR = new Color(0, 96, 0);
	public static final java.awt.Color MUSHROOM_TWO_HIT_COLOR = new Color(0, 64, 0);
	public static final java.awt.Color MUSHROOM_THREE_HIT_COLOR = new Color(0, 32, 0);
	
	public static final java.awt.Color MUSHROOM_BORDER_START_COLOR = new Color(255,0,0);
	public static final java.awt.Color MUSHROOM_BORDER_COLOR_ONE_HIT = new Color(224,0,0);
	public static final java.awt.Color MUSHROOM_BORDER_COLOR_TWO_HIT = new Color(192,0,0);
	public static final java.awt.Color MUSHROOM_BORDER_COLOR_THREE_HIT = new Color(160,0,0);
	
	public static final java.awt.Color MUSHROOM_POISONOUS_COLOR_START = new Color(0, 250, 255);
	public static final java.awt.Color MUSHROOM_POISONOUS_ONE_HIT = new Color(0, 200, 255);
	public static final java.awt.Color MUSHROOM_POISONOUS_TWO_HITS = new Color(0, 150, 255);
	public static final java.awt.Color MUSHROOM_POISONOUS_THREE_HITS = new Color(0, 100, 255);
	public static final int MUSHROOM_DESTROY_SCORE = 1;
	public static final int MUSHROOM_REGEN_SCORE = 5;
	public static final int MUSHROOM_SPAWN_CHANCE = 8; // 8
	public static final int PLAYER_AREA_MUSHROOM_SPAWN_CHANCE = 32; // recommend 32
	public static final int MUSHROOM_BASE_HEALTH = 4;
	
	// Centipede
	public static final int CENTIPEDE_INITIAL_SEGMENTS = 10;
	public static final int CENTIPEDE_BODY_SCORE = 10;
	public static final int CENTIPEDE_HEAD_SCORE = 100;
	public static final java.awt.Color CENTIPEDE_HEAD_COLOR = java.awt.Color.PINK;
	public static final java.awt.Color CENTIPEDE_HEAD_BORDER_COLOR = new Color(254, 27, 7);
	public static final java.awt.Color CENTIPEDE_BODY_COLOR = java.awt.Color.GREEN;
	public static final java.awt.Color CENTIPEDE_BODY_BORDER_COLOR = java.awt.Color.YELLOW;
	public static final java.awt.Color CENTIPEDE_POISONOUS_HEAD_COLOR = new Color(180, 160, 255);
	public static final java.awt.Color CENTIPEDE_POISONOUS_BODY_COLOR = new Color(0, 150, 255);
	public static final int CENTIPEDE_SPAWN_X = BLOCK_SIZE * (WIDTH / 2);
	public static final int CENTIPEDE_SPAWN_Y = BLOCK_SIZE;
	public static final String CENTIPEDE_INITIAL_DIRECTION = RIGHT;
	public static final String CENTIPEDE_INITIAL_BODIES_LOCATION = UP;
	public static final boolean CENTIPEDE_INITIAL_PLAYER_AREA_SWITCH = false;
	public static final int ONE_SEGMENT_HEAD_CENTIPEDE_CHANCE = 2000; // 2000
	
	// Flea
	public static final int FLEA_SCORE = 200;
	public static final int FLEA_SPAWN_CHANCE = 1000; // 1000
	public static final int FLEA_SPAWN_MUSHROOM_CHANCE = 5; // 5
	public static final java.awt.Color FLEA_COLOR = new Color(115, 24, 0);
	public static final java.awt.Color FLEA_BORDER_COLOR = Color.CYAN;
	
	// Spider
	public static final int SPIDER_SCORE_ONE = 300;
	public static final int SPIDER_SCORE_TWO = 600;
	public static final int SPIDER_SCORE_THREE = 900;
	public static final int SPIDER_AREA = 18;
	public static final int SPIDER_SPAWN_CHANCE = 0; // 
	public static final int SPIDER_EATS_MUSHROOM_CHANCE = 3; // 3
	public static final int SPIDERE_INITIAL_HORIZONTAL_DIRECTION = -BLOCK_SIZE;
	public static final int SPIDERE_INITIAL_VERTICAL_DIRECTION = BLOCK_SIZE;
	public static final java.awt.Color SPIDER_COLOR = new Color(34, 24, 0);
	public static final java.awt.Color SPIDER_BORDER_COLOR = Color.CYAN;
	public static final int SPIDER_CLOSENESS_MULTIPLIER = 2;
	
	// Scorpion
	public static final int SCORPION_SCORE = 1000;
	public static final int SCORPION_SPAWN_CHANCE = 1000; // 1000
	public static final java.awt.Color SCORPION_COLOR = new Color(76, 70, 50);
	public static final java.awt.Color SCORPION_BORDER_COLOR = Color.WHITE;
	
	// Laser
	public static final java.awt.Color LASER_COLOR = java.awt.Color.MAGENTA;

	
}
