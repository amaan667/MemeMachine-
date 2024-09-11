import javax.swing.*;
import java.awt.event.*;

/**
 * Class for a popup window when insert button is clicked to select which shape you want to insert
 * @author Wan Naufal
 */
public class Shape_PopWin
{
    /**
     * Pop-up window for choosing the shape
     */
    private JFrame window_shape = new JFrame();
    /**
     * list of shapes
     */
    private String[] optionlist = {"Square", "Triangle", "Circle"};
    
    /**
     * Constructor for the Shape_PopWin class
     */
    public Shape_PopWin()
    {
        
    }
    
    /**
     * Method that prompts a pop up window
     * @return optionChosen--the selected shape that the user has choose
     */
    public String chooseShape()
    {
        //var for the string user has chosen
        String optionChosen;

        Object selected = JOptionPane.showInputDialog(window_shape, "Select a shape", "Select a shape", JOptionPane.INFORMATION_MESSAGE, null, optionlist, optionlist[0]);

        optionChosen = (String) selected;
        
        return optionChosen;
    }
}