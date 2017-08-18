
package ie.gmit.sw;

// Class to provide menu interface to PortaCipher Class

import java.nio.file.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.*;

public class RunPortaCipher{


	// Helpful utility function to check if all characaters in string are uppper case

	private static boolean isAllUpperCase(String s)
	{
	    for (int i=0; i<s.length(); i++)
	    {
	        if (Character.isLowerCase(s.charAt(i)))
	        {
	            return false;
	        }
	    }
	    return true;
	}

	// Main Menu of Application

	private static int displayMenu() {
		// display main menu and get user entered option

		Scanner console =new Scanner(System.in);

		System.out.println("\n     ---- Porta Cipher ----\n");
		System.out.println("    Sarah Carroll ( G00330821 )\n");
		System.out.println(" 1) Encipher a File or URL");
		System.out.println(" 2) Encipher a String");
		System.out.println(" 3) Enter a new Encryption Key");
		System.out.println(" 4) Exit");
		System.out.print("\n Select Option: ");

		return console.nextInt();		// prompt for user input

	}
	//console.close();


	// Main method

	public static void main(String[] args) {
		// Main Program Loop

		Scanner console=new Scanner(System.in);
		boolean done = false;
		int option;
		String dummy;
		String filename;
		String ofilename;
		String newkey;
		long startTime;

		PortaCipher pc = new PortaCipher();

		while(done!=true) {

			option = displayMenu();

			switch(option) {
				case 1 :
							// Get name of book or URL to process
							System.out.print("\nPlease enter .txt filename of BOOK or URL to ENCIPHER: ");
							filename = console.nextLine();

							// Get name of encrypted output file
							System.out.print("\nPlease enter filename of output ENCIPHERed file: ");
							ofilename = console.nextLine();


							//Read file or URL and Encipher
							startTime = System.currentTimeMillis();

							pc.processFile(filename,ofilename);			// Encipher all letters in file

							System.out.println("Running time (ms): " + (System.currentTimeMillis() - startTime));

							System.out.print("\n\nPress ENTER to return to menu ...");
							dummy = console.nextLine();
							break;

				case 2 :
							// Get string from user to process

							System.out.print("\nPlease enter String to ENCIPHER: ");
							filename = console.nextLine();

							System.out.println("Input String : "+filename);
							startTime = System.currentTimeMillis();

							StringBuilder sb = pc.Translate(filename);

							System.out.println("Output String: "+sb);
							System.out.println("Running time (ms): " + (System.currentTimeMillis() - startTime));


							System.out.print("\n\nPress ENTER to return to menu ...");
							dummy = console.nextLine();
							break;

				case 3 :	// enter new encryption key

							System.out.print("\nEncryption Key must consist of exactly 26 UPPER CASE letters!");

							System.out.print("\n  Current Encryption Key: "+pc.getKeyword());

							System.out.print("\nEnter New Encryption Key: ");
							newkey = console.nextLine();

							if(newkey.length() != 26 || isAllUpperCase(newkey) == false) {
								System.out.print("\nEncryption Key must consist of exactly 26 UPPER CASE letters!");
							} else {
								pc.setKeyword(newkey);		// set new key
								System.out.print("\nEncryption Key now changed to :"+pc.getKeyword());
							}

							System.out.print("\n\nPress ENTER to return to menu ...");
							dummy = console.nextLine();
							break;

				case 4 :	// exit
							done = true;
							break;

				default:	// loop around again
							break;
			}


		}

		console.close();

		System.out.println("\nProgram terminated ...");

	}	// end of main method

} // end of RunPortaCipher class
