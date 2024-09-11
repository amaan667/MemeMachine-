

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * fill shapes with a color
 * @author Yudai Yamase, Thomas L French
 */
public class FillShape extends JButton implements ActionListener{
    /**
     * Checks is fill tool is on
     */
    private Boolean ON = false;
    /**
     * Label for the name of fill tool
     */
    private String label;
    /**
     * The default colour of the fill tool, will start as current background 
     */
    private Color default_color = this.getBackground();
    
    /**
     * Constructor, will make a button to use the program with
     * @param label The name given to the button
     */
    public FillShape(String label){
        ImageIcon fill_shapeIcon = new ImageIcon("button_icons/fill_bucket.png"); 
        this.setIcon(fill_shapeIcon);
        this.label = label;
        this.setText(label);
    
        addActionListener(this);
    }

    /**
     * Returns if the program is on or not
     * @return Boolean, true if on, false if not
     */
    public boolean isON(){
        return ON;
    }

    /**
     * Sets the status of the tool
     * @param status The status to set the tool to
     */
    public void setStatus(Boolean status){
        this.ON = status;
    }
    /**
     * Returns the default colour 
     * @return the default colour
     */
    public Color getDefaultColor(){
        return default_color;
    }
    
    /**
     * Overrides the action performed
     * @param e The action which triggered the function
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
