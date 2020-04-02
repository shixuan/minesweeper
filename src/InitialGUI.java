import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Title: InitialGui.java
 * Description: The primaty gui of game "Minesweeper".
 * @author Shi Xuan
 * @version 1.0
 */
public class InitialGui extends JFrame implements ActionListener{
    /**
     * Constructor of InitialGui
     */
    public InitialGui() {
        Container content = this.getContentPane();
        content.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel("Minesweeper", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 60));
        content.add(title);

        /* choice buttons*/
        Button easy = new Button("Easy");
        Button normal = new Button("Normal");
        Button hard = new Button("Hard");
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

        JPanel btns = new JPanel();
        btns.setLayout(new GridLayout(5, 1, 0, 10));
        btns.add(new JLabel(""));
        btns.add(easy);
        btns.add(normal);
        btns.add(hard);
        btns.add(new JLabel(""));
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 5, 10, 0));
        bottom.add(new JLabel(""));
        bottom.add(new JLabel(""));
        bottom.add(btns);
        bottom.add(new JLabel(""));
        bottom.add(new JLabel(""));
        content.add(bottom); 
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
            width = 300;
            height = 390;
        }
        else if(event.getActionCommand().equals("Normal")) {
            gridLength = 9;
            mineNum = 10;
            width = 540;
            height = 600;
        }
        else if(event.getActionCommand().equals("Hard")) {
            gridLength = 10;
            mineNum = 15;
            width = 500;
            height = 560;
        }
        MSCore m = new MSCore(gridLength, mineNum);
        m.startGame();
        MSGui frame = new MSGui(m);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.initGameboard();
        frame.setVisible(true);
        this.setVisible(false);
    }
}