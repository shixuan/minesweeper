import javax.swing.*;
/**
 * Title: MAIN.java
 * Description: The driver of game "Minesweeper".
 * @author Xuan Shi
 */
public class Main {
    public static void main(String[] args) {
        InitialGui frame = new InitialGui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540,400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
