import java.util.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Class that handles the cut and paste functionality
 * and also implements a rectangular selection tool
 * that allows user to select an area on canvas for which area
 * the user wants to cut/paste
 * 
 * @author Wan Naufal
 */
public class CutPaste extends Canvas
{
    /**
     * get coordinate for start in x and y
     */
    private int startX, startY;  
     /**
     * get coordinate for end in x and y
     */
    private int endX, endY; 
    /**
     * varible to see and switch between cut and paste only
     */
    private String currentMode; 
    /**
     * Current Canvas being cut
     */
    private CanvasM currentCanvas;
    /**
     * Grahics of the canvas
     */
    private Graphics canvGraphics;
    /**
     * Buffered imahge from the canvas
     */
    private BufferedImage canvImg;
    /**
     * Check to see if function is currently in effect
     */
    private boolean isON = false;
    /**
     * The area of cut
     */
    private int[] selectedArea;
    /**
     * The buffered image of the area to be cut
     */
    private BufferedImage tobeCut;

    /**
     * Constructor for this class
     * @param art the Canvas that the program will use
     */
    public CutPaste(CanvasM art)
    {
        this.currentCanvas = art;
        
    }
    /**
     * Cuts the given area
     */
    public void cut()
    {
        paint(currentCanvas.getGraphics());

        this.isON = false; //immediately switch off
        currentMode = null;
    }
    /**
     * Pastes to the given area the cut image
     */
    public void paste()
    {
        paint(currentCanvas.getMainDrawingGraphics());
        paint(currentCanvas.getGraphics());
        currentCanvas.refresh();

        this.isON = false; //immediately switch off
        currentMode = null;
    }

    @Override
    public void paint(Graphics g) 
    {
        //get selected area of canvas
        if (currentMode == "Cut")
        {
            int width = endX - startX;
            int height = endY - startY;

            canvImg = currentCanvas.getMainImageGraphics();

            tobeCut = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            
            selectedArea = canvImg. getRGB(startX, startY, width, height, null, 0, width);
            tobeCut.setRGB(0, 0, width , height, selectedArea, 0, width);

            currentCanvas.getMainDrawingGraphics().setColor(Color.WHITE);
            currentCanvas.getMainDrawingGraphics().fillRect(startX, startY, width, height);
            g.drawRect(startX, startY, width, height);
        }   

        //draw cut image on canvas
        else if (currentMode == "Paste")
        {
            g.drawImage(tobeCut,startX,startY,null);
        }
    }

    /**
     * Method that obtains the initial coordinate of canvas when mouse is clicked [see mousePressed()]
     * @param eX The x coordinate of the mouse
     * @param eY The y coordinate of the mouse
     */
    public void getMouseCoordinate(int eX, int eY)
    {
        this.startX = eX;
        this.startY = eY;
    }

    /**
     * Setter method for currentMode that obtains the cut and paste mode after right clicking (see RightClickMenu.java)
     * @param mode The mode to change the program to
     */
    public void setFeat(String mode)
    {
        currentMode = mode;

        if (currentMode == "Cut")
        {
            isON = true;
        }

        else if (currentMode == "Paste")
        {
            isON = true;
        }
    }

    /**
     * Getter method for currentMode that obtains the cut and paste mode after right clicking (see RightClickMenu.java)
     * @return string of current mode
     */
    public String getFeat()
    {
        return currentMode;
    }

    /**
     * Method that returns if cut and paste option is on
     * @return if the program is on
     */
    public boolean isON()
    {
        return isON;
    }

    /**
     * Method that gets the final coordinate of Y to perform selection of an area of the canvas and update to the vars
     * @param obtainEndX The end x value
     * @param obtainEndY The end y value 
     */
    public void updateEndPoint(int obtainEndX, int obtainEndY)
    {
        this.endX = obtainEndX;
        this.endY = obtainEndY;
    }
}
