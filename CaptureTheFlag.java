
public class Main {

	private static final int SIZE_X = 20;
	private static final int SIZE_Y = 10;
	static Position flagPosition;
	static Robot robot;

	public static void main(String[] args) {
		initGame();
		int moves = 0;
		int manhatten = getManhatten(flagPosition, robot.position);
		
		System.out.println("Startposition");
		printField();
		while(true) {
			Command command = robot.getCommand(flagPosition);
			if (command == Command.TAKE) {
				if (robot.position.matches(flagPosition)) {
					System.out.println("Captured the flag!");
					break;
				} else {
					System.out.println("Fehlgriff!");
				}
			} else {
				moveRobot(robot, command);
			}
			moves++;
			System.out.println("Nach Schritt Nr. " + moves);
			printField();
		}
		System.out.println("Erwartete Schritte: " + manhatten);
		System.out.println("Benötigte Schritte: " + moves);
	}

	private static int getManhatten(Position pos1, Position pos2) {
		return Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y);
	}

	private static void initGame() {
		robot = new Robot();
		flagPosition = getZufallPosition();
		robot.position = getZufallPosition();
	}
	
	private static Position getZufallPosition() {
		return createPosition(getZufall(1, SIZE_Y - 1), getZufall(1, SIZE_X - 1));
	}
	
	private static int getZufall(int min, int max) {
		return (int)(Math.random() * (max - min)) + min;
	}

	private static void printField() {
		for (int y = 0; y < SIZE_Y; y++) {
			for (int x = 0; x < SIZE_X; x++) {
				Position position = createPosition(y, x);
				if (flagPosition.matches(position)) {
					System.out.print("P");
				} else if (robot.position.matches(position)) {
					System.out.print("X");
				} else {
					System.out.print(" ");
				}
			}
			System.out.println("");
		}
		System.out.println("##############");
	}

	private static void moveRobot(Robot robot, Command command) {
		if (command == Command.UP) {
			if (robot.position.y > 0) {
				robot.position.y--;
			}
		}
		if (command == Command.DOWN) {
			if (robot.position.y < SIZE_Y - 1) {
				robot.position.y++;
			}
		}
		if (command == Command.LEFT) {
			if (robot.position.x > 0) {
				robot.position.x--;
			}
		}
		if (command == Command.RIGHT) {
			if (robot.position.x < SIZE_X - 1) {
				robot.position.x++;
			}
		}
	}

	private static Position createPosition(int y, int x) {
		Position position = new Position();
		position.x = x;
		position.y = y;
		return position;
	}

}
