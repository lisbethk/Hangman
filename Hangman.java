import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

/*
 * Hangman
 * Game runs while user wants to continue and gives 5 tries per game.
 * 
 * @author Kseniia Andreeva
 * @version 3/29/17
 */
public class Hangman {

	public static void main(String[] args) throws IOException	{		
		System.out.println("Welcome to Hangman! You get 5 tries to guess a word. Good luck!");
		do {
			int missedTimes = 0;
			String hiddenWord = getHiddenWord();			
			//String hiddenWord = getWordFromList();
			
			String[] wordMask = getWordMask(hiddenWord); // creates an array of "*" that hides the word under the mask
			// creates an array out of hidden word like "h" "e" "l" "l" "o" to test wordMask later
			String[] wordForTesting = hiddenWord.split("");

			do {				
				String guess = getGuess(wordMask);
				if (!findLetter(hiddenWord, guess)) {
					System.out.println(guess + " is not in the word. You missed " + ++missedTimes + " times.");
					if (lost(missedTimes)) 
						break;
				}
				else if (Arrays.asList(wordMask).contains(guess))
					System.out.println(guess + " is already in the word.");
				else {
					int index = hiddenWord.indexOf(guess);
					while (true){
						if (index == -1)
							break;	            
						wordMask[index] = guess;
						index = hiddenWord.indexOf(guess,index+1);
					}
				}
			} while (!solved(wordForTesting, wordMask) && !lost(missedTimes)); // break when word is solved or game is lost
			
			displayResults(hiddenWord, missedTimes);
			missedTimes = 0; // reset missed times for the next game
		}
		while (playAgain());
	}
	// method returns a random word out of array of words
	public static String getHiddenWord() {
		String[] words = {"write", "that", "coding", "homework", "fun", "money", "motivation"};	
		Random temp = new Random();			
		return words[temp.nextInt((6) + 1)];
	}
	// method returns a random word out of a text file words.txt
	public static String getWordFromList() throws IOException {
		ArrayList<String> allWords = new ArrayList<String>();		
		BufferedReader br = new BufferedReader(new FileReader("words.txt")); // creates BufferedReader and FileReader, reads a text file
		String line = br.readLine();
		while (line != null) {				
			StringTokenizer st = new StringTokenizer(line);
			allWords.add(st.nextToken());
			line = br.readLine();			
		}
		br.close();
		Random temp = new Random();			
		return allWords.get(temp.nextInt(allWords.size()));
	}
	// returns array of "*" symbols that's as long as the hidden word
	public static String[] getWordMask(String hiddenWord) {
		String[] wordMask = new String[hiddenWord.length()];
		for (int i = 0; i < hiddenWord.length(); i++) {
			wordMask[i] = "*";
		}
		return wordMask;
	}
	// returns a String provided by user (doesn't check for number of characters in String)
	public static String getGuess(String[] wordMask) {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a letter in word ");
		turnToString(wordMask);
		return input.next();	
	}
	
	// checks if guess is in word, if index is not -1, letter was found, returns true
	public static boolean findLetter(String hiddenWord, String guess) {
		return hiddenWord.indexOf(guess) != -1 ? true : false; 
	}
	// returns true (solved) if both arrays are equal
	public static boolean solved(String[] wordForTesting, String[] wordMask) {
		return Arrays.equals(wordForTesting, wordMask);
	}
	// returns true if missed 5 times
	public static boolean lost(int missedTimes) {
		return missedTimes == 5 ? true : false;
	}	
	// display results based on number of missed times
	public static void displayResults(String hiddenWord, int missedTimes) {
		if (lost(missedTimes))
			System.out.println("\nYou lost! The word is " + hiddenWord + ". You missed " + missedTimes + " times.");
		else
			System.out.println("\nYou won! The word is " + hiddenWord + ". You missed " + missedTimes + " times.");
	}
	// returns true if user enters "y" to play again
	public static boolean playAgain() {
		Scanner input = new Scanner(System.in);
		System.out.println("Do you want to play again? y/n");
		return input.next().equals("y") ? true : false;
	}
	public static void turnToString(String[] array) {
		for(String s : array) {
			System.out.print(s);
		}
		System.out.println();
	}
}