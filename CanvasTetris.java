// CanvasTetris.java: This is the canvas.

import java.awt.*;

// This is the canvas.
public class CanvasTetris extends Canvas {
	
    // Hold the screen mode.
	// "0": Starting screen.
	// "1": Playing screen.
	// "2": Summary screen (After losing the game)
	int screenMode;
	// Hold device coordinates.
	int maxX, maxY, xCenter, yCenter, minMaxXY;
	// Hold the total size of the canvas based on the minMaxXY (Isotropic Mapping Mode).
	float side;
	// Hold the unit of the canvas (22 x 22 blocks) based on the side.
	float unit;
	
	// Default constructor.
	public CanvasTetris() {
		// First thing to do is that print the "Starting Screen" - "Menu".
		screenMode = 0;
	}
	
	// Initialize the graphics.
	public void initgr() {
		// Get dimension of the canvas.
		Dimension d = getSize();
		// Get maxX and maxY.
		maxX = d.width - 1;
		maxY = d.height - 1;
		// Get minMaxXY for "Isotropic Mapping Modes".
		minMaxXY = Math.min(maxX, maxY);
		// Get device center coordinates.
		xCenter = maxX / 2;
		yCenter = maxY / 2;
		// Get the side for this canvas.
		// Use 99% of the canvas to paint.
		side = 0.99F * minMaxXY;
		// Device the paintable canvas area into 22x22 blocks, each block has size of 1 unit.
		unit = side / 22;
	}
	
	// Paint method of this canvas.
	public void paint(Graphics g) {
		// Initialize the graphics.
		initgr();
		
		// Paint the canvas based on the ScreenMode.
		switch (screenMode) {
		case 0:
			// Staring Screen.
			paintMenu(g);
			break;
		case 1:
			// Do something here.
			break;
		case 2:
			// Do something here.
			break;
		default:
			// Do something here.
		}
	}
	
	// Starting Screen.
	// Goal: Print the menu with those options: Play, HighScores, Quit.
	public void paintMenu(Graphics g) {
		// Draw the boundary of this canvas.
		g.drawRect(xCenter - (int) (11 * unit), yCenter - (int) (11 * unit), (int) (22 * unit), (int) (22 * unit));
		// Paint the PLAY BOX.
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		g.drawRect(xCenter - (int) (7 * unit), yCenter - (int) (9 * unit), (int) (14 * unit), (int) (5 * unit));
		// Paint the HIGH SCORE BOX.
		g.drawRect(xCenter - (int) (7 * unit), yCenter - (int) (3 * unit), (int) (14 * unit), (int) (5 * unit));
		// Paint the EXIT BOX.
		g.drawRect(xCenter - (int) (7 * unit), yCenter + (int) (3 * unit), (int) (14 * unit), (int) (5 * unit)); 
	}
}
