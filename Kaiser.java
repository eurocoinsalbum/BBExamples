import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner myScanner = new Scanner(System.in);
		int vorrat = 1500;
		int leute = 1000;
		print("Der Vorrat zu Beginn beträgt: " + vorrat);
		print("Die Bevölkerungsanzahl zu Beginn ist: " + leute);
		
		for (int alter = 50; alter < 80; alter++) {
			print("Du bist " + alter + " Jahre alt");
			boolean eingabeOk = false;
			int essen = -1;
			int saatgut = -1;
			
			// Daten vom Spieler erfragen
			while(!eingabeOk) {
				essen = readEssen(myScanner, vorrat);
				saatgut = readSaatgut(myScanner, vorrat);
				if (essen + saatgut > vorrat) {
					print("So viel hast Du nicht. Maximal " + vorrat);
				} else {
					eingabeOk = true;
				}
			}
			
			int ernte = berechneErnte(saatgut, leute);
			vorrat = vorrat - saatgut - essen + ernte;

			int zuwachs = berechneZuwachs(essen, leute);
			leute = leute + zuwachs;
			
			print("Die Ernte betrug: " + ernte);
			print("Der Vorrat beträgt jetzt: " + vorrat);
			print("Die Bevölkerungsanzahl ist: " + leute);
		}
		myScanner.close();
	}

	private static int readEssen(Scanner myScanner, int vorrat) {
		boolean eingabeOk = false;
		int zahl = -1;
		while(!eingabeOk) {
			print("Wieviel Essen soll verteilt werden?");
			zahl = myScanner.nextInt();
			if (zahl > vorrat) {
				print("So viel hast Du nicht. Maximal " + vorrat);
			} else {
				eingabeOk = true;
			}
		}
		return zahl;
	}

	private static int readSaatgut(Scanner myScanner, int vorrat) {
		boolean eingabeOk = false;
		int zahl = -1;
		while(!eingabeOk) {
			print("Wieviel Saatgut soll gesät werden?");
			zahl = myScanner.nextInt();
			if (zahl > vorrat) {
				print("So viel hast Du nicht. Maximal " + vorrat);
			} else {
				eingabeOk = true;
			}
		}
		return zahl;
	}

	private static int berechneErnte(int saatgut, int erntehelfer) {
		int arbeitskraftFaktor = 4;
		int wetterFaktor = (int)(Math.random() * 3);
		int ertrag = saatgut * (wetterFaktor + 2);
		int erntekapaziaet = erntehelfer * arbeitskraftFaktor;
		// ohne genügend Erntehelfer kann nicht die gesamte Ernte eingefahren werden
		int geerntet = Math.min(ertrag, erntekapaziaet);
		int nichtGeerntet = ertrag - geerntet;
		int arbeitsplaetze = geerntet / arbeitskraftFaktor;
		int arbeitslos = erntehelfer - arbeitsplaetze;
		
		if (wetterFaktor == 0) {
			print("Die Ernte war schlecht.");
		} else if (wetterFaktor == 1) {
			print("Die Ernte war normal.");
		} else {
			print("Die Ernte war super.");
		}
		
		if (nichtGeerntet > 0) {
			print("!!! " + nichtGeerntet + " Einheiten der Ernte konnte nicht geernet werden. Zu wenig Leute!");
		}
		if (arbeitslos > 0) {
			print(arbeitslos + " Erntehelfer hatten nichts zu tun. Zu viele Erntehelfer!");
		}
		return geerntet;
	}

	private static int berechneZuwachs(int anzahlEssen, int anzahlPersonen) {
		// Jede Person ißt genau ein Essen
		int differenz = anzahlEssen - anzahlPersonen;
		if (differenz > 0) {
			print("Die Bevölkerung ist um " + differenz + " gestiegen.");
		} else if (differenz < 0) {
			print("!!! Die Bevölkerung ist um " + Math.abs(differenz) + " gesunken.");				
		} else {
			print("Die Bevölkerung hat sich nicht verändert.");
		}
		return differenz;
	}

	private static void print(String text) {
		System.out.println(text);
	}
}
