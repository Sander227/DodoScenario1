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
    
    public void turn180() {
        turnRight(); 
        turnRight();
    }
    
    public boolean grainAhead(){
        boolean grain = false;
        move();
        boolean isGrain = onGrain();
        stepOneCellBackwards();
        return isGrain;
    }
    
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
    
    public void goBackToStartOfRowAndFaceBack(){
        turn180();
        while( ! borderAhead() && canMove() ){
            move();
        }
        turn180();
    }
    
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
    
    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }
    
    public void ifTheNestIsEmptyLayAnEgg(){
        while (! borderAhead()) {
            move();
            if (onNest() && !onEgg()){
            layEgg();
        } 
        }
    }
    
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
    
    public boolean eggToRight(){
        turnRight();
        boolean ahead = eggAhead();
        turnLeft();
        return ahead;
    }
    
    public boolean eggToLeft(){
        turnLeft();
        boolean ahead = eggAhead();
        turnRight();
        return ahead;
    }
    
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
        }
    }
    
    public boolean fenceRight() {
        boolean x;
        turnRight();
        x = fenceAhead();
        turnLeft();
        return x;
    }
    
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
    
    public void goToLocation(int coordX, int coordY){
        if (validCoordinates(coordX, coordY)) {
            while (!locationReached(coordX, coordY)) {
                if (getX() < coordX) {
                    faceEast();
                    move();
                } else if (getX() > coordX) {
                    faceWest();
                    move();
                } if (getY() < coordY) {
                    faceSouth();
                    move();
                } else if (getY() > coordY) {
                    faceNorth();
                    move();
                }
                faceEast();
            }
        }
    }
    
    public boolean locationReached(int x, int y){
        if (getX() == x && getY() == y) {
            return true;
        }
        return false;
    }
    
    public boolean validCoordinates(int x, int y){
        if (x > getWorld().getWidth()-1 || y > getWorld().getHeight()-1) {
            showError ("Invalid coordinates");
            return false;
        } else {
            return true;
        }
    }
    
    public int countEggsInRow() {
        int totalEggs = 0;
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
}
