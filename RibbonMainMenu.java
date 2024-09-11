import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 * This class is used to create the ribbon menu at the top of the application window
 * @author Thomas L French, Cameron Rashidi, Wan Naufal, Yudai Yamase, Conor James
 */
public class RibbonMainMenu extends JMenuBar implements ActionListener
{
    //File menu objects
    /**
     * The File drop-down menu
     */
    private JMenu fileMenu = new JMenu("File");
    /**
     * The button for opening a new file
     */
    private JMenuItem newFileButton = new JMenuItem("New");
    /**
     * The button for saving a file
     */
    private JMenuItem saveButton = new JMenuItem("Save File");
    /**
     * The button for saving as a file
     */
    private JMenuItem saveAsButton = new JMenuItem("Save File As");
    /**
     * The button for opening a saved file
     */
    private JMenuItem openButton = new JMenuItem("Open File");
    /**
     * The button for exiting the application
     */
    private JMenuItem exitButton = new JMenuItem("Exit");
    //edit menu objects
    /**
     * The Edit drop-down menu
     */
    private JMenu editMenu = new JMenu("Edit"); 
    /**
     * The button for undoing an action
     */
    private JMenuItem undoButton = new JMenuItem("Undo");
    /**
     * The button for redoing an action
     */
    private JMenuItem redoButton = new JMenuItem("Redo");
    /**
     * The button for opening the colour wheel
     */
    private JMenuItem colourWheelButton = new JMenuItem("Colour Wheel");
    //insert menu objects
    /**
     * The Insert drop-down menu
     */
    private JMenu insertMenu = new JMenu("Insert");
    /**
     * The button for choosing an image to insert
     */
    private JMenuItem chooseImageButton = new JMenuItem("Choose Image");

    //view objects
    /**
     * The View drop-down menu
     */
    private JMenu viewMenu = new JMenu("View");
    /**
     * The button for zooming in
     */
    private JMenuItem zoomIn = new JMenuItem("Zoom In");
    /** The button for zooming out */
    private JMenuItem zoomOut = new JMenuItem("Zoom Out");
    /**
     * The amount the application zooms in by
     */
    private double zoomVal = 1.1;
    
    /**
     * The canvas
     */
    private CanvasM canvas;


    /**
     * The main window
     */
    private ApplicationWindow mainWindow;
    /**
     * The zoom functionality
     */
    private CanvasZoom zoomFunction;
    /**
     * The colour toolbox
     */
    private Colour_Toolbox toolboxc;

    /**
     * Constructor for the Ribbon Menu
     * @param mainWindow The main Application window in which the ribbon menu resides
     * @param toolboxc The colour toolbox used by the ribbon menu
     * @param canvas The CanvasM used by the mainWindow Application
     */
    public RibbonMainMenu(ApplicationWindow mainWindow, Colour_Toolbox toolboxc, CanvasM canvas)
    {
        this.add(fileMenu);
        populateFileMenu();

        this.add(editMenu);
        populateEditMenu();

        this.add(insertMenu);
        populateInsertMenu();

        this.add(viewMenu);
        populateViewMenu();
        this.canvas = canvas;

        this.mainWindow = mainWindow;
        this.toolboxc = toolboxc;

        this.mainWindow = mainWindow;
    }

    /**
     * Populates the File menu with its options 
     */
    private void populateFileMenu()
    {
        ImageIcon file_image = new ImageIcon("button_icons/file_image.png"); // Creates imageicon
        fileMenu.add(newFileButton);
        fileMenu.setIcon(file_image);
        newFileButton.addActionListener(this);
        fileMenu.add(saveButton);
        saveButton.addActionListener(this);
        fileMenu.add(saveAsButton);
        saveAsButton.addActionListener(this);
        fileMenu.add(openButton);
        openButton.addActionListener(this);
        fileMenu.add(exitButton);
        exitButton.addActionListener(this);
    }

    /**
     * Populates the Edit menu with its options 
     */
    private void populateEditMenu()
    {

        ImageIcon undo_image = new ImageIcon("button_icons/undo_image.png");
        ImageIcon redo_image = new ImageIcon("button_icons/redo_image.png");
        editMenu.add(undoButton);  
        undoButton.addActionListener(this);
        undoButton.setIcon(undo_image);
        editMenu.add(redoButton);
        redoButton.addActionListener(this);
        redoButton.setIcon(redo_image);
        editMenu.add(colourWheelButton);
        colourWheelButton.addActionListener(this);
    }

    /**
     * Populates the Insert menu with its options 
     */
    private void populateInsertMenu()
    {
        insertMenu.add(chooseImageButton);
        chooseImageButton.addActionListener(this);
    }

    /**
     * Populates the View menu with its options 
     */
    private void populateViewMenu()
    {
        viewMenu.add(zoomIn);
        zoomIn.addActionListener(this);
        
        viewMenu.add(zoomOut);
        zoomOut.addActionListener(this);
    }



    /**
     * Triggers if a button is clicked
     * @param e Button that triggered the action event 
     */
    public void actionPerformed(ActionEvent e)
    {

        switch(e.getActionCommand())
        {
            case "New"://Resets Canvas 
            ApplicationWindow applicationWindow = new ApplicationWindow();
                break;

            case "Save File"://Saves canvas to currently selected file
                mainWindow.saveCanvas(false);
                break;

            case "Save File As"://Saves canvas to a user selected file
                mainWindow.saveCanvas(true);
                break;

            case "Open File"://Opens a user selected file and sets the canvas to it
                mainWindow.openCanvas();
                break;

            case "Exit" ://Kills program
                System.exit(0);
                break;

            case "Undo":
            mainWindow.undo();
                break;
            
            case "Redo":
            mainWindow.redo();
                break;

            case "Colour Wheel"://Calls the colour wheel and if a new colour, addis it to the custome colour array
                ColourWheel colourWheel = new ColourWheel(toolboxc, canvas, true);
                colourWheel.WheelThread();
               
            case "Zoom In":
                //pass your canvas here from ApplicationWindow object
                if (zoomFunction == null)
                {
                    zoomFunction = new CanvasZoom(mainWindow.getCanvasM());
                    zoomFunction.setZoom(zoomFunction.getZoom() * zoomVal);
                }

                else
                {
                    zoomFunction = new CanvasZoom(mainWindow.getCanvasM());
                    zoomFunction.setZoom(zoomFunction.getZoom() * zoomVal);
                }
                break;
            
            case "Zoom Out":
                //pass your canvas here from ApplicationWindow object
                if (zoomFunction == null)
                {
                    zoomFunction = new CanvasZoom(mainWindow.getCanvasM());
                    zoomFunction.setZoom(zoomFunction.getZoom() / zoomVal);
                }

                else
                {
                    zoomFunction = new CanvasZoom(mainWindow.getCanvasM());
                    zoomFunction.setZoom(zoomFunction.getZoom()/ zoomVal);
                }
                break;
            
            case "Choose Image":
                ImportImg_Toolbox importImg = new ImportImg_Toolbox(getName(), canvas);
                importImg.getImg();
                break;

            default:
                System.out.println("Menu Item requires  function code in the Ribbon Main Menu switch");

        }
    }
}
