import java.awt.*;
import java.awt.event.*;
/**
 * Class that implements the Shape interface class to draw a shape (in this case a rectangle)
 * @author Wan Naufal
 */
public class Shape_Rectangle implements Shape
{
    /**
     * Constructor for shape class
     */
    public Shape_Rectangle()
    {
        super();
    }

    /**
     * Method that inserts the shape into the canvas
     * @param g create new graphics to be inserted into the canvas
     * @param e mouse event
     * @param size set the size of the shape based on the brush size
     */
    @Override
    public void insert(Graphics g, MouseEvent e, int size)
    {
        g.drawRect(e.getX(), e.getY(), size, size);
    }
}