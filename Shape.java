import java.awt.*;
import java.awt.event.*;

/**
 * An interface class for implementing shapes
 * @author Wan Naufal
 */
public interface Shape 
{
    /**
     * Method that adds a shape to the canvas
     * @param g get the graphics of the canvas
     * @param e to get the coordinate of mouse using e.getX(), e.getY()
     * @param size The size of the shape
     */
    public void insert(Graphics g, MouseEvent e, int size);

    // public void fill();

}