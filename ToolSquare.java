import javax.swing.*;
import java.awt.event.*;

/**
 * Class for ToolSquare
 * 
 * @author Wan Naufal
 */
public class ToolSquare extends JButton implements ActionListener
{
    /**
     * create a button for the toolbox
     */
    JButton ToolSqr;
    
    /**
     * Constructor for toolsquare
     * @param label string to add label to jbutton
     */
    public ToolSquare(String label)
    {
        ToolSqr = new JButton(label);
        ToolSqr.addActionListener(this);
    }

    /**
     * Method that listen to mouse click and do action
     * @param e takes actionlistener
     */
    public void actionPerformed(ActionEvent e)
    {
        
    }
}