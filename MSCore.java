import java.util.*;

/**
* Title: MineSweeper.java
* Description: The core of game "Minesweeper".
* @author Shi Xuan
* @version 1.0
*/
public class MSCore {
	private int gridLength = 9; // edge length
	private int[][] grid = new int[gridLength][gridLength]; // a matrix to store the values of each box in the chessboard
	                                                        // -1 means there is a mine.
	private int[][] memory = new int[gridLength][gridLength]; // a matrix to store whether the box has been checked.
															  // 0 means has not been checked.
															  // 1 means user chose this box before.
	                            	// the value bigger than 1 means user did not choose this box but this box has been checked.
	private int mineNum = 10; // the number of mines.

	public int getGridLength() {
		return this.gridLength;
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public int[][] getMemory() {
		return this.memory;
	}

	/** Build the chessboard, generate mines randomly
	* and calculate the values of each box.
	*/
	public void startGame() {
		for(int k = 0; k < mineNum; k++) {
			int i = (int)(Math.random() * gridLength);
			int j = (int)(Math.random() * gridLength);
			if(grid[i][j] == -1) {
				k--;
				continue;
			}
			grid[i][j] = -1;
			if(j < gridLength - 1 && grid[i][j + 1] != -1)
				grid[i][j + 1]++;
			if(j > 0 && grid[i][j - 1] != -1)
				grid[i][j - 1]++;
			if(i < gridLength - 1 && grid[i + 1][j] != -1)
				grid[i + 1][j]++;
			if(i > 0 && grid[i - 1][j] != -1)
				grid[i - 1][j]++;
			if(i > 0 && j > 0 && grid[i - 1][j - 1] != -1)
				grid[i - 1][j - 1]++;
			if(i > 0 && j < gridLength - 1 && grid[i - 1][j + 1] != -1)
				grid[i - 1][j + 1]++;
			if(i < gridLength - 1 && j > 0 && grid[i + 1][j - 1] != -1)
				grid[i + 1][j - 1]++;
			if(i < gridLength - 1 && j < gridLength - 1 && grid[i + 1][j + 1] != -1)
				grid[i + 1][j + 1]++;
		}
	}

	/**
	* Check the user's choice.
	* @param i Ordinate of the chessboard.
	* @param j Abscissa of the chessboard.
	* @return 0 means the game should continue. 1 means user wins. 2 means user loses;
	*/
	public int check(int i, int j, int blankNum) {
		if(grid[i][j] == -1)
			return 2;
		else if(blankNum == mineNum + 1)
			return 1;
		else
			return 0;
	}
}
