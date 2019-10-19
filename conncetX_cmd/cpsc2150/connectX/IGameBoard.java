package cpsc2150.connectX;

/*
Name: Aneri Desai
Date: 09/27/2018
File Name: Connect4 gameboard
Class: 2150 section 002
*/

/**
 * This interface checks if the column is free, and if it is then checks if either the
 * player won horizonatally, vertically or diagonally, or if the game is tied. The interface does not
 * place a token, prints to the screen or check what is at the certain position
 *
 * Defines: Row: Z
 *          Column: Z
 *          Win: Z
 *
 * Constrains: 3 <= Row <= 100 and 3 <= Column <= 100 and 3<= Win <= 25
 *
 * Initialization Ensures: row = Row and column = Column and win = Win and (board[][] = board[Row][Column]
 *                        board[i][j] = ' ' ) or  (board = new HashMap<>())
 *
 */
public interface IGameBoard {

    /**
     *
     * @param
     * p Player mark
     * @param
     * c - is the column that player want to put their token in
     *
     * @requires
     * checkFree function should be called in main before calling this function
     * P is an uppercase character
     * 0 <= c < getNumColumn
     *
     * @ensure
     * the token is placed in the column that the player asked
     */
    void placeToken(char p, int c);

    /**
     *
     * @param
     * r is the row that the token was placed in
     * @param
     * c is the column that player want to put their token in
     *
     * @require
     * 0 <= c < getNumColumns
     * 0 <= r < getNumRows
     *
     * @return
     * what is at the position of that particular row and column
     *
     * @ensure
     * What is exactly at the position of that row and column
     * if no token is stored than return ' '
     */
    char whatsAtPos(int r, int c);

    /**
     *
     * @require
     * 3 <= Row <= 100
     *
     * @return
     * get number of rows
     *
     */
    int getNumRows();

    /**
     *
     * @require
     * 3 <= Column <= 100
     *
     * @return
     * number of columns
     */
    int getNumColumns();

    /**
     *
     * @require
     * 3 <= Win <= 25
     *
     * @return
     * win number
     */
    int getNumToWin();

    /**
     * @require
     * must call the placeToken function before this function
     *
     * @return
     * true - there is no black space in the last row
     * false - the last row has space available
     *
     * @ensure
     * no variables are changed in the function
     */
    default boolean checkTie(){
        //int k = 0;
        int j = getNumColumns()-1;

        //checking if the last row empty or not
        for(int i = 0; i < getNumColumns(); i++){
            if(whatsAtPos(j,i) == ' ')
                return false;
        }
        return true;
    }

