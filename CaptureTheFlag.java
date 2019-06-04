
public class Main {

	private static final int SIZE_X = 20;
	private static final int SIZE_Y = 10;
	static Position flagPosition;
	static Robot robot;

	public static void main(String[] args) {
		initGame();
		
		for (int i = 0; i < 50; i++) {
			Command command = robot.getCommand(flagPosition);
			if (command == Command.TAKE) {
				if (robot.position.matches(flagPosition)) {
					System.out.println("Captured the flag!");
					break;
				}
			} else {
				moveRobot(robot, command);
			}
			printField();
		}
	}

	private static void initGame() {
		robot = new Robot();
		flagPosition = createPosition(4, 8);
		robot.position = createPosition(SIZE_Y / 2, SIZE_X / 2);
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
		System.out.println("####################");
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
