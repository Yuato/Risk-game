import java.util.ArrayList;

public class Player {
    //Instance variables for Player object
    private String player_name;
    private String player_color;
    private int territories;
    
    /**
    Initializes a Player object.
    Precondition: Player object must take a String name and String color.
    Postcondition: Instance variables String player_name and String player_color are initialized with 
    String name, and String color.
    
    @param name - String to initialize instance variable player_name for Player object
    @param color - String to initialize instance variable player_color for Player object
    */
    public Player(String name, String color){
        player_name = name;
        player_color = color;
    }
    
    /**
    Returns the String player_color of a given Player object.
    Precondition: Player object must be initialized.
    Postcondition: Returns String player_color accessed from the Player object.
    
    @return player_color - the ANSI color code of the Player object
    */
    public String getColor(){
        return player_color;
    }
    
    /**
    Returns the String player_color of a given Player object.
    Precondition: Player object must be initialized.
    Postcondition: Returns String player_name accessed from the Player object.
    
    @return player_name - the name of the Player object
    */
    public String getName(){
        return player_name;
    }
    
    /**
    Returns the int territories of a given Player object.
    Precondition: Player object must be initialized.
    Postcondition: Returns int territories accessed from the Player object.
    
    @return territories - the number of territories possessed by the Player object
    */
    public int getTerritories(){
        return territories;
    }
    
    /**
    Sets the territories value of a given Player object.
    Precondition: Player object must be initialized.
    Postcondition: int territories of Power object is changed to the value of int num_territories
    
    @param num_territories -the new value of territories to be set for Player object
    */
    public void setTerritories(int num_territories){
        territories = num_territories;
    }
}