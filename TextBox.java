import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Class to create text boxes - non functional code, is not implmented by program
 */
public class TextBox extends JButton implements ActionListener {

/**
 * Constructor for the class
 * @param label Sets text of text box to label
 */
public TextBox(String label) {  
   ImageIcon selectimage_icon = new ImageIcon("button_icons/TextBox_icon.png"); 
    this.setIcon(selectimage_icon);

    this.setText(label);
    addActionListener(this);
   }
   
  
/**
 * Override of action perfored to ask user to enter text
 * @param event Triggerer of the function
 */
@Override
public void actionPerformed(ActionEvent event) {
  JFrame frame = new JFrame();
  String text = (String)JOptionPane.showInputDialog(frame, "Enter Text"); 
  JTextField Textfield = new JTextField(text);
//applicationWindow.add(Textfield);
}

  }

