import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Title: MSGUI.java
 * Description: The gui of game "Minesweeper".
 * @author Shi Xuan
 * @version 1.0
 * @version 1.1
 * Add face icon.
 */
public class MSGUI extends JFrame implements ActionListener {
    private int[][] grid; // the matrix to store the values of each box. Values are same to MSCore.java
    private int gridLength; // edge length. same to MSCore.java
    private int[][] memory; // the matrix to store whether the box has been checked. same to MSCore.java
    private Map<JButton, Integer> buttonToInt = new HashMap<>(); // a map of an integer to a button.
    private Map<Integer, JButton> intToButton = new HashMap<>(); // a map of a button to an integer.
    private MSCore ms; // the core object
    private ArrayList<String> blank = new ArrayList<String>(); // an arraylist to store the blank which has not been checked.
    static int count = 0;

    /**
     * constructor of MSGUI.
     * @param ms the core object.
     */
    public MSGUI(MSCore ms) {
        this.setTitle("MineSweeper");
        this.ms = ms;
        this.grid = ms.getGrid();
        this.gridLength = ms.getGridLength();
        this.memory = ms.getMemory();
    }

    /**
     * Initialize the game board
     */
    public void initGameboard() {
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        int width = (int)this.getSize().getWidth();
        int height = (int)this.getSize().getHeight();
        JPanel gameBoard = new JPanel();
        JPanel faceBoard = new JPanel();
        faceBoard.setPreferredSize(new Dimension(width, 60));
        content.add(faceBoard, BorderLayout.NORTH);
        content.add(gameBoard, BorderLayout.CENTER);

        /* Create the face icon */
        JButton face = new JButton();
        face.setPreferredSize(new Dimension(50, 50));
        face.addActionListener(this);
        face.setActionCommand("face_button");
        face.setIcon(new ImageIcon("images/normal.png"));
        faceBoard.add(face);
        buttonToInt.put(face, -1);
        intToButton.put(-1, face);

        /* Create game board */
        gameBoard.setLayout(new GridLayout(gridLength, gridLength));
        for(int i = 0; i < gridLength * gridLength; i++) {
            JButton button = new JButton();
            blank.add(i + "");
            buttonToInt.put(button, i);
            intToButton.put(i, button);
            button.addActionListener(this);
            button.setActionCommand("game_button");
            gameBoard.add(button);
        }
    }

    /**
     * The method for listening the button.
     */
    public void actionPerformed(ActionEvent event) {
        if(event.getActionCommand().equals("game_button")) {
            JButton button = (JButton)(event).getSource();
            int i = buttonToInt.get(button) / gridLength;
            int j = buttonToInt.get(button) % gridLength;
            int end = ms.check(i, j, blank.size()); // check the game is over or not.
            if(end == 1) { //user wins
                intToButton.get(-1).setIcon(new ImageIcon("images/win.png"));
                finishGame(end);
            }
            else if(end == 2) { //user loses
                intToButton.get(-1).setIcon(new ImageIcon("images/lose.png"));
                finishGame(end);
            }
            else // the game is not over, continue.
                showBlank(i, j);
        }
        else if(event.getActionCommand().equals("face_button")) { // user cheats
            JButton button = (JButton)(event).getSource();
            count++;
            if(count == 6) {
                button.setIcon(new ImageIcon("images/cheated.png"));
                finishGame(3);
            }
        }
    }

    /**
     * A method for showing all the blank buttons.
     * @param i the row of the button which user click.
     * @param j the column of the button which user click.
     */
    public void showBlank(int i, int j) {
        if(grid[i][j] != 0) {
            intToButton.get(i * gridLength + j).setText(grid[i][j]+"");
            intToButton.get(i * gridLength + j).setEnabled(false);
            if(blank.contains((i * gridLength + j) + ""))
                blank.remove((i * gridLength + j) + "");
            return;
        }
        else {
            intToButton.get(i * gridLength + j).setEnabled(false);
            if(blank.contains((i * gridLength + j) + ""))
                blank.remove((i * gridLength + j) + "");
            memory[i][j] = 1;
        }

        if(i > 0 && memory[i-1][j] != 1)
            showBlank(i - 1, j);
        if(j > 0 && memory[i][j-1] != 1)
            showBlank(i, j - 1);
        if(i < gridLength - 1 && memory[i+1][j] != 1)
            showBlank(i + 1, j);
        if(j < gridLength - 1 && memory[i][j+1] != 1)
            showBlank(i, j + 1);
    }

    /**
     * Finish the game and show all bombs.
     * @param win 1 means user wins. 2 means user loses. 3 means user cheats.
     */
    public void finishGame(int win) {
        JDialog pop = new JDialog(this, "Oops!");
        pop.pack();
        pop.setVisible(true);
        pop.setLocationRelativeTo(null);
        pop.setResizable(false);
        Container content = pop.getContentPane();
        JLabel msg = new JLabel();
        if(win == 1)
            msg.setText("You Win!");
        else if(win == 2)
            msg.setText("You Lose!");
        else {
            msg.setText("You little bastard cheated!!!");
            pop.setTitle("Shit");
        }
        msg.setHorizontalAlignment(JLabel.CENTER);
        msg.setVerticalAlignment(JLabel.CENTER);
        content.add(msg);

        showChessboard();
    }

    /**
     * The method for showing all bombs.
     */
    public void showChessboard() {
        for(int i = 0; i < gridLength * gridLength; i++) {
            JButton button = intToButton.get(i);
            button.setEnabled(false);
            if(grid[buttonToInt.get(button) / gridLength][buttonToInt.get(button) % gridLength] == -1)
                button.setIcon(new ImageIcon("images/bomb.png"));
                //button.setText("@");
        }
    }

}
