
import java.awt.Color;
/**
 * This class changes the colour of the paintbrush
 * @author Conor James, Thomas L French
 */
public class Colour
{
    /**
     * The array of set colours
     */
    private Color setColours[] = {new Color(255,0,0), new Color(255,153,0), new Color(255,255,0), new Color(0,255,51), new Color(0,204,0), new Color(51,204,255), new Color(0,0,255), new Color(102,0,153), new Color(153,153,153), new Color(0,0,0)};
    /**
     * The array of custom colours 
     */
    private Color custColours[] = {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,Color.WHITE};

    /**
     * Constructor. Creates an instance of the Colour class.
     */
    public Colour()
    {}

    /**
     * Returns the colour corresponding to the button clicked on
     * 
     * @param i The number button clicked on in the ui. 0-9 corresponds to set colours,while 10-14 corresponds to custom colours
     * @return Returns the new colour 
     */
    public Color ColourSet(int i)
    {
        if(i <= 9)
        {
            return setColours[i];
        }
        else
        {
            i = i - 10;
            return custColours[i];
        }
    }

    /**
     * Sets the custom colour into the custom colour stack
     * @param custom The color to be added
     * @return false is colour is already in stack and true if colour unique and added to stack
     */
    public boolean setCustom(Color custom)
    {
        for(int i = 0; i < 15; i++)
        {
            if(i <= 9)
            {
                if(custom.getRGB() == setColours[i].getRGB())
                {
                    return false;
                }
            }
            else
            {
                if(custom.getRGB() == custColours[i-10].getRGB())
                {
                    return false;
                }
            }
        }
        Color current = custom;
        Color c;
        for(int i = 0; i < 5; i++)
        {
            c = custColours[i];
            custColours[i] = current;
            current = c;
        }
        return true;
    }
}