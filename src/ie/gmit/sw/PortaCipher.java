package ie.gmit.sw;

/**
 * @author Sarah Carroll ( G00330821 )
 *
 *
 */

import java.nio.file.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.*;



public class PortaCipher{

	/*
	  The following tableau is used by the Porta Cipher:

	  Keys| A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
	  ---------------------------------------------------------
	  A,B | N O P Q R S T U V W X Y Z A B C D E F G H I J K L M
	  C,D | O P Q R S T U V W X Y Z N M A B C D E F G H I J K L
	  E,F | P Q R S T U V W X Y Z N O L M A B C D E F G H I J K
	  G,H | Q R S T U V W X Y Z N O P K L M A B C D E F G H I J
	  I,J | R S T U V W X Y Z N O P Q J K L M A B C D E F G H I
	  K,L | S T U V W X Y Z N O P Q R I J K L M A B C D E F G H
	  M,N | T U V W X Y Z N O P Q R S H I J K L M A B C D E F G
	  O,P | U V W X Y Z N O P Q R S T G H I J K L M A B C D E F
	  Q,R | V W X Y Z N O P Q R S T U F G H I J K L M A B C D E
	  S,T | W X Y Z N O P Q R S T U V E F G H I J K L M A B C D
	  U,V | X Y Z N O P Q R S T U V W D E F G H I J K L M A B C
	  W,X | Y Z N O P Q R S T U V W X C D E F G H I J K L M A B
	  Y,Z | Z N O P Q R S T U V W X Y B C D E F G H I J K L M A


	  keyword: DATASTRUCTURESANDALGORITHMS
	  Plain Text: THECURFEWTOLLSTHEKNELLOFPARTINGDAY

	  (1) Repeat the keyword above the plaintex
		DATASTRUCTURESANDALGORITHMSDATASTR
		THECURFEWTOLLSTHEKNELLOFPARTINGDAY

	  (2) For each character in the plaintext

			Find the character at the intersection of the row
			containing the key character and the column containing the
			plaintext.

			K: DATASTRUCTURESANDALGORITHMSDATASTR
			P: THECURFEWTOLLSTHEKNELLOFPARTINGDAY
			   ----------------------------------
			C: FUNPLINOIKETNJGNSXIUSTKOMTIFVETZWD

	The encryption and decryption processes are identical. Encrypting
	a piece of text twice with the same key will return the original text:

		K: DATASTRUCTURESANDALGORITHMSDATASTR
		C: FUNPLINOIKETNJGNSXIUSTKOMTIFVETZWD
		   ----------------------------------
		P: THECURFEWTOLLSTHEKNELLOFPARTINGDAY

	*/

