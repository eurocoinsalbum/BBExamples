import java.util.Scanner;

public class Main {
	public static int BAUM = 0;
	public static int STAMM = 1;
	public static int WIESE = 2; 
	public static int SETZLING = 3;
	public static int BRETTER = 4;
	public static int FOERSTER = 5;
	public static int HOLZFAELLER = 6;
	public static int SAEGEWERK = 7;
	public static int ANZAHL_FELDINHALT_TYPEN = 8;
	public static int ANZAHL_GEBAEUDE_TYPEN = 3;
	public static int[] gebaeudeTypen = new int[ANZAHL_GEBAEUDE_TYPEN];
	public static int SIZE_X = 15;
	public static int SIZE_Y = 15;
	public static int[][] land = new int[SIZE_Y][SIZE_X];
	public static String[] feldGrafik = new String[9];
	public static int[] arbeitsRadius = new int[9];
	public static boolean[][] berechnet = new boolean[SIZE_Y][SIZE_X];
	public static Spieler spieler = new Spieler();
	public static Scanner scanner = new Scanner(System.in);
	public static Siedler2D siedler2DFrame;

	public static void main(String[] args) {
		initialisiereSpiel();
		
		while (checkWeiterspielen()) {
			printLand();
			printSpielstatus();
			benutzerAktion();
			rechne();
			spieler.alter++;
		}
	}
	
	private static boolean checkWeiterspielen() {
		if (spieler.gold >= 100) {
			print("Gewonnen! Du hast " + spieler.gold + " Gold verdient.");
			return false;			
		}
		if (spieler.alter >= 80) {
			print("Verloren! Bis zu Deinem " + spieler.alter + ". Geburtstag hast Du nicht genug Gold verdient.");
			return false;			
		}
		return true;
	}

	private static void benutzerAktion() {
		printAktionsMenu();
		int aktion = scanner.nextInt();
		if (aktion == 0) {
			// mache nichts
		} else if (aktion == 1) {
			benutzerAktionBauen();
		} else if (aktion == 2) {
			benutzerAktionAbreissen();
		}
	}

	private static void benutzerAktionBauen() {
		int feldInhalt = frageGebaeudeTyp();
		Position bauPosition = fragePosition();
		if (getFeldInhalt(bauPosition) == WIESE) {
			setFeldInhalt(bauPosition, feldInhalt);
		} else {
			print("Dort befindet sich kein Bauland");
		}
	}

	private static void benutzerAktionAbreissen() {
		Position abrissPosition = fragePosition();
		int feldInhalt = getFeldInhalt(abrissPosition);
		if (istGebaeude(feldInhalt)) {
			setFeldInhalt(abrissPosition, WIESE);
		} else {
			print("Dort befindet sich kein Bauland");
		}
	}

	private static int frageGebaeudeTyp() {
		print("Bitte Gebäude wählen:");
		print("1 - Holzfäller");
		print("2 - Förster");
		print("3 - Sägewerk");
		
		int gebaeudeTyp = scanner.nextInt();
		if (gebaeudeTyp == 1) {
			return HOLZFAELLER;
		}
		if (gebaeudeTyp == 2) {
			return FOERSTER;
		}
		if (gebaeudeTyp == 3) {
			return SAEGEWERK;
		}
		return WIESE;
	}

	private static boolean istGebaeude(int feldInhalt) {
		for (int i = 0; i < ANZAHL_GEBAEUDE_TYPEN; i++) {
			if (gebaeudeTypen[i] == feldInhalt) {
				return true;
			}
		}
		return false;
	}

	private static Position fragePosition() {
		Position position = new Position();
		print("Position Reihe:");
		position.y = scanner.nextInt();
		print("Position Spalte:");
		position.x = scanner.nextInt();
		return position;
	}

	private static void printAktionsMenu() {
		print("Bitte Aktion wählen:");
		print("0 - Urlaub");
		print("1 - Bauen");
		print("2 - Abreißen");
	}

