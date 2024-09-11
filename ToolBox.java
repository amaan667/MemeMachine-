import javax.swing.*;
import java.awt.*;

/**
 * Class for the toolbox
 * 
 * @author Wan Naufal
 */
public class ToolBox
{
    /**
     * create a panel for ToolBox
     */
     JPanel Toolbox_Panel;
    
    /**
     * Created a ToolBox constructor
     * @param x set rows for GridLayout
     * @param y set cols for GridLayout
     * @param label set label for string
     * @param setSquare sets how many toolsqrs you want inside your toolbox
     */
    public ToolBox(int x, int y, String label, int setSquare)
    {
        Toolbox_Panel= new JPanel(new GridLayout(x,y));    //create a panel for ToolBox
        
        /*code for future task */
        // ToolSquare sqr[] = new ToolSquare[setSquare];   
        
        // for(int i = 0; i < sqr.length; i++)
        // {
        //     ToolSquare button = new ToolSquare(label);
        //     Toolbox_Panel.add(button.ToolSqr); 
        // }

    }
}