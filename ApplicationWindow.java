import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.util.*;
import java.awt.MouseInfo;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.PlainDocument;

import java.io.*;

/**
 * This class creates the window in which the application will main application will run
 *@author Cameron P Rashidi,Thomas L French, Yudai Yamase, Conor James, Wan Naufal
 */
public class ApplicationWindow implements MouseListener, MouseMotionListener, KeyListener
{

    /**
     * Offset of canvas co-ords used for user display of co-ords
     */
    private int[] canvasOffset = {180,69};

    /**
     * The frame in which the whole program will be run
     */
    private JFrame applicationWindow = new JFrame();

    /**
     * Toolbox for tools stored at top of the screen
     */
    private JPanel top_toolbox = new JPanel(new GridLayout(1,10));

    /**
     * Toolbar used for left hand side tool storage
     */
    private JPanel side_toolbox = new JPanel(new GridLayout(10,1,0,0));

    /**
     * Mode for current interaction with the canvas
     */
    private String mode ;
    /**
     * Current brush colour
     */
    private Color brushColour;

    /**
     * Current brush type
     */
    private String brushType; 

    /**
     * The colour which will be the backgrond of the canvas  upon creation
     */
    private Color background = new Color(255,255,255);    

    /**
     * The canvas in which all user drawing/art will take place
     */
    private CanvasM art = new CanvasM(background,1400 - canvasOffset[0],950 - canvasOffset[1]);
    /**
     * The Toolbox where colours, both normal and custom are stored
     */
    private Colour_Toolbox toolboxc = new Colour_Toolbox("TBC", art);

    /**
     * Creates ribbonmenu object
     */
    private RibbonMainMenu ribbonMenu = new RibbonMainMenu(this, toolboxc, art);

    /**
     * Creates right button menu
     */
    private RightClickMenu rightButtonMenu = new RightClickMenu(this);

    /**
     * Toolbox to storw brushs and similar tools
     */
    private Brush_Toolbox BrushToolBox = new Brush_Toolbox("Brush ToolBox");
    
    /**
     * Toolbox used for import image
     */
    private ImportImg_Toolbox toolboxi = new ImportImg_Toolbox("TBI", art);

    /**
     * Toolbox used to store brush settings
     */
    private Brush_Setting_Toolbox brushSettingToolbox = new Brush_Setting_Toolbox("TBS");

    /**
     * Stoage spaced for orginal x and y of mouse
     */
    private int original_distance_x, original_distance_y;

    /**
     * Current x and y of mouse
     */
    private int mouseX, mouseY;
    /**
     * Image storage space for input image 
     */
    private BufferedImage activeImg;

    /**
     * Button for user to active select image tool
     */
    private SelectImg selectImgButton = new SelectImg("Select Image");

    /**
     * Sets if mouse input if drawing or loaction clicking
     */
    private Boolean locationClick = false;

    /**
     * Fill shape tool 
     */
    private FillShape fillShape = new FillShape("Fill Shape");

    /**
     * The Button used to active the fill function
     */
    private JButton backFill = new JButton("Change Background");

    //Mouse coords varibles 
    /**
     * Text area to show user mouse position
     */
    private JTextArea mouseCoOrds = new JTextArea("Mouse Position:");

    /**
     * Boolean to see if mouse is within application window, if so will track mouse
     */
    private Boolean insideWindow = false;

    /**
     * Font used by mouseCoOrds text area
     */
    private Font textFont = new Font("textFont",Font.PLAIN,15);

    /**
     * Storage for current layer
     */
    private Layer currentLayer = null;

    /**
     * Icon used for user taskbar
    */
    private ImageIcon mainIcon;

    /**
     * current mouse position of x, used for user display of mouse coOrds
     */
    private int currentX;

    /**
     * current mouse position of y, used for user display of mouse coOrds
     */
    private int currenty;

    /**
     * The constructor for the main application
     */
    public ApplicationWindow()
    {    
        //Create basic window and settings
        createUI();
        applicationWindow.setTitle("MemeMachine");
        applicationWindow.setSize(1500, 1000);
        applicationWindow.setResizable(false);
        applicationWindow.setVisible(true);
        applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationWindow.setLayout(new BorderLayout());
        applicationWindow.setJMenuBar(ribbonMenu);//Adds ribbonmenu to application window

        //CanvasM adding and adjusments 
        Colour CanvasColour = new Colour();
        applicationWindow.add(art,BorderLayout.CENTER);//Adds CanvasM to JFrame
        art.addMouseListener(this);//Added clicking to CanvasM
        art.addMouseMotionListener(this);
        art.addKeyListener(this);

        //Other
        mainIcon = new ImageIcon("button_icons/mainWindowIcon.png");//Image used for Icon in system tray
        applicationWindow.setIconImage(mainIcon.getImage());
        mouseCoOrds.setBackground(side_toolbox.getBackground());
        mouseCoOrds.setFont(textFont);
    }




