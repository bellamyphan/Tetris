// Tetris.java: This is the frame of the Tetris game.

import java.awt.*;
import java.awt.event.*;

// This is the frame.
public class Tetris extends Frame {
    
    // Constructor.
    public Tetris() {
        // Set the frame name (window name).
        super("Tetris");
        // Close the window at the window meny "X - button".
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        // Initialize the window size.
        setSize(1000, 850);
        // Add a canvas in this frame.
        add("Center", new TetrisCv());
        // Set visible.
        setVisible(true);
        
        System.out.println("Call frame constructor.");
    }
    
}
