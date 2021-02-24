import java.awt.Color;

public class Mushroom extends Creature {
	
	
	private boolean isPoison;
	public Mushroom(int x, int y, java.awt.Color color) 
	{
		super(x, y, color);
		super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_START_COLOR);
		super.setLocation(x, y);
		super._health = CentipedeConstants.MUSHROOM_BASE_HEALTH;
		isPoison = false;
	}
	
	public void mushroomWasShot() {
		super._health -= 1;
		if (super._health == 3) {
			if (isPoison == false) {
				super._creature.setColor(CentipedeConstants.MUSHROOM_ONE_HIT_COLOR);
			}
			else {
				super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_ONE_HIT);
			}
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_COLOR_ONE_HIT);
		}
		else if (super._health == 2) {
			if (isPoison == false) {
				super._creature.setColor(CentipedeConstants.MUSHROOM_TWO_HIT_COLOR);
			}
			else {
				super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_TWO_HITS);
			}
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_COLOR_TWO_HIT);
		}
		else {
			if (isPoison == false) {
				super._creature.setColor(CentipedeConstants.MUSHROOM_THREE_HIT_COLOR);
			}
			else {
				super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_THREE_HITS);
			}
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_COLOR_THREE_HIT);
		}
	}
	
	public void changeToPoison() {
		isPoison = true;
		if (super._health == 4) {
			super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_COLOR_START);
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_START_COLOR);
		}
		else if (super._health == 3) {
			super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_ONE_HIT);
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_COLOR_ONE_HIT);
		}
		else if (super._health == 2) {
			super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_TWO_HITS);
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_COLOR_TWO_HIT);
		}
		else {
			super._creature.setColor(CentipedeConstants.MUSHROOM_POISONOUS_THREE_HITS);
			super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_COLOR_THREE_HIT);
		}
	}
	
	public void changeToRegular() {
		if(isPoison) isPoison = false;
		super._health = CentipedeConstants.MUSHROOM_BASE_HEALTH;
		super._creature.setColor(CentipedeConstants.MUSHROOM_START_COLOR);
		super._creature.setBorderColor(CentipedeConstants.MUSHROOM_BORDER_START_COLOR);
	}
	
	public boolean isPoison() 
	{
		return this.isPoison;
	}
}


























