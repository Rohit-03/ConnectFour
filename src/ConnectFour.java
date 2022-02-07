import java.util.Scanner;


public class ConnectFour {

	// CS312 Students, add your constants here.

	public static void main(String[] args) {
		intro();

		// Complete this method.
		// Make and use one Scanner connected to System.in.
		Scanner keyboard = new Scanner(System.in);

		playConnectFour(keyboard);
	}

	// CS312 Students, add your methods here.

	// Runs the entire connect four game
	public static void playConnectFour(Scanner keyboard) {

		// Create 2D array to represent the game board
		int numberOfRows = 6;
		int numberOfColumns = 7;

		// player pieces
		String playerOnePiece = "r";
		String playerTwoPiece = "b";

		// Assign player numbers
		int pOne = 1;
		int pTwo = 2;

		// Get player names
		String playerOne = getNames(keyboard, pOne);
		System.out.println();
		String playerTwo = getNames(keyboard, pTwo);

		// Initialize game board
		String[][] gameBoard = initalizeGameBoard(numberOfRows, numberOfColumns);

		// Print game board
		System.out.println("\nCurrent Board");
		printBoard(gameBoard);

		// Set the first player to player one and assign them the first player's piece
		String currPlayer = playerOne;
		String currPlayerPiece = playerOnePiece;

		// Method runs the connect four game until a winner is decided
		runGame(currPlayer, currPlayerPiece, keyboard, numberOfColumns, numberOfRows, gameBoard, 
				playerOnePiece,playerTwoPiece, playerOne, playerTwo);
	}

