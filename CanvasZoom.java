import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * Class that provides the zoom functionality of the pixel editor
 * @author Wan Naufal
 */
public class CanvasZoom extends Canvas {
    /**
     * The current canvas
     */
    private CanvasM currentCanvas;
    /**
     * The current zoom value
     */
    private double zoomVal = 1.0;
    
    //vars to be used when getMainImageGraphics() and getMainDrawingGraphics() is called
    /**
     * The imaage of the new canvas
     */
    BufferedImage canv;
    /**
     * Graphics used by the program
     */
    Graphics myG;
    
    /**
     * Constructor for class, pass your canvas class inside here
     * @param art The canvas that will be used
     */
    public CanvasZoom(CanvasM art) {
      this.currentCanvas = art;
      canv = currentCanvas.getMainImageGraphics();
      myG = currentCanvas.getMainDrawingGraphics();
    }
    
    /**
     * Setter method that set the value of your zoom in
     * @param zoom How much to soon too
     */
    public void setZoom(double zoom)
    {
      this.zoomVal = zoom;
      paint(currentCanvas.getGraphics());
      paint(myG);
      // paint(canv.getGraphics());
    }

    /**
     * Getter method that set the value of your zoom
     * @return value of the zoom var
     */
    public double getZoom() 
    {
      return zoomVal;
    }

    @Override
    /**
     * Paint method is overrided and is used to construct the logic behind zoom functionality
     * @param g Obtained from the class main graphics class
     */
    public void paint(Graphics g) 
    {
      Graphics2D g2 = (Graphics2D) g;
      AffineTransform transform = new AffineTransform();
      transform.scale(zoomVal, zoomVal);
      g2.transform(transform);
      g2.drawImage(canv, 0, 0, this);
      // g2.dispose();
      currentCanvas.refresh();
    }
}
