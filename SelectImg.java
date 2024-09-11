import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button for selecting an image to drag
 * @author Yudai Yamase
 */
public class SelectImg extends JButton implements ActionListener {
    /**
     * Determines whether this functionality is being used or not
     */
    private Boolean ON = false;
    /**
     * The text on the button
     */
    private String label;
    /**
     * The colour of the button
     */
    private Color default_color = this.getBackground();
    
    /**
     * Constructor for the SelectIMG class
     * @param label The text displayed on the button
     */
    public SelectImg(String label){
        ImageIcon selectimage_icon = new ImageIcon("button_icons/image_icon.png"); 
        this.setIcon(selectimage_icon);
        this.label = label;
        this.setText(label);
        addActionListener(this);
    }

    /**
     * Returns whether this functionality is being used
     * @return Whether the button has been clicked or not
     */
    public boolean isON(){
        return ON;
    }

    /**
     * The functionality for when the button is clicked
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (ON == false){
            this.setBackground(Color.yellow);
            ON = true;
        }
        else if (ON == true){
            this.setBackground(default_color);
            ON = false;
        }
    }
}
