import java.awt.*;
import java.awt.event.*;
/**
 * Class that implements the Shape interface class to draw a shape (in this case a triangle)
 * @author Wan Naufal
 */
public class Shape_Triangle implements Shape
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
        //coordinate of the mouse
        int tri_X = e.getX();
        int tri_Y = e.getY();
    
        // draw triangle
        g.drawPolyline(new int[] {tri_X, tri_X-size, tri_X+size, tri_X}, new int[] {tri_Y,tri_Y+size,tri_Y+size,tri_Y}, 4);
    }
}