import java.awt.*;
import java.util.Random;

public class TetrisVariant {

    //Stores the index within blockRegistry in the block object
    int blockID;
    //The color of the block
    Color color;
    //The center square of a block used for rotating
    int blockCenter;
    //The type (shape) of the block
    int blockType;
    //Determines whether the block will move or not this iteration


    boolean isHalted = false;

    TetrisVariant(TetrisVariant[] blockRegistry){
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

                this.color = Color.RED;
                break;
            case 2:
                //The "S" block

                this.color = Color.ORANGE;
                break;
            case 3:
                //The "J" block

                this.color = Color.YELLOW;
                break;
            case 4:
                //The "O" block
            	
                this.color = Color.GREEN;
                break;
            case 5:
                //The "Z" block

                this.color = Color.BLUE;
                break;
            case 6:
                //The "T" block

                this.color = Color.MAGENTA;
                break;
            case 7:
                //The "L" block

                this.color = Color.PINK;
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
