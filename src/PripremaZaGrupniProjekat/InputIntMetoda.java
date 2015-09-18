package PripremaZaGrupniProjekat;

/**
 * @author Dijana Markovic
 **/

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputIntMetoda {

	public static int inputInt(int from, int to) {
		int result = 0;
		boolean isOk = true;
		Scanner input = new Scanner(System.in);
		do {
			try {
				System.out.println(" Enter number in range between" + from
						+ " - " + to);
				result = input.nextInt();

				if (result >= from && result <= to) {
					isOk = false;
				} else {
					System.out.println("Out  of range! Try again");
				}

			} catch (InputMismatchException e) {

				System.out.println("You must enter integer type value between "
						+ from + " - " + to);
				input.nextLine();
			}

		} while (isOk);
		input.close();
		return result;
	}

	/** Main Test */

	public static void main(String[] args) {
		System.out.println(inputInt(0, 9));
	}

}
