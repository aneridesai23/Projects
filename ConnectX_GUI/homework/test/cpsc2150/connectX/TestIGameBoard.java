package cpsc2150.connectX;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestIGameBoard {

    @Test
    public void testMinConstruct(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(expRow, gb.getNumRows());
        assertEquals(expCol,gb.getNumColumns());
        assertEquals(expWin,gb.getNumToWin());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void testMaxConstruct(){
        int expRow = 100;
        int expCol = 100;
        int expWin = 25;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(expRow, gb.getNumRows());
        assertEquals(expCol,gb.getNumColumns());
        assertEquals(expWin,gb.getNumToWin());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void testMidConstruct(){
        int expRow = 50;
        int expCol = 50;
        int expWin = 13;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(expRow, gb.getNumRows());
        assertEquals(expCol,gb.getNumColumns());
        assertEquals(expWin,gb.getNumToWin());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void testColFullIfFree(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);

        assertEquals(false,gb.checkIfFree(0));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void testEmptyIfFree(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(true,gb.checkIfFree(0));
        assertEquals(true,gb.checkIfFree(1));
        assertEquals(true,gb.checkIfFree(2));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void testFullIfFree(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false,gb.checkIfFree(0));
        assertEquals(false,gb.checkIfFree(1));
        assertEquals(false,gb.checkIfFree(2));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkEmptyBoardHorizWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(false, gb.checkHorizWin(0,0,'O'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkWinHorizWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][]board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][1] = 'X';
        board[0][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('X',2);

        assertEquals(true, gb.checkHorizWin(0,1,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkNoHorizWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false, gb.checkHorizWin(2,2,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkDifHorizWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][]board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][1] = 'X';
        board[0][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('O',2);

        assertEquals(false, gb.checkHorizWin(0,0,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkFullBoardHorizWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 4;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[2][1] = 'X';
        board[0][2] = 'O';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('X',1);
        gb.placeToken('O',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false, gb.checkHorizWin(2,2,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkEmptyBoardVertWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(false, gb.checkVertWin(0,0,'O'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkWinVertWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][]board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'X';
        board[2][0] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);

        assertEquals(true, gb.checkVertWin(1,0,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkNoVertWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false, gb.checkVertWin(2,2,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkDifVertWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][]board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][1] = 'X';
        board[1][1] = 'X';
        board[2][1] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',1);
        gb.placeToken('X',1);
        gb.placeToken('O',1);

        assertEquals(false, gb.checkVertWin(0,0,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkFullBoardVertWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 4;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[2][1] = 'X';
        board[0][2] = 'O';
        board[1][2] = 'O';
        board[2][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('X',1);
        gb.placeToken('O',2);
        gb.placeToken('O',2);
        gb.placeToken('O',2);

        assertEquals(false, gb.checkVertWin(2,2,'O'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkEmptyBoardDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(false,gb.checkDiagWin(0,0,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkLeftDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'X';
        board[2][0] = 'O';
        board[0][1] = 'X';
        board[1][1] = 'O';
        board[0][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('O',2);

        assertEquals(true,gb.checkDiagWin(0,2,'O'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkRightDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[0][2] = 'O';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(true,gb.checkDiagWin(2,2,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkDifLeftDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'X';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'O';
        board[0][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('O',1);
        gb.placeToken('O',2);

        assertEquals(false,gb.checkDiagWin(0,2,'O'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkDifRightDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[0][2] = 'O';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('O',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false,gb.checkDiagWin(2,2,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkWinDifLeftDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 4;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[1][0] = 'X';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false, gb.checkDiagWin(2,0,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkWinDifRightDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 4;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[1][0] = 'X';
        board[2][0] = 'X';
        board[0][1] = 'X';
        board[1][1] = 'O';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('O',2);

        assertEquals(false,gb.checkDiagWin(2,2,'O'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkMidDiagWin(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'X';
        board[1][1] = 'X';
        board[0][2] = 'O';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('O',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('X',1);
        gb.placeToken('O',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(false,gb.checkDiagWin(1,1,'X'));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkEmptyBoardTie(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(false,gb.checkTie());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkFullBoardTie(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[1][0] = 'X';
        board[2][0] = 'X';
        board[0][1] = 'X';
        board[1][1] = 'O';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals(true,gb.checkTie());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkHalfFullTie(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);


        assertEquals(false,gb.checkTie());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void checkFirstRowFullTie(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][1] = 'O';
        board[0][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',2);

        assertEquals(false,gb.checkTie());
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void placeEmptyBoardToken(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);

        //checking the placeToken function now
        board[0][1] = 'O';
        gb.placeToken('O',1);

        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void placeCornerToken(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);

        board[0][1] = 'O';
        gb.placeToken('O',1);

        //checking the placeToken function now
        board[0][0] = 'X';
        gb.placeToken('X',0);

        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void placeMidToken(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);

        board[0][0] = 'X';
        board[0][1] = 'O';

        gb.placeToken('X',0);
        gb.placeToken('O',1);

        //checking the placeToken function now
        board[1][1] = 'O';
        gb.placeToken('O',1);

        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void placeFullBoardToken(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[1][0] = 'X';
        board[2][0] = 'X';
        board[0][1] = 'X';
        board[1][1] = 'O';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';
        board[2][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);
        gb.placeToken('O',2);

        assertEquals(stringBoard,gb.toString());

        gb.placeToken('K',2);

        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void placeLastToken(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'O';
        board[1][0] = 'X';
        board[2][0] = 'X';
        board[0][1] = 'X';
        board[1][1] = 'O';
        board[2][1] = 'O';
        board[0][2] = 'X';
        board[1][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);

        gb.placeToken('O',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('O',1);
        gb.placeToken('O',1);
        gb.placeToken('X',2);
        gb.placeToken('O',2);

        //checking the placeToken function now
        board[2][2] = 'O';
        gb.placeToken('O',2);

        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatsAtEmptyBoardPos(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        assertEquals(' ',gb.whatsAtPos(0,0));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatsAtCornerPos(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][2] = 'O';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',2);

        assertEquals('X', gb.whatsAtPos(0,0));
        assertEquals('O',gb.whatsAtPos(0,2));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatsAtFullColPos(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'O';
        board[2][0] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',0);
        gb.placeToken('X',0);

        assertEquals('X', gb.whatsAtPos(2,0));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatsAtTopPos(){
        int expRow = 6;
        int expCol = 6;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][5] = 'J';
        board[1][5] = 'Q';
        board[2][5] = 'A';
        board[3][5] = 'K';
        board[4][5] = 'N';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('J',5);
        gb.placeToken('Q',5);
        gb.placeToken('A',5);
        gb.placeToken('K',5);
        gb.placeToken('N',5);

        assertEquals(' ', gb.whatsAtPos(5,5));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatsAtWinHorizPos(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][]board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][1] = 'X';
        board[0][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',1);
        gb.placeToken('X',2);

        assertEquals('X', gb.whatsAtPos(0,2));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatsAtWinVertPos(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][]board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[1][0] = 'X';
        board[2][0] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('X',0);
        gb.placeToken('X',0);

        assertEquals('X',gb.whatsAtPos(2,0));
        assertEquals(stringBoard,gb.toString());
    }

    @Test
    public void whatAtWinDiagPos(){
        int expRow = 3;
        int expCol = 3;
        int expWin = 3;

        char[][] board = new char[expRow][expCol];
        clearBoard(expRow,expCol,board);

        board[0][0] = 'X';
        board[0][1] = 'O';
        board[1][1] = 'X';
        board[0][2] = 'O';
        board[1][2] = 'O';
        board[2][2] = 'X';

        IGameBoard gb = factory(expRow,expCol,expWin);
        String stringBoard = checkString(expRow,expCol,board);

        gb.placeToken('X',0);
        gb.placeToken('O',1);
        gb.placeToken('X',1);
        gb.placeToken('O',2);
        gb.placeToken('O',2);
        gb.placeToken('X',2);

        assertEquals('X', gb.whatsAtPos(2,2));
        assertEquals(stringBoard,gb.toString());
    }

    /**
     * @param
     * r is the row that the token was placed in
     * @param
     * c is the column that player want to put their token in
     * @param
     * win is the number of win needed to win the game
     *
     * @require
     * LOWROW <= row <= HIGHROW
     * LOWCOL <= column <= HIGHCOL
     * LOWWIN <= Win <= HIGHWIN
     *
     * @return
     * object of the constructor call
     *
     * @ensure
     * nothing should change
     */
    private IGameBoard factory(int r, int c, int win){
        //return new GameBoard(r,c,win);
        return new GameBoardMem(r,c,win);
    }

    /**
     * @param
     * row is the row that the token was placed in
     * @param
     * column is the column that player want to put their token in
     * @param
     * board is a 2d array that stores the token
     *
     * @require
     * LOWROW <= row <= HIGHROW
     * LOWCOL <= column <= HIGHCOL
     * board[][] = board[Row][Column]
     *
     * @ensure
     * board[i][j] = ' '
     */
    private void clearBoard(int row, int column, char[][] board){
        for(int i = 0; i < row; i++){
            for(int j = 0; j < column; j++){
                board[i][j] = ' ';
            }
        }
    }

    /**
     * @param
     * row is the row that the token was placed in
     * @param
     * column is the column that player want to put their token in
     * @param
     * board is a 2d array that stores the token
     *
     * @require
     * LOWROW <= row <= HIGHROW
     * LOWCOL <= column <= HIGHCOL
     * board[][] = board[Row][Column]
     *
     * @return
     * the expected game board
     *
     * @ensure
     * should print board exactly the way it is
     */
    private String checkString(int row, int column, char[][] board){

        String string = "";
        String string1 = "";
        String finalString;

        for(int i = row-1; i >= 0; i--) {

            string += "| ";

            //loop to just print out the board with tokens
            for (int j = 0; j < column; j++) {
                char t = board[i][j];
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
