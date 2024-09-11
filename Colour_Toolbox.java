
import javax.swing.*;
import java.awt.event.*;
import java.lang.ProcessHandle.Info;
import java.sql.Time;
import java.awt.Color;
/**
 * Class to represent one section of the toolbar, a toolbox that contains all accessible colours
 * 
 * @author Conor James, Thomas L French, Cameron Rashidi
 */
public class Colour_Toolbox extends ToolSquare
{
    /**
     * The toolbox in which colours will be stored
     */
    private ToolBox colourToolBox = new ToolBox(2,8,"Colour toolbox",16);
    /**
     * The tool squares to store colours
     */
    private ToolSquare[] cols = new ToolSquare[16];
    /**
     * Colour object used
     */
    private Colour colours = new Colour();
    /**
     * Colour black used
     */
    private Color brush = Color.BLACK;
    /**
     * Colour wheel that is called when user clicks on empty custom colour
     */
    private ColourWheel wheel;
    /**
     * The panel to store colours in
     */
    JPanel colourPanel;
    /** 
     * The storage varible for the canavs that will be effected
     */
    private CanvasM art;

    /**
     * Constructor of class
     * @param label The lable of the toolbox
     * @param art The Canvas in which the toolbow will be used 
     */
    public Colour_Toolbox(String label, CanvasM art)
    {
        super(label);
        colourPanel = this.colourToolBox.Toolbox_Panel;
        this.art = art;
        addItems();
        
        JButton infobutton = new JButton();
        infobutton.setText("?");
        this.colourToolBox.Toolbox_Panel.add(infobutton);
        infobutton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Use the blank colour squares to choose any custom colour!", "Colour Info", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        
    }

   


    /**
     * Method that adds the toolsquare objects instantiated into @param Brush_Setting_Toolbox toolbox panel
     * and also adds actionListeners to the toolSquares
     */
    public void addItems()
    {
        for(int i = 0; i < 16; i++)
        {
            cols[i] = new ToolSquare("");
            cols[i].ToolSqr.setBackground(colours.ColourSet(i));
            this.colourPanel.add(cols[i].ToolSqr);
            cols[i].ToolSqr.addActionListener(this);
        }
        
    }

    @Override
    /**
     * Method that changes the paint colour when a toolSquare is clicked
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < 16; i++)
        {
            if(e.getSource() == cols[i].ToolSqr)
            {
                if (colours.ColourSet(i) == Color.WHITE)
                {
                    wheel = new ColourWheel(this, art, true);
                    wheel.WheelThread();
                }
                else
                {
                    brush = colours.ColourSet(i);
                }
                //System.out.println("Change Colour");
            }
        }
    }

    /**
     * Method that returns the brush colour
     * @return The brush colour
     */
    public Color returnCol()
    {
        return brush;
    }

    /**
     * Creates new colour
     * @param rHex The red hex value of the new colour
     * @param gHex The green hex value of the new colour
     * @param bHex The blue hex value of the new colour
     */
    public void newCustom(int rHex, int gHex, int bHex)
    {
        brush = new Color(rHex, gHex, bHex);
        boolean exist = colours.setCustom(brush);
        if(exist)
        {
            for (int i = 9; i < 16; i++)
            {
                cols[i].ToolSqr.setBackground(colours.ColourSet(i));
            }
        }
    }}
