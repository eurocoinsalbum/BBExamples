import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner myScanner = new Scanner(System.in);
		int[] tische = new int[10];
		int[] menuKarte = new int[3];
		menuKarte[0] = 199;
		menuKarte[1] = 599;
		menuKarte[2] = 299;
		int kasse = 0;
		
		for(int i = 0; i < tische.length; i++) {
			tische[i] = 0;
		}
		
		while(true) {
			print("Welcher Tisch? (-1 für Feierabend)");
			int tischNr = myScanner.nextInt();
			if (tischNr == -1) {
				break;
			}
			print("Menu Nummer?  (-1 für Rechnung bezahlen)");
			int menuNr = myScanner.nextInt();
			if (menuNr == -1) {
				print("Rechnung für Tisch " + tischNr + ": " + tische[tischNr]);
				kasse += tische[tischNr];
				tische[tischNr] = 0;
			} else {
				tische[tischNr] += menuKarte[menuNr];
				print("Zwischensumme für Tisch " + tischNr + ": " + tische[tischNr]);
			}
		}
		
		print("Einnahmen heute: " + kasse);
		
		myScanner.close();
	}
	
	private static void print(String text) {
		System.out.println(text);
	}

}
