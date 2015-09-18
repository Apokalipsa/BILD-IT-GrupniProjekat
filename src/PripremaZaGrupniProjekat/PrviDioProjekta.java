package PripremaZaGrupniProjekat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PrviDioProjekta {
	public static void main(String[] args) {
		                                                         // kreiramo glavni meni
		System.out.println("Welcome to the main Menu ");
		System.out.println("------------------------");
		System.out.println("[1] Search for the Country");
		System.out.println("[2] Search by Population");
		System.out.println("[3] Search by Country Name");
		System.out.println("[4] Search by City Name ");
		System.out.println("[0] Exit from main Menu");
		System.out.println("\nChoose option by entering the offered number: ");

		int choice = Options();                // uzimamo unos odabira od korisnika i pozivamo se na metodu za opcije				
		String question = " ";                // kreiramo memoriski prostor za korisnicki odabir opcija pri upitu
		
		switch (choice) {
		case 1: {              // pretraga zemalja svih kontinenata
	
			question = "SELECT Continent, COUNT(*) FROM country GROUP BY Continent;";        // popunjavamo string 
			System.out.println(String.format("%-10s %s", " Continent "," Countries "));     // formatiramo naslov 	pretrahe	
			System.out.println("__________________________________________");
			OutputFromDatabase(question, "Continent", "COUNT(*)");                    // pozivamo metodu koja vraca trazeni izbor iz baze
			break;  
		}
		case 2: {            // pretraga zemalja po populaciji
	            	
			question = "SELECT Name, Continent, Population FROM country ORDER BY Population;";          // popunjavamo string 
			
			System.out.println(String.format("%-10s %-10s %s", "Country","Continent", "Population"));  // formatiramo naslov pretrage		
			System.out.println("_________________________________________________________________");
			OutputFromDatabase(question, "Name", "Continent", "Population");           // pozivamo metodu koja vraca trazeni izbor iz baze
			break;
		}
		case 3: {         // pretraga podataka pomocu imena drzave
			
			System.out.println("Enter name of the country: ");         // trazimo unos od korisnika
			String country = StringInput();                           // smjestamo unos imena od korisnika u novu promenljivu kreiranom metodom
			
			question = "SELECT Code,Name,Region,SurfaceArea,Population FROM country WHERE Name = '"+ country + "';";   // popunjavamo string		                                                          
			System.out.println(String.format("%-10s %-10s %-10s %-10s %-10s"," Code ", " Name ", " Region ", " SurfaceArea ", " Population "));		
			System.out.println("______________________________________________________________");      // formatiramo naslov pretrage
			OutputFromDatabase(question, "Code", "Name", "Region","SurfaceArea", "Population");
			                                                                               // pozivamo metodu koja vraca trazeni izbor iz baze	
			break;
		}
		case 4: {        // pretraga pomocu imena grada
			
			System.out.println("\nEnter City Name:");        // trazimo unos od korisnika
			String city = StringInput();                    // smjestamo unos imena od korisnika u novu promenljivu kreiranom metodom
			
			question = "SELECT CountryCode,Name FROM city WHERE Name LIKE '"+ city + "%'";
			System.out.println(String.format("%-10s %s", " Country Code "," Name "));		
			System.out.println("_________________________________________________");     // formatiramo naslov pretrage
			OutputFromDatabase(question, "CountryCode", "Name");                    // pozivamo metodu koja vraca trazeni izbor iz baze
			break;
		}
		default: {
			System.out.println(" Thank you for using our service ");
			System.exit(0);
		}
		}

	}

	public static void OutputFromDatabase(String query, String... args) {
		
		try {           // kreiramo try blok radi eventualnih gresaka kako bi zastitili program od crasha
			
			Connection fromBase = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root") ;    // povezujemo se sa bazom podataka
					
			Statement statement =  fromBase.createStatement();      // pozivamo odabir operacije tj sta zelimo manipulisati iz baze 
			ResultSet result = statement.executeQuery(query );                 // kreiramo objekat rezultat prethodnog odabira
			
			while (result.next()) {                               // petlja radi sve dok ima odabira
				for (int i = 0; i < args.length; i++) {          // prolazimo petljom kroz argument da bi kreirali tabelu 
					System.out.print(String.format("%-10s ",result.getString(args[i]))); // printamo polje za prikaz u konzoli
				}
				System.out.println();

			}

		} catch (SQLException e) {  // ukoliko se pojavi eventualna greska catch blok je hvata i nastavlja sa radom
			e.printStackTrace();   // ispisuje poruku upozorenja i pokazuje gdje se tacno nalazi greska i koje je vrste
		}
	}

	public static int Options() {       // metoda koja kontrolise odabir korisnika u glavnom meniu
		
		boolean On = false;           // postavljamo promenljivu kao da jos nema unosa zato je false
		int userInput = 0;           // kreiramo memoriski prostor za promenljivu koja ce primit unos od korisnika
		while (!On) {               // postavljamo uslov u petlju da ima unosa
			Scanner input = new Scanner(System.in);       // omogucavamo unos korisniku 
			On = true;                                   // postavljamo unos na true

			try {                                      // kreiramo try blok radi eventualnih gresaka pri unosu
				userInput = input.nextInt();          // smjestamo korisnicki unos u prethodno kreiranu promenljivu
				if (userInput <= 0 || userInput > 5) { 
					On = false;
				}

			} catch (InputMismatchException e) {                  // ukoliko korisnik unese nesto sto nije broj program nastavlja sa radom						
				System.out.println("Warning ! Invalid input. Please try again: ");  // te ispisuje se poruka upozorenja korisniku
				input.nextLine();        // procesuira se unos od korisnika
				On = false;             // zato stavljamo false i prekidamo tu obradu
			}
		}

		return userInput;            // te vracamo uneseni broj za glavni meni

	}

	public static String StringInput() {
		
		String userInputString = " ";                   // kreiramo prazan string koji ce primati korisnicke unose istog tipa
		try {                                          // kreiramo trz blok radi eventualnih gresaka korisnika
			Scanner input = new Scanner(System.in);   // omogucavamo unos
			userInputString = input.nextLine();      // smjestamo unos u prethodno kreiranu promenljivu
		} catch (Exception e) {                     // ukoliko dodje do greske program se ne prekida
			System.out.println("Warning ! Invalid input. Please try again: ");          // ispisujemo poruku upozorenja
			StringInput();              // ukoliko nema greske procesuiramo unos dalje na obradu
		}
		return userInputString;        // te vracamo obradjen unos

	}
}



