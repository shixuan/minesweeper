
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class Button extends JButton  {
	 private Shape shape = null;
	 private Color color;
	 
	 public Button(String s) {
		 super(s);
		 color = new Color(200, 200, 200);
		 setContentAreaFilled(false);
	 }
	 
	 public Button(String s, Color color) {
		 super(s);
		 this.color = color;
		 setContentAreaFilled(false);
	 }	 
	 
	 public void paintComponent(Graphics g) {
		 g.setColor(color);
		 g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
		 super.paintComponent(g);
	 }
	 
	 public void paintBorder(Graphics g) {
		 g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 20, 20);
	 }

}