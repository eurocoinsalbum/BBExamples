import java.util.Scanner;

public class Main {
	public static int LEER = 0;
	public static int BAUER = 1;
	public static int TURM = 2;
	public static int SPRINGER = 3;
	public static int LAEUFER = 4;
	public static int DAME = 5;
	public static int KOENIG = 1;

	public static void main(String[] args) {
		Scanner myScanner = new Scanner(System.in);
		int[][] brett = new int[8][8];
		boolean schachmatt = false;
		startaufstellung(brett);
		
		while(!schachmatt) {
			zeigeBrett(brett);
			print("Startfeld eingeben:");
			int vonSpalte = getSpalte(myScanner);
			int vonZeile = getZeile(myScanner);
			int figur = brett[vonZeile][vonSpalte];
			if (figur == LEER) {
				print("Auf diesem Feld steht keine Figur");
				continue;
			}
			
			print("Zielfeld eingeben:");
			int nachSpalte = getSpalte(myScanner);
			int nachZeile = getZeile(myScanner);
			zieheFigur(brett, vonZeile, vonSpalte, nachZeile, nachSpalte);
		}
		
		myScanner.close();
	}

	private static void zeigeBrett(int[][] brett) {
		print("  abcdefgh");
		for (int zeile = 0; zeile < 8; zeile++) {
			System.out.print((8 - zeile) + "-");
			for (int spalte = 0; spalte < 8; spalte++) {
				// alle Figuren einer Zeile direkt nebeneinander ohne Zeilenumbruch ausgeben
				System.out.print(brett[zeile][spalte]);
			}
			System.out.println("-" + (8 - zeile));
		}
		print("  abcdefgh");
	}

	private static void startaufstellung(int[][] brett) {
		// Schachbrett anlegen und alle Felder leer machen (keine Figur)
		for (int zeile = 0; zeile < 8; zeile++) {
			for (int spalte = 0; spalte < 8; spalte++) {
				brett[zeile][spalte] = LEER;
			}
		}
		// Figuren aufstellen. Erst weiß, dann schwarz.
		initialisiereFiguren(brett, false);
		initialisiereFiguren(brett, true);
	}

	private static void initialisiereFiguren(int[][] brett, boolean schwarz) {
		int zeileBauern = 1;
		if (schwarz) {
			zeileBauern = 6;
		}
		for (int spalte = 0; spalte < 8; spalte++) {
			brett[zeileBauern][spalte] = BAUER;
		}
		int zeileFiguren = 0;
		if (schwarz) {
			zeileFiguren = 7;
		}
		brett[zeileFiguren][0] = TURM;
		brett[zeileFiguren][1] = SPRINGER;
		brett[zeileFiguren][2] = LAEUFER;
		if (schwarz) {
			brett[zeileFiguren][3] = KOENIG;
			brett[zeileFiguren][4] = DAME;
		} else {
			brett[zeileFiguren][3] = DAME;
			brett[zeileFiguren][4] = KOENIG;
		}
		brett[zeileFiguren][5] = LAEUFER;
		brett[zeileFiguren][6] = SPRINGER;
		brett[zeileFiguren][7] = TURM;
	}

	private static int getSpalte(Scanner scanner) {
		print("Zeile (a-h):");
		String zeileBuchstabe = scanner.next();
		int zeile = zeileBuchstabe.charAt(0) - 'a';
		return zeile;
	}

	private static int getZeile(Scanner scanner) {
		return 8 - scanner.nextInt();
	}

	private static void zieheFigur(int[][] brett, int vonZeile, int vonSpalte, int nachZeile, int nachSpalte) {
		druckeZug(brett, vonZeile, vonSpalte, nachZeile, nachSpalte);
		brett[nachZeile][nachSpalte] = brett[vonZeile][vonSpalte];
		brett[vonZeile][vonSpalte] = LEER;
	}

	private static void druckeZug(int[][] brett, int vonZeile, int vonSpalte, int nachZeile, int nachSpalte) {
		System.out.print("Zug: " + vonSpalte + vonZeile);
		if (brett[nachZeile][nachSpalte] == LEER) {
			System.out.print("-");
		} else {
			System.out.print("x");
		}
		System.out.print("Zug: " + nachSpalte + nachZeile);
	}

	private static void print(String text) {
		System.out.println(text);
	}

}
