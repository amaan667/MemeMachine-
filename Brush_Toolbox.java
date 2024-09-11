import javax.swing.*;
import java.awt.event.*;

/**
 * Class to represent one section of the toolbar, a toolbox that contains main drawing stationaries
 * 
 * @author Wan Naufal,Thomas L French, Yudai Yamase, Cameron Rashidi
 */
public class Brush_Toolbox extends ToolSquare {
    /**
     * Toolbox to store brushes
     */
    private ToolBox brushToolBox = new ToolBox(2, 2, "Brush toolbox", 4);
    /**
     * Eraser tool square
     */
    private ToolSquare eraser = new ToolSquare("Eraser");
    /**
     * Pencil tool square
     */
    private ToolSquare pencil = new ToolSquare("Pencil");
    /**
     * Brush tool square
     */
    private ToolSquare brush = new ToolSquare("Brush");
    /**
     * Insert tool square
     */
    private ToolSquare insert = new ToolSquare("Insert");
    /**
     * Eyedrop tool square
     */
    private ToolSquare eyedrop = new ToolSquare("EyeDropper");
    /**
     * Current mode
     */
    private String mode = "Paint";
    /**
     * Variable for shape method
     */
    private String chosenShape;

    /**
     * BrushPanel to store brush
     */
    JPanel brushPanel;

    /**
     * Constructor of class
     * 
     * @param label The string to label the object
     */
    public Brush_Toolbox(String label) {
        super(label);
        this.mode = mode;
        brushPanel = this.brushToolBox.Toolbox_Panel;
        addItems();
    }

    /**
     * Method that adds the toolsquare objects instantiated into 
     * toolbox panel
     * and also adds actionlistener to the toolsqrs
     */
    public void addItems() {
        ImageIcon eraser_image = new ImageIcon("button_icons/eraser_image.png"); // Creates imageicon
        this.brushPanel.add(eraser.ToolSqr);
        eraser.ToolSqr.addActionListener(this);
        eraser.ToolSqr.setIcon(eraser_image);

        ImageIcon pencil_image = new ImageIcon("button_icons/pencil_image.png");
        this.brushPanel.add(pencil.ToolSqr);
        pencil.ToolSqr.addActionListener(this);
        pencil.ToolSqr.setIcon(pencil_image);

        ImageIcon paintbrush_image = new ImageIcon("button_icons/paintbrush_image.png");
        this.brushPanel.add(brush.ToolSqr);
        brush.ToolSqr.addActionListener(this);
        brush.ToolSqr.setIcon(paintbrush_image);

        ImageIcon insert_image = new ImageIcon("button_icons/insert_image.png");
        this.brushPanel.add(insert.ToolSqr);
        insert.ToolSqr.addActionListener(this);
        insert.ToolSqr.setIcon(insert_image);

        ImageIcon eyedropper_icon = new ImageIcon("button_icons/eye_dropper_image.png");
        this.brushPanel.add(eyedrop.ToolSqr);
        eyedrop.ToolSqr.addActionListener(this);
        eyedrop.ToolSqr.setIcon(eyedropper_icon);
    }

    @Override
    /**
     * Method that performes an action when a toolsqr is clicked
     * 
     * @param e The action which called the function
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eraser.ToolSqr) {
            mode = "Eraser";
        }

        else if (e.getSource() == pencil.ToolSqr) {
            mode = "Pencil";
        }

        else if (e.getSource() == brush.ToolSqr) {
            mode = "Paint";

        }

        //for inserting shape
        else if(e.getSource() == insert.ToolSqr)
        {
            mode = "Shape";
            Shape_PopWin window_shape = new Shape_PopWin();
            chosenShape = window_shape.chooseShape();
        }

        else if(e.getSource() == eyedrop.ToolSqr)
        {
            mode = "EyeDrop";
        }
    }

    /**
     * Changes the mode of the brush
     * @param mode The mode to be changed to
     */
    public void changeMode(String mode)
    {
        this.mode = mode;
    }

    /**
     * Returns the current mode
     * @return The mode of the class
     */
    String returnmode() {
        return mode;
    }

    /**
     * Returns the current shape 
     * @return The current shape
     */
    String returnShape()
    {
        return chosenShape;
    }
}
