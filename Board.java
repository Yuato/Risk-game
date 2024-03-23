import java.lang.Math;
import java.util.ArrayList;

public class Board {
    //Instance variable for Board class
    private int [][] board = new int [5][5];
    private int [][] player_board = new int [5][5];
    private int rows;
    private int columns;
    
    /**
    Initializes a Board object.
    Precondition: Coordinate object must take a int x, int y and Player[]players.
    Postcondition: Instance variables int rows, int columns, int [][] player_board and int [][] board are initialized with 
    int x, int y, and Player[]players.
    
    @param x - int to initialize instance variables rows, board, player_board for Power object.
    @param y - int to initialize instance variables columns, board, player_board for Power object.
    @param players - Player[] to initialize instance variables board, player_board for Power object
    */
    public Board (int x, int y, Player[]players){
        board = new int [x][y];
        player_board = new int[x][y];
        rows = x;
        columns = y;
        ArrayList <Coordinate> coordinates = new ArrayList<Coordinate>();
        for (int i = 0; i<x;i++){
            for (int j = 0; j<y; j++){
                Coordinate coord = new Coordinate (i,j);
                coordinates.add(coord);
            }
        }
        for (int i = 0; i<players.length; i++){
            int units = ((x*y)/players.length)*3;
            for (int j = 0; j<x*y/players.length; j++){
                int rand_coord = (int)(Math.random() * coordinates.size());
                
                int y_placement = coordinates.get(rand_coord).getY();
                int x_placement = coordinates.remove(rand_coord).getX();
                
                int rand = (int)((Math.random() * 5)+1);
                if (((x*y)/players.length-j-1)==0){
                    rand = units;
                }
                else{
                    while ((units-rand)/((x*y)/players.length-j-1)<1){
                        rand--;
                    }
                }
                board[x_placement][y_placement] = rand;
                units-=rand;
                player_board[x_placement][y_placement] = i+1;
            }
        }
        
    }
    
    
    /**
    Returns the int [][] board of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int int [][] board is returned.
    
    @return board - the 2D array showing the number of soldiers on each territory.
    */
    public int[][] getBoard(){
        return board;
    }
    
    /**
    Returns the int [][] player_board of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int [][] player_board is returned.
    
    @return player_board - The 2D array showing which player possesses which territory.
    */
    public int[][] getPlayerBoard(){
        return player_board;
    }
    
    /**
    Returns number of rows of the board of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int rows is returned.
    
    @return rows - The number of rows on the board of a Board object.
    */
    public int getRows(){
        return rows;
    }
    
    /**
    Returns the number of columns on the board of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int columns is returned.
    
    @return columns - The number of columns on the board of a Board object.
    */
    public int getColumns(){
        return columns;
    }
    
    /**
    Returns the size of the board of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int rows*columns is returned.
    
    @return rows*columns - The int value of the total size of the 2D board.
    */
    public int getBoardSize(){
        return rows*columns;
    }
    
    /**
    Returns the value of board[row][column] of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int board[row][column] of Board object is returned
    
    @param row -the row of the value that needs to be changed in the Board object.
    @param column - the column of the value that needs to be change in the Board object.
    @return board[row][column] - The int value stored within board[row][column].
    */
    public int getSoldiers(int row, int column){
        return board[row][column];
    }
    
    /**
    Sets the value of board[row][column] of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int board[row][column] of Board object is changed to the value of int soldiers
    
    @param row -the row of the value that needs to be changed in the Board object
    @param column - the column of the value that needs to be change in the Board object
    @param soldiers- the new value of board[row][column] of the Board object
    */
    public void changeBoard(int row, int column, int soldiers){
        board[row][column] = soldiers;
    }
    
    /**
    Sets the value of player_board[row][column] of a given Board object.
    Precondition: Board object must be initialized.
    Postcondition: int player_board[row][column] of Board object is changed to the value of int player
    
    @param row -the row of the value that needs to be changed in the Board object
    @param column - the column of the value that needs to be change in the Board object
    @param player - the new value of player_board[row][column] of the Board object
    */
    public void changePossession(int row, int column, int player){
        player_board[row][column] = player;
    }
}