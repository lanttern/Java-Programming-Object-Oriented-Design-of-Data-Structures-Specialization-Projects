package guimodule;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.TextArea;

import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class button extends JPanel
{  // Instantiate a button and a textarea.
   private JButton pushMe = new JButton();
   private JTextArea ta = new JTextArea("", 5, 15);

   public button()
   {  // Set the attributes for the buttons
      pushMe.setBackground(Color.red);
      pushMe.setLabel("Push Me");
      pushMe.addActionListener(new ButtonListener());

      // Add all the widgets to the applet
      this.getRootPane().add(pushMe);
      this.getRootPane().add(ta);
   }

   // The listener for the button.
   private class ButtonListener implements ActionListener
   {  public void actionPerformed(ActionEvent evt)
      {  if (ta.getText().length() > 0)
         {  ta.append("\n");  }
         ta.append("Ouch!");
      }
   }
}