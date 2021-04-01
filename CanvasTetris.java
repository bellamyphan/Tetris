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
	// (Starting Screen) "PLAY TETRIS" coordinates.
	int x1, y1, x2, y2;
	// (Starting Screen) "HIGH SCORES" coordinates.
	int x3, y3, x4, y4;
	// (Starting Screen) "EXIT" coordinates.
	int x5, y5, x6, y6;
	
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
		// Message to the console.
		System.out.println("Print the Starting Screen.");
		
		// Draw the boundary of this canvas.
		g.drawRect(xCenter - (int) (11 * unit), yCenter - (int) (11 * unit), (int) (22 * unit), (int) (22 * unit));
		
		// Set up the Font and the Size for printing strings.
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (2 * unit)));
		
		// Paint the PLAY TETRIS BOX and store the coordinates.
		x1 = xCenter - (int) (7 * unit);
		y1 = yCenter - (int) (9 * unit);
		x2 = xCenter - (int) (7 * unit) + (int) (14 * unit);
		y2 = yCenter - (int) (9 * unit) + (int) (5 * unit);
		g.drawRect(x1, y1, x2 - x1, y2 - y1);
		//g.drawRect(xCenter - (int) (7 * unit), yCenter - (int) (9 * unit), (int) (14 * unit), (int) (5 * unit));
		g.drawString("PLAY TETRIS", (int) (xCenter - 6.5 * unit), (int) (yCenter - 5.7 * unit)); 
		
		// Paint the HIGH SCORE BOX.
		x3 = xCenter - (int) (7 * unit);
		y3 = yCenter - (int) (3 * unit);
		x4 = xCenter - (int) (7 * unit) + (int) (14 * unit);
		y4 = yCenter - (int) (3 * unit) + (int) (5 * unit);
		g.drawRect(x3, y3, x4 - x3, y4 - y3);
		//g.drawRect(xCenter - (int) (7 * unit), yCenter - (int) (3 * unit), (int) (14 * unit), (int) (5 * unit));
		g.drawString("HIGH SCORES", (int) (xCenter - 6.9 * unit), (int) (yCenter + 0.2 * unit)); 
		
		// Paint the EXIT BOX.
		x5 = xCenter - (int) (7 * unit);
		y5 = yCenter + (int) (3 * unit);
		x6 = xCenter - (int) (7 * unit) + (int) (14 * unit);
		y6 = yCenter + (int) (3 * unit) + (int) (5 * unit);
		g.drawRect(x5, y5, x6 - x5, y6 - y5);
		//g.drawRect(xCenter - (int) (7 * unit), yCenter + (int) (3 * unit), (int) (14 * unit), (int) (5 * unit));
		g.drawString("EXIT", (int) (xCenter - 2.5 * unit), (int) (yCenter + 6.2 * unit)); 
		
		// Restore the Font and Size setting.
		g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (20)));
		
		/*// Scaling.
		g.drawLine(xCenter, 0, xCenter, maxY);*/
	}
}
