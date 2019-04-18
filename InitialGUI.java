import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Title: InitialGUI.java
 * Description: The primaty gui of game "Minesweeper".
 * @author Shi Xuan
 * @version 1.0
 */
public class InitialGUI extends JFrame implements ActionListener{
    /**
     * Constructor of InitialGUI
     */
    public InitialGUI() {
        Container content = this.getContentPane();
        content.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel("Minesweeper", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 60));
        content.add(title);

        /* choice buttons*/
        JButton easy = new JButton("Easy");
        JButton normal = new JButton("Normal");
        JButton hard = new JButton("Hard");
        Dimension choiceSize = new Dimension(80, 30);
        easy.setPreferredSize(choiceSize);
        easy.addActionListener(this);
        easy.setActionCommand("Easy");

        normal.setPreferredSize(choiceSize);
        normal.addActionListener(this);
        normal.setActionCommand("Normal");

        hard.setPreferredSize(choiceSize);
        hard.addActionListener(this);
        hard.setActionCommand("Hard");

        JPanel choice = new JPanel();
        choice.add(easy);
        choice.add(normal);
        choice.add(hard);
        content.add(choice);
    }

    /**
     * Listener method
     */
    public void actionPerformed(ActionEvent event) {
        int gridLength = 0;
        int mineNum = 0;
        int width = 0;
        int height = 0;

        if(event.getActionCommand().equals("Easy")) {
            gridLength = 5;
            mineNum = 3;
            width = 250;
            height = 310;
        }
        else if(event.getActionCommand().equals("Normal")) {
            gridLength = 9;
            mineNum = 10;
            width = 540;
            height = 600;
        }
        else if(event.getActionCommand().equals("Hard")) {
            gridLength = 10;
            mineNum = 20;
            width = 500;
            height = 560;
        }
        MSCore m = new MSCore(gridLength, mineNum);
        m.startGame();
        MSGUI frame = new MSGUI(m);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.initGameboard();
        frame.setVisible(true);
        this.setVisible(false);
    }
}