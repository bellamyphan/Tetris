// FrameTetris.java: This is the frame of the Tetris game.

import java.awt.*;
import java.awt.event.*;

// This is the frame.
public class FrameTetris extends Frame {
    
    // Constructor.
    public FrameTetris() {
        // Set the frame name (window name).
        super("Tetris - Group 7");
        // Close the window at the window menu "X - button".
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        // Output message to the console.
        System.out.println("Call frame constructor.");
        
        // Initialize the window size.
        Dimension d = getSize();
        setSize(d.width - 1, d.height - 1);
        System.out.println("d.width = " + d.width);
        System.out.println("d.height = " + d.height);
        // Add a canvas in this frame.
        add("Center", new CanvasTetris());
        // Set visible.
        setVisible(true);
        
        
    }
    
}
