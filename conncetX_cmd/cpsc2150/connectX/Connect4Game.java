package cpsc2150.connectX;
import java.util.*;
/*
Name: Aneri Desai
Class: cpsc 2150 002
File name: Connect4Game main
Date: 09/27/2018
*/

public class Connect4Game {

    public static void main(String[] args) {

        char play;

        do {
                //initialzing variables
                play = ' ';

                int count = 0;
                int column = 0;
                char var;
                boolean free = false;
                boolean repeat = true;
                boolean tie = false;
                boolean win = false;
                String gameKind;

                Scanner keyboard = new Scanner(System.in);

                //calling functions to get the values
                char player[] = whatPlayer();
                int rows = getRows();
                int cols = getCols();
                int wins = getWin();

                //making an object of interface
                IGameBoard boardObj;
                boardObj = new GameBoard(rows, cols, wins);//initializing the object

                do {
                    System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game (M/m)?");
                    gameKind = keyboard.nextLine();
                    //System.out.println(gameKind.charAt(0));

                    if (gameKind.charAt(0) == 'f' || gameKind.charAt(0) == 'F') {
                        boardObj = new GameBoard(rows, cols, wins);
                        break;
                    }
                    if (gameKind.charAt(0) == 'm' || gameKind.charAt(0) == 'M') {
                        boardObj = new GameBoardMem(rows, cols, wins);
                        break;
                    }

                    if(gameKind.charAt(0)!= 'f' || gameKind.charAt(0)!= 'F'
                            || gameKind.charAt(0)!= 'm' || gameKind.charAt(0)!= 'M'){
                        System.out.println("Please enter F or M");
                    }
                } while ( gameKind.charAt(0)!= 'f' || gameKind.charAt(0)!= 'F'
                        || gameKind.charAt(0)!= 'm' || gameKind.charAt(0)!= 'M');

                //main loop to continue the game until player wins or game is tied
                while(repeat) {

                    //printing the board
                    String string = boardObj.toString();
                    System.out.println(string);

                    if(count == player.length)
                        count = 0;

                    var = player[count];
                    count++;

                    do{
                        int col = cols-1;

                        System.out.println("Player " + var + ", what column do you want to place your marker in?");
                        column = keyboard.nextInt();

                        //conditions to check if the number followed the guidelines
                        if (column < 0)
                            System.out.println("Column cannot be less than 0");

                        if (column >= cols)
                            System.out.println("Column cannot be greater than " + col);

                        do {
                            if (column < cols && column >= 0) {
                                free = boardObj.checkIfFree(column);
                                if(!free){
                                    System.out.println("Column is full");
                                    break;
                                }
                                else
                                    break;
                            }
                            else
                                break;

                        }while(!free);


                    } while (!free || column < 0 || column >= cols);

                    //calling the function to place the token in the game board
                    boardObj.placeToken(var,column);

                    while (free && column >= 0 && column < cols){

                        win = boardObj.checkForWin(column);
                        tie = boardObj.checkTie();

                        //checks if the player won the game or the game was tied or not
                        if (win) {
                            String string1 = boardObj.toString();
                            System.out.println(string1);

                            System.out.println("Player " + var + " Won!");

                        } else if (tie) {

                            String string1 = boardObj.toString();
                            System.out.println(string1);

                            System.out.println("The game is tied");
                        }
                        break;
                    }

                    //ask if the user wants to play the game after the tie or winning the game
                    while ((win == true || tie == true) && (play != 'N'
                            || play != 'n' || play != 'Y' || play != 'y')) {

                        System.out.println("Would you like to play again? Y/N");
                        play = keyboard.next().charAt(0);

                        if (play == 'N' || play == 'n' || play == 'Y' || play == 'y') {
                            repeat = false;
                            break;
                        }
                    }
                }
            } while(play == 'Y' || play == 'y');
        }

        /**
         *
         * @return
         * number of rows
         *
         * @ensure
         * 3 <= rows <= 25
         */
        private static int getRows(){

            Scanner keyboard = new Scanner(System.in);
            int rows = 0;
            int lessRows = 3;
            int highRow = 100;

            //loop to ensure that the rows are valid
            while(rows < lessRows || rows > highRow) {
                System.out.println("How many rows should be on the board?");
                rows = keyboard.nextInt();

                if (rows < lessRows) {
                    System.out.println("Must have at least 3 rows");
                }

                if (rows > highRow) {
                    System.out.println("Can have at most 100 row");
                }
            }

            return rows;

        }

        /**
         *
         * @return
         * number of columns
         *
         * @ensure
         * 3 <= columns <= 25
         */
        private static int getCols(){
            Scanner keyboard = new Scanner(System.in);
            int cols = 0;
            int lessCols = 3;
            int highCols = 100;

            //loop to ensure that the columns are valid
            while(cols < lessCols || cols > highCols){
                System.out.println("How many columns should be on the board?");
                cols = keyboard.nextInt();

                if (cols < lessCols) {
                    System.out.println("Must have at least 3 columns");
                }

                if (cols > highCols) {
                    System.out.println("Can have at most 100 columns");
                }
            }
            return cols;
        }

        /**
         *
         * @return
         * number of wins
         *
         * @ensure
         * 3 <= wins <= 25
         */
        private static int getWin(){
            Scanner keyboard = new Scanner(System.in);
            int wins = 0;
            int lowWin = 3;
            int highWin = 25;

            //loop to ensure that the wins are valid
            while(wins < lowWin || wins > highWin) {
                System.out.println("How many in a row to win?");
                wins = keyboard.nextInt();

                if(wins < lowWin) {
                    System.out.println("Must have at least 3 in a row to win");
                }

                if(wins > highWin) {
                    System.out.println("Can have at most 25 in a row to win");
                }
            }
            return wins;
        }

    /**
     *
     * @return
     * array of player
     *
     * @ensure
     * 2 <= player.length <= 10
     * player[i] = uppercase character of the each player from 1 to player.length
     * Player[i] != (player[n-1] || player[n+1])
     */
    private static char[] whatPlayer(){ //just make variable equal to whatever we get
            //initializing the variables
            Scanner keyboard = new Scanner(System.in);
            int numPlay = 0;
            char charOfPlayer = ' ';
            char[] player;
            int lowPlayer = 2;
            int highPlayer = 10;
            String playerChar;

            //loop to continue until correct number of players are entered
            while(numPlay < lowPlayer || numPlay > highPlayer){
                System.out.println("How many players?");
                numPlay = keyboard.nextInt();
                keyboard.nextLine();

                if(numPlay < lowPlayer)
                    System.out.println("Must be at least 2 players");
                if(numPlay > highPlayer)
                    System.out.println("Must be 10 players or fewer");
            }

            player = new char[numPlay]; //initializing the array to number of player
            int i = 1;
            int j,k;
            boolean flag = true;

            while (flag) {
                System.out.println("Enter the character to represent player " + i);
                playerChar = keyboard.nextLine();
                charOfPlayer = playerChar.charAt(0);
                j = i -1;
                player[j] = Character.toUpperCase(charOfPlayer);

                k = j - 1;
                //loop to make sure that the same token is not entered twice
                while (k >= 0) {
                    if (player[k] == player[j]) {
                        System.out.println(player[k] + " is already taken as a player token!");
                        break;
                    } else
                        k--;
                }
                if (k < 0)
                    i++;

                if (i > numPlay)
                    flag = false;
            }
            return player;
        }

    }
