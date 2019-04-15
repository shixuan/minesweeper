import java.util.*;
import javax.swing.*;
/**
* Title: GameDriver.java
* Description: The driver of game "Minesweeper".
* @author Shi Xuan
* @version 1.0
*/
public class MSDriver {
	public static void main(String[] args) {
    MSCore m = new MSCore();
    m.startGame();
    MSGUI frame = new MSGUI(m);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(540, 600);
    frame.setVisible(true);
  }
}
