
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.awt.geom.*;

import java.lang.Math.*;
import java.nio.channels.spi.SelectorProvider;
import java.rmi.server.RemoteStub;


/**
 * Creates a canvas in which the art will be made
 * @author Thomas L French, Yudai Yamase, Conor James, Wan Naufal
 */
class CanvasM extends Canvas
{
    /**
     * Past coOrds of mouse used to draw lines
     */
    private int pastUsedCoOrds[] = {-1,-1};
    /**
     * Main layer of the canvas 
     */
    private BufferedImage mainLayer;
    /**
     * Drawing layed of the canvas
     */
    private BufferedImage drawing;
    /**
     * Array of all layers in the canvas
     */
    private ArrayList<Layer> layers;
    /**
     * Size of image to insert
     */
    private int img_x = 100, img_y = 100;
    /**
     * Canvas graphics
     */
    private Graphics2D g;
    /**
     * Storage varible for canvas background colour
     */
    private Color backgroundColor;
    /**
     * Type of brush stroke 
     */
    private int type = BasicStroke.CAP_ROUND;

    //variables for zoom functions
    /**
     * Used to ger contents of graphics 
     */
    protected Graphics canvGra;
    /**
     * Used to store graphics image
     */
    protected BufferedImage canvImg;
    /**
     * Array list of objects used for a multi dimentional list
     */
    ArrayList<ArrayList<Object>> multiDimList = new ArrayList<ArrayList<Object>>();
    
    //Variables for saving
    /**
     * Current name of save file
     */
    private String saveName = null;
    /**
     * The object used to save and open files
     */
    private JFileChooser fileChooser = new JFileChooser();
    /** 
     * The filter used by fileChooser 
     */
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif","PNG");

