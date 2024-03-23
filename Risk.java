import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class Risk
{
    //static variables for RISK game
    private static final String ANSI_RESET = "\u001B[0m";
    private static Scanner sc = new Scanner(System.in);
    private static Player [] players;
    private static Board board;
    
    /**
    Finds if two coordinates are connected if travelling to territories owned by the player.
    Precondition: board object must be initialized along with int index, ArrayList<Coordinate>start, Coordinate end, ArrayList<Coordinate>visited.
    Postcondition: Returns true if the coordinate are connected or false otherwise
    
    @param index - int to understand which players turn it is and the territories the soldiers can travel through
    @param start - ArrayList<Coordinate> to have the starting coordinate and to store possible coordinate that can be travelled through
    @param end - Coordinate to find the coordinate the user wants to send soldiers to
    @param visited - ArrayList<Coordinate> to make sure a Coordinate isn't checked twice
    */
    public static boolean searchCoord (int index, ArrayList<Coordinate>start, Coordinate end, ArrayList<Coordinate>visited){
        int [][] player_board = board.getPlayerBoard();
        int x; int y;
        while (start.size()>0){
            Coordinate newcoord;
            Coordinate node = start.remove(0);
            x = node.getX();
            y = node.getY();
            if (node.equals(end)){
                return true;
            }
            else if (x>=0&&x<board.getRows()&&y>=0&&y<board.getColumns()&&player_board[node.getX()][node.getY()]==index+1){
                newcoord = new Coordinate(x+1, y);
                if (!findCoord(start,newcoord)){
                    start.add(newcoord);
                    visited.add(newcoord);
                }
                newcoord = new Coordinate(x-1, y);
                if (!findCoord(start,newcoord)){
                    start.add(newcoord);
                    visited.add(newcoord);
                }
                newcoord = new Coordinate(x, y+1);
                if (!findCoord(start,newcoord)){
                    start.add(newcoord);
                    visited.add(newcoord);
                }
                newcoord = new Coordinate(x, y-1);
                if (!findCoord(start,newcoord)){
                    start.add(newcoord);
                    visited.add(newcoord);
                }
            }
        }
        return false;
    }
    
    /**
    Linear search of an ArrayList to search for the same Coordinate object
    Precondition: Takes in an ArrayList <Coordinate> coords and Coordinate key.
    Postcondition: Returns true if the object key is in the ArrayList and false otherwise
    
    @param coords - ArrayList<Coordinate> with Coordinate objects initialized
    @param key - Coordinate object to find within coords
    */
    public static boolean findCoord(ArrayList <Coordinate> coords, Coordinate key){
        for (int i = 0; i<coords.size(); i++){
            if (key.equals(coords.get(i))){
                return true;
            }
        }
        return false;
    }
    
    /**
    Delays console outputs by int time.
    Precondition: Must take an int time value.
    Postcondition:  Delays console outputs by int time milliseconds
    
    @param time - int to stop console outputs for this amount of milliseconds
    */
    public static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        }
    }
    
    //prints the current players that are playing 
    public static void printPlayers(){
        for (int i = 0; i<players.length; i++){
            System.out.print((i+1)+". "+players[i].getColor()+players[i].getName()+ANSI_RESET);
            if (i+1!=players.length){
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    /**
    Returns a valid Coordinate object from user inputs
    Precondition: Must take an String terr value.
    Postcondition:  Returns a Coordinate class object
    
    @param terr - String coordinate that the user wants to input
    @return retcoord - returns the Coordinate object based on user inputs 
    */
    public static Coordinate getCoordinate(String terr){
        int row = -1; int column = -1;
        if (terr.length()==2){
            row = ((int)terr.charAt(0))-49;
            column = ((int)terr.charAt(1))-65;
            if (row<0||row>=board.getRows()){
                row = ((int)terr.charAt(1))-49;
                column = ((int)terr.charAt(0))-65;
            }
        }
        Coordinate retcoord;
        retcoord = new Coordinate(row, column);
        while (terr.length()!=2||row<0||row>=board.getRows()||column<0||column>=board.getColumns()){
            row = -1; column = -1;
            System.out.print("Invalid coordinate, please input another coordinate:");
            terr = sc.nextLine();
            if (terr.length()==2){
                row = ((int)terr.charAt(0))-49;
                column = ((int)terr.charAt(1))-65;
                retcoord = new Coordinate (terr);
                if (row<0||row>=board.getRows()){
                    row = ((int)terr.charAt(1))-49;
                    column = ((int)terr.charAt(0))-65;
                    retcoord = new Coordinate (row,column);
                }
            }
        }
        return retcoord;
    }
    
    /**
    Allows the user to place troops into territories they control on the board.
    Precondition: Must take an int index.
    Postcondition:  Changes the Board class object called board and adds value.
    
    @param index - int to know which players turn it is and to validate if the territory belongs to them
    */
    public static void reinforce(int index){
        int [][] player_board = board.getPlayerBoard();
        int units = players[index].getTerritories();
        String terr; int reinforcement = units+1;
        String hold; int row = -1; int column = -1;
        
        while (units>0){
            reinforcement = units+1;
            System.out.println("Player "+(index+1)+"("+players[index].getName()+") you still have "+ units+" reinforcements left, which territory do you want to reinforce: ");
            terr = sc.nextLine();
            if (terr.length()==2){
                row = ((int)terr.charAt(0))-49;
                column = ((int)terr.charAt(1))-65;
                if (row<0||row>=board.getRows()){
                    row = ((int)terr.charAt(1))-49;
                    column = ((int)terr.charAt(0))-65;
                }
            }
            while(terr.length()!=2||row<0||row>=board.getRows()||column<0||column>=board.getColumns()||player_board[row][column]!=index+1){
                System.out.print("Please enter a territory under your control (enter 0 to get players and their matching color, -1 for the board and the number of soldiers): ");
                terr = sc.nextLine();
                if (terr.equals("0")){
                    printPlayers();
                }
                else if (terr.equals("-1")){
                    printBoard(board.getBoard());
                }
                if (terr.length()==2){
                    row = ((int)terr.charAt(0))-49;
                    column = ((int)terr.charAt(1))-65;
                    if (row<0||row>=board.getRows()){
                        row = ((int)terr.charAt(1))-49;
                        column = ((int)terr.charAt(0))-65;
                    }
                }
            }
            while (reinforcement>units){
                System.out.print("Input how many reinforcements you want to send to territory "+terr+": ");
                reinforcement = sc.nextInt();
                hold = sc.nextLine();
            }
            board.changeBoard(row,column,board.getSoldiers(row,column)+reinforcement);
            System.out.println("The new board is:");
            printBoard(board.getBoard());
            units-=reinforcement;
        }
    }

    
    /**
    Allows they player to choose which territory they want to attack and try to conquer.
    Precondition: Must take an int index.
    Postcondition:  Starts roll_attack method.
    
    @param index - Shows which player is in their attack phase.
    */
    public static void attack(int index){
        System.out.println("Welcome player "+(index+1)+"("+players[index].getName()+") to your attack phase!");
        System.out.print("Would you like to start your attack(type \"no\" to leave attack phase): ");
        String continue_attack = sc.nextLine();
        int [][] Player_board = board.getPlayerBoard();
        continue_attack = continue_attack.toLowerCase();
        while (!continue_attack.equals("no")){
            System.out.println("The board currently looks like:");
            printBoard(board.getBoard());
            System.out.print("Which territory would you like to attack: ");
            String coord = sc.nextLine();
            Coordinate atk = getCoordinate(coord);
            System.out.print("Which territory would you like your troops to attack from (must be adjacent to the attack coordinate): ");
            coord = sc.nextLine();
            Coordinate from = getCoordinate(coord);
            if (Math.abs(atk.getX()-from.getX())+Math.abs(atk.getY()-from.getY())!=1){
                System.out.println("These coordinates aren't adjacent.");
            }
            else if (Player_board[from.getX()][from.getY()]!=index+1){
                System.out.println("You do not own this territory!");
            }
            else if (Player_board[atk.getX()][atk.getY()]==Player_board[from.getX()][from.getY()]){
                System.out.println("Territory is controlled by the same player.");
            }
            else{
                roll_attack(index,atk,from);
            }
            
            System.out.print("Would you like to continue your attack(type \"no\" to leave attack phase):");
            continue_attack = sc.nextLine();
            continue_attack = continue_attack.toLowerCase();
        }
        
    }
    
    /**
    Rolls die for both players and the player with the smaller roll loses a soldier. If defenders lose all soldiers attackers win the territory.
    Precondition: Must take an int index, Coordinate attack, Coordinate from to determine the territory to attack and where it is attacked from.
    Postcondition:  Changes Board board into new number of soldiers and new possession if any.
    
    @param index - int to know which players turn it is and to validate if the territory be
    @param attack - Coordinate of where the attack is and number of defending soldiers.
    @param from - Coordinate of where the attack is coming from to determine number of soldiers for the attacker.
    */
    public static void roll_attack(int index, Coordinate attack, Coordinate from){
        int [][] playingBoard = board.getBoard();
        int [][] playerBoard = board.getPlayerBoard();
        while (playingBoard[from.getX()][from.getY()]>1 && playingBoard[attack.getX()][attack.getY()]>0){
            System.out.println();
            System.out.println("Attackers left: "+(playingBoard[from.getX()][from.getY()]-1));
            System.out.println("Defenders left: "+playingBoard[attack.getX()][attack.getY()]);
            System.out.println("Rolling for attackers...");
            int attacker = (int)(Math.random()*6)+1;
            sleep(2000);
            System.out.println("Player "+playerBoard[from.getX()][from.getY()]+" rolls a "+attacker);
            System.out.println("Rolling for defenders...");
            int defender = (int)(Math.random()*6)+1;
            sleep(2000);
            System.out.println("Player "+playerBoard[attack.getX()][attack.getY()]+" rolls a "+defender);
            if (attacker>defender){
                System.out.println("Attackers advance, -1 soldier for player "+playerBoard[attack.getX()][attack.getY()]);
                playingBoard[attack.getX()][attack.getY()] = playingBoard[attack.getX()][attack.getY()]-1;
            }
            else if (defender==attacker){
                System.out.println("Tie, no losses from either side.");
            }
            else {
                System.out.println("Defenders push back, -1 soldier for player "+playerBoard[from.getX()][from.getY()]);
                playingBoard[from.getX()][from.getY()] = playingBoard[from.getX()][from.getY()]-1;
            }
        }
        int defend_player = playerBoard[attack.getX()][attack.getY()];
        if (playingBoard[attack.getX()][attack.getY()]==0){
            System.out.println("\nAttackers win! GLORY to player "+playerBoard[from.getX()][from.getY()]+"!");
            board.changeBoard(attack.getX(),attack.getY(),playingBoard[from.getX()][from.getY()]-1);
            board.changePossession(attack.getX(), attack.getY(), index+1);
            players[index].setTerritories(players[index].getTerritories()+1);
            players[defend_player-1].setTerritories(players[defend_player-1].getTerritories()-1);
        }
        else {
            System.out.println("\nDefenders come out victorious! PRAISE to player "+playerBoard[attack.getX()][attack.getY()]+"!");
            board.changeBoard(attack.getX(), attack.getY(), playingBoard[attack.getX()][attack.getY()]);
        }
        board.changeBoard(from.getX(),from.getY(),1);
        System.out.println("The new board is: ");
        printBoard(board.getBoard());
    }
    
    /**
    Allows the player to move soldiers to territories that are traversable and owned.
    Precondition: Must take an int index.
    Postcondition:  Changes board depending on where soldiers are relocated to.
    
    @param index - Shows which player is in their maneuver phase.
    */
    public static void maneuver(int index){
        System.out.println("Welcome to your maneuver phase player"+(index+1)+"!");
        System.out.println("Select the territories you want to send soldiers to and from where(must leave one troop at any territory)");
        System.out.print("Would you like to start maneuvering(type \"no\" to leave maneuver phase): ");
        String continue_maneuver = sc.nextLine();
        int [][] Player_board = board.getPlayerBoard();
        int [][] playing_board = board.getBoard();
        continue_maneuver = continue_maneuver.toLowerCase();
        ArrayList<Coordinate>visited = new ArrayList<Coordinate>();
        while (!continue_maneuver.equals("no")){
            playing_board = board.getBoard();
            System.out.println("The board currently looks like:");
            printBoard(board.getBoard());
            System.out.print("Input the territory you want to move your troops to: ");
            String coord = sc.nextLine();
            Coordinate to = getCoordinate(coord);
            System.out.print("Which territory would you like your troops to move from: ");
            coord = sc.nextLine();
            Coordinate from = getCoordinate(coord);
            System.out.print("Input how many troops do you want to send to "+coord+": ");
            int troops = sc.nextInt();
            coord = sc.nextLine();
            ArrayList <Coordinate> start = new ArrayList<Coordinate>();
            start.clear();
            start.add(from);
            if (!searchCoord(index,start,to,visited)){
                System.out.println("These coordinates aren't connected.");
            }
            else if (Player_board[from.getX()][from.getY()]!=index+1){
                System.out.println("You do not own these territories!");
            }
            else if (Player_board[to.getX()][to.getY()]!=index+1){
                System.out.println("You do not own these territories!");
            }
            else if (troops>playing_board[from.getX()][from.getY()]-1){
                System.out.println("You do not have enough soldiers!");
            }
            else{
                board.changeBoard(to.getX(),to.getY(),troops+playing_board[to.getX()][to.getY()]);
                board.changeBoard(from.getX(),from.getY(),playing_board[from.getX()][from.getY()]-troops);
            }
            System.out.print("Would you like to continue maneuvering(type \"no\" to leave maneuver phase): ");
            continue_maneuver = sc.nextLine();
            continue_maneuver = continue_maneuver.toLowerCase();
        }
        System.out.println("The new board is: ");
        printBoard(board.getBoard());
    }
    
    /**
    Prints out all available commands that the user can use.
    Precondition: NULL.
    Postcondition:  Prints all commands that are available .
    */
    public static void commands(){
        System.out.println();
        System.out.println("1. Clear console");
        System.out.println("2. Print playing board");
        System.out.println("3. Print players");
        System.out.println("4. escape (type anything)");
    }
    
    /**
    Prints out the String ArrayList.
    Precondition: Must take in an initialized ArrayList<String>list.
    Postcondition:  Prints out list.
    
    @param list - ArrayList<String> that shows the ArrayList that needs to be printed.
    */
    public static void print_list(ArrayList<String> list){
        for (int i = 0; i<list.size(); i++){
            System.out.print(list.get(i));
            if (i+1!=list.size()){
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    /**
    Searches an ArrayList to find the key that is desired.
    Precondition: Must take an ArrayList<String> list to search through and a String key to search for.
    Postcondition: Returns int index of the location of key or -1 if not found.
    
    @param list - ArrayList<String> to linear search through.
    @param key - String to search for in list.
    */
    public static int linear_search(ArrayList<String> list, String key){
        for (int i = 0; i<list.size(); i++){
            if (list.get(i).equals(key)){
                return i;
            }
        }
        return -1;
    }
    
    /**
    Prints out a 2D array and formats it.
    Precondition: Takes in an initialized int [][] arr.
    Postcondition: Prints out the 2D array with colors dependant on who possesses the square and formats the array.
    
    @param arr - int [][] that is to be printed out to the console.
    */
    public static void printBoard(int [][]arr){
        int [][] player_arr = board.getPlayerBoard();
        int rows = board.getRows(); int columns = board.getColumns();
        int chr = 65;
        System.out.print("    |");
        for (int i = 0; i<columns; i++){
            System.out.print("   "+(char)(chr+i)+"   |");
        }
        System.out.println("");
        System.out.print("----+");
        for (int i = 0; i<columns; i++){
            System.out.print("-------+");
        }
        System.out.println();
        int hold;
        for (int i = 0; i<rows; i++){
            System.out.print("  "+(i+1)+" |");
            for (int j = 0; j<columns; j++){
                String color = players[(player_arr[i][j]-1)].getColor();
                System.out.print("   "+color+arr[i][j]+ANSI_RESET);
                int size = 0;
                hold = arr[i][j];
                while (hold/10>=1){
                    size++;
                    hold = hold/10;
                }
                for (int z = 0; z<3-size;z++){
                    System.out.print(" ");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.print("----+");
            for (int j = 0; j<columns; j++){
                System.out.print("-------+");
            }
            System.out.println();
        }
    }
    
    /**
    Starts the game allowing players to choose colors, set up the board, and choose a name.
    Precondition: NULL.
    Postcondition: Starts the game allowing all other methods in the Risk class.
    */
    public static void start_game(){
        System.out.println("Hello, welcome to Risk!");
        System.out.print("Please input the number of players: ");
        int num_players = sc.nextInt();
        String name = sc.nextLine();
        while (num_players<2||num_players>5){
            System.out.print("Please input a valid number of players (2-5): ");
            num_players = sc.nextInt();
            name = sc.nextLine();
        }
        
        
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("\u001B[31m");
        colors.add("\u001B[34m");
        colors.add("\u001B[32m");
        colors.add("\u001B[33m");
        colors.add("\u001B[35m");
        ArrayList <String> color_name = new ArrayList<String>();
        color_name.add("red");
        color_name.add("blue");
        color_name.add("green");
        color_name.add("yellow");
        color_name.add("purple");
        
        players  = new Player [num_players];
        for (int i = 0; i<num_players; i++){
            System.out.print("Player "+(i+1)+", please input your name: ");
            name = sc.nextLine();
            int col_select = -1;
            while (col_select<0){
                System.out.print("The color options are: ");
                Risk.print_list(color_name);
                System.out.print("Please choose your color (enter the name of the desired color): ");
                String chosen_color = sc.nextLine().toLowerCase();
                col_select = Risk.linear_search(color_name, chosen_color);
            }
            players[i] = new Player(name, colors.get(col_select));
            System.out.println("Player "+players[i].getName()+" has chosen the color "+players[i].getColor()+color_name.get(col_select)+ANSI_RESET+".");
            color_name.remove(col_select);
            colors.remove(col_select);
            System.out.println();
        }
        int x = 1;
        int y = 1;
        String holder;
        while (!((x*y)%num_players==0&&x>1&&y>1&&x<=5&&y<=5)){
            System.out.print("Input the number of rows your board will have: ");
            x = sc.nextInt();
            holder = sc.nextLine();
            System.out.print("Input the number of columns your board will have: ");
            y = sc.nextInt();
            holder = sc.nextLine();
        }
        board = new Board(x,y, players);
        for (int i = 0; i<players.length; i++){
            players[i].setTerritories((x*y)/players.length);
        }
        printBoard(board.getBoard());
        System.out.println();
        System.out.println("LET THE GAMES BEGIN!\n");
        int index = (int)(Math.random()*players.length);
        int winner = 0;
        while (true){
            play(index, 1);
            index = (index+1)%players.length;
            int players_left = 0;
            for (int i = 0; i<players.length; i++){
                if (players[i].getTerritories()>0){
                    players_left++;
                    winner = i;
                }
            }
            if (players_left==1){
                break;
            }
        }
        System.out.println("CONGRATS player "+(winner+1)+"("+players[winner].getName()+") YOU WIN!!!");
        
    }
    
    /**
    Allows the player to go through each of their phases and checks if they have territories or else skip.
    Precondition: Takes in an int index signifying which players turn it is and an int phase for which phase the player is at in their turn.
    Postcondition: Goes to reinforce, attack, commands or maneuver method.
    
    @param index - int to signofy which players turn it is.
    @param phase - int to signify which phase the player is currently on in their turn.
    */
    public static void play(int index, int phase){
        String cmd ="";
        if (players[index].getTerritories()==0){
            System.out.println("Player "+(index+1)+"("+players[index].getName()+") you have been eliminated, moving to next player.");
            phase = 4;
        }
        else{
            System.out.print("Player "+(index+1)+"("+players[index].getName()+") it is your turn, choose an action (type \"command\" for special commands, type nothing to move to next phase");
            if (phase == 1){
                System.out.print("(REINFORCE phase)):");
            }
            else if (phase==2){
                System.out.print("(ATTACK phase)):");
            }
            else{
                System.out.print("(MANEUVER phase)):");
            }
            cmd = sc.nextLine();
            cmd = cmd.toLowerCase();
        }
        if (cmd.equals("command")){
            System.out.print("Which of the following command would you want:");
            commands();
            cmd = sc.nextLine();
            if (cmd.equals("1")){
                System.out.print("\033[H\033[2J");  
                System.out.flush();  
            }
            else if (cmd.equals("2")){
                printBoard(board.getBoard());
            }
            else if (cmd.equals("3")){
                printPlayers();
            }
            else{
                System.out.println("Escaped.");
            }
            play(index,phase);
        }
        else if (phase==1){
            System.out.println("\nPlayer "+(index+1)+"("+players[index].getName()+") it is your turn, start by reinforcing your territories!");
            reinforce(index);
            phase++;
            play(index, phase);
        }
        else if (phase == 2){
            System.out.println("\nPlayer "+(index+1)+"("+players[index].getName()+") begin your attack!");
            attack(index);
            phase++;
            play(index, phase);
        }
        else if (phase == 3){
            System.out.println("\nPlayer "+(index+1)+"("+players[index].getName()+") maneuver your troops!");
            maneuver(index);
            phase = 1; 
            index = (index+1)%players.length;
        }
    }
    
    //driver code
    public static void main(String[] args)
    {
        boolean play = true;
        while (play){
            Risk.start_game();
            System.out.print("Would you like to play again (enter Y to play again, anything else to exit): ");
            String again = sc.nextLine().toUpperCase();
            if (again.equals("Y")){
                play = true;
            }
            else {
                play = false;
            }
        }
    }
}