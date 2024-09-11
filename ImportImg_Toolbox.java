
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
/**
 * Creates a toolbox that contains import img button
 * 
 * @author Yudai Yamase, Thomas l French
 */
public class ImportImg_Toolbox extends ToolSquare{
    /**
     * The toolbox where the image tool will reside
     */
    ToolBox imgToolBox = new ToolBox(2, 10, "Img toolbox", 1);
    /**
     * The toolsquare where the image tool will be
     */
    ToolSquare importImg = new ToolSquare("importImg");
    /**
     * Storage varible for the selected image
     */
    BufferedImage selectedImg = null;

    /**
     * The cavas where the tool will be used
     */
    private CanvasM canvas;
    /**
     * The JPanel where the image will be placed
     */
    JPanel imgPanel;
    /**
     * Constructor of class
     * @param label The name of the JButton created
     * @param canvas The canvas where the tool will be used
     */
    public ImportImg_Toolbox(String label, CanvasM canvas) {
        super(label);
        imgPanel = this.imgToolBox.Toolbox_Panel;
        this.canvas = canvas;
        addItems();
    }

    /**
     * Method that adds the toolsquare objects instantiated into @param imgToolBox toolbox panel
     * and also adds actionlistener to the toolsqrs
     */
    public void addItems() 
    {
        ImageIcon import_image = new ImageIcon("button_icons/import_image.png"); 
        this.imgPanel.add(importImg.ToolSqr);
        importImg.ToolSqr.addActionListener(this);
        importImg.ToolSqr.setIcon(import_image);
    }

    @Override
    /**
     * Method that performes an action when a toolsqr is clicked
     * @param e
     */
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == importImg.ToolSqr)
        {   
            //mode = "ON";
            getImg();
        }
    }
    /**
     * returns the mode, which is either ON or OFF
     * @return ON or OFF
     */
    /* String returnmode()
    {
        return mode;
    }  */

    /**
     * sets a mode, which is either ON or OFF
     * @param mode ON or OFF
     */
    /* public void setmode(String mode){
        this.mode = mode;
    }  */

    /**
     * Allows users to import an image from a device
     */
    public void getImg() 
    {
        JFileChooser fileChooser = new JFileChooser();
        
        // Set the filter to show only image files
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Image files", "jpg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        // Show the file chooser and get the user's selection
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            // User selected an image file
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Read the image file into a BufferedImage object
                BufferedImage image = ImageIO.read(selectedFile);
                Image scaledImg = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                
                BufferedImage bufferedScaledImg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = (Graphics2D) bufferedScaledImg.createGraphics();
                g2d.drawImage(scaledImg, 0, 0, null);
                canvas.addLayer(new Layer(bufferedScaledImg, 0, 0));
                canvas.refresh();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    

}
