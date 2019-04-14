import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MSGUI extends JFrame implements ActionListener {
  private int[][] grid;
  private int gridLength;
  private int[][] memory;
  private Map<JButton, Integer> buttonToInt = new HashMap<>();
  private Map<Integer, JButton> intToButton = new HashMap<>();
  private MSCore ms;
  private ArrayList<String> blank = new ArrayList<String>();

  public MSGUI(MSCore ms) {
    this.setTitle("MineSweeper");
    this.ms = ms;
    this.grid = ms.getGrid();
    this.gridLength = ms.getGridLength();
    this.memory = ms.getMemory();
    Container content = this.getContentPane();
    content.setLayout(new GridLayout(gridLength, gridLength));
    for(int i = 0; i < gridLength * gridLength; i++) {
      JButton button = new JButton();
      blank.add(i + "");
      buttonToInt.put(button, i);
      intToButton.put(i, button);
      button.addActionListener(this);
      content.add(button);
    }
  }

  public void actionPerformed(ActionEvent event) {
    JButton button = (JButton)(event).getSource();
    int i = buttonToInt.get(button) / gridLength;
    int j = buttonToInt.get(button) % gridLength;
    int end = ms.check(i, j, blank.size());
    if(end == 1) {
      finishGame(true);
    }
    else if(end == 2) {
      finishGame(false);
    }
    else
      showBlank(i, j);
  }

  public void showBlank(int i, int j) {
    if(grid[i][j] != 0) {
      if(blank.contains((i * 9 + j) + ""))
        blank.remove((i * 9 + j) + "");
      intToButton.get(i * 9 + j).setText(grid[i][j]+"");
      intToButton.get(i * 9 + j).setEnabled(false);
      return;
    }
    else {
      if(blank.contains((i * 9 + j) + ""))
        blank.remove((i * 9 + j) + "");
      intToButton.get(i * 9 + j).setEnabled(false);
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

  public void finishGame(Boolean win) {
		JDialog pop = new JDialog(this, "Oops!");
    pop.pack();
    pop.setVisible(true);
    pop.setLocation(400, 400);
    Container content = pop.getContentPane();
    JLabel msg = new JLabel();
    if(win)
      msg.setText("You Win!");
    else
      msg.setText("You Lose!");
    content.add(msg);

    showChessboard();
	}

  public void showChessboard() {
    for(int i = 0; i < gridLength * gridLength; i++) {
      JButton button = intToButton.get(i);
      button.setEnabled(false);
      if(grid[buttonToInt.get(button) / gridLength][buttonToInt.get(button) % gridLength] == -1)
        button.setText("@");
    }
  }

}
