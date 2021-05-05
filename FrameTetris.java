// FrameTetris.java: This is the frame of the Tetris game.

import java.awt.*;
import java.awt.event.*;

// This is the frame.
public class FrameTetris extends Frame {
    
    // Constructor for the frame.
    public FrameTetris() {
        // Set the frame name (window name).
        super("Tetris - Group 7");
        // Close the window at the window menu "X - button".
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	// Output message to the console.
                System.out.println("Exit Frame.");
            	System.exit(0);
            }
        });
        // Initialize the window size based on the current screen size.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        // Add a canvas in this frame.
        add("Center", new CanvasTetris());
        // Set visible.
        setVisible(true);
    }
    
}