    //undo/redo
    /**
     * The size of the undo stack
     */
    private int undoSize = 20;
    /**
     * The undo stack used for undo and redo
     */
    private BufferedImage undoStack [] = new BufferedImage[undoSize];
    /**
     * Used to track user inputs and time till next undo stack update 
     */
    private long undoTime = System.currentTimeMillis()-50;//Allows for an instant undo after launch
    /**
     * Used to track place in undo stack
     */
    private int undoCounter = 0;
    /**
     * Used to stop overflow of undo stack
     */
    private int undoStartTimer = -2;
    /**
     * Constructor for the class
     * @param startingColour Is used to set the starting colour of the canvas 
     * @param sizeX is the starting X size of the canvas
     * @param sizeY is the starting Y size of the canvas
     */
    public CanvasM(Color startingColour,int sizeX, int sizeY)
    {
        super();
        setBackground(startingColour);
        backgroundColor = startingColour;
        setSize(sizeX,sizeY);
        mainLayer = new BufferedImage(3*sizeX, 3*sizeY, BufferedImage.TYPE_INT_ARGB);
        drawing = new BufferedImage(3*sizeX, 3*sizeY, BufferedImage.TYPE_INT_ARGB);

        Graphics2D drawingG2d = drawing.createGraphics();
        drawingG2d.setColor(backgroundColor);
        drawingG2d.fillRect(0, 0, drawing.getWidth(), drawing.getHeight());

        // this.layers = new ArrayList<BufferedImage>();
        this.layers = new ArrayList<Layer>();
        layers.add(new Layer(drawing, 0, 0));

        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File("."));
    }

      /**
     * Paints a line upon the canvas
     * @param x The x position of the shape
     * @param y The y position of the shape 
     * @param c The colour of the line/shape to draw
     * @param xSize The x size of the shape to draw, also used for width for stuff like line width
     * @param ySize The x size of the shape to draw
     * @param movement Used to determine a line is being drawn or a dot clicked
     * @param dash The sieze of the dash gap used, if set to zero there will be no dash
     * @param press Checks to see if the mouse action is a press or click and if press does not draw without movement 
     * @param brushType The type of brush the user wishes to use
     */
    public void paint(int x,int y,Color c, int xSize,int ySize,boolean movement,int dash,Boolean press, String brushType)
    {    
        Graphics gra = drawing.createGraphics();
        Graphics2D test = (Graphics2D) gra;
        //Checks which type of stroke the user wants to use
        switch(brushType)
        {
            case "Round":
                type = BasicStroke.CAP_ROUND;
                break;
            case "Square":
                type = BasicStroke.CAP_SQUARE;
                break;
        }
        //Sets stroke type 
        if(dash == 0) // sets brush stroke type based on dash size 
        {
            test.setStroke(new BasicStroke(xSize,type, BasicStroke.JOIN_ROUND));//Makes line smooth and round
        }
        else
        {
            float gap = dash/10;//Used to give users higher degree of change in gap size
            if(gap<1)//Ensures dash is min of 1
            {
                gap =1 ;
            }
            float[] dashArray = {gap, gap};
            test.setStroke(new BasicStroke(xSize,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,1.0f,dashArray,0f));//Makes line smooth and round and dashed
        }

       test.setColor(c);
    
      if(movement == false && press == false)//Resets coords if clicking not dragging mouse and not a press action
        {        
            test.fillOval(x-(xSize/2), y-(ySize/2), xSize, ySize);
            pastUsedCoOrds[0] = -1;
            pastUsedCoOrds[1] = -1;
        }
        else//If dragging mouse
        {
            if (pastUsedCoOrds[0] != -1 && press == false)
            {       
                test.drawLine(pastUsedCoOrds[0],pastUsedCoOrds[1],x,y);
                pastUsedCoOrds[0] = x;
                pastUsedCoOrds[1] =y;            
            }
            else
            {
                pastUsedCoOrds[0] = x;
                pastUsedCoOrds[1] =y;
            }
        }

        //both passed into CanvasZoom
        getBuffImg(drawing);
        getGra(gra);

        // revalidate();
        // paint(this.getGraphics());
        refresh();
    }

    /**
     * Sets location of the image x and y varibles
     * @param x X coordinate to set too
     * @param y y coordinate to set too
     */
    public void setLocation(int x, int y) 
    {
        img_x = x;
        img_y = y;
    }
    
    /**
     * Adds a new layer to the layers stack
     * @param layer The new layer to be added
     */
    public void addLayer(Layer layer) 
    {
        layers.add(layer);
    }


    /**
     * Fill tool - fill a contained space with a spcific colour, if not contiaind will fill whole canvas
     * @param x Starting x position of the fill tool, based off mouse position
     * @param y Starting y position of the fill too, based off mouse position
     * @param fillColor Colour to fill the space with 
     * @param startColor Colour to be replaced
     */
    public void fill(int x, int y, int fillColor, int startColor) {
        // Create a stack to store the coordinates of pixels to be filled
        Stack<Integer[]> stack = new Stack<Integer[]>();
        
        // Push the starting pixel onto the stack
        stack.push(new Integer[]{x, y});
    
        // Loop through the stack until it is empty
        while (!stack.empty()) {
            // Pop the next pixel from the stack
            Integer[] coords = stack.pop();
            int cx = coords[0];
            int cy = coords[1];
    
            // Check if the pixel is within the bounds of the image
            if (cx >= 0 && cx < drawing.getWidth() && cy >= 0 && cy < drawing.getHeight()) {
                // Check if the pixel is the same color as the starting pixel
                if (drawing.getRGB(cx, cy) == startColor) {
                    if (drawing.getRGB(cx, cy) != fillColor){
                        // Fill the pixel with the fill color
                        drawing.setRGB(cx, cy, fillColor);
        
                        // Push adjacent pixels onto the stack
                        stack.push(new Integer[]{cx+1, cy});
                        stack.push(new Integer[]{cx-1, cy});
                        stack.push(new Integer[]{cx, cy+1});
                        stack.push(new Integer[]{cx, cy-1});
                    }
                }
            }
        }
    }

    /**
     * EyeDrop tool - takes the colour of the clicked pixel and applies it to the brush
     * @param x the X position of the mouse
     * @param y the Y position of the mouse
     * @param toolbox The Colour Toolbox through which setting the brushes colour is accessed
     * @param brushToolbox The Brush Toolbox through which the mode is changed
     */
    public void eyeDrop(int x, int y, Colour_Toolbox toolbox, Brush_Toolbox brushToolbox)
    {
        Color c = new Color(drawing.getRGB(x,y));
        toolbox.newCustom(c.getRed(), c.getGreen(), c.getBlue());
        brushToolbox.changeMode("Paint");
    }

    /**
     * Draws shapes upon the canvas
     * @param e takes the MouseEvent variable
     * @param optionSelected var that takes the string variable from Shape_PopWin class
     * @param colour The colour of the shape to be drawn
     * @param size the size of the shape you want it to be scaled
     */
    public void drawShape(MouseEvent e, String optionSelected,Color colour, int size)
    {
        //instantiated the shapes
        Graphics2D g =(Graphics2D) drawing.createGraphics();
        g.setColor(colour);
        Shape_Circle c = new Shape_Circle();
        Shape_Rectangle r = new Shape_Rectangle();
        Shape_Triangle t = new Shape_Triangle();

        //choose which one to use
        switch(optionSelected)
        {
            case "Circle":
                c.insert(g, e, size);
                break;
            case "Square":
                r.insert(g, e, size);
                break;
            case "Triangle":
                t.insert(g, e, size);
                break;
        }
        getBuffImg(drawing);
        getGra(g);
        refresh();
    }


    /**
     * Chnages the background colour of the canvas
     * @param backgroundColor The colour the canvas will be 
     */
    public void changeBackgroundColour(Color backgroundColor)
    {
        this.backgroundColor = backgroundColor;
        this.setBackground(backgroundColor);
    }

    /**
     * refreshes the canvas
     */
    void refresh() 
    {    
        updateStack();
        for(int i =0; i< undoCounter-1;i++)//Updates stack after a undoing to keeps timeline
        {
            updateStack();
        }
        Graphics2D g2d = (Graphics2D) mainLayer.createGraphics();
        g2d.setBackground(backgroundColor);
        g2d.clearRect(0, 0, mainLayer.getWidth(), mainLayer.getHeight());

        for (int i = 0; i < layers.size(); i++) {
            g2d.drawImage(
                layers.get(i).getImg(), 
                layers.get(i).getHorizontalPos(), 
                layers.get(i).getVerticalPos(), 
                null
            );
        }
        
        paint(this.getGraphics());    
        getBuffImg(mainLayer);
        
    }


    /**
     * Returns the top most layer hovered over by the mouse
     * @param x Current x postion
     * @param y Current y position
     * @return The top most layer
     */
    public Layer getTopMostLayer(int x, int y) {
        for (int i = layers.size() - 1; i > 0; i--) {
            int layerX = layers.get(i).getHorizontalPos();
            int layerY = layers.get(i).getVerticalPos();
            Layer currentLayer = layers.get(i);
            
            if (layerX <= x  && x <= layerX + currentLayer.getImg().getWidth())
                if (layerY <= y && y <= layerY + currentLayer.getImg().getHeight())
                    return currentLayer;
        }
        return null;
    }



    /**
     * Paints to the canvas, using graphics provided
     * @param g The graphics to use
     */
    public void paint(Graphics g)
    {
        g.drawImage(mainLayer, 0, 0, null);
    }


    /**
     * Saves the buffred image as a png
     * @param saveAs A check to see the save type, if true it is a save as
     */
    public void save(boolean saveAs)
    {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=(Graphics2D)image.createGraphics();     
        this.paint(g2);

        if(saveAs == true || saveName == null) //Save As, or if a new file asks user to choose a save file
        {
            int result = fileChooser.showSaveDialog(null);//Asks user to choose file to save to and for file name
            if (result == JFileChooser.APPROVE_OPTION)
            {   
                File selectedFile = fileChooser.getSelectedFile();

                if(selectedFile.exists() == true)
                {
                    int doubleCheck = JOptionPane.showConfirmDialog(null,"The file chosen already exists, would you like to overwrite it? (WARNING - Doing so will permanently remove the original file!)","File Already Exists",JOptionPane.YES_NO_OPTION);
                    if(doubleCheck == JFileChooser.CANCEL_OPTION)
                    {
                        return;
                    }
                }
                saveName = selectedFile.getName();
            }

            else//If user does not approve saveas, will not save
            {
                return;
            }
        }

        //Ensure file name ends in .png
        if(saveName.endsWith(".png") == false)
        {
            saveName = saveName+".png";
        }

        try
        {
            ImageIO.write(image, "png", new File(saveName));
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());
        }
        g2.dispose();
    }

    
    /**
     * Opens a png file and sets it as the canvas 
     */
    public void openCanvas()
    {
        Graphics g = drawing.getGraphics();

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            try
            {
                mainLayer = ImageIO.read(selectedFile);
                Image scaledImg = mainLayer.getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_SMOOTH);
                g.drawImage(scaledImg,0,0,this);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        refresh();
    }

    /**
     * Mutator for the main drawing graphics (when paint method is called)
     * Used for zoom feature
     * @param gra The grapics to be stored
     */
    public void getGra(Graphics gra)
    {
        this.canvGra = gra;
    }

    /**
     * Accessor for the whole class 
     * Used for zoom feature
     * @return graphics object of the whole canvas
     */
    public Graphics getMainDrawingGraphics()
    {
        return canvGra;
    }

    /**
     * Mutator for the main drawing buffer image (when paint method is called)
     * Used for zoom feature
     * 
     * @param wholeCanv The buffered image to be saved to canvImg
     */
    public void getBuffImg(BufferedImage wholeCanv)
    {
        this.canvImg = wholeCanv;

    }

    /**
     * Accessor for the whole canvas buffered image (whole drawings)
     * Used for zoom feature
     * 
     * @return buffered image object of the whole canvas
     */
    public BufferedImage getMainImageGraphics()
    {
        return canvImg;
    }

   
   
   
   
    //Undo and Redo functions 
   
    /**
     * Updates the undoRedo stack
     */
    private void updateStack()
    {
        if(System.currentTimeMillis() - undoTime < 50)
        {
            return;
        }
        else
        {
            undoTime = System.currentTimeMillis();
            for(int i = undoSize-1 ;i>0;i--)
            {
                undoStack[i] = undoStack[i-1];
            }
            BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2=(Graphics2D)image.createGraphics();     
            this.paint(g2);

            undoStack[0] = image;
            if(undoStartTimer < undoSize)
            {
                undoStartTimer++;
            }
        }
    }

    /**
     * refreshes the canvas for undo/redo
     * @param draw The Image from the undostack to be draw on the canvas
     */
    private void undoRedoRefresh(BufferedImage draw) 
    {
        Graphics g = drawing.getGraphics();
        mainLayer = draw;
        Image scaledImg = mainLayer.getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledImg,0,0,this);
        Graphics2D g2d = (Graphics2D) mainLayer.createGraphics();
        g2d.setBackground(backgroundColor);
        g2d.clearRect(0, 0, mainLayer.getWidth(), mainLayer.getHeight());

        for (int i = 0; i < layers.size(); i++) {
            g2d.drawImage(
                layers.get(i).getImg(), 
                layers.get(i).getHorizontalPos(), 
                layers.get(i).getVerticalPos(), 
                null
            );
        }

        paint(this.getGraphics());
        getBuffImg(mainLayer);
    }

    /**
     * Sets the drawing bufferedImage back one on the stack
     */
    public void undo()
    {   
        if(undoCounter < undoStartTimer)
        {
            undoCounter++;
            if(undoCounter >= undoStartTimer)//rechecks undo to make sure no overflow errors occur with the stack
            {
                undoCounter--;
                return;
            }
            if(undoStack[undoCounter] == null)
            {
                return;
            }
            undoRedoRefresh(undoStack[undoCounter]);
        }
    }

    /**
     * Sets the drawing bufferedImage back on the stack
     */
    public void redo()
    {    
        if(undoCounter > 0)
        {
            undoCounter--;
            undoRedoRefresh(undoStack[undoCounter]);
        } 
    }

    /**
     * Gets the main Layer ofin the CanvasM
     * @return Returns the main layer 
     */
    public BufferedImage getMainerLayer() {
        return mainLayer;
    }



}

