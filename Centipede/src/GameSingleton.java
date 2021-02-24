
public class GameSingleton {
	private int _level;
	private int _lives_remaining;
	private int _score;
	private GameSingleton instance;
	private int _scoreUpdateThreshold;
	
	public GameSingleton() {
		// TODO Auto-generated constructor stub
		_level = CentipedeConstants.INITIAL_LEVEL;
		_score = CentipedeConstants.STARTING_SCORE;
		_scoreUpdateThreshold = CentipedeConstants.NEW_LIFE_THRESHOLD;
		_lives_remaining = CentipedeConstants.STARTING_LIVES;
	}
	
	public GameSingleton getInstance() {
		return instance;
		
	}
	
	public int getLevel() {
		return _level;
	}
	
	public void setLevel(int level) {
		_level += level;
	}
	
	public int getScore() {
		return _score;
	}
	
	public void setScore(int pointsToAdd) {
		_score += pointsToAdd;
		updateNewLifeThresold();
	}
	
	public int getLivesRemaining() {
		return _lives_remaining;
		
	}
	
	public void setLife(int in) {
		_lives_remaining += in;
	}
	
	public void updateNewLifeThresold() {
		if (_score >= _scoreUpdateThreshold) {
			_scoreUpdateThreshold += CentipedeConstants.NEW_LIFE_THRESHOLD;
			if (_lives_remaining < CentipedeConstants.MAX_LIFE) {
				_lives_remaining+= 1;
			}
		}
	}
}
