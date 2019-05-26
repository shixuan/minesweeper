import java.awt.Color;

import javax.swing.JButton;

/**
 * Title: GameButton.java
 * Description: Class of game buttons
 * @author Shi Xuan
 * @version 1.0
 */
public class GameButton extends JButton {

    /**
     * Constructor
     */
    public GameButton() {
        super();
        setBackground(new Color(190, 190, 190));
    }

    /**
     * Override setEnabled.
     */
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = isEnabled();
        super.setEnabled(enabled);
        if(oldEnabled != enabled) {
            if(!enabled)
                setBackground(new Color(238, 238, 238));
            else
                setBackground(new Color(190, 190, 190));

            repaint();
        }
    }
}