	private static void initialisiereSpiel() {
		spieler.gold = 0;
		spieler.alter = 20;
		
		resetSperren();
		
		feldGrafik[0] = " ";
		feldGrafik[BAUM] = "*";
		feldGrafik[STAMM] = "|";
		feldGrafik[WIESE] = " ";
		feldGrafik[SETZLING] = ".";
		feldGrafik[BRETTER] = "=";
		feldGrafik[FOERSTER] = "F";
		feldGrafik[HOLZFAELLER] = "H";
		feldGrafik[SAEGEWERK] = "S";

		arbeitsRadius[0] = 0;
		arbeitsRadius[BAUM] = 0;
		arbeitsRadius[STAMM] = 0;
		arbeitsRadius[WIESE] = 0;
		arbeitsRadius[SETZLING] = 0;
		arbeitsRadius[BRETTER] = 0;
		arbeitsRadius[FOERSTER] = 2;
		arbeitsRadius[HOLZFAELLER] = 2;
		arbeitsRadius[SAEGEWERK] = 3;
		
		gebaeudeTypen[0] = HOLZFAELLER;
		gebaeudeTypen[1] = FOERSTER;
		gebaeudeTypen[2] = SAEGEWERK;
				
		for (int y = 0; y < SIZE_Y; y++) {
			for (int x = 0; x < SIZE_X; x++) {
				land[y][x] = WIESE;
			}
		}
		
		land[2][5] = BAUM;
		land[4][1] = BAUM;
		land[4][2] = BAUM;
		land[3][2] = BAUM;
		land[4][3] = SETZLING;
		land[6][7] = SETZLING;
		land[6][5] = SETZLING;
		land[5][3] = STAMM;
		land[6][1] = STAMM;
		land[3][5] = STAMM;
		land[2][6] = BRETTER;
		land[2][7] = FOERSTER;
		land[3][6] = HOLZFAELLER;
		land[2][4] = SAEGEWERK;
		
		siedler2DFrame = new Siedler2D();
	}

	private static void resetSperren() {
		for (int y = 0; y < SIZE_Y; y++) {
			for (int x = 0; x < SIZE_X; x++) {
				berechnet[y][x] = false;
			}
		}
	}

	private static void rechne() {
		for (int y = 0; y < SIZE_Y; y++) {
			for (int x = 0; x < SIZE_X; x++) {
				Position position = createPosition(y, x);
				int feldInhalt = getFeldInhalt(position);
				// ein Arbeiter darf nur arbeiten, wenn sein Haus nicht gerade frisch gebaut wurde (Feld gesperrt für diese Runde)
				boolean feldGesperrt = berechnet[position.y][position.x];

				if (feldInhalt == SETZLING) {
					setFeldInhalt(position, BAUM);
				} else if (feldInhalt == FOERSTER && !feldGesperrt) {
					arbeiteFoerster(position);
				} else if (feldInhalt == HOLZFAELLER && !feldGesperrt) {
					arbeiteHolzfaeller(position);
				} else if (feldInhalt == SAEGEWERK && !feldGesperrt) {
					arbeiteSaegewerk(position);
				}
			}
		}
		resetSperren();
	}

	private static void printLand() {
		System.out.print("  ");
		for (int x = 0; x < SIZE_X; x++) {
			System.out.print(x % 10);
		}
		print("");
		
		for (int y = 0; y < SIZE_Y; y++) {
			System.out.print((y % 10) + " ");
			for (int x = 0; x < SIZE_X; x++) {
				Position position = createPosition(y, x);
				int feldInhalt = getFeldInhalt(position);
				System.out.print(feldGrafik[feldInhalt]);
			}
			print("");
		}
		print("");
	
		siedler2DFrame.zeichne();
	}

	private static void printSpielstatus() {
		print("Du bist jetzt " + spieler.alter + " Jahre alt.");
		print("Du hast " + spieler.gold + " Gold.");
	}
	
	public static int getFeldInhalt(Position position) {
		return land[position.y][position.x];
	}

	private static Position findePosition(Position startPosition, int suchFeldInhalt, int radius) {
		for (int r = 1; r <= radius; r++) {
			for (int y = startPosition.y - r; y <= startPosition.y + r; y++) {
				for (int x = startPosition.x - r; x <= startPosition.x + r; x++) {
					Position suchPosition = createPosition(y, x);
					if (!isValidPosition(suchPosition)) {
						continue;
					}
					if (berechnet[y][x]) {
						continue;
					}
					int feldInhalt = getFeldInhalt(suchPosition);
					if (feldInhalt == suchFeldInhalt) {
						return suchPosition;
					}
				}
			}
		}
		return null;
	}

	private static int countPositions(Position startPosition, int suchFeldInhalt, int radius) {
		int anzahlGefunden = 0;
		for (int y = startPosition.y - radius; y <= startPosition.y + radius; y++) {
			for (int x = startPosition.x - radius; x <= startPosition.x + radius; x++) {
				Position suchPosition = createPosition(y, x);
				if (!isValidPosition(suchPosition)) {
					continue;
				}
				if (berechnet[y][x]) {
					continue;
				}
				int feldInhalt = getFeldInhalt(suchPosition);
				if (feldInhalt == suchFeldInhalt) {
					anzahlGefunden++;
				}
			}
		}
		return anzahlGefunden;
	}