    //Mouse Listener Functions

    /**
     * Calls the right click menu on right mouse button click
     * @param e The mouse event which  trigged the function
     */
    public void mouseClicked(MouseEvent e)
    {
        //Calls the right click menu
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            rightButtonMenu.callMenu(e.getX(), e.getY());
        }
        else
        {   
            //checks if paste is active, if so pastes and returns 
            if(rightButtonMenu.cutAndPaste.getFeat() == "Paste" 
            && rightButtonMenu.cutAndPaste.isON() == true)
            {
                rightButtonMenu.cutAndPaste.getMouseCoordinate(e.getX(), e.getY());
                rightButtonMenu.cutAndPaste.paste();
                return;
            }

            if (selectImgButton.isON() == false){
                locationClick = false;
                useCanvas(e,false);
            }
            this.currentLayer = art.getTopMostLayer(e.getX(), e.getY());
            if (this.currentLayer != null){
                original_distance_x = e.getX() - currentLayer.x;
                original_distance_y = e.getY() - currentLayer.y;
            }
        }

        

    }
    /**
     * Overwriting function for mouse Entered
     * @param e The mouse event which  trigged the function
     */
    public void mouseEntered(MouseEvent e)
    {
        insideWindow = true; 
    }
    /**
     * Overwriting function for mouse exited
     * @param e The mouse event which  trigged the function
     */
    public void mouseExited(MouseEvent e)
    {
        insideWindow = false;
    }

    /**
     * One of the main functions used for mouse interations with the canvas, primairly for single click interactions 
     * @param e The mouse event which  trigged the function
     */
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            return;
        }
        else
        {
            // sets location for canvasM
            locationClick = true;
        }

        //if select option is choosen
        if (rightButtonMenu.cutAndPaste.isON() == true && rightButtonMenu.cutAndPaste.getFeat() == "Cut")
        {
            rightButtonMenu.cutAndPaste.getMouseCoordinate(e.getX(), e.getY());
        }
        
    }

    /**
     * Overwriting function for mouse released 
     * @param e The mouse event which  trigged the function
     */
    public void mouseReleased(MouseEvent e)
    {
        if(rightButtonMenu.cutAndPaste.isON() == true && rightButtonMenu.cutAndPaste.getFeat() == "Cut")
        {
            rightButtonMenu.cutAndPaste.cut();
        }
    }

    /**
     * Overwriting function for mouse moved, used to set coords of mosue for user to see
     * @param e The mouse event which  trigged the function
     */
    public void mouseMoved(MouseEvent e)
    {
        if(insideWindow == true)
        {
            mouseCoOrds.setText("Mouse Position: \n X = "+(e.getX() - canvasOffset[0])+" \n Y = "+(e.getY() - canvasOffset[1]));
            currentX = e.getX();
            currenty = e.getY();
        }

    }

    /**
     * one of the main functions used for mouse interations with the canvas, primairly for dragging interactions 
     * @param e The mouse event which  trigged the function
     */
    public void mouseDragged(MouseEvent e)
    {   
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            return;
        }
        if (selectImgButton.isON() == true){
            if (currentLayer == null) return;
            // Update the coordinates of the image based on the mouse's movement
            // art.setLocation(e.getX(), e.getY());
            
            currentLayer.updateCoords(e.getX()-original_distance_x, e.getY()-original_distance_y);
            art.refresh();
        }

        else if(rightButtonMenu.cutAndPaste.isON() == true)
        {
            if(rightButtonMenu.cutAndPaste.getFeat() == "Cut")
            {
                rightButtonMenu.cutAndPaste.updateEndPoint(e.getX(), e.getY());
            }
        }

        else
        {
            useCanvas(e,true);
        }
    }


    //Keyboard Listener Functions

    /**
     * Override of key typed function
     * @param e Key that trigged the function
     */
    public void keyTyped(KeyEvent e)
    {

    }

     /**
     * Override of key released function
     * @param e Key that trigged the function
     */
    public void keyReleased(KeyEvent e)
    {

    }

     /**
     * Override of key pressed function, used for keyboard shortcuts
     * @param e Key that trigged the function
     */
    public void keyPressed(KeyEvent e)
    {
        if(e.isControlDown())//Checks if control key is pressed down for shortcut
        {
            switch(e.getKeyCode())//checks users other keystroke to see if complete shortcut has been typed
            {
                case KeyEvent.VK_S://Save canvas
                    saveCanvas(false);
                    break;
                case KeyEvent.VK_D://Save as canvas
                    saveCanvas(true);
                    break;
                case KeyEvent.VK_Z:// undo canvas
                    undo();
                    break;
                case KeyEvent.VK_Y:// redo canvas
                    redo();
                    break;
                case KeyEvent.VK_C://Cuts from canvas
                    rightButtonMenu.cut();
                    break;
                case KeyEvent.VK_V://pastes to canvas
                rightButtonMenu.paste();
                    rightButtonMenu.cutAndPaste.getMouseCoordinate(currentX, currenty);
                    rightButtonMenu.cutAndPaste.paste();
                    break;
                    
                
            }

        }


    }





    /**
     * Allows user to effect the canvas based upon thier current mode
     * @param e The current position of the mouse
     * @param movement Used to determine if the mouse is being dragged or jsut clicked
     */
    public void useCanvas(MouseEvent e,boolean movement)
    {   
        if (fillShape.isON()){
            // try to fill shape        

            int x = e.getX();
            int y = e.getY();
            int startColor = art.getMainerLayer().getRGB(x, y);
            art.fill(x, y, toolboxc.returnCol().getRGB(), startColor);
            art.refresh();
            fillShape.setStatus(false);
            fillShape.setBackground(fillShape.getDefaultColor());

        }
        //If not filling shape
        else{
            mode = BrushToolBox.returnmode();
            brushColour = toolboxc.returnCol();
            brushType = brushSettingToolbox.getBrushType();
            switch(mode)//How Canvas is used
            {
                case "Paint":
                    art.paint(e.getX(),e.getY(),brushColour,brushSettingToolbox.getBrushSize(),brushSettingToolbox.getBrushSize(),movement,brushSettingToolbox.getBrushDash(),locationClick,brushType); 
                    break;
                case "Eraser":
                    art.paint(e.getX(),e.getY(),background,brushSettingToolbox.getBrushSize(),brushSettingToolbox.getBrushSize(),movement,brushSettingToolbox.getBrushDash(),locationClick,brushType); 
                    break;
                case "Shape":
                    art.drawShape(e, BrushToolBox.returnShape(),brushColour, brushSettingToolbox.getBrushSize());
                    break;
                case "Pencil":
                    art.paint(e.getX(),e.getY(),brushColour.darker(),brushSettingToolbox.getBrushSize()/3,brushSettingToolbox.getBrushSize()/3,movement,brushSettingToolbox.getBrushDash(),locationClick,brushType);
                    break;
                case "EyeDrop":
                    art.eyeDrop(e.getX(),e.getY(),toolboxc, BrushToolBox);
                    break;

            }
        }
         locationClick = false;       
    }
    /**
     * Adds the UI of the main window to the window 
     */
    private void createUI()
    { 
        applicationWindow.add(top_toolbox,BorderLayout.NORTH);
        applicationWindow.add(side_toolbox,BorderLayout.WEST);
        top_toolbox.add(BrushToolBox.brushPanel);
        top_toolbox.add(brushSettingToolbox.brushSetting.ToolSqr);
        top_toolbox.add(toolboxc.colourPanel);
        side_toolbox.add(toolboxi.importImg.ToolSqr);
        side_toolbox.add(selectImgButton);
        side_toolbox.add(fillShape);
        /* side_toolbox.add(backFill);
        backFill.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ColourWheel wheel = new ColourWheel(toolboxc, art, false);
                wheel.WheelThread();
            }
        });;*/



        side_toolbox.add(mouseCoOrds);
    }

    /**
     * Sends a save request to the canvas
     * @param saveType A check to see if the save is a regular save (false), or a save as (true)
     */
    public void saveCanvas(boolean saveType)
    {
        art.save(saveType);
    }

    /**
     * Sends the ooen request to the canvas
     */
    public void openCanvas()
    {
        art.openCanvas();
    }

    /**
     * Setter method for canvas class (used in RibbonMainMenu.java)
     * @param art Sets canvas to that given
     */
    public void setCanvasM(CanvasM art)
    {
        this.art = art;
    }
  
    /**
     * Getter method for canvas class (used in  RibbonMainMenu.java)
     * @return Returns the CanvasM
     */
    public CanvasM getCanvasM() 
    {
        return art;
    }

    /**
     * Calls undo method for canvas class (used in RibbonMainMenu.java)
     */
    public void undo()
    {
        art.undo();
    }

    /**
     * Calls redo method for canvas class (used in RibbonMainMenu.java)
     */
    public void redo()
    {
        art.redo();
    }

}

