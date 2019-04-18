import java.util.*;

/**
* Title: MSCore.java
* Description: The core of game "Minesweeper".
* @author Shi Xuan
* @version 1.0
*/
public class MSCore {
	private int gridLength; // edge length
	private int[][] grid; 	// a matrix to store the values of each box in the chessboard
							// -1 means there is a mine.
	private int[][] memory; // a matrix to store whether the box has been checked.
							// 0 means has not been checked.
							// 1 means user chose this box before.
							// the value bigger than 1 means user did not choose this box but this box has been checked.
	private int mineNum; // the number of mines.

	/**
	 * Constructor of MSCore
	 * @param gridLength length of game board
	 * @param mineNum number of mines
	 */
	public MSCore(int gridLength, int mineNum) {
		this.gridLength = gridLength;
		this.mineNum = mineNum;
		this.grid = new int[gridLength][gridLength];
		this.memory = new int[gridLength][gridLength];
	}

	/**
	 * Getter of gridLenght
	 * @return gridlength
	 */
	public int getGridLength() {
		return this.gridLength;
	}

	/**
	 * Getter of grid
	 * @return grid
	 */
	public int[][] getGrid() {
		return this.grid;
	}

	/**
	 * Getter of memory
	 * @return memory
	 */
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
		if(grid[i][j] == -1) { //This block has already been a mine.
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
	* @param blankNum the number of rest blank. if blankNum = mineNum+1, user wins.
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
