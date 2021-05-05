// CanvasTetris.java: This is the canvas.

import java.awt.*;
import java.awt.event.*;

// This is the canvas.
public class CanvasTetris extends Canvas implements KeyListener {
	
    // Hold the screen mode.
	// "0": Starting screen.
	// "1": Playing screen.
	// "2": Summary screen (After losing the game).
	// "3": High Score table.
	int screenMode;
	
	// Hold device coordinates.
	int maxX, maxY, xCenter, yCenter, minMaxXY;
	// Hold the total size of the canvas based on the minMaxXY (Isotropic Mapping Mode).
	float side;
	// Hold the unit of the canvas (22 x 22 blocks) based on the side.
	float unit;
	// Mouse coordinates.
	int mouseX, mouseY;
	
	// (Starting Screen) "PLAY TETRIS" coordinates.
	int x1, y1, x2, y2;
	// (Starting Screen) "HIGH SCORES" coordinates.
	int x3, y3, x4, y4;
	// (Starting Screen) "EXIT" coordinates.
	int x5, y5, x6, y6;

	// Default constructor for this canvas.
	public CanvasTetris() {
		// First thing to do is that print the "Starting Screen" - "Menu".
        // Set the screenMode = 0.
		screenMode = 0;
		
		// Add KeyListener to this Canvas.
		// Handle KeyEvents below.
		addKeyListener(this);
		
		// Add MouseListener to this Canvas.
		addMouseListener(new MouseAdapter() {
			// Record the MousePressed event.
    		public void mousePressed(MouseEvent e) {
    			// Get the coordinate of the mouse if it is pressed.
                // Use the mouse coordinates to determine which boxes is clicked or none of boxes are clicked.
    			mouseX = e.getX();
    			mouseY = e.getY();
    			// Message to the console.
    			// System.out.println("User pressed the mouse. (" + mouseX + ", " + mouseY + ")");
    			// In the "Starting Screen".
    			// Check if the the user choose 1 of 3 boxes, Play Tetris, High Scores, Exit.
    			if (screenMode == 0) {
    				// Check for box EXIT.
    				if (x5 < mouseX && mouseX < x6 && y5 < mouseY && mouseY < y6) {
    					// Message to the console.
    					System.out.println("User hit EXIT button.");
    					System.out.println("Exit Tetris.");
    					// Exit the program.
    					System.exit(0);
    				}
    				// Check for box PLAY TETRIS.
    				if (x1 < mouseX && mouseX < x2 && y1 < mouseY && mouseY < y2) {
    					// Message to the console.
    					System.out.println("User hit PLAY TETRIS button.");
    					// Change the ScreenMode.
    					screenMode = 1;
    				}
    				// Check for the box HIGH SCORES.
    				if (x3 < mouseX && mouseX < x4 && y3 < mouseY && mouseY < y4) {
    					// Message to the console.
    					System.out.println("User hit HIGH SCORES button.");
    					// Change the ScreenMode.
    					screenMode = 3;
    				}
    			}
    			// Repaint the canvas after mousePressed event.
    			repaint();
    		} // End mousePresses event.	
    	}); // End mouseListener.
	}
	
	
	// Convert logical coordinates to device coordinates.
	// We will use 22x22 unit with the origin (0,0) is at the middle of the canvas.
	// Max values of x and y:
		// -11 <= x <= 11
		// -11 <= y <= 11
	private int iX(float x) {
		return xCenter + Math.round(x * unit);
	}
	private int iY(float y) {
		return yCenter - Math.round(y * unit);
	}
	
	
	
	
	// Initialize the graphics.
    // Called in the paint method.
	private void initgr() {
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
		// unit = 46;
	}
	
