import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Title: MSGui.java
 * Description: The gui of game "Minesweeper".
 * @author Xuan Shi
 */
public class MSGui extends JFrame implements ActionListener, MouseListener{
    private final int gridLength; // edge length. same to MSCore.java
    private Map<GameButton, Integer> buttonToInt = new HashMap<>(); // a map of an integer to a button.
    private Map<Integer, GameButton> intToButton = new HashMap<>(); // a map of a button to an integer.
    private Button face;
    private final MSCore ms; // the core object
    private int faceBtnClicked;
    private int countFlag;
    private int countBomb;
    
    private JLabel countPane;

    static java.net.URL normalImg = MSGui.class.getResource("images/normal.png");
    static java.net.URL winImg = MSGui.class.getResource("images/win.png");
    static java.net.URL loseImg = MSGui.class.getResource("images/lose.png");
    static java.net.URL bombImg = MSGui.class.getResource("images/bomb.png");
    static java.net.URL cheatedImg = MSGui.class.getResource("images/cheated.png");
    static java.net.URL flagImg = MSGui.class.getResource("images/flag.png");

    /**
     * constructor of MSGui.
     * @param ms the core object.
     */
    public MSGui(MSCore ms) {
        this.setTitle("MineSweeper");
        this.ms = ms;
        this.gridLength = ms.getGridLength();
        
        this.faceBtnClicked = 0;
        this.countFlag = 0;
        this.countBomb = 0;
    }

    /**
     * Initialize the game board
     */
    public void initGameboard(int width) {
        Container content = getContentPane();
        content.setLayout(new BorderLayout());

        JPanel gameBoard = new JPanel();
        JPanel faceBoard = new JPanel();
        faceBoard.setPreferredSize(new Dimension(width, 60));
        content.add(faceBoard, BorderLayout.NORTH);
        content.add(gameBoard, BorderLayout.CENTER);

        /* Create the face icon */
        face = new Button("");
        face.setPreferredSize(new Dimension(50, 50));
        face.setBackground(new Color(238, 238, 238));
        face.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                faceBtnClicked++;
                if (faceBtnClicked == 6) {
                    face.setIcon(new ImageIcon(cheatedImg));
                    finishGame(3);
                }
            }
        });
        face.setIcon(new ImageIcon(normalImg));
        faceBoard.setLayout(null);
        face.setBounds(width/2-33, 5, 50, 50);
        faceBoard.add(face);
        countPane = new JLabel("x"+(ms.getMineNum()-countFlag), JLabel.CENTER);
        countPane.setFont(new Font("Microsoft Yahei", Font.BOLD, 15));
        countPane.setBounds(width*4/5, 5, 50, 50);
        faceBoard.add(countPane);

        /* Create game board */
        gameBoard.setLayout(new GridLayout(gridLength, gridLength));
        for (int i = 0; i < gridLength * gridLength; i++) {
            GameButton button = new GameButton();
            buttonToInt.put(button, i);
            intToButton.put(i, button);
            button.addMouseListener(this);
            gameBoard.add(button);
        }
    }

    /**
     * A method for showing all the blank buttons.
     * @param i the row of the button which user click.
     * @param j the column of the button which user click.
     */
    public void showBlank(int i, int j) {
        if (ms.getGridElem(i, j) != 0) {
        	intToButton.get(i * gridLength + j).setIcon(null);
            intToButton.get(i * gridLength + j).setText(ms.getGridElem(i, j)+"");
            intToButton.get(i * gridLength + j).setEnabled(false);
            return;
        }
        else {
        	intToButton.get(i * gridLength + j).setIcon(null);
            intToButton.get(i * gridLength + j).setEnabled(false);
            ms.setMemoryElem(i, j, 1);
        }

        if (i > 0 && ms.getMemoryElem(i - 1, j) != 1)
            showBlank(i - 1, j);
        if (j > 0 && ms.getMemoryElem(i, j - 1) != 1)
            showBlank(i, j - 1);
        if (i < gridLength - 1 && ms.getMemoryElem(i + 1, j) != 1)
            showBlank(i + 1, j);
        if (j < gridLength - 1 && ms.getMemoryElem(i, j + 1) != 1)
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
        if (win == 1)
            msg.setText("You Win!");
        else if (win == 2)
            msg.setText("You Lose!");
        else {
            msg.setText("You little bastard cheated!!!");
            pop.setTitle("Damn");
        }
        msg.setHorizontalAlignment(JLabel.CENTER);
        msg.setVerticalAlignment(JLabel.CENTER);

        Button again = new Button("Again");
        again.addActionListener(new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InitialGui frame = new InitialGui();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(540,400);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

        Button quit = new Button("Quit");
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
        pop.revalidate();
        
        faceBtnClicked = 999;

        showChessboard();
    }

    /**
     * The method for showing all bombs.
     */
    public void showChessboard() {
        GameButton button;
        for (int i = 0; i < gridLength * gridLength; i++) {
            button = intToButton.get(i);
            button.setEnabled(false);
            if (ms.getGridElem(buttonToInt.get(button) / gridLength,
                    buttonToInt.get(button) % gridLength) == -1)
                button.setIcon(new ImageIcon(bombImg));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        GameButton button = (GameButton)(e).getSource();
        int i = buttonToInt.get(button) / gridLength;
        int j = buttonToInt.get(button) % gridLength;
        boolean isBomb = ms.isBomb(i, j);

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (isBomb) { //user loses
                face.setIcon(new ImageIcon(loseImg));
                finishGame(2);
            }
            else // the game is not over, continue.
                showBlank(i, j);
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
        	
        	if (!button.getFlag() && countFlag < ms.getMineNum()) {
        		button.setIcon(new ImageIcon(flagImg));
        		button.toggleFlag();
        		countFlag++;
        		
        		if (ms.isBomb(i, j))
        			countBomb++;
        		if (ms.isWin(countBomb)) {
                	face.setIcon(new ImageIcon(winImg));
                    finishGame(1);
                }
        		
        	}
        	else if (button.getFlag()) {
        		button.setIcon(null);
        		button.toggleFlag();
        		countFlag--;

                if (ms.isBomb(i, j))
                    countBomb--;
        	}
        	countPane.setText("x"+(ms.getMineNum()-countFlag));
            //button.setEnabled(false);
            
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