	private static final String[][] UpperTableau = {
		{"KEYS", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"},
		{"AB",   "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"},
		{"CD",   "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"},
		{"EF",   "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"},
		{"GH",   "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"},
		{"IJ",   "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H", "I"},
		{"KL",   "S", "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G", "H"},
		{"MN",   "T", "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F", "G"},
		{"OP",   "U", "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E", "F"},
		{"QR",   "V", "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D", "E"},
		{"ST",   "W", "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C", "D"},
		{"UV",   "X", "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B", "C"},
		{"WX",   "Y", "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A", "B"},
		{"YZ",   "Z", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "A"}
	};

	private static final String[][] LowerTableau = {
		{"KEYS", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"},
		{"AB",   "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"},
		{"CD",   "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "n", "m", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l"},
		{"EF",   "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "n", "o", "l", "m", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"},
		{"GH",   "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "n", "o", "p", "k", "l", "m", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j"},
		{"IJ",   "r", "s", "t", "u", "v", "w", "x", "y", "z", "n", "o", "p", "q", "j", "k", "l", "m", "a", "b", "c", "d", "e", "f", "g", "h", "i"},
		{"KL",   "s", "t", "u", "v", "w", "x", "y", "z", "n", "o", "p", "q", "r", "i", "j", "k", "l", "m", "a", "b", "c", "d", "e", "f", "g", "h"},
		{"MN",   "t", "u", "v", "w", "x", "y", "z", "n", "o", "p", "q", "r", "s", "h", "i", "j", "k", "l", "m", "a", "b", "c", "d", "e", "f", "g"},
		{"OP",   "u", "v", "w", "x", "y", "z", "n", "o", "p", "q", "r", "s", "t", "g", "h", "i", "j", "k", "l", "m", "a", "b", "c", "d", "e", "f"},
		{"QR",   "v", "w", "x", "y", "z", "n", "o", "p", "q", "r", "s", "t", "u", "f", "g", "h", "i", "j", "k", "l", "m", "a", "b", "c", "d", "e"},
		{"ST",   "w", "x", "y", "z", "n", "o", "p", "q", "r", "s", "t", "u", "v", "e", "f", "g", "h", "i", "j", "k", "l", "m", "a", "b", "c", "d"},
		{"UV",   "x", "y", "z", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "a", "b", "c"},
		{"WX",   "y", "z", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "a", "b"},
		{"YZ",   "z", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "a"}
		};


	//                        12345678901234567890123456
	private String keyword = "SARAHISABSOLUTELYBRILLIANT";


	// Key Offsets
	private int[] key = new int[26];		// Array to hold key row offsets - tableau[key[keyOffset],letter index]

	// Methods

	// Constructor

	public PortaCipher() {
		//Constructor - Initialize various private properties
		Initialise();
	}

	// Get/Set keyword

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String newkey) {
		keyword = newkey;
		Initialise();			// rebuffer key for speed
	}


	// Initialise offsets in tableaux for each letter in key - this is to speed-up lookups on each iteration!

	private void Initialise() {

		int x;

		for(int i=0;i<26;i++) {
			x = ((int)keyword.charAt(i))-65;
			key[i] = (x / 2)+1;				// set in range 1 to 13
		}
	}	// end of Initialise method


	public StringBuilder Translate(String inputLine) {

		// Translate Line

		// System.out.println("Input Length: "+inputLine.length());

		// StringBuilder is more efficient than Strings or the threadsafe StringBuffer. Built for speed!

		StringBuilder sb = new StringBuilder(inputLine.length()+1);	// Allocate new Output buffer of required length
		int keyOffset = 0;
		int keyLength, y;
		char ch, newch;

		keyLength = keyword.length();				// Buffer value for speed

		// Loop through each character on input line and translate

		for(int i=0;i<inputLine.length();i++) {

			keyOffset = i % keyLength;				// Get key value row number to use for this character

			ch = inputLine.charAt(i);				// Pick up next character from input string
			//System.out.println("i = "+i+", ch = "+ch+",(int)ch = "+(int)ch);

			y = (int)ch;							// Get Numeric value of character

			if(y < 127 && Character.isLowerCase(ch)) {			// Lower case ASCII translate

				y = y - 96;							// Set offset within row to 1 - 26 (a to z)
													// Lower case starts at value 97 in ASCII
				newch = LowerTableau[key[keyOffset]][y].charAt(0);

			} else if(y < 127 && Character.isUpperCase(ch)) {	// Upper case ASCII translate

				y = y - 64;							// Set offset within row to 1 - 26 (A to Z)
													// Upper case starts at value 65 in ASCII
				newch = UpperTableau[key[keyOffset]][y].charAt(0);

			} else {
				newch = ch;							// Otherwise leave asis (spaces, newlines, commas, numbers, etc)
			}

			sb.append(newch);
		}

		return sb;

	} // end of Translate method



	public boolean processFile(String filename, String ofilename) {

		//buffer reader.
		BufferedReader br = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		// This reads files off local system or via Internet
		if(filename.contains("http:")) {	// is it web address?
			try {
				br = new BufferedReader(new InputStreamReader(new URL(filename).openConnection().getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		//file read, translate and write to output file
		try {

			fw = new FileWriter(ofilename);
			bw = new BufferedWriter(fw);

			//work with string to read lines of text
			String s = null;
			long count = 1;
			while ((s = br.readLine()) != null) {

				StringBuilder sb = Translate(s);
				bw.write(sb.toString()+"\n");
				//System.out.println("Line "+count+" :"+sb);
				count++;
			}

			System.out.println(count+" lines written to output file.");

			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}



}	// end of class
