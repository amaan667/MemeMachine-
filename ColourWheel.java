

import java.io.Console;
import java.util.concurrent.CountDownLatch;

import javax.swing.*;
import javax.swing.colorchooser.*;
import java.awt.Color;
/**
 * Class used to create the colour wheel popup 
 *@author Cameron Rashidi, Conor James, Thomas L French
 */
public class ColourWheel
{
    /**
     * The toolbox where colours are stored, is a storage varible
     */
    private Colour_Toolbox toolboxc;
    /**
     * Storage varible for the canvas used in the program
     */
    private CanvasM art;
    /**
     * Storage varible to check is colour will be background or not
     */
    private Boolean notBackground;
    /**
     * Storage varible for the red hex value  used in the program
     */
    private Integer r;
    /**
     * Storage varible for the green hex value used in the program
     */
    private Integer g;
    /**
     * Storage varible for the blue hex value used in the program
     */
    private Integer b;

    /**
     * Constructor for the class
     * @param toolboxc The toolbox used to store the colours created
     * @param art The canvas used to interact with the created colours
     * @param notBackground Used to check is new colour will be for the brush/ pen or canvas background 
     */
    public ColourWheel(Colour_Toolbox toolboxc, CanvasM art, Boolean notBackground)
    {
        this.toolboxc = toolboxc;
        this.art = art;
        this.notBackground = notBackground;
    }
    /**
     * "Pops" the popup for the user to use
     */
    public void WheelThread()
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            public synchronized void run() 
            {
                String hexValue;
                JColorChooser cc = new JColorChooser();
                AbstractColorChooserPanel[] panels = cc.getChooserPanels();
                for (AbstractColorChooserPanel accp : panels)
                {
                    if (accp.getDisplayName().equals("RGB"))
                    {
                        JOptionPane.showMessageDialog(cc, accp, "RGB Colour Chooser", JOptionPane.PLAIN_MESSAGE);
                    }
                }
                hexValue=cc.getColor().toString();
                hexValue = String.format( "#%02x%02x%02x", cc.getColor().getRed(), cc.getColor().getGreen(), cc.getColor().getBlue());
                r = (Integer) cc.getColor().getRed();
                g = (Integer) cc.getColor().getGreen();
                b = (Integer) cc.getColor().getBlue();
                if(notBackground)
                {
                    toolboxc.newCustom(r, g, b);
                }
                else
                {
                    art.changeBackgroundColour(new Color(r, g, b));
                }
            }
            
        });
    }
}