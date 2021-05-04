import java.awt.*;
import java.util.Random;

public class BlockTetris {
    Color WHITE = new Color(255, 255, 255);
    Color RED = new Color(255, 255, 255);
    Color ORANGE = new Color(255, 255, 255);
    Color YELLOW = new Color(255, 255, 255);
    Color GREEN = new Color(255, 255, 255);
    Color BLUE = new Color(255, 255, 255);
    Color PURPLE = new Color(255, 255, 255);
    Color PINK = new Color(255, 255, 255);

    //Stores the index within blockRegistry in the block object
    int blockID;
    //The color of the block
    Color blockColor;
    //The center square of a block used for rotating
    int blockCenter;
    //The type (shape) of the block
    int blockType;
    //Determines whether the block will move or not this iteration


    boolean isHalted = false;

    BlockTetris(BlockTetris[] blockRegistry){
        //Sets the blockID and registers the block in blockRegistry
        int i = 0;
        while (!(blockRegistry[i] == null))
        {
            i+=1;
        }

        blockRegistry[i] = this;
        this.blockID = i;

        //Sets the blockColor and blockType
        Random rand = new Random();
        int randomType = rand.nextInt(7) + 1;
        this.blockType = randomType;

        switch (randomType)
        {
            case 1:
                //The "I" block

                this.blockColor = RED;
                break;
            case 2:
                //The "S" block

                this.blockColor = ORANGE;
                break;
            case 3:
                //The "J" block

                this.blockColor = YELLOW;
                break;
            case 4:
                //The "O" block

                this.blockColor = GREEN;
                break;
            case 5:
                //The "Z" block

                this.blockColor = BLUE;
                break;
            case 6:
                //The "T" block

                this.blockColor = PURPLE;
                break;
            case 7:
                //The "L" block

                this.blockColor = PINK;
                break;
        }

        this.isHalted = false;
    }

    //Used for updating the grid after block rotations or grid iterations (block fallen by one layer)
    public void updateBlockCenter() {

    }

    public void rotateCW() {

    }

    public void rotateCCW() {

    }
}