	// Paint method of this canvas.
	public void paint(Graphics g) {
		// Message to the console.
		System.out.println("Paint the canvas");
		// Set the default color to BLACK.
		g.setColor(Color.BLACK);
		// Initialize the coordinates for this canvas.
		initgr();
		
		// Paint the canvas based on the ScreenMode.
        // Constructor set screenMode to 0.
        // MousePressed will change screenMode.
		switch (screenMode) {
		case 0:
			// Staring Screen.
            // Print the main menu.
			paintMenu(g);
			break;
		case 1:
            // Print the tetris game.
			paintGame(g);
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
    // MousePressed will change the screenMode.
	public void paintMenu(Graphics g) {
		// Message to the console.
		System.out.println("Print the Starting Screen.");
		
		// Draw the boundary of this canvas.
		g.drawRect(xCenter - (int) (11 * unit), yCenter - (int) (11 * unit), (int) (22 * unit), (int) (22 * unit));
		
		// Set up the Font and the Size for printing strings.
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (2 * unit)));
		
		/* Old code 
		x1 = xCenter - (int) (7 * unit);
		y1 = yCenter - (int) (9 * unit);
		x2 = xCenter - (int) (7 * unit) + (int) (14 * unit);
		y2 = yCenter - (int) (9 * unit) + (int) (5 * unit);
		*/
		// Paint the PLAY TETRIS BOX and store the coordinates.
		x1 = iX(-7);
		y1 = iY(9);
		x2 = iX(7);
		y2 = iY(4);
		g.drawRect(x1, y1, x2 - x1, y2 - y1);
		// g.drawRect(xCenter - (int) (7 * unit), yCenter - (int) (9 * unit), (int) (14 * unit), (int) (5 * unit));
		g.drawString("PLAY TETRIS", (int) (xCenter - 6.5 * unit), (int) (yCenter - 5.7 * unit)); 
		
		/* Old code
		x3 = xCenter - (int) (7 * unit);
		y3 = yCenter - (int) (3 * unit);
		x4 = xCenter - (int) (7 * unit) + (int) (14 * unit);
		y4 = yCenter - (int) (3 * unit) + (int) (5 * unit);
		*/
		// Paint the HIGH SCORE BOX.
		x3 = iX(-7);
		y3 = iY(3);
		x4 = iX(7);
		y4 = iY(-2);
		g.drawRect(x3, y3, x4 - x3, y4 - y3);
		// g.drawRect(xCenter - (int) (7 * unit), yCenter - (int) (3 * unit), (int) (14 * unit), (int) (5 * unit));
		g.drawString("HIGH SCORES", (int) (xCenter - 6.9 * unit), (int) (yCenter + 0.2 * unit)); 
		
		/* Old code
		x5 = xCenter - (int) (7 * unit);
		y5 = yCenter + (int) (3 * unit);
		x6 = xCenter - (int) (7 * unit) + (int) (14 * unit);
		y6 = yCenter + (int) (3 * unit) + (int) (5 * unit);
		*/
		// Paint the EXIT BOX.
		x5 = iX(-7);
		y5 = iY(-3);
		x6 = iX(7);
		y6 = iY(-8);
		g.drawRect(x5, y5, x6 - x5, y6 - y5);
		// g.drawRect(xCenter - (int) (7 * unit), yCenter + (int) (3 * unit), (int) (14 * unit), (int) (5 * unit));
		g.drawString("EXIT", (int) (xCenter - 2.5 * unit), (int) (yCenter + 6.2 * unit)); 
		
		// Restore the Font and Size setting.
		g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (20)));
		
		/*
		// Scaling with a vertical middle line.
		g.drawLine(xCenter, 0, xCenter, maxY);
		*/
	}
	
	public void paintGame(Graphics g) {
		// Message to the console.
		System.out.println("Print the Gaming Screen.");
		
		// Print the playground boundary.
		g.drawRect(iX(-9), iY(10), (int) (10 * unit), (int) (20 * unit));
		
		// Print the next piece boundary.
		g.drawRect(iX(3), iY(10), (int) (6 * unit), (int) (6 * unit));
		
		// Print the Level Box.
		g.drawRect(iX(3), iY(3), (int) (6 * unit), (int) (2 * unit));
		
		// Print the Lines Box.
		g.drawRect(iX(3), iY(0), (int) (6 * unit), (int) (2 * unit));
		
		// Print the Score Box.
		g.drawRect(iX(3), iY(-3), (int) (6 * unit), (int) (2 * unit));
		
		// Draw the vertical grid for the playground based on the logical coordinates.
		// Top left point (-9,10).
		// Top right point (1, 10).
		// Bottom left point (-9, -10).
		// Bottom right point (1, -10).
		for (int x = -9; x < 2; x++) {
			g.drawLine(iX(x), iY(10), iX(x), iY(-10));
		}
		// Draw the horizontal grid for the playground based on the logical coordinates.
		// Top left point (-9,10).
		// Top right point (1, 10).
		// Bottom left point (-9, -10).
		// Bottom right point (1, -10).
		for (int y = -10; y < 11; y++) {
			g.drawLine(iX(-9), iY(y), iX(1), iY(y));
		}
		
		/*
		// Draw the boundary of this canvas.
		g.drawRect(xCenter - (int) (10 * unit), yCenter - (int) (10 * unit), (int) (20 * unit), (int) (20 * unit));
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (unit)));	
		g.drawString("TETRIS", (int) (xCenter + -2 * unit), (int) (yCenter - 10.2 * unit));
		//create the next piece box
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (unit)));		
		g.drawString("Next Piece:", (int) (xCenter + 12 * unit), (int) (yCenter - 10.2 * unit));
		g.drawRect(xCenter - (int) (-12 * unit), yCenter - (int) (10 * unit), (int) (5 * unit), (int) (5 * unit));
		//draw score
		g.drawString("Score:", (int) (xCenter + 12 * unit), (int) (yCenter - 4 * unit));
		
		//for grid
		int topY=yCenter - (int) (10* unit); //top of y for drawing squares
		int bottomY=yCenter - (int) (-9* unit); //bottom of y for drawing squares
		int leftMostX=xCenter - (int) (10 * unit); //left of x for drawing squares
		int rightMostX=xCenter - (int) (-9 * unit); //right of x for drawing squares
		//draw grid
		for(int x=10; x>=-9;x--)
		{
			for(int y=10; y>=-9;y--)
			{
			g.drawRect(xCenter-(int) (x* unit), yCenter-(int) (y* unit), (int) (unit), (int) (unit));
			}
		}
				
		
		//for ex change numbers from 0 to 19 for left(0) to right(19) or top(0) to bottom(19)
		g.fillRect((int) (leftMostX+unit*0), (int) (topY+unit*0), (int) (unit), (int) (unit));//fills top left
		g.fillRect((int) (leftMostX+unit*19), (int) (topY+unit*19), (int) (unit), (int) (unit));//fills bottom right
		*/
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("Key Typed");
		System.out.println("Key code = " + e.getKeyCode());
	}


	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key Pressed");
		System.out.println("Key code = " + e.getKeyCode());
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Key Released");
		System.out.println("Key code = " + e.getKeyCode());
		
	}

	/*
	public void paintGrid(SquareTetris[][] grid){
		SquareTetris squareObj = new SquareTetris();
		grid[1][1] = squareObj;
	} */
}
