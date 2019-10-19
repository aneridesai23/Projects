package cpsc2150.connectX;

/*
Name: Aneri Desai
Date: 11/18/2018
File Name: Connect4 gameboard
Class: 2150 section 002
*/

/**
 * Correspondence:
 * rows = getNumRow
 * column = getNumColumn
 * win = getNumToWin
 * board[][] = board[0...getNumRow-1][0...getNumColumn-1]
 *
 * @invariants
 * LOWROW <= Row <= HIGHROW
 * LOWCOL <= Column <= HIGHCOL
 * LOWWIN <= Win <= HIGHWIN
 * Board[i][j] = is an array that stores the each token
 *             - 0 <= i < Row (i is the row)
 *             - 0 <= j < Column (j is the column)
 */
public class GameBoard implements IGameBoard{
    //declaring variables
    public static final int LOWROW = 3;
    public static final int HIGHROW = 100;
    public static final int LOWCOL = 3;
    public static final int HIGHCOL = 100;
    public static final int LOWWIN = 3;
    public static final int HIGHWIN = 25;
    private int row;
    private int column;
    private int win;
    private char[][] board;

    /**
     * @param
     * rows - number of rows
     * @param
     * columns - number of columns
     * @param
     * wins - number of wins
     *
     * @require
     * LOWROW <= rows <= HIGHROW
     * LOWCOL <= column <= HIGHCOL
     * LOWWIN <= win <= HIGHWIN
     *
     * @ensure
     * row = rows
     * column = columns
     * win = wins
     * board[][] = ' '
     */
    public GameBoard(int rows, int columns, int wins){
        row = rows;
        column = columns;
        win = wins;

        board = new char[row][column];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                board[i][j] = ' '; //initializing each element to blank space
            }
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

    public void placeToken(char p, int c) {
        //place the token inside the board
        for (int i = 0; i < row; i++) {
            if (whatsAtPos(i,c) == ' ') {
                board[i][c] = p;
                break;
            }
        }
    }

    public char whatsAtPos(int r, int c) {
        char player = ' ';
        int siz = 0;
        siz = board.length;

        //checking to make sure that there is a value at the location in the list
        if(r < siz){
            player = board[r][c];
            return player;
        }
        return player;
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
    public String toString() {
        String string = "";
        String string1 = "";
        String finalString;

        for(int i = row-1; i >=0;  i--) {

            string += "| ";
            //loop to just print out the board with tokens
            for (int j = 0; j < column; j++) {
                string += whatsAtPos(i,j) + "| ";
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

