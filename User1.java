

public class MyRoboter extends Roboter {
	int i = 0;
	boolean ausspielen = false;
	
	@Override
	public Schwierigkeit getSchwierigkeit() {
		return Schwierigkeit.S4_DRIBBELT;
	}

	@Override
	public Kommando getKommando(SpielHelfer spielHelfer) {
		Position ball = spielHelfer.getBall();	
		Position zielFeld = spielHelfer.calcPosition(ball, Kommando.LEFT);
		if (position.x == spielHelfer.getBall().x +1 && position.y == spielHelfer.getBall().y){
			return Kommando.LEFT_UP;
		}
		if (position.y == spielHelfer.getBall().y -1 && position.x == spielHelfer.getBall().x){
			return Kommando.LEFT_DOWN;
		}
		if (position.y == spielHelfer.getBall().y +1  && position.x == spielHelfer.getBall().x){
			return Kommando.LEFT_UP;
		}
		if (position.x == spielHelfer.getRoboterData(1).position.x -2 && position.y == spielHelfer.getRoboterData(1).position.y) {
			if (position.x == spielHelfer.getBall().x -1 && position.y == spielHelfer.getRoboterData(1).position.y){
				ausspielen = true;
				}
			}
		
		
		if (ausspielen){
			if (i == 0){
				i++;
				return Kommando.DOWN;
				
			}
			if (i == 1){
				i++;
				return Kommando.RIGHT_UP;
				
			}
			if (i == 2){
				i = 0;
				ausspielen = false;
				return Kommando.UP;
		}
		}
		if(position.x == spielHelfer.getSizeX() -4){
			if (position.x == spielHelfer.getBall().x -1 && position.y == spielHelfer.getBall().y){
			return Kommando.KICK;
			}
		}
		return spielHelfer.calcRichtung(position, spielHelfer.getBall());
		
	}

	@Override
	public Position getStartPosition(SpielHelfer spielHelfer) {
		i = 0;
		return null;
	}
}
