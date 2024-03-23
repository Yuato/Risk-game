public class Coordinate {
    //Instance variable for Coordinate class
    private int x;
    private int y;
    
    /**
    Initializes a Coordinate object.
    Precondition: Coordinate object must take a int x_coord and int y_coord.
    Postcondition: Instance variables int x and int y are initialized with 
    int x_coord, and int y_coord.
    
    @param x_coord - int to initialize instance variable x for Power object
    @param y_coord - int to initialize instance variable y for Power object
    */
    public Coordinate (int x_coord, int y_coord){
        x = x_coord;
        y = y_coord;
    }
    
    public Coordinate(String coord){
        x = (int)(coord.charAt(0))-49;
        y = (int)(coord.charAt(1))-65;
    }
    
    /**
    Returns the int x of a given Coordinate object.
    Precondition: Coordinate object must be initialized.
    Postcondition: Returns int x accessed from the Coordinate object.
    
    @return x -the located row of the Coordinate object
    */
    public int getX(){
        return x;
    }
    
    /**
    Returns the int y of a given Coordinate object.
    Precondition: Coordinate object must be initialized.
    Postcondition: Returns int y accessed from the Coordinate object.
    
    @return y -the located column of the Coordinate object
    */
    public int getY() {
        return y;
    }
    
    /**
    Returns the int strength of a given Power object.
    Precondition: Coordinate object must be initialized.
    Postcondition: Returns the boolean of the compared Coordinate objects instance variables.
    
    @return equaivalent - the boolean of the comparison between the instance variables of two Coordinate objects
    */
    public boolean equals(Coordinate compare){
        boolean equivalent = compare.getX()==this.x &&compare.getY()==this.y;
        return equivalent;
    }
}