import java.awt.*;
import java.util.Random;

public class TetrisVariant {

	/*
    // Stores the index within blockRegistry in the block object
    int blockID;
    // The center square of a block used for rotating
    int blockCenter;
    */
    
    
    // Store the color of this variant.
    private Color color;
    // Store the coordinate of this variant.
    private int x; 
    private int y;
    // Store the shape of this block.
    // Shape = {I, J, L, O, S, T, Z}.
    private char shape;
    // Store the state of this variant.
    // Each variant has 4 states based on the picture.
    // 0, 1, 2, 3.
    private int state;
    
    
    /*
    boolean isHalted = false;
    */
    
    // Constructor.
    TetrisVariant() {
    	// Based on the 22x22 grid, new variant will appear outside the canvas.
    	x = 0;
    	y = 20;
    	// Get random values for color and shape.
    	color = this.getRandomColor();
    	shape = this.getRandomShape();
    	state = this.getRandomState();
    }
    
    // Change the coordinates to the starting point.
    public void changeCoordiantesToStartingPoint() {
    	x = -5;
    	y = 13;
    }
    
    // Change the coordinates to the Next Piece box.
    public void changeCoordinatesToNewBox() {
    	x = 6;
    	y = 7;
    }

    // Used for updating the grid after block rotations or grid iterations (block fallen by one layer).
    public void updateBlockCenter() {

    }
    
    // Rotate the variant.
    // Each variant has 4 states: 0, 1, 2, 3.
    // Only change the state when rotating.
    public void rotate() {
    	state++;
    	state = state % 4;
    }
    
    // Get a random shape.
    // Shape = {I, J, L, O, S, T, Z}.
    private char getRandomShape() {
    	Random rand = new Random();
    	int randomNumber = rand.nextInt(7);
    	switch (randomNumber) {
    	case 0:
    		return 'I';
		case 1:
    		return 'J';
		case 2:
			return 'L';
		case 3:
			return 'O';
		case 4:
			return 'S';
		case 5:
			return 'T';
		case 6:
			return 'Z';
		default:
			return 'S';
    	}
    }
    
    // Get a random color.
    // Color list = {GREEN, RED, ORANGE, BLUE, CYAN, ...}.
    private Color getRandomColor() {
    	Random rand = new Random();
    	int randomNumber = rand.nextInt(7);
    	switch (randomNumber) {
    	case 0:
    		return Color.BLUE;
		case 1:
    		return Color.CYAN;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.MAGENTA;
		case 4:
			return Color.ORANGE;
		case 5:
			return Color.RED;
		case 6:
			return Color.YELLOW;
		default:
			return Color.PINK;
    	}
    }
    
    // Get a random state.
    // Each variant has 4 states: 0, 1, 2, 3.
    private int getRandomState() {
    	Random rand = new Random();
    	return rand.nextInt(4);
    }
    
    // Moving the variant.
    // Successful return TRUE.
    // Failure return FALSE.
    // Will need to check for the boundary late
    public boolean moveUp() {
    	y++;
    	return true;
    }
    public boolean moveDown() {
    	y--;
    	return true;
    }
    public boolean moveLeft() {
    	x--;
    	return true;
    }
    public boolean moveRight() {
    	x++;
    	return true;
    }
    
    // Output the Paint Code.
    public String outputStringCode() {
    	String stringCode = "";
    	switch(shape) {
    	case 'I':
    		switch (state) {
    		case 0:
    		case 2:
    			stringCode = "UPDPDPDP";
    			break;
    		case 1:
    		case 3:
    			stringCode = "LPRPRPRP";
    			break;
    		}
    		break;
    	case 'J':
    		switch (state) {
    		case 0:
    			stringCode = "UPDPDPLP";
    			break;
    		case 1:
    			stringCode = "RPLPLPUP";
    			break;
    		case 2:
    			stringCode = "DPUPUPRP";
    			break;
    		case 3:
    			stringCode = "LPRPRPDP";
    			break;
    		}
    		break;
    	case 'L':
    		switch (state) {
    		case 0:
    			stringCode = "UPDPDPRP";
    			break;
    		case 1:
    			stringCode = "RPLPLPDP";
    			break;
    		case 2:
    			stringCode = "DPUPUPLP";
    			break;
    		case 3:
    			stringCode = "LPRPRPUP";
    			break;
    		}
    		break;
    	case 'O':
    		stringCode = "PUPLPDP";
    		break;
    	case 'S':
    		switch (state) {
    		case 0:
    		case 2:
    			stringCode = "LPRPUPRP";
    			break;
    		case 1:
    		case 3:
    			stringCode = "DPUPLPUP";
    			break;
    		}
    		break;
    	case 'T':
    		switch (state) {
    		case 0:
    			stringCode = "LPRPDPURP";
    			break;
    		case 1:
    			stringCode = "UPDPDPULP";
    			break;
    		case 2:
    			stringCode = "LPRPRPLUP";
    			break;
    		case 3:
    			stringCode = "UPDPDPURP";
    			break;
    		}
    		break;
    	case 'Z':
    		switch (state) {
    		case 0:
    		case 2:
    			stringCode = "LPRPDPRP";
    			break;
    		case 1:
    		case 3:
    			stringCode = "UPDPLPDP";
    			break;
    		}
    		break;
    	}
    	return stringCode;
    }
    
    
    // Get current coordinate.
    public int getX() {
    	return x;
    }
    public int getY() {
    	return y;
    }
    
    // Get the color.
    public Color getColor() {
    	return color;
    }
  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}