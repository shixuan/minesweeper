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
 * @version 1.2
 * Change game gui and finish gui.
 */
public class MSGUI extends JFrame implements ActionListener, MouseListener{
    private int[][] grid; // the matrix to store the values of each box. Values are same to MSCore.java
    private int gridLength; // edge length. same to MSCore.java
    private int[][] memory; // the matrix to store whether the box has been checked. same to MSCore.java
    private Map<GameButton, Integer> buttonToInt = new HashMap<>(); // a map of an integer to a button.
    private Map<Integer, GameButton> intToButton = new HashMap<>(); // a map of a button to an integer.
    private MSCore ms; // the core object
    //private ArrayList<String> blank = new ArrayList<String>(); // an arraylist to store the blank which has not been checked.
    private int faceBtnClicked = 0;
    private int countFlag = 0;

    static java.net.URL normalImg = MSGUI.class.getResource("images/normal.png");
    static java.net.URL winImg = MSGUI.class.getResource("images/win.png");
    static java.net.URL loseImg = MSGUI.class.getResource("images/lose.png");
    static java.net.URL bombImg = MSGUI.class.getResource("images/bomb.png");
    static java.net.URL cheatedImg = MSGUI.class.getResource("images/cheated.png");
    static java.net.URL flagImg = MSGUI.class.getResource("images/flag.png");

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
        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        int width = (int)getSize().getWidth();
        int height = (int)getSize().getHeight();
        JPanel gameBoard = new JPanel();
        JPanel faceBoard = new JPanel();
        faceBoard.setPreferredSize(new Dimension(width, 60));
        content.add(faceBoard, BorderLayout.NORTH);
        content.add(gameBoard, BorderLayout.CENTER);

        /* Create the face icon */
        GameButton face = new GameButton();
        face.setPreferredSize(new Dimension(50, 50));
        face.setBackground(new Color(238, 238, 238));
        face.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                faceBtnClicked++;
                if(faceBtnClicked == 6) {
                    face.setIcon(new ImageIcon(cheatedImg));
                    finishGame(3);
                }
            }
        });
        face.setIcon(new ImageIcon(normalImg));
        faceBoard.add(face);
        buttonToInt.put(face, -1);
        intToButton.put(-1, face);

        /* Create game board */
        gameBoard.setLayout(new GridLayout(gridLength, gridLength));
        for(int i = 0; i < gridLength * gridLength; i++) {
            GameButton button = new GameButton();
            buttonToInt.put(button, i);
            intToButton.put(i, button);
            button.addMouseListener(this);
            gameBoard.add(button);
        }
    }

    /**
     * The method for listening the button.
     */
    /* public void actionPerformed(ActionEvent event) {
        GameButton button = (GameButton)(event).getSource();
        int i = buttonToInt.get(button) / gridLength;
        int j = buttonToInt.get(button) % gridLength;
        int end = ms.check(i, j, blank.size()); // check the game is over or not.
        if(end == 1) { //user wins
            intToButton.get(-1).setIcon(new ImageIcon(winImg));
            finishGame(end);
        }
        else if(end == 2) { //user loses
            intToButton.get(-1).setIcon(new ImageIcon(loseImg));
            finishGame(end);
        }
        else // the game is not over, continue.
            showBlank(i, j);
    } */

    /**
     * A method for showing all the blank buttons.
     * @param i the row of the button which user click.
     * @param j the column of the button which user click.
     */
    public void showBlank(int i, int j) {
        if(grid[i][j] != 0) {
            intToButton.get(i * gridLength + j).setText(grid[i][j]+"");
            intToButton.get(i * gridLength + j).setEnabled(false);
            return;
        }
        else {
            intToButton.get(i * gridLength + j).setEnabled(false);
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
        pop.setSize(200,130);;
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
            pop.setTitle("Damn");
        }
        msg.setHorizontalAlignment(JLabel.CENTER);
        msg.setVerticalAlignment(JLabel.CENTER);

        JButton again = new JButton("Again");
        again.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InitialGUI frame = new InitialGUI();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(540,400);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        JButton quit = new JButton("Quit");
        quit.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel btns = new JPanel();
        btns.add(again);
        btns.add(quit);
        content.setLayout(new GridLayout(2, 1));
        content.add(msg);
        content.add(btns);
        
        faceBtnClicked = 999;

        showChessboard();
    }

    /**
     * The method for showing all bombs.
     */
    public void showChessboard() {
        GameButton button;
        for(int i = 0; i < gridLength * gridLength; i++) {
            button = intToButton.get(i);
            button.setEnabled(false);
            if(grid[buttonToInt.get(button) / gridLength][buttonToInt.get(button) % gridLength] == -1)
                button.setIcon(new ImageIcon(bombImg));
                //button.setText("@");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameButton button = (GameButton)(e).getSource();
        int i = buttonToInt.get(button) / gridLength;
        int j = buttonToInt.get(button) % gridLength;
        boolean isBomb = ms.isBomb(i, j);

        if(e.getButton() == MouseEvent.BUTTON1) {
            if(isBomb) { //user loses
                intToButton.get(-1).setIcon(new ImageIcon(loseImg));
                finishGame(2);
            }
            else // the game is not over, continue.
                showBlank(i, j);
        }
        else if(e.getButton() == MouseEvent.BUTTON3) {
            button.setIcon(new ImageIcon(flagImg));
            button.setEnabled(false);
            if(isBomb) countFlag++;

            if(!ms.isOver(countFlag)) { //user wins
                intToButton.get(-1).setIcon(new ImageIcon(winImg));
                finishGame(1);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
