import java.util.*;

/**
* Title: MSCore.java
* Description: The core of game "Minesweeper".
* @author Xuan Shi
*/
public class MSCore {
	private final int gridLength; // edge length
	private int[][] grid; 	// a matrix to store the values of each box in the chessboard
							// -1 means there is a mine.
	private int[][] memory; // a matrix to store whether the box has been checked.
							// 0 means has not been checked.
							// 1 means user chose this box before.
							// the value bigger than 1 means user did not choose this box but this box has been checked.
	private final int mineNum; // the number of mines.

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
	 * Set grid values
	 * @param i	row
	 * @param j	column
	 * @param val	passed value
	 */
	public void setGridElem(int i, int j, int val) {
		assert(i < getGridLength() && j < getGridLength());
		grid[i][j] = val;
	}

	/**
	 * Get grid values
	 * @param i	row
	 * @param j	column
	 * @return	grid value at [i, j]
	 */
	public int getGridElem(int i, int j) {
		assert(i < getGridLength() && j < getGridLength());
		return grid[i][j];
	}

	/**
	 * Set memory values
	 * @param i	row
	 * @param j	column
	 * @param val	passed value
	 */
	public void setMemoryElem(int i, int j, int val) {
		assert(i < getGridLength() && j < getGridLength());
		memory[i][j] = val;
	}

	/**
	 * Get memory values
	 * @param i	row
	 * @param j	column
	 * @return	memory value at [i, j]
	 */
	public int getMemoryElem(int i, int j) {
		assert(i < getGridLength() && j < getGridLength());
		return memory[i][j];
	}

	/**
	 * Get the number of mines
	 * @return	the number of mines
	 */
	public int getMineNum() {return this.mineNum;}

	/** Build the chessboard, generate mines randomly
	* and calculate the values of each box.
	*/
	public void startGame() {
	for (int k = 0; k < mineNum; k++) {
		int i = (int)(Math.random() * gridLength);
		int j = (int)(Math.random() * gridLength);
		if (grid[i][j] == -1) { //This block has already been a mine.
			k--;
			continue;
		}
		grid[i][j] = -1;
		if (j < gridLength - 1 && grid[i][j + 1] != -1)
			grid[i][j + 1]++;
		if (j > 0 && grid[i][j - 1] != -1)
			grid[i][j - 1]++;
		if (i < gridLength - 1 && grid[i + 1][j] != -1)
			grid[i + 1][j]++;
		if (i > 0 && grid[i - 1][j] != -1)
			grid[i - 1][j]++;
		if (i > 0 && j > 0 && grid[i - 1][j - 1] != -1)
			grid[i - 1][j - 1]++;
		if (i > 0 && j < gridLength - 1 && grid[i - 1][j + 1] != -1)
			grid[i - 1][j + 1]++;
		if (i < gridLength - 1 && j > 0 && grid[i + 1][j - 1] != -1)
			grid[i + 1][j - 1]++;
		if (i < gridLength - 1 && j < gridLength - 1 && grid[i + 1][j + 1] != -1)
			grid[i + 1][j + 1]++;
		}
	}

	/**
	* Check current box is mine or not.
	* @param i row of the chessboard.
	* @param j colmun of the chessboard.
	* @return whether there is a bomb.
	*/
	public boolean isBomb(int i, int j) {
		return grid[i][j] == -1;
	}

	/**
	 * @param findNum	find how many mines
	 * @return 	win or lose.
	 */
	public boolean isWin(int findNum) {
		return findNum == mineNum;
	}
}
