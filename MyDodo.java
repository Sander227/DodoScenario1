import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;

    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * climbs over the fence when there is a fence ahead
     */
    public void climbOverFence() {
        boolean fence = false;
        if (fenceAhead()) {
            turnLeft();
            move();
            turnRight();
            move();
            move();
            turnRight();
            while(!fence){
                if (fenceAhead()) {
                    turnLeft();
                    move();
                    turnRight();
                }  else {
                    fence =true;
                }
            }
            move();
            turnLeft();
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public String jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
        return "You have moves " +nrStepsTaken+ " spaces";
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        for(int i = 0; i<15; i++){
            System.out.println();
        } 
        while( ! borderAhead() && canMove() ){
            System.out.println ( "X " +getX()+ " Y " +getY());
            move();
        }
        System.out.println ( "X " +getX()+ " Y " +getY());
        System.out.println ("Boink");
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{
            return true;
        }
    }  

    /**
     * Turns to the opposite side of where dodo is facing
     */
    public void turn180() {
        turnRight(); 
        turnRight();
    }

    /**
     * Checks if there is a grain ahead
     */
    public boolean grainAhead(){
        boolean grain = false;
        move();
        boolean isGrain = onGrain();
        stepOneCellBackwards();
        return isGrain;
    }

    /**
     * Goes to the first egg he encounters in his row
     */
    public void goToEgg(){
        boolean eggFound = false;
        while (!eggFound){
            if (onEgg()) {
                eggFound = true;
                break;
            } else {
                move();
            }
        }
    }

    /**
     * Goes to the start of the row and faces the opposite direction
     */
    public void goBackToStartOfRowAndFaceBack(){
        turn180();
        while( ! borderAhead() && canMove() ){
            move();
        }
        turn180();
    }

    /**
     * Walks to the world edge and when he sees a fence he climbs over it and goes on
     */
    public void walkToWorldEdgeClimbingOverFences(){
        while( ! borderAhead() ){
            if (fenceAhead()) {
                climbOverFence();
            } else{
                move();
            } if (onNest()){
                layEgg();
                break;
            }
        }
    }

    /**
     * Pick up grains in the row and prints the coordinates of each grain
     */
    public void pickUpGrainsAndPrintCoordinates(){
        for(int i = 0; i<15; i++){
            System.out.println();
        } 
        while (! borderAhead()) {
            if (onGrain()){
                pickUpGrain();
                System.out.println ( "X " +getX()+ " Y " +getY());
            }
            move();
            if (onGrain()){
                pickUpGrain();
                System.out.println ( "X " +getX()+ " Y " +getY());
            }
        }
    }

    /**
     * Steps one cell backwards
     */
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }

    /**
     * Goes to the world edge and when a comes across a nest he lays an egg in it
     */
    public void ifTheNestIsEmptyLayAnEgg(){
        while (! borderAhead()) {
            move();
            if (onNest() && !onEgg()){
                layEgg();
            } 
        }
    }

    /**
     * Walks around an area of fences
     */
    public void walkAroundFencedArea(){
        while (!onEgg()) {
            turnRight();
            if (fenceAhead()) {
                turnLeft();
                if (fenceAhead()) {
                    turnLeft();
                }
                move();
            }
            else {
                move();
            }
        } 
    }

    /**
     * Checks if there is an egg to his right
     */
    public boolean eggToRight(){
        turnRight();
        boolean ahead = eggAhead();
        turnLeft();
        return ahead;
    }

    /**
     * Checks if there is an egg to his left
     */
    public boolean eggToLeft(){
        turnLeft();
        boolean ahead = eggAhead();
        turnRight();
        return ahead;
    }

    /**
     * Locates where the nest is and turns towards the nest
     */
    public void locateNest(){
        if (!nestAhead()) {
            turnLeft();
        }
        if (!nestAhead()) {
            turnLeft();
        }
        if (!nestAhead()) {
            turnLeft();
        }
        if (!nestAhead()) {
            turnLeft();
        }
    }

    /**
     * Lays a trail of eggs until he reaches the nest
     */
    public void eggTrailToNest(){
        while (!onNest()) {
            if (eggAhead()){
                move();
                pickUpEgg();
            } else if (eggToRight()) {
                turnRight();
                move();
                pickUpEgg();
            } else if (eggToLeft()) {
                turnLeft();
                move();
                pickUpEgg();
            } else if (!nestAhead()){
                locateNest();
                move();
            } else {
                break;
            }
            if(onEgg()){
                pickUpEgg();
            }
        }
    }

    /**
     * Checks if there is a fence to his right
     */
    public boolean fenceRight() {
        boolean x;
        turnRight();
        x = fenceAhead();
        turnLeft();
        return x;
    }

    /**
     * Checks if there is a fence to his left
     */
    public boolean fenceLeft() {
        boolean x;
        turnLeft();
        x = fenceAhead();
        turnRight();
        return x;
    }

    public void simpleMaze(){
        while (!onNest()){
            if (!fenceRight()){
                turnRight();
                move();
            } else if (!fenceAhead()) {
                move();
            } else if (!fenceLeft()) {
                turnLeft();
                move();
            } else {
                turn180();
            }
        }
    }

    /**
     * Faces in the direction that is given
     */
    public void faceDirection(int direction){
        if (direction < NORTH || direction > WEST){
            return;
        }
        while (getDirection() != direction) {
            turnRight();
        }
    }

    public void faceNorth(){
        while (getDirection() != NORTH) {
            turnRight();
        }
    }

    public void faceEast(){
        while (getDirection() != EAST) {
            turnRight();
        }
    }

    public void faceSouth(){
        while (getDirection() != SOUTH) {
            turnRight();
        }
    }

    public void faceWest(){
        while (getDirection() != WEST) {
            turnRight();
        }
    }
    /**
     * 
     */
    public void goToLocation(int coordX, int coordY){
        if (validCoordinates(coordX, coordY)) {
            goToTopLeft();
            while (coordX != getX()){
                move();
            }
            turnRight();
            while (coordY != getY()){
                move();
            }
            turnLeft();
        }
        else {
            showError("Invalid coordinates");
        }
    }

    public boolean locationReached(int x, int y){
        if (getX() == x && getY() == y) {
            return true;
        }
        return false;
    }

    public boolean validCoordinates(int x, int y){
        if (x > getWorld().getWidth()-1 || y > getWorld().getHeight()-1 || x < 0 || y < 0) {
            return false;
        } else {
            return true;
        }
    }

    public int countEggsInRow() {
        int totalEggs = 0;
        goBackToStartOfRowAndFaceBack();
        while (! borderAhead()) {
            if (onEgg()) {
                totalEggs++;
            }
            move();
        }
        if (onEgg()) {
            totalEggs++;
        }
        goBackToStartOfRowAndFaceBack();
        return totalEggs;
    }

    public int countEggsInColumn() {
        turnRight();
        int eggs = countEggsInRow();
        turnLeft();
        return eggs;
    }

    /**
     * Return -1 betekent dat als alles even is en dat er geen ei bij hoeft in de rij
     * 
     * @return int
     */
    public int findUnevenRow() {
        int worldHeight = getWorld().getHeight();
        goToTopLeft();
        for (int i = 0; i < worldHeight; i++) {
            int eggs = countEggsInRow();
            if (eggs % 2 != 0) {
                return i;
            }
            turnRight();
            if(!borderAhead()){
               move(); 
            }
            turnLeft();
        }
        return -1;
    }

    public int findUnevenColumn() {
        int worldWidth = getWorld().getWidth();
        goToTopLeft();
        for (int i = 0; i < worldWidth; i++) {
            int eggs = countEggsInColumn();
            if (eggs % 2 != 0) {
                return i;
            }
            if (!borderAhead()) {
                move();
            }
        }
        return -1;
    }

    public void layTrailOfEggs(int n) {
        if (n <= 0) {
            System.out.println("Fout: aantal eieren moet groter zijn dan 0.");
            return;
        }
        for (int i = 0; i < n; i++) {
            layEgg();
            move();  
        }
    }

    public int totalEggsInWorld(){
        goToLocation(0,0);
        boolean end = false;
        int totalEggs = 0;
        while (end==false) {
            totalEggs = totalEggs + countEggsInRow();
            goBackToStartOfRowAndFaceBack();
            turnRight();
            if (borderAhead()) {
                end = true;
                break;
            }
            move();
            turnLeft();
        }
        turnLeft();
        return totalEggs;
    }

    public int mostEggsInRow(){
        boolean end = false;
        int mostEggs = 0;
        int rowEggs = 0;
        int row = 0;
        int mostEggsRow = 0;
        int startX = getX();
        int startY = getY();
        goToLocation(0,0);
        while (end==false) {
            row++;
            rowEggs = countEggsInRow();
            if (rowEggs >= mostEggs) {
                mostEggs = rowEggs;
                mostEggsRow = row;
            }
            goBackToStartOfRowAndFaceBack();
            turnRight();
            if (borderAhead()) {
                end = true;
                break;
            }
            move();
            turnLeft();
        }
        goToLocation(startX,startY);
        turnLeft();
        return mostEggsRow;
    }

    public void monumentOfEggs() {
        int row = 0;
        boolean done = false;
        while (!done) {
            if (!validCoordinates(0, row)) {
                done = true;
                break;
            }
            goToLocation(0, row);
            faceEast();
            int column = 0;
            while (true) {
                if (!validCoordinates(column, row)) {
                    break;
                }
                if (column <= row) {
                    if (canLayEgg()) {
                        layEgg();
                    }
                }
                if (!borderAhead()) {
                    move();
                    column++;
                } else {
                    break;
                }
            }
            row++;
        }
    }

    public void monumentOfEggs2() {
        int eggsInRow = 1;
        int startX = getX();
        int startY = getY();
        int worldWidth = getWorld().getWidth();
        int worldHeight = getWorld().getHeight();
        int row = 0;
        while (startY + row < worldHeight && startX + eggsInRow - 1 < worldWidth) {
            goToLocation(startX, startY + row);
            faceEast();
            int layed = 0;
            while (layed < eggsInRow && !borderAhead()) {
                if (canLayEgg()) {
                    layEgg();
                }
                layed++;
                if (layed < eggsInRow && !borderAhead()) {
                    move();
                }
            }
            eggsInRow *= 2;
            row++;
        }
    }

    public void stablePyramidOfEggs() {
        int stappenNaarLinks = 2;
        int eierenLeggen = 1;
        while (getY() < getWorld().getHeight() - 1) {
            layTrailOfEggs(eierenLeggen);
            eierenLeggen += 2;
            for (int w = 0; w < stappenNaarLinks; w++) {
                if (getX() != 0) {
                    stepOneCellBackwards();
                } else {
                    return;
                }
            }
            stappenNaarLinks += 2;
            faceSouth();
            if (canMove()) {
                move();
            } else {
                return;
            }
            faceEast();
        }
        layTrailOfEggs(eierenLeggen);
    }

    public double averageEggsInWorld(){
        boolean end = false;
        double averageEggs = 0.0;
        int eggsInWorld = 0;
        int rowCount = 1;
        int beginX = getX();
        int beginY = getY();
        goToLocation(0,0);
        while (end==false) {
            eggsInWorld = eggsInWorld + countEggsInRow();
            goBackToStartOfRowAndFaceBack();
            turnRight();
            if (borderAhead()) {
                end = true;
                break;
            }
            move();
            rowCount++;
            turnLeft();
        }
        turnLeft();
        averageEggs = (double)eggsInWorld / rowCount;
        goToLocation(beginX,beginY);
        return averageEggs;
    }

    public void fixBrokenPart() {
        int x = findUnevenColumn();
        int y = findUnevenRow();

        if (validCoordinates(x,y)) {

            goToLocation(x,y);

            if (onEgg()) {
                pickUpEgg();
            } else {
                layEgg();
            }
        } else{
            showError("There are no broken parts");
        }
    }

    public void faceRight() {
        int x = getX();
        int y = getY();
        if (borderAhead()){
            turn180();
        }
        move();
        if (x > getX()) {
            turn180();
        }
        else if (y > getY()){
            turnRight();
        } 
        else if (y < getY()) {
            turnLeft();
        }
    }
    
    public void goToTopLeft() {
        faceRight();
        goBackToStartOfRowAndFaceBack();
        turnRight();
        goBackToStartOfRowAndFaceBack();
        turnLeft();
    }
    
}