	private static boolean isValidPosition(Position suchPosition) {
		// checke, ob suchPosition sich innerhalb der Landkarte befindet
		if (suchPosition.x < 0 || suchPosition.y < 0 || suchPosition.x >= SIZE_X || suchPosition.y >= SIZE_Y) {
			return false;
		}
		return true;
	}

	private static void arbeiteSaegewerk(Position hausPosition) {
		verkaufeBretter(hausPosition);
		
		// Lagerplatz suchen
		Position lagerPosition = findePosition(hausPosition, WIESE, arbeitsRadius[SAEGEWERK]);
		if (lagerPosition == null) {
			print("Sägewerk hat keinen Lagerplatz");
			return;
		}
		
		boolean hatArbeit = arbeite(hausPosition, SAEGEWERK, STAMM, WIESE, "Sägewerk hat keine Arbeit");
		if (hatArbeit) {
			print("Sägewerk [" + hausPosition.y + "/" + hausPosition.x + "] hat Bretter bei [" + lagerPosition.y + "/" + lagerPosition.x + "] gelagert.");
			setFeldInhalt(lagerPosition, BRETTER);
		}
	}
	
	private static void verkaufeBretter(Position hausPosition) {
		// für 5 Bretter gibt es ein Gold
		int anzahlBretter = countPositions(hausPosition, BRETTER, arbeitsRadius[SAEGEWERK]);
		// wenn 5 Bretter rund um das Sägewerk liegen, dann verkaufe diese
		if (anzahlBretter >= 5) {
			print("Sägewerk [" + hausPosition.y + "/" + hausPosition.x + "] hat 5 Bretter verkauft");
			for (int i = 0; i < 5; i++) {
				Position bretterPosition = findePosition(hausPosition, BRETTER, arbeitsRadius[SAEGEWERK]);
				print(" - Brett [" + bretterPosition.y + "/" + bretterPosition.x + "] verkauft");
				setFeldInhalt(bretterPosition, WIESE);
			}
			spieler.gold += 1;
		}
	}

	private static void arbeiteHolzfaeller(Position hausPosition) {
		arbeite(hausPosition, HOLZFAELLER, BAUM, STAMM, "Der Holzfäller findet keine Bäume zum Fällen");
	}
	
	private static void arbeiteFoerster(Position hausPosition) {
		arbeite(hausPosition, FOERSTER, WIESE, SETZLING, "Der Förster hat keinen Platz für neue Setzlinge");
	}
	
	private static boolean arbeite(Position heimat, int feldInhalt, int sucheFeldInhalt, int arbeitsErgebnisFeldInhalt, String arbeitslosenText) {
		Position arbeitsPosition = findePosition(heimat, sucheFeldInhalt, arbeitsRadius[feldInhalt]);
		if (arbeitsPosition == null) {
			print("[" + heimat.y + "," + heimat.x + "] " + arbeitslosenText);
			return false;
		}
		// lege Arbeitsergebnis auf Arbeitsposition ab
		setFeldInhalt(arbeitsPosition, arbeitsErgebnisFeldInhalt);
		printArbeitstext(feldInhalt, heimat, arbeitsPosition);
		return true;
	}

	private static void printArbeitstext(int beruf, Position heimat, Position arbeitsPosition) {
		if (beruf == HOLZFAELLER) {
			print("Baum [" + arbeitsPosition.y + "/" + arbeitsPosition.x + "] wurde vom Holzfäller [" + heimat.y + "/" + heimat.x + "] gefällt.");
		}
		if (beruf == FOERSTER) {
			print("Setzling [" + arbeitsPosition.y + "/" + arbeitsPosition.x + "] wurde vom Förster [" + heimat.y + "/" + heimat.x + "] gepflanzt.");
		}
		if (beruf == SAEGEWERK) {
			print("Stamm [" + arbeitsPosition.y + "/" + arbeitsPosition.x + "] wurde vom Sägewerk [" + heimat.y + "/" + heimat.x + "] abgeholt.");
		}
	}

	private static void setFeldInhalt(Position position, int feldInhalt) {
		land[position.y][position.x] = feldInhalt;
		berechnet[position.y][position.x] = true;
	}

	public static Position createPosition(int y, int x) {
		Position position = new Position();
		position.x = x;
		position.y = y;
		return position;
	}

	private static void print(String text) {
		System.out.println(text);
	}

}
