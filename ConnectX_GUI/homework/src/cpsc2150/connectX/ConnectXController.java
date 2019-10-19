package cpsc2150.connectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 *
 * This is where you will write code
 *
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Homework 3
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */

public class ConnectXController {
    //our current game that is being played
    private IGameBoard curGame;


    //The screen that provides our view
    private ConnectXView screen;



    public static final int MAX_PLAYERS = 10;
    //our play tokens are hard coded. We could make a screen to get those from the user, but
    //I want to keep this example simple
    private char[] players = {'X', 'O', 'Y', 'Z', 'A', 'K', 'E', 'J', 'N', 'H'};

    private int numPlayers;

   //varibales to keep track of the game
    private int playerToken;
    private boolean startNewgame;
    private boolean printToken;
    private int tempPlayerToken;

    /**
     *
     * @param model the board implementation
     * @param view the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np){
        this.curGame = model;
        this.screen = view;
        numPlayers = np;

        //intializing the variables
        startNewgame = false;
        playerToken = 0;
        printToken = false;
        tempPlayerToken = 0;
    }

    /**
     *
     *
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col) {

        int row = curGame.getNumRows();
        int column = curGame.getNumColumns();
        int win = curGame.getNumToWin();
        int rowPos = 0;
        boolean tie;
        boolean checkWin = false;

        if(startNewgame) {//starts a newgame if player wins the game or the game is tied
            playerToken = 0;
            newGame();//call the newgame function
        }

        boolean flag = curGame.checkIfFree(col);//call the checkIfFree function

        //if there is no space in the column
        if (!flag) {

            tie = curGame.checkTie();

            if (tie) {
                screen.setMessage("The board if full; tie game." + "\n" + "Press anywhere to start a new game.");
                startNewgame = true;
                printToken = true;
            } else {
                screen.setMessage("The column is full," + "still " + players[playerToken] + "'s turn.");
                tempPlayerToken = playerToken;
                printToken = true;
            }
        }//if there is space in the column
        else {
            //finding out what row to place the token in
            for (int j = 0; j < row; j++) {
                if (curGame.whatsAtPos(j, col) == ' ') {
                    rowPos = j;
                    break;
                }
            }

            //placing the token on the board
            screen.setMarker(rowPos, col, players[playerToken]);
            curGame.placeToken(players[playerToken], col);

            checkWin = curGame.checkForWin(col);
            tie = curGame.checkTie();

            if (checkWin) {//if there is a win
                screen.setMessage("Player " + players[playerToken] + " won." + "\n" + "Press anywhere to start a new game.");
                startNewgame = true;
                printToken = true;
            } else if(tie) {//check the tie again if the game tied after placing the last token
                screen.setMessage("The board is full; tie game." + "\n" + "Press anywhere to start a new game.");
                startNewgame = true;
                printToken = true;
            }else{

                playerToken++;//change the playertoken

                if (playerToken == numPlayers)//reset the player token if playerToken equals numPlayers
                    playerToken = 0;

                tempPlayerToken = playerToken;
                printToken = false;
            }
        }

        if(!printToken)//tells which player's turn it is
            screen.setMessage("It is " + players[tempPlayerToken] + "'s turn.");

    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame()
    {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}
