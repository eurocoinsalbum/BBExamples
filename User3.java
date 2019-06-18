

public class MyRoboter extends Roboter {
	int i = 0;
	
	@Override
	public Schwierigkeit getSchwierigkeit() {
		return Schwierigkeit.S3_SCHIESST_TOR;
	}
	
	@Override
	public Kommando getKommando(SpielHelfer spielHelfer) {
		Position zielFeld = new Position();
		zielFeld.setTo(spielHelfer.getBall());
		zielFeld.x += 1;
		
		if (spielHelfer.isFreeValidPosition(zielFeld) || isGoal(zielFeld)|| spielHelfer.getRoboterData(1).position)   {
			if (spielHelfer.calcRichtung (position, spielHelfer.getBall()) ==  Kommando.RIGHT) {
				return Kommando.RIGHT;
			}
		}
		
		if (spielHelfer.calcRichtung (position, spielHelfer.getBall()) ==  Kommando.RIGHT) {
			return Kommando.RIGHT_DOWN;
		}
		if (spielHelfer.calcRichtung (position, spielHelfer.getBall()) ==  Kommando.UP) {
			
			if (spielHelfer.isFreeValidPosition(zielFeld)) {
			return Kommando.LEFT_UP;
		}
			return Kommando.UP;
		}
		
		
		
		return null;
	}
	@Override
	public Position getStartPosition(SpielHelfer spielHelfer) {
		i=0;
		return null;
	}
	
	private boolean isGoal(Position position) {
		if (position.x < 0 && position.y > Spiel.GOAL1_A.y && position.y < Spiel.GOAL1_B.y) {
			return true;
		}
		if (position.x > Spiel.SIZE_X - 1 && position.y > Spiel.GOAL2_A.y && position.y < Spiel.GOAL2_B.y) {
			return true;
		}
		return false;
	}
}
