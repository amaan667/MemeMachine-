
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JOptionPane;

/**
 * Creates a toolbox where you can set the width of brush,
 * dot interval and whatnot.
 * 
 * @author Yudai Yamase, Thomas L French
 */
public class Brush_Setting_Toolbox extends ToolSquare{
    
    /**
     * Creates toolbox for the brush settings
     */
    ToolBox brushSettingToolbox = new ToolBox(2, 10, "brush setting toolbox", 1);
    
    /**
     *Brush settings toolsquare  
     */
    ToolSquare brushSetting = new ToolSquare("Brush Settings");

    /**
    *default size of the brush is 10
    */
    private int brushSize = 10;
    
    /**
    *default size of dash - 0 results in no dash
    */
    private int brushDash = 0;

    /**
     * Current brush type in array
     */
    private int brushIndexType = 0;

    /**
     * Array of brush options
     */
    private String[] typesOfBrush = new String[] {"Round", "Square"};

    /**
     *Current brush option in string
     */
    private String brushType = "Round";

    /**
     * Panel which stores setting toolbox
     */
    JPanel brushSettingPanel;

    /**
     * Constructor of class
     * @param label String used to name toolbox
     */
    public Brush_Setting_Toolbox(String label) {
        super(label);
        brushSettingPanel = this.brushSettingToolbox.Toolbox_Panel;
        addItems();
    }

    /**
     * Method that adds the toolsquare objects instantiated into @param brushSettingToolbox toolbox panel
     * and also adds actionlistener to the toolsqrs
     */
    public void addItems() {
        ImageIcon settings_image = new ImageIcon("button_icons/settings_image.png"); 
        this.brushSettingPanel.add(brushSetting.ToolSqr);
        brushSetting.ToolSqr.addActionListener(this);
        brushSetting.ToolSqr.setIcon(settings_image);
    }


    /**
     * Gets the size of the brush
     * @return The brush size
     */
    public int getBrushSize(){
        return this.brushSize;
    }

    /**
     * Gets the size of the dash
     * @return The brush dash size
     */
    public int getBrushDash()
    {
        return this.brushDash;
    }

    /**
     * Gets the brush type 
     * @return Teh brush type
     */
    public String getBrushType()
    {
        return this.brushType;
    }

    /**
     * Sets the size of the brush
     * @param size The size to set the brush too
     */
    public void setBrushSize(int size){
        this.brushSize = size;
    }

    /**
     * Sets the size of the dash
     * @param dash The size to set the dash too
     */
    public void setBrushDash(int dash)
    {
        this.brushDash = dash;
    }

    /**
     * Sets the type of the brush
     * @param type The type to set the brush too
     */
    public void setBrushType(String type)
    {
        this.brushType = type;
    }

    /**
     * Method that checks if a string is numeric
     * @param str The string to check if numeric
     * @return boolean, if true is numeric, if false is not
     */
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }


    @Override
    /**
     * Method that performs an action when a toolsqr is clicked
     * @param e The action which triggered the function
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == brushSetting.ToolSqr)
        {   
            JFrame popup = new JFrame("Popup Window");

            popup.setLocation(150, 230);
            popup.setSize(240, 170);
            popup.setVisible(true);
            popup.setLayout(new GridLayout(4,2));
            popup.setTitle("Brush Settings");

            //Thinkness 
            JLabel label1 = new JLabel("Thickness of line");
            JTextField textField1 = new JTextField(20);
            textField1.setText(Integer.toString(getBrushSize()));

            //Dash
            JLabel label2 = new JLabel("Dash distance");
            JTextField textField2 = new JTextField(20);
            textField2.setText(Integer.toString(getBrushDash()));

            //Type
            JLabel label3 = new JLabel("Type of brush");
            JComboBox chosenBrushType = new JComboBox<String>(typesOfBrush);
            chosenBrushType.setSelectedIndex(brushIndexType);
            chosenBrushType.setEditable(false);


            popup.add(label1);
            popup.add(textField1);
            popup.add(label2);
            popup.add(textField2);
            popup.add(label3);
            popup.add(chosenBrushType);

            JButton applyButton = new JButton("Apply");
            JButton closeButton = new JButton("Close");

            popup.add(applyButton);
            popup.add(closeButton);

            popup.getRootPane().setDefaultButton(applyButton);

            // Click the apply button by hitting enter
            applyButton.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        applyButton.doClick();
                    }
                }
            });

            // Add an action listener to the apply button
            applyButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // When the button is clicked, set the size of the brush accordingly
                    // only accepts numeric characters (int)
                    String text = textField1.getText();
                    String dashText = textField2.getText();
                    if (isNumeric(text) && isNumeric(dashText)){
                        // check if it is float
                        if (text.indexOf('.') != -1 && dashText.indexOf('.') != -1){
                            // throw an error saying the number has to be integer
                            JOptionPane.showMessageDialog(null, "The size cannot be float!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            // convert into int
                            int size = Integer.parseInt(text);
                            int sizeDash = Integer.parseInt(dashText);
                            if (size <= 0 && sizeDash < 0){
                                // throw an error saying the size has to be greater than 0
                                JOptionPane.showMessageDialog(null, "The size has to be greater than 0!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                setBrushSize(size);
                                setBrushDash(sizeDash);
                                try
                                {
                                    String typeChosen = (String)chosenBrushType.getSelectedItem();
                                    brushIndexType = chosenBrushType.getSelectedIndex();
                                    setBrushType(typeChosen);
                                }
                                catch(Exception t)
                                {
                                    System.out.println("Errer in choosing brush type! Error code: " + t); 
                                }
                                // close the popup
                                popup.dispose();
                            }
                        }          
                    }
                    else{
                        // throw an error saying the size has to be numeric
                        JOptionPane.showMessageDialog(null, "The size has to be numeric!",
                    "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            // when close button is clicked, it closed the popup window
            closeButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    popup.dispose();
                }
            });
        }
    }
}
