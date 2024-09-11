
import java.awt.image.*;
/**
 * layer for adding images
 * @author Yudai Yamase
 */ 
public class Layer {
    /**
     * Buffered image storage varible
     */
    BufferedImage img;
    /**
     * Storage vaible for the x and y of the layer
     */
    int x, y;
    /**
     * Constructor 
     * @param img The image that will be stored in the layer
     * @param x The x value of the layer
     * @param y The y value of the layer
     */
    public Layer(BufferedImage img, int x, int y) {
        this.img = img;
        this.x = x;
        this.y = y;
    }
    /**
     * Returns the image stored in the layer
     * @return The image stored
     */
    public BufferedImage getImg() {
        return img;
    }
    /**
     * Updates the x and y coord values of the layer
     * @param x The x value to be updated
     * @param y The y value to be updated 
     */
    public void updateCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns the x value of the layer
     * @return The x value of the layer
     */
    public int getHorizontalPos() {
        return x;
    }

    /**
     * Returns the y value of the layer
     * @return The y value of the layer
     */
    public int getVerticalPos() {
        return y;
    }
}