	// Run the Connect 4 game until a winner is decided or tie
	public static void runGame(String currPlayer, String currPlayerPiece, Scanner keyboard,
			int numberOfColumns, int numberOfRows, String[][] gameBoard, String playerOnePiece, 
			String playerTwoPiece, String playerOne, String playerTwo) {

		// Boolean value for is a winner is decided
		boolean winOrLoose = false;
		// Boolean value for if the game is a draw
		boolean gameDraw = false;

		// While loop until a winner is decided or game is a draw
		while (!winOrLoose && !gameDraw) {

			// Get the current player's column num
			int columnNumOne = getColumn(currPlayer, currPlayerPiece, keyboard, numberOfColumns,
					numberOfRows,gameBoard);

			// Add the current player's game piece 
			gameBoard = addGamePiece(columnNumOne, gameBoard, currPlayerPiece);

			// Check if a player won
			winOrLoose = playerWin(gameBoard, numberOfColumns, numberOfRows, currPlayerPiece);

			// Check if game board is full
			gameDraw = !emptyGameBoard(gameBoard, numberOfColumns);

			// If game is not over print current board
			if (!winOrLoose && !gameDraw) {

				// Print current Board
				System.out.println("\nCurrent Board");
				printBoard(gameBoard);

				// Switch player
				if (currPlayer.equals(playerOne)) {
					currPlayer = playerTwo;
					currPlayerPiece = playerTwoPiece;
				} else {
					currPlayer = playerOne;
					currPlayerPiece = playerOnePiece;
				}
			}

		}

		// Print game result
		gameResult(winOrLoose, gameDraw, gameBoard, currPlayer);
	}

	
	// Method to print game result
	public static void gameResult(boolean winOrLoose, boolean gameDraw, String[][] gameBoard, 
			String currPlayer) {
		// If a winner is decided print winning statement, otherwise print draw statement
		if (winOrLoose) {
			System.out.println("\n" + currPlayer + " wins!!");
		} else {
			System.out.println("\nThe game is a draw.");

		}
		// Print final board
		System.out.println("\nFinal Board");
		printBoard(gameBoard);

	}

	
	// Ask for player Names
	public static String getNames(Scanner keyboard, int playerNumber) {
		System.out.print("Player " + playerNumber + " enter your name: ");
		
		// Store the player's name, from the user's input
		String name = keyboard.next();
		return name;
	}

	
	// Initially format gameBoard
	public static String[][] initalizeGameBoard(int numberOfRows, int numberOfColumns) {

		String blankSpot = ".";
		String[][] printedGameBoard = new String[numberOfRows][numberOfColumns];
		
		// Loop through each row and column and fill the null spots with blank spots
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				printedGameBoard[row][column] = blankSpot;

			}
		}
		// return the blank game board
		return printedGameBoard;
	}

	
	
	// Method to print the current board
	public static void printBoard(String[][] board) {

		System.out.println("1 2 3 4 5 6 7  column numbers");
		int numRows = board.length;
		int numColumns = board[0].length;
		
		// loop through the current game board and print each spot
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				System.out.print(board[row][column] + " ");

			}
			System.out.println("");
		}
		System.out.println("");

	}

	// get the column input from user
	public static int getColumn(String playerName, String gamePiece, Scanner keyboard, 
			int numberOfColumns, int numOfRows, String[][] gameBoard) {
		int columnNum = 0;
		System.out.println(playerName + " it is your turn.");
		System.out.println("Your pieces are the " + gamePiece + "'s.");
		
		// get valid input column form user
		int validColumnNum = returnValidColumn(playerName,keyboard,columnNum, 
		numberOfColumns, numOfRows, gameBoard);
		
		return validColumnNum;
		
	}

	// Method gets a valid column number from player
	public static int returnValidColumn(String playerName,Scanner keyboard, int columnNum, 
			int numberOfColumns, int numOfRows, String[][] gameBoard  ) {
		boolean validColumn = false;
		// While the user's input is invalid keep running
		while (!validColumn) {
			System.out.print(playerName + ", enter the column to drop your checker: ");
			// check if the input is an integer
			boolean isAnInt = keyboard.hasNextInt();
			if (isAnInt == true) {
				columnNum = keyboard.nextInt();
				// if the input is an integer check if it's in the range
				if (0 < columnNum && columnNum <= numberOfColumns) {
					boolean isColumnFree = isColumnFree(columnNum, numOfRows, gameBoard);
					if (isColumnFree) {
						validColumn = true;
					} else {
						System.out.println("\n" + columnNum + " is not a legal column. That "
								+ "column is full"); }
				} else {
					System.out.println("\n" + columnNum + " is not a valid column."); }
			} else {
				String invalidColumnNum = keyboard.next();
				System.out.println("\n" + invalidColumnNum + " is not an integer."); } 
			}
		// adjust column for array index out of bounds  by subtracting one.
		int adjustedColumnNum = columnNum - 1;
		return adjustedColumnNum;
	}
	
	
	
	
	
	
	// Check if the column that the player specified is true
	public static boolean isColumnFree(int columnNum, int numberOfRows, String[][] gameBoard){
		String blankSpot = ".";
		
		// Adjust row and column num by -1 to avoid index out of bounds error
		int rowNum = numberOfRows - 1;
		int colNum = columnNum - 1;
	
		boolean rowIsFull = false;
		
		// Run while loop until the a blank spot is found or loop goes through the entire column
		while (!rowIsFull && rowNum >= 0) {
			
			// Loop through the column and check if the string 
			String currPiece = gameBoard[rowNum][colNum];
			
			// The current space equals the string for a blank spot, then there is is still
			// space in the coulmn and return true
			if (currPiece.equals(blankSpot)) {
				return true;
			}
			rowNum--;

		}
		// If the method loops through the entire column without finding a blank spot, return 
		// false for if the column is free
		return false;
	}

	
	
	
	// add game piece
	public static String[][] addGamePiece(int columnNum, String[][] gameBoard, String gamePiece){
		String blankSpot = ".";
		int rowNum = gameBoard.length - 1;
		String currentState = gameBoard[rowNum][columnNum];
		// loops up in the column until a blank spot is found and add the players piece there
		while (!currentState.equals(blankSpot) && rowNum > 0) {
			rowNum--;
			currentState = gameBoard[rowNum][columnNum];
		}
		// add the game piece to the first blank spot found
		gameBoard[rowNum][columnNum] = gamePiece;
		// return the updated game board with the added piece
		return gameBoard;

	}

	// Check for horizontal win
	public static boolean horizontalWin(String[][] gameBoard, int numberOfColumns, 
			int numberOfRows, String gamePiece) {
		// Value for the number of pieces to check in a row after the current piece
		int numPiecesToCheck = 3;
		
		// Sets the final column number of the current piece that we will check in the loop
		int adjustedColumnNum = numberOfColumns - numPiecesToCheck;
		
		// Integer for each additional value we will check for four in a row
		int firstAdditional = 1;
		int secondAdditional = 2;
		int thirdAdditional = 3;

		// loops through each row to check for for in a row
		for (int rowNum = 0; rowNum < numberOfRows; rowNum++) {
			for (int columnNum = 0; columnNum < adjustedColumnNum; columnNum++) {
				
				// store the values for the 3 additional pieces after the current piece
				int secondInARow = columnNum + firstAdditional;
				int thirdInARow = columnNum + secondAdditional;
				int fourthInARow = columnNum + thirdAdditional;
				
				// Checks to see if each piece equals the specified game piece.
				boolean first = gameBoard[rowNum][columnNum] == gamePiece;
				boolean second = gameBoard[rowNum][secondInARow] == gamePiece;
				boolean third = gameBoard[rowNum][thirdInARow] == gamePiece;
				boolean fourth = gameBoard[rowNum][fourthInARow] == gamePiece;

				// If they all equal each other then there is a 4 in a row detected
				if (first && second && third && fourth) {
					return true;
				}
			}
		}

		return false;
	}

	// Check for vertical win
	public static boolean verticalWin(String[][] gameBoard, int numberOfColumns, 
			int numberOfRows, String gamePiece) {
		// Value for the number of pieces to check in a row after the current piece
		int numPiecesToCheck = 3;
		// Sets the final row number of the current piece that we will check in the loop
		int adjustedRowNum = numberOfRows - numPiecesToCheck;
		
		// Integer for each additional value we will check for four in a row
		int firstAdditional = 1;
		int secondAdditional = 2;
		int thirdAdditional = 3;
		
		// loops through each column to check for for in a row
		for (int rowNum = 0; rowNum < adjustedRowNum; rowNum++) {
			for (int columnNum = 0; columnNum < numberOfColumns; columnNum++) {
				int secondInARow = rowNum + firstAdditional;
				int thirdInARow = rowNum + secondAdditional;
				int fourthInARow = rowNum + thirdAdditional;

				// Checks to see if each piece equals the specified game piece.
				boolean first = gameBoard[rowNum][columnNum] == gamePiece;
				boolean second = gameBoard[secondInARow][columnNum] == gamePiece;
				boolean third = gameBoard[thirdInARow][columnNum] == gamePiece;
				boolean fourth = gameBoard[fourthInARow][columnNum] == gamePiece;

				// If they all equal each other then there is a 4 in a row detected
				if (first && second && third && fourth) {
					return true;
				}
			}
		}

		return false;
	}

	// Check for upwards diagonal win
	public static boolean diagonalUp(String[][] gameBoard, int numberOfColumns, int numberOfRows, 
			String gamePiece, int firstAdditional, int secondAdditional, int thirdAdditional) {
		// Value for the number of pieces to check in a row after the current piece
		int numAddPiecesToCheck = 3;
		// Sets the final column number of the current piece that we will check in the loop
		int adjustedColNum = numberOfColumns - numAddPiecesToCheck;
		// Sets the final row number of the current piece that we will check in the loop
		int lastRowNum = 3;
		// loops each column and row in the specified range to check 4 in a row diagonally up
		for (int rowNum = numberOfRows - 1; rowNum >= lastRowNum; rowNum--) {
			for (int columnNum = 0; columnNum < adjustedColNum; columnNum++) {
				// second piece in a row is 1 row up and 1 column to the right
				int secondInRow = rowNum - firstAdditional;
				int secondInCol = columnNum + firstAdditional;

				// third piece in a row is 2 row up and 2 columns to the right
				int thirdInRow = rowNum - secondAdditional;
				int thirdInCol = columnNum + secondAdditional;
				// fourth piece in a row is 3 row up and 3 columns to the right
				int fourthInRow = rowNum - thirdAdditional;
				int fourthInCol = columnNum + thirdAdditional;
				// Checks to see if each piece equals the specified game piece.
				boolean first = gameBoard[rowNum][columnNum] == gamePiece;
				boolean second = gameBoard[secondInRow][secondInCol] == gamePiece;
				boolean third = gameBoard[thirdInRow][thirdInCol] == gamePiece;
				boolean fourth = gameBoard[fourthInRow][fourthInCol] == gamePiece;
				// If they all equal each other then there is a 4 in a row detected
				if (first && second && third && fourth) {
					return true;
				}
			}
		}
		return false;
	}

	// Check for downwards diagonal win
	public static boolean diagonalDown(String[][] gameBoard,int numberOfColumns,int numberOfRows,
			String gamePiece,  int firstAdditional, int secondAdditional, int thirdAdditional) {
		// Value for the number of pieces to check in a row after the current piece
		int numAddPiecesToCheck = 3;
		// Sets the final column number of the current piece that we will check in the loop
		int adjustedColNum = numberOfColumns - numAddPiecesToCheck;
		// Sets the initial row number of the current piece that we will check in the loop
		int adjustedRowNum = numberOfRows - numAddPiecesToCheck;
		// loops each column and row in the specified range to check 4 in a row diagonally down
		
		for (int rowNum = adjustedRowNum - 1; rowNum >= 0; rowNum--) {
			for (int columnNum = 0; columnNum < adjustedColNum; columnNum++) {
				// Second in a row is 1 row to the right and 1 column down
				int secondInRow = rowNum + firstAdditional;
				int secondInCol = columnNum + firstAdditional;
				// Third in a row is 2 rows to the right and 2 columns down
				int thirdInRow = rowNum + secondAdditional;
				int thirdInCol = columnNum + secondAdditional;
				// Fourth in a row is 2 rows to the right and 2 columns down
				int fourthInRow = rowNum + thirdAdditional;
				int fourthInCol = columnNum + thirdAdditional;
				
				// Checks to see if each piece equals the specified game piece.
				boolean first = gameBoard[rowNum][columnNum] == gamePiece;
				boolean second = gameBoard[secondInRow][secondInCol] == gamePiece;
				boolean third = gameBoard[thirdInRow][thirdInCol] == gamePiece;
				boolean fourth = gameBoard[fourthInRow][fourthInCol] == gamePiece;
				// If they all equal each other then there is a 4 in a row detected
				if (first && second && third && fourth) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	// Check for diagonal win
	public static boolean diagonalWin (String[][] gameBoard, int numberOfColumns, 
			int numberOfRows, String gamePiece) {
		
		// Integer for each additional value we will check for four in a row
		int firstAdditional = 1;
		int secondAdditional = 2;
		int thirdAdditional = 3;
		
		// Check if there is an upwards 4 in a row
		boolean diagonalDownWin = diagonalDown(gameBoard, numberOfColumns, numberOfRows, 
				gamePiece, firstAdditional, secondAdditional, thirdAdditional);
		// Check if there is a downwards 4 in a row
		boolean diagonalUpWin = diagonalUp(gameBoard, numberOfColumns, numberOfRows, gamePiece, 
				firstAdditional, secondAdditional, thirdAdditional);
		
		// If either and upwards or downwards 4 in a row are true, return true for diagonal win
		if (diagonalDownWin || diagonalUpWin) {
			return true;
		}
		else {
			return false;
		}
	}
	
	

	// check for win
	public static boolean playerWin(String[][] gameBoard, int numberOfColumns, int numberOfRows, 
			String gamePiece) {
		
		// Check each possible win opportunity: 4 in a row horizontal, vertical, or diagonal
		// Store boolean values for each win possibility, and if a win is detected store true
		boolean horizontalWin = horizontalWin(gameBoard, numberOfColumns, numberOfRows, 
				gamePiece);
		boolean verticalWin = verticalWin(gameBoard, numberOfColumns, numberOfRows, gamePiece);
		boolean diagonalWin = diagonalWin (gameBoard, numberOfColumns, numberOfRows, gamePiece);
		
		// If win there is a win, either horizontal, vertical, or diagonal return true, for winner
		if (horizontalWin || verticalWin || diagonalWin) {
			return true;
		} else {
			return false;
		}
	}

	// Check if game board is full
	public static boolean emptyGameBoard(String[][] gameBoard, int numberOfColumns) {
		int topRow = 0;
		boolean topRowFull = true;
		int colNum = 0;
		String blankSpot = ".";
		
		// loops through the top row of the game board and until a blank spot is found or the 
		//method finishes incrementing through the entire row
		while (topRowFull && colNum < numberOfColumns) {
			// Loops through each value of the top row
			String currPiece = gameBoard[topRow][colNum];
			// If the the current piece equals a blank spot that means the board is not full,
			// so return true 
			if (currPiece.equals(blankSpot)) {
				return true;
			} else {
				colNum++;
			}
		}
		// If no blank spots are found in the top row, the board is full, so return false
		return false;
	}

	// Show the introduction.
	public static void intro() {
		System.out.println("This program allows two people to play the");
		System.out.println("game of Connect four. Each player takes turns");
		System.out.println("dropping a checker in one of the open columns");
		System.out.println("on the board. The columns are numbered 1 to 7.");
		System.out.println("The first player to get four checkers in a row");
		System.out.println("horizontally, vertically, or diagonally wins");
		System.out.println("the game. If no player gets fours in a row and");
		System.out.println("and all spots are taken the game is a draw.");
		System.out.println("Player one's checkers will appear as r's and");
		System.out.println("player two's checkers will appear as b's.");
		System.out.println("Open spaces on the board will appear as .'s.\n");
	}

	// Prompt the user for an int. The String prompt will
	// be printed out. key must be connected to System.in.
	public static int getInt(Scanner key, String prompt) {
		while (!key.hasNextInt()) {
			String notAnInt = key.nextLine();
			System.out.println(notAnInt + " is not an integer.");
			System.out.print(prompt);
		}
		int result = key.nextInt();
		key.nextLine();
		return result;
	}
}
