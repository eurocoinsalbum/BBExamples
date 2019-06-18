import java.util.Scanner;

public class MyRoboter extends Roboter {
	int i = 0;
	@Override
	public Schwierigkeit getSchwierigkeit() {
		return Schwierigkeit.S3_SCHIESST_TOR;
	}
	
	@Override
	public Kommando getKommando(SpielHelfer spielHelfer) {
		Scanner myScanner = new Scanner(System.in);
		int aktion = myScanner.nextInt();
		if (aktion == 1){
			return Kommando.RIGHT;
		}
		if (aktion == 2){
			return Kommando.RIGHT_UP;
		}
		if (aktion == 3){
			return Kommando.UP;
		}
		if (aktion == 4){
			return Kommando.LEFT_UP;
		}
		if (aktion == 5){
			return Kommando.LEFT;
		}
		if (aktion == 6){
			return Kommando.LEFT_DOWN;
		}
		if (aktion == 7){
			return Kommando.DOWN;
		}
		if (aktion == 8){
			return Kommando.RIGHT_DOWN;
		}
		if (aktion == 9){
			return Kommando.KICK;
		}
		return null;
		/*
		 * Position lvb = new Position();
		lvb.setTo(spielHelfer.getBall());
		lvb.x -=1;
		if (position.machtes(lvb) && position.x > spielHelfer.getSizeX() - 5){
			return Kommando.KICK;
		}
		Position ovb = new Position();
		ovb.setTo(spielHelfer.getBall());
		ovb.y -= 1;		
		
		if (position.machtes(ovb)){
			Position rvb = new Position();
			rvb.setTo(spielHelfer.getBall());
			rvb.x += 1;
			if (spielHelfer.getRoboterData(1).position.machtes(rvb)){
				return Kommando.DOWN;
			}
		}
		Position zielFeld2 = new Position();
		zielFeld2.setTo(spielHelfer.getBall());
		zielFeld2.x -= 1;
		Kommando command = spielHelfer.calcRichtung(position, zielFeld2);
		if (command == null) {
			Position zielFeld = new Position();
			zielFeld.setTo(spielHelfer.getBall());
			zielFeld.x += 1;
			
			if (spielHelfer.isFreeValidPosition(zielFeld)) {
				return Kommando.RIGHT;
			}
			Position zielFeld3 = new Position();
			zielFeld3.setTo(spielHelfer.getBall());
			zielFeld3.y -= 1;
			Kommando command2 = spielHelfer.calcRichtung(position, zielFeld3);
			return command2;
		}
		
		return command;
		*/
	}

	@Override
	public Position getStartPosition(SpielHelfer spielHelfer) {
		i = 0;
		return null;
	}
}