    /**
     *
     * @param
     * c is the column that player want to put their mark in
     *
     * @requires
     * 0 <= c < getNumColumn
     *
     * @return
     * true- the column is not full
     * false- the column has no empty space
     *
     * @ensure
     * no variables are changed in the function
     */
    default boolean checkIfFree(int c){
        //checking that column is between 0 to 6
        if(c >= 0 && c < getNumColumns()) {

            //checking if the particular space is empty or not
            for (int i = 0; i < getNumRows(); i++) {
                if (whatsAtPos(i,c) == ' ') {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param
     * c is the column that player want to put their mark in
     *
     * @requires
     * placeToken should be called first
     * 0 <= c < getNumColumn
     *
     * @return
     * True - when player should have atleast won
     *    horizontally, vertically or diagonally
     * False - when player does not win horizontally,
     *      vertically or diagonally
     *
     * @ensure
     * calls checkHorizWin, checkVertWin, and checkDiagWin function for players
     * and should return true or false depending on the situation
     */
    default boolean checkForWin(int c){
        int token = c;
        int row = 0;
        char player;
        boolean flag1;
        boolean flag2;
        boolean flag3;

        //loop to find the row where token was placed in
        for(int j = (getNumRows()-1); j >= 0; j--){
            if(whatsAtPos(j,c) != ' '){
                row = j;
                break;
            }
        }

        //return the token at the position
        player = whatsAtPos(row,token);

        //calling each function to see if player won
        flag1 = checkHorizWin(row, token, player);
        flag2 = checkVertWin(row,token, player);
        flag3 = checkDiagWin(row, token, player);


        //returning true if player won horizontally, vertically or diagonally
        if(flag1 == true || flag2 == true || flag3 == true)
            return true;
        else
            return false;
    }

    /**
     *
     * @param
     * r is the row that the token was placed in
     * @param
     * c is the column that player want to put their token in
     * @param
     * p player mark
     *
     * @requires
     * P is an uppercase character
     * 0 <= c < getNumColumn
     * 0 <= r < getNumRow
     *
     * @return
     * true - if and only if the player has same number of wins as getNumWins with the same token in a row
     * false - same type of token are not in a row for consequently same number of wins
     *
     * @ensure
     * If the player get same number of win as getNumWin horizontally, the player should win
     */
    default boolean checkHorizWin(int r, int c, char p){
        int count = 1;

        //loop to check right side
        for(int i = c+1; i < getNumColumns(); i++){
            if(whatsAtPos(r, i) != p){
                break;
            }
            else
                count++;
        }

        //loop to check the left side
        for(int i = c-1; i >= 0; i--){ //go left
            if(whatsAtPos(r, i)!=p) {
                break;
            }else {
                count++;
            }
        }

        //checking if the player won or not
        if(count >= getNumToWin())
            return true;
        else
            return false;

    }

    /**
     *
     * @param
     * r is the row that the token was placed in
     * @param
     * c is the column that player want to put their token in
     * @param
     * p player mark
     *
     * @requires
     * The function shoud be called from checkForWin funciton
     * P is an uppercase character
     * 0 <= c < getNumColumn
     * 0 <= r < getNumRow
     *
     * @return
     * true - if and only if the player has same number of wins as getNumWins with the same token
     *        in a column consequently
     * false - same type of token are not in a column for consequently same number of wins
     *
     *@ensure
     *If the player get same number of win as getNumWin vertically, the player should win
     */
    default boolean checkVertWin(int r, int c, char p){
        int count = 1;

        //loop to go up
        for(int i = r+1; i < getNumRows(); i++){
            if(whatsAtPos(i, c) != p){
                break;
            }
            else
                count++;
        }

        //loop to check down
        for(int i = r-1; i >= 0 ; i--){
            if(whatsAtPos(i, c) != p) {
                break;
            }else {
                count++;
            }
        }

        //checking if the player won or not
        if(count >= getNumToWin())
            return true;
        else
            return false;

    }

    /**
     *
     * @param
     * r is the row that the token was placed in
     * @param
     * c is the column that player want to put their token in
     * @param
     * p player mark
     *
     * @requires
     * this function should be called from checkForWin function
     * P is an uppercase character
     * 0 <= c < getNumColumns
     * 0 <= r < getNumRows
     *
     * @return
     * true - if and only if the player has same number of wins as getNumWins with the same token
     *        diagonally or antidiagonally consequently
     * false - same type of token are not in diagonally or antidiagonally for consequently same number of wins
     *
     *@ensure
     *If the player get same number of win as getNumWin diagonally or antidiagonally, the player should win
     */
    default boolean checkDiagWin(int r, int c, char p){
        int i, j;
        int count = 1;

        //loop to check antidiagonal down
        for(i = r-1, j = c-1; i >= 0 && j >= 0 ; i--, j--){
            if(whatsAtPos(i,j) != p){
                break;
            }
            else
                count++;
        }

        //loop to check antidiagonal up
        for(i = r+1, j = c+1; i < getNumRows() && j < getNumColumns(); i++, j++){
            if(whatsAtPos(i,j) != p){
                break;
            }
            else
                count++;
        }

        //return true if player won
        if(count >= getNumToWin())
            return true;

        count = 1; //resetes the count

        //loop to check diagonal down
        for(i = r-1, j = c+1; i >= 0 && j < getNumColumns(); i--, j++){
            if(whatsAtPos(i,j) != p){
                break;
            }
            else
                count++;
        }

        //loop to check diagonal up
        for(i = r+1, j = c-1; i < getNumRows() && j >= 0 ; i++, j--){
            if(whatsAtPos(i,j) != p){
                break;
            }
            else
                count++;
        }

        //return true if player won
        if(count >= getNumToWin())
            return true;
        else
            return false;
    }

}
