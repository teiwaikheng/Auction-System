/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;

/**
 *
 * @author User
 */
public class MyGUIProgram extends Frame{

   private Label lblInput;     // Declare input Label
   private Label lblOutput;    // Declare output Label
   private TextField tfInput;  // Declare input TextField
   private TextField tfOutput; // Declare output TextField
   private int sum = 0;        // Accumulated sum, init to 0
 
   // Constructor to setup the GUI components and event handlers
   public MyGUIProgram() {
      setLayout(new FlowLayout());
         // "super" Frame (a Container) sets layout to FlowLayout, which arranges
         // the components from left-to-right, and flow to next row from top-to-bottom.
 
      lblInput = new Label("Enter an Integer: "); // Construct Label
      add(lblInput);               // "super" Frame adds Label
 
      tfInput = new TextField(10); // Construct TextField
      add(tfInput);                // "super" Frame adds TextField
 
      tfInput.addActionListener((ActionListener) this);
         // tfInput is the source object that fires ActionEvent when entered.
         // The source add "this" instance as an ActionEvent listener, which provides
         //  an ActionEvent handler called actionPerformed().
         // Hitting enter on tfInput invokes actionPerformed().
 
      lblOutput = new Label("The Accumulated Sum is: ");  // allocate Label
      add(lblOutput);               // "super" Frame adds Label
 
      tfOutput = new TextField(10); // allocate TextField
      tfOutput.setEditable(false);  // read-only
      add(tfOutput);                // "super" Frame adds TextField
 
      setTitle("AWT Accumulator");  // "super" Frame sets title
      setSize(350, 120);  // "super" Frame sets initial window size
      setVisible(true);   // "super" Frame shows
   }
   
}
