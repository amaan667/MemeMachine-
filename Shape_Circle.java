import java.awt.*;
import java.awt.event.*;
/**
 * Class that implements the Shape interface class to draw a shape (in this case a circle)
 * @author Wan Naufal
 */
public class Shape_Circle implements Shape
{

    @Override
    /**
     * Method that inserts the shape into the canvas
     * @param g create new graphics to be inserted into the canvas
     * @param e mouse event
     * @param size set the size of the shape based on the brush size
     */
    public void insert(Graphics g, MouseEvent e, int size)
    {
        g.drawOval(e.getX()-size/2, e.getY()-size/2, size, size);
    }
}