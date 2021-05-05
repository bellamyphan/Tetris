import java.awt.*;

public class GridTetris {
	
	// Hold the coordinate.
	private int x;
	private int y;
	
	// Hold the color.
	private Color color;
	
	// Constructor.
	public GridTetris() {
		x = 0;
		y = 0;
		color = Color.WHITE;
	}
	public GridTetris(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	// Set methods.
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	// Get methods.
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Color getColor() {
		return color;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
    //Creates a default grid for the blocks to fall into
    //This is the updated grid, used for painting the grid of the game
    SquareTetris newGrid[][] = new SquareTetris[20][20];
    //This is the old grid, used for updating the new grid
    SquareTetris oldGrid[][] = new SquareTetris[20][20];
    //Registers the blocks currently on the board
    BlockTetris blockRegistry[] = new BlockTetris[30];
    //The current block that needs to fall
    BlockTetris currentBlock = new BlockTetris(blockRegistry);

    //Checks if one layer in the grid is full needs to be removed
    public void checkGrid() {
        boolean doesGridNeedClearing = true;
        int rowIndexToClear = -1;

        for (int y = 0; y < newGrid.length; y++) {
            for (int x = 0; x < newGrid[0].length; x++) {
                if (newGrid[y][x].squareColor == "WHITE") {
                    doesGridNeedClearing = false;
                }
            }

            //If there is a full layer, store value
            if (doesGridNeedClearing = true) {
                //Stores row index
                rowIndexToClear = y;
            }
        }

        //If there is a stored row index value
        if (rowIndexToClear != -1) {
            shiftGridSectionDown(rowIndexToClear);
        }
    }

    //***Exclusively for if line in grid was full and removed***
    //All above lines of blocks in the grid are shifted down one
    public void shiftGridSectionDown(int yCoordGrid) {
        //Shifts each row down
        for (int i = (yCoordGrid + 1); i > 0; i--) {
               for (int j = 0; j < newGrid[0].length; j++) {
                   //Sets a square equal to the square directly above it for the whole row
                    newGrid[i][j] = newGrid[(i - 1)][j];
               }
        }

        //Removes the top (non visible) row of colors
        for (int x = 0; x < newGrid[0].length; x++) {
            newGrid[0][x].removeColor();
        }
    }

    //Updates the old grid's values for future iterations
    public void updateOldGrid() {
        oldGrid = newGrid.clone();
    } */
}

