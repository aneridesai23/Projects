package cpsc2150.connectX;
import java.util.*;

/*
Name: Aneri Desai
Date: 09/27/2018
File Name: Connect4 gameboard
Class: 2150 section 002
*/

/**
 * Correspondence:
 * rows = getNumRow
 * column = getNumColumn
 * win = getNumToWin
 * board< , > = board<0...getNumColumn-1, List<0...getNumRow>>>
 *
 * @invariants
 * 3 <= Row <= 100
 * 3 <= Column <= 100
 * 3<= Win <= 25
 * Board = is a map that stores the each token of player inside list
 *             - Integer(column)
 *             - list<Character>(token)
 */
public class GameBoardMem implements IGameBoard {

    private int row;
    private int column;
    private int win;
    private Map<Integer, List<Character>>board;

    /**
     * @param
     * rows - number of rows
     * @param
     * columns - number of columns
     * @param
     * wins - number of wins
     *
     * @ensure
     * row = rows
     * column = columns
     * win = wins
     * board = new HashMap<>()
     * empty list should be initialized for each column
     */
    public GameBoardMem(int rows, int columns, int wins){
        row = rows;
        column = columns;
        win = wins;
        board = new HashMap<>();

        //initializing the list to the empty list
        for(int i = 0; i < column; i++) {
            List<Character> listi = new ArrayList<Character>();
            board.put(i,listi);
        }

    }

    public int getNumRows(){
        return row;
    }

    public int getNumColumns(){
        return column;
    }

    public int getNumToWin(){
        return win;
    }

    public char whatsAtPos(int r, int c){
        char player = ' ';
        int siz = board.get(c).size();

        //checking to make sure that there is a value at the location in the list
        if(r < siz){
            player = board.get(c).get(r);
            return player;
        }
        return player;
    }

    public void placeToken(char p, int c){
        board.get(c).add(p);
    }

    /**
     *
     * @return
     * the current board game
     *
     * @ensure
     * should print board exactly the way it is
     */
    @Override
    public String toString(){
        String string = "";
        String string1 = "";
        String finalString;

        for(int i = row-1; i >= 0; i--) {

            string += "| ";

            //loop to just print out the board with tokens
            for (int j = 0; j < column; j++) {
                //string += whatsAtPos(i,j) + "| ";
                char t = whatsAtPos(i,j);
                string += t + "| ";

            }

            string += "\n";
        }

        string1 += "| ";

        //loop for the space of either being one or two for the top row
        for(int k = 0; k < column; k++){
            int highCol = 9;
            if(k >= highCol)
                string1 += k + "|";
            else
                string1 += k +"| ";
        }
        string1 += "\n";

        //concatinating two strings into one string
        finalString = string1 + string;

        return finalString;
    }

}
