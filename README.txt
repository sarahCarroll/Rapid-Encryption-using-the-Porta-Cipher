
README.TXT for Rapid Encryption using the Porta Cipher
======================================================

Author:		      Sarah Carroll
Student ID:	      G00330821

This project consists of 2 source files:

RunPortaCipher.java - contains main() method which controls the flow of the application.

PortaCipher.java    - which defines the PortaCipher Class which implements the main functionality.

PortaCipher.jar     - contains the executable application.


To execute the application use this command: 

	java -cp ./PortaCipher.jar ie.gmit.sw.RunPortaCipher 

This will present the user with the following main menu:


     ---- Porta Cipher ----

    Sarah Carroll ( G00330821 )

 1) Encipher a File or URL
 2) Encipher a String
 3) Enter a new Encryption Key
 4) Exit

 Select Option: 
 
Option 1 will prompt the user for a filename or an URL. Enter .txt     filename from local system or of an URL similar to:
 
 http://www.puzzlers.org/pub/wordlists/pocket.txt

or

 WarAndPeace-LeoTolstoy.txt 

Note the string "http:" will cause the program to try and read the file as an URL!


 
Option 2 permits the user to encipher a simple string typed at the console.

Case sensitivity of text files or entered string is maintained through the encryption process.



Option 3 allows the user to alter the encryption key used to encipher subsequent files or entered text.
 
 
 
Option 4 EXITS the program.
 
=======================
Sample Usage - Option 1
=======================
 
Please enter .txt filename of BOOK or URL to ENCIPHER: http://www.puzzlers.org/pub/wordlists/pocket.txt

Please enter filename of output ENCIPHERed file: puzzle.txt
21111 lines written to output file.
Running time (ms): 2672 

Press ENTER to return to menu ...

Or

Please enter .txt filename of BOOK or URL to ENCIPHER: WarAndPeace-LeoTolstoy.txt

Please enter filename of output ENCIPHERed file: xyz.txt
64928 lines written to output file.
Running time (ms): 233


Press ENTER to return to menu ... 

=======================
Sample Usage - Option 2
=======================

Please enter String to ENCIPHER: sarah
Input String : sarah
Output String: jnjnx
Running time (ms): 0


Press ENTER to return to menu ... 

=======================
Sample Usage - Option 3
=======================

Encryption Key must consist of exactly 26 UPPER CASE letters!
  Current Encryption Key: SARAHISABSOLUTELYBRILLIANT
Enter New Encryption Key: ABCDEFGHIJKLMNOPQRSTUVWXYZ

Encryption Key now changed to :ABCDEFGHIJKLMNOPQRSTUVWXYZ

Press ENTER to return to menu ...



===========================
Key Features of Application
===========================

1.	Original sample provided a tableau to encipher uppercase letters only.  
	This implementation uses two separate tableaux to handle upper and lower case letters. 
	It does not translate number or other special characters – these are left asis in the file or text enciphered.

2.	In order to increase the speed of the tableaux lookups for individual letters of the key, the key row offsets
	are buffered in an array of 26 ints. This is initialised in the PortaCipher constructor method and any time 
	the key is changed subsequently.

3.	In the Translate() method, I use StringBuilder class for the output buffer for each line enciphered as is it
	more efficient than normal Java Strings or StringBuffer.
