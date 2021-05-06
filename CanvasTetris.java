// CanvasTetris.java: This is the canvas.

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
	
	// Hold the current running tetris variant while playing the game.
	TetrisVariant runningVariant;
	TetrisVariant nextVariant;
	// Keep track if this variant is halted.
	boolean isHalted;
	// Keep track if the next variant is used.
	boolean isUsed;
	
	// Hold the gridTetris for the tetris game.
	// The origin of this grid is at (-9,-10).
	GridTetris[][] grid;
	
	// Botttom boundary, for the Tetris game.
	// If the current runningVariant reach the bottom boundary, then it will be halted and then create a new variant.
	int[] bottomBoundaryX;
	int[] bottomBoundaryY;
	
	// Hold the base time clock.
	// Each level will decrease the amount of time.
	long baseClock;
	long lastTime;
	long currentTime;
	boolean isSystemMoved; // If the system moved, then update the lastTime.
	
	// Keep track with the scoring system.
	int level;
	int lines;
	int score;
	int mileStone; // Use this as milestone to increase level.
	int variantCount;
	boolean isScoreSaved;
	

	// Default constructor for this canvas.
	public CanvasTetris() {
		
		
		
		
		// Initialize the lastTime and SystemMove.
		lastTime = System.currentTimeMillis();
		isSystemMoved = false;
		
		// Initialize the gridTetris.
		// The origin of this grid is at (-9,-10).
		grid = new GridTetris[10][20];
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 20; y++) {
				grid[x][y] = new GridTetris(-9 + x, -10 + y, Color.WHITE);
			}
		}
		
		// Initialize the value of isHalted and isUsed to create a new tetris variant.
		isHalted = true;
		isUsed = true;
		
		
		// Initialize the value for bottom boundary x and y.
		bottomBoundaryX = new int[10];
		bottomBoundaryY = new int[10];
		for (int i = 0; i < bottomBoundaryX.length; i++) {
			bottomBoundaryX[i] = -9 + i;
		}
		for (int i = 0; i < bottomBoundaryY.length; i++) {
			bottomBoundaryY[i] = -10;
		}
		
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
    					// Initialize the scoring system.
    					score = 0;
    					level = 1;
    					lines = 0;
    					mileStone = 10;
    					variantCount = 0;
    					baseClock = 1300;
    					isScoreSaved = false;
    					// Repaint the canvas.
    					repaint();
    				}
    				// Check for the box HIGH SCORES.
    				if (x3 < mouseX && mouseX < x4 && y3 < mouseY && mouseY < y4) {
    					// Message to the console.
    					System.out.println("User hit HIGH SCORES button.");
    					// Change the ScreenMode.
    					screenMode = 3;
    					// Repaint the canvas.
    					repaint();
    				}
    			}
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
	
	// Check the lost condition.
	public boolean isLost() {
		for (int i = 0; i < 10; i++) {
			if (bottomBoundaryY[i] >= 9) {
				System.out.println("LOST");
				return true;
			}
		}
		return false;
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
			paintSummary(g);
			break;
		default:
			paintHighScore(g);
			// Do something here.
		}
	}
	
    
	// Starting Screen.
	// Goal: Print the menu with those options: Play, HighScores, Quit.
    // MousePressed will change the screenMode.
	public void paintMenu(Graphics g) {
		
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
		
		
		// Draw the grid.
		this.drawGrid(g);
		g.setColor(Color.BLACK);
		
		// Debug. Paint bottom boundary.
		// paintBottomBoundary(g);
		
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
		
		// Print the Level Info.
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) unit));
		g.drawString("Level:", iX(3.3f), iY(1.5f));
		g.drawString(Integer.toString(level), iX(6.3f), iY(1.5f));
		
		// Print the Line Info.
		g.drawString("Lines:", iX(3.3f), iY(-1.5f));
		g.drawString(Integer.toString(lines), iX(6.3f), iY(-1.5f));
		
		// Print the Score Info.
		g.drawString("Score:", iX(3.3f), iY(-4.5f));
		g.drawString(Integer.toString(score), iX(6.3f), iY(-4.5f));
		
		
		
		
		
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
		
		// Create a new variant shown in the Next Piece Box.
		if (isUsed) {
			nextVariant = new TetrisVariant();
			isUsed = false;
			nextVariant.changeCoordinatesToNewBox();
		}
		
		// Get the new variant from the next piece box.
		if (isHalted) {
			variantCount++;
			runningVariant = nextVariant;
			isUsed = true;
			isHalted = false;
			runningVariant.changeCoordiantesToStartingPoint();
		}
		
		
		// Paint the running tetris variant.
		this.drawTetrisVariant(g, runningVariant);
		// Paint the next tetris variant.
		this.drawTetrisVariant(g, nextVariant);
		
		
		// Keep the paint method recall itself.
		// Need this to work with the timing system.
		try {
			TimeUnit.MILLISECONDS.sleep(10);
			repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// When system not move, get the current time and check baseClock.
		if (!isSystemMoved) {
			currentTime = System.currentTimeMillis();
			if (currentTime - lastTime > baseClock) {
				runningVariant.moveDown();
				isSystemMoved = true;
				repaint();
			}
		}
		if (isSystemMoved) {
			isSystemMoved = false;
			lastTime = System.currentTimeMillis();
		}
		
		// Check line deletion.
		// Keep up with Line Counter to calculate the score.
		int lineCount = 0;
		while (checkLineDeletion() != -1) {
			deleteFilledLine(checkLineDeletion());
			lineCount++;
		}
		// Update the score.
		if (lineCount > 0) {
			lines += lineCount;
			switch (lineCount) {
			case 1:
				score += 5 * level;
				break;
			case 2:
				score += 15 * level;
				break;
			case 3:
				score += 75 * level;
				break;
			case 4:
				score += 400 * level;
				break;
			}
		}
		// Update the level with variant count and milestone.
		// Update the baseClock.
		if (variantCount >= mileStone) {
			level++;
			baseClock = (long) (baseClock * 90.0 / 100);
			mileStone += 10;
		}
		
		// Check the lost condition.
		if (isLost()) {
			screenMode = 2;
		}
		
		
		
		
	}
	
	public void paintHighScore(Graphics g) {
		
		// Print HIGH SCORE List.
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (2 * unit)));
		g.drawString("High Scores", iX(-5), iY(5));
		// Read the Highscores.txt and store the scores in the LinkedList.
		LinkedList<Integer> scoreList = new LinkedList<>();
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File("Highscores.txt"));
			while (fileInput.hasNext()) {
				scoreList.add(fileInput.nextInt());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileInput.close();
		// Add the current score to the list.
		scoreList.add(score);
		// Sort the list.
		Collections.sort(scoreList);
		// Remove the smallest score.
		scoreList.removeFirst();
		
		
		// Save the new high score list.
		PrintWriter fileOutput = null;
		try {
			fileOutput = new PrintWriter(new File("Highscores.txt"));
			for (int i = 0; i < scoreList.size(); i++) {
				fileOutput.println(scoreList.get(i));
			}
			fileOutput.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Output the high score list.
		for (int i = 0; i < scoreList.size(); i++) {
			// Highlight the user score.
			if (scoreList.get(scoreList.size() - 1 - i) == score) {
				g.setColor(Color.RED);
			}
			g.drawString(i + 1 + ". " + scoreList.get(scoreList.size() - 1 - i), iX(-2.7f), iY(1.3f - 1.7f * i));
			g.setColor(Color.BLACK);
		}
		
	}
	
	// Paint the summary screen.
	public void paintSummary(Graphics g) {
		
		// Print the summary score.
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (2 * unit)));
		g.drawString("Your Score: " + score, iX(-7), iY(6));
		
		
		// Print HIGH SCORE List.
		g.setFont(new Font("TimesRoman", Font.BOLD, (int) (1 * unit)));
		g.drawString("High Scores", iX(-3), iY(3));
		// Read the Highscores.txt and store the scores in the LinkedList.
		LinkedList<Integer> scoreList = new LinkedList<>();
		Scanner fileInput = null;
		try {
			fileInput = new Scanner(new File("Highscores.txt"));
			while (fileInput.hasNext()) {
				scoreList.add(fileInput.nextInt());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileInput.close();
		
		// Make sure the score is save once.
		if (!isScoreSaved) {
			// Add the current score to the list.
			scoreList.add(score);
			// Sort the list.
			Collections.sort(scoreList);
			// Remove the smallest score.
			scoreList.removeFirst();
		}
		
		
		// Save the new high score list.
		PrintWriter fileOutput = null;
		try {
			fileOutput = new PrintWriter(new File("Highscores.txt"));
			for (int i = 0; i < scoreList.size(); i++) {
				fileOutput.println(scoreList.get(i));
			}
			fileOutput.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Output the high score list.
		for (int i = 0; i < scoreList.size(); i++) {
			// Highlight the user score.
			if (scoreList.get(scoreList.size() - 1 - i) == score) {
				g.setColor(Color.RED);
			}
			g.drawString(i + 1 + ". " + scoreList.get(scoreList.size() - 1 - i), iX(-2), iY(1.3f - 1.2f * i));
			g.setColor(Color.BLACK);
		}
		
		// Flag this score is saved.
		isScoreSaved = true;
		
		
	}
	
	
	// Draw the current tetris variant.
	public void drawTetrisVariant(Graphics g, TetrisVariant variant) {
		// Check for the bottom boundary.
		checkBottomBoundary();
		
		// Get the current coordinates.
		int x = variant.getX();
		int y = variant.getY();
		// Set the color based on this variant.
		g.setColor(variant.getColor());
		// Get the list of string code.
		String[] list = variant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
			if (list[i].compareTo("P") == 0) {
				// Only paint when y coordinate inside the playground.
				if (y <= 10) {
					g.fillRect(iX(x), iY(y), (int) (unit), (int) (unit));
				}
			}
		}
		// Default color is BLACK.
		g.setColor(Color.BLACK);
		// Draw the edge after finish painting the variant.
		drawTetrisVariantEdge(g, variant);
		
	}
	
	// Draw tetris variant edge.
	// Called after drawing the tetris variant.
	public void drawTetrisVariantEdge(Graphics g, TetrisVariant variant) {
		// Default color is BLACK.
		g.setColor(Color.BLACK);
		// Get the current coordinates.
		int x = variant.getX();
		int y = variant.getY();
		// Get the list of string code.
		String[] list = variant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
			if (list[i].compareTo("P") == 0) {
				if (y <= 10) {
					g.drawRect(iX(x), iY(y), (int) (unit), (int) (unit));
				}
			}
		}
		
	}
	
	// Debug. Paint the bottom boundary.
	public void paintBottomBoundary(Graphics g) {
		for (int i = 0; i < 10; i++) {
			int x = bottomBoundaryX[i];
			int y = bottomBoundaryY[i];
			g.setColor(Color.BLACK);
			g.fillRect(iX(x), iY(y), (int) unit, (int) unit);
		}
	}
	
	// Check the tetris variant with bottom boundary.
	public void checkBottomBoundary() {
		// Get the current coordinates.
		int x = runningVariant.getX();
		int y = runningVariant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			
			for (int j = 0; j < 10; j++) {
				if (bottomBoundaryX[j] == x &&
						bottomBoundaryY[j] == y - 1) {
					isHalted = true;
					addVariantToGrid();
					updateBottomBoundary();
					repaint();
				}
			}
			
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
	}
	
	
	// Check the left boundary.
	// This method will check if the variant hit the left boundary, then send the message to the KeyListener.
	// Then will not allow it move left anymore.
	public boolean checkLeftBoundary() {
		// Get the current coordinates.
		int x = runningVariant.getX();
		int y = runningVariant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			
			if (x <= -9) {
				return true;
			}
			
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
		// If it pass the loop, then it does not hit the left boundary.
		return false;
	}
	// Similar to the method checkLeftBoundary. But specific for rotation case.
	public boolean checkLeftBoundaryRotation() {
		// Get the current coordinates.
		int x = runningVariant.getX();
		int y = runningVariant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			
			if (x < -9) {
				return true;
			}
			
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
		// If it pass the loop, then it does not hit the left boundary.
		return false;
	}
	// Check the right boundary.
	public boolean checkRightBoundary() {
		// Get the current coordinates.
		int x = runningVariant.getX();
		int y = runningVariant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			
			if (x > -1) {
				return true;
			}
			
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
		// If it pass the loop, then it does not hit the left boundary.
		return false;
	}
	// Check right boundary while rotating.
	public boolean checkRightBoundaryRotation() {
		// Get the current coordinates.
		int x = runningVariant.getX();
		int y = runningVariant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			
			if (x > 0) {
				return true;
			}
			
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
		// If it pass the loop, then it does not hit the left boundary.
		return false;
	}
	
	
	
	// Update bottom boundary.
	public void updateBottomBoundary() {
		for (int x = 0; x < 10; x++) {
			// Hold the high y.
			int highY = grid[x][0].getY();
			for (int y = 0; y < 20; y++) {
				if (grid[x][y].getColor() != Color.WHITE) {
					highY = grid[x][y].getY();
				}
			}
			bottomBoundaryY[x] = highY;
		}
	}
	
	
	// Count the distance from the current variant to the bottom boundary.
	// Use with the Space Button.
	public int countDistanceToBottom(TetrisVariant variant) {
		// Hold the longest distance.
		int shortestDistance = 100;
		// Get the current coordinate.
		int x = variant.getX();
		int y = variant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			// Update distance.
			int xIndex = 0;
			for (int j = 0; j < 10; j++) { // Find the index of bottomBoundary.
				if (bottomBoundaryX[j] == x) {
					xIndex = j;
					/*
					// Debug.
					System.out.println("x = " + x + " j = " + j);
					System.out.println("There is a match in count distance y.");
					*/
					break;
				}
			}
			/*
			// Debug.
			System.out.println("new distance = " + (bottomBoundaryY[xIndex] - y));
			System.out.println("y = " + y + " bottomY = " + bottomBoundaryY[xIndex]);
			*/
			if (y - bottomBoundaryY[xIndex] - 1 < shortestDistance) { // Compare distance in y axis.
				shortestDistance = y - bottomBoundaryY[xIndex] - 1;
			}
			// Update coordinates.
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
		return shortestDistance;
	}
	
	
	// Add the running variant to the grid.
	public void addVariantToGrid() {
		// Get the current coordinates.
		int x = runningVariant.getX();
		int y = runningVariant.getY();
		// Get the list of string code.
		String[] list = runningVariant.outputStringCode().split("");
		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < 10; j++) {
				for (int k = 0; k < 20; k++) {
					if (grid[j][k].getX() == x &&
							grid[j][k].getY() == y) {
						grid[j][k].setColor(runningVariant.getColor());
					}
				}
			}
			if (list[i].compareTo("U") == 0) {
				y++;
			}
			if (list[i].compareTo("D") == 0) {
				y--;
			}
			if (list[i].compareTo("L") == 0) {
				x--;
			}
			if (list[i].compareTo("R") == 0) {
				x++;
			}
		}
	}
	
	
	// Check for line deletion.
	// Return the y index if it found a filled line.
	public int checkLineDeletion() {
		for (int y = 0; y < 20; y++) {
			boolean isFilled = true; // Flag
			for (int x = 0; x < 10; x++) {
				if (grid[x][y].getColor() == Color.WHITE) {
					isFilled = false;
					break;
				}
			}
			if (isFilled) {
				return y;
			}
		}
		return -1;
	}
	
	public void deleteFilledLine(int yIndex) {
		if (yIndex < 0) {
			return;
		}
		for (int y = yIndex; y < 19; y++) {
			for (int x = 0; x < 10; x++) {
				grid[x][y].setColor(grid[x][y+1].getColor());
			}
		}
		updateBottomBoundary();
	}
	
	
	
	// Draw the grid.
	public void drawGrid(Graphics g) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 20; j++) {
				if (grid[i][j].getColor() != Color.WHITE) {
					g.setColor(grid[i][j].getColor());
					g.fillRect(iX(grid[i][j].getX()), iY(grid[i][j].getY()), (int) (unit), (int) (unit));
				}
			}
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// Do nothing here.
	}


	@Override
	public void keyPressed(KeyEvent e) {
		
		// Debug.
		// System.out.println(e.getKeyCode());
		
		// Check if we are at playing mode.
		if (screenMode == 1) {
			// Left Arrow.
			// Check for the left boundary.
			if (e.getKeyCode() == 37 && !checkLeftBoundary()) {
				runningVariant.moveLeft();
				repaint();
			}
			// Up Arrow. Rotate the variant.
			// If it is currently hitting the left or right boundary and rotate, push it to the right one unit.
			// If we get the I case, right rotation checking will need to be done twice.
			if (e.getKeyCode() == 38) {
				runningVariant.rotate();
				while (checkLeftBoundaryRotation() || checkRightBoundaryRotation()) {
					if (checkLeftBoundaryRotation()) {
						runningVariant.moveRight();
					}
					if (checkRightBoundaryRotation()) {
						runningVariant.moveLeft();
					}
				}
				repaint();
			}
			// Right Arrow.
			// Check for the right boundary.
			if (e.getKeyCode() == 39 && !checkRightBoundary()) {
				runningVariant.moveRight();
				repaint();
			}
			// Down Arrow.
			if (e.getKeyCode() == 40) {
				runningVariant.moveDown();
				// Bonus score.
				score += 1 * level;
				repaint();
			}
			// Space Button.
			if (e.getKeyCode() == 32) {
				// Bonus score.
				score += 1 * level * countDistanceToBottom(runningVariant);
				// Debug.
				// System.out.println("Hit the Space Button");
				// System.out.println("Counter = " + countDistanceToBottom(runningVariant));
				for (int i = 0; i < countDistanceToBottom(runningVariant);) {
					// Debug.
					// System.out.println("i = " + i + " counter = " + countDistanceToBottom(runningVariant));
					runningVariant.moveDown();
				}
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Do nothing here.
		
	}

	
	
	
	
}
