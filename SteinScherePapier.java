//copyright by Matthias Kleiber
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner myScanner = new Scanner(System.in);
		print("Bitte Auswahl treffen (Stein/Schere/Papier): ");
		String mensch = myScanner.next();
		int menschAuswahl = konvertiereAuswahl(mensch);
		int computerAuswahl = getComputerAuswahl();
		int ergebnis = ermittleSieger(menschAuswahl, computerAuswahl);

		print("Computer wählte: " + konvertiereAuswahlZuText(computerAuswahl));
		if (ergebnis == 0) {
			print("Unentschieden");
		}
		if (ergebnis == 1) {
			print("Du gewinnst");
		}
		if (ergebnis == 2) {
			print("Computer gewinnt");
		}
		
		myScanner.close();
	}

	private static int konvertiereAuswahl(String mensch) {
		if (mensch.equals("Stein")) {
			return 0;
		}
		if (mensch.equals("Schere")) {
			return 1;
		}
		if (mensch.equals("Papier")) {
			return 2;
		}
		return -1;
	}

	private static String konvertiereAuswahlZuText(int auswahl) {
		if (auswahl == 0) {
			return "Stein";
		}
		if (auswahl == 1) {
			return "Schere";
		}
		if (auswahl == 2) {
			return "Papier";
		}
		return "";
	}

	private static int getComputerAuswahl() {
		return (int)(Math.random() * 3);
	}

	private static int ermittleSieger(int menschAuswahl, int computerAuswahl) {
		if (menschAuswahl == computerAuswahl) {
			return 0;
		}
		
		if (menschAuswahl == 0 && computerAuswahl == 1) {
			return 1;
		}
		
		if (menschAuswahl == 1 && computerAuswahl == 2) {
			return 1;
		}
		
		if (menschAuswahl == 2 && computerAuswahl == 0) {
			return 1;
		}
		
		return 2;
	}

	private static void print(String text) {
		System.out.println(text);
	}

}
