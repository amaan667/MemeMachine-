import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *Creates a popup menu, should be used for right click functionality
 * 
 * @author Thomas l French, Wan Naufal
 */
public class RightClickMenu implements ActionListener
{
    /**
     * The pop-up window
     */
    private JFrame popup = new JFrame();

    /**
     * The button for the Cut function
     */
    private JButton cut = new JButton("Cut");
    /**
     * The button for the Paste function
     */
    private JButton paste = new JButton("Paste");
    /**
     * The button for the Undo function
     */
    private JButton undo = new JButton("Undo");
    /**
     * The button for the redo function
     */
    private JButton redo = new JButton("Redo");

    //modified the constructor for cut and paste
    /**
     * The Cut and Paste functionality
     */
    CutPaste cutAndPaste;
    /**
     * The main window
     */
    ApplicationWindow mainWindow;

    /**
     * Construtor for right click menu
     * @param window The ApplicationWindow in which this object will be held, used to send function requests to the ApplicationWindow
     */
    public RightClickMenu(ApplicationWindow window)
    {
        popup.setSize(120, 140);
        popup.setVisible(false);
        popup.setLayout(new GridLayout(4,1));
        popup.setUndecorated(true);
        popup.add(cut);
        popup.add(paste);
        popup.add(undo);
        popup.add(redo);
        cut.addActionListener(this);
        paste.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);

        this.mainWindow = window;
        cutAndPaste = new CutPaste(mainWindow.getCanvasM());
    }

    /**
     * Allows the menu to be called when the right button is clicked 
     * @param width Where in the x axis the menu should appear
     * @param height Where in the y axis the menu should appear
     */
    public void callMenu(int width,int height)
    {
            popup.setLocation(width+(popup.getWidth()/2),height+(popup.getHeight()/2));
            popup.setVisible(true);
    }

    /**
     * Allows for the menu to be closed
     */
    public void closeMenu()
    {
        popup.setVisible(false);
    }

    /**
     * Closes the menu and perfoms the requested action by the user
     * @param e Which option on the menu is chosen
     */
    public void actionPerformed(ActionEvent e)
    {
        closeMenu();
        switch(e.getActionCommand())
        {
            case "Cut":
               cut();
                break;
            case "Paste":
            paste();
                break;
            case "Undo":
                mainWindow.undo();
                break;
            case "Redo":
                mainWindow.redo();
                break;
            default:
                System.out.println("Unsupported rcm action");
                break;
        }
    }

    /**
     * Initiats a cut action for the user in the canvas
     */
    public void cut()
    {
        if (cutAndPaste == null)
        {
            cutAndPaste = new CutPaste(mainWindow.getCanvasM());
            cutAndPaste.setFeat("Cut");
        }

        else
        {
            cutAndPaste.setFeat("Cut");
        }

    }

    /**
     * Initiats a paste action for the user in the cavas based on the last cut
     */
    public void paste()
    {
        if (cutAndPaste == null)
        {
            cutAndPaste = new CutPaste(mainWindow.getCanvasM());
            cutAndPaste.setFeat("Paste");
        }

        else
        {
            try
            {
                cutAndPaste.setFeat("Paste");
            }
            catch(Exception u)
            {
                System.out.println("Paste error: " + u);
            }
               
        }
    }

}