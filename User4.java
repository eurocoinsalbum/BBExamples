

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
		zielFeld.x -= 1;
		Kommando command= spielHelfer.calcRichtung(position, zielFeld);
		if(command != null){ 
			return command;
		}
		
		zielFeld.x += 2;
		if(spielHelfer.isFreeValidPosition(zielFeld)) {
			return  Kommando.RIGHT;  
		}

		if (spielHelfer.getBall().x==spielHelfer.getSizeX()-1) {
			return Kommando.RIGHT;
		}
		if (spielHelfer.getBall().y==spielHelfer.getSizeY()-1) {
			if (Math.random()/40 > 20){
				return Kommando.UP;
			}else{
				return Kommando.UP;				
			}		
		}
			
		
		
		return null;
		
	}
	


	@Override
	public Position getStartPosition(SpielHelfer spielHelfer) {
		i = 0;
		return null;
	}
}
