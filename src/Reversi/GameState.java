package Reversi;

import javax.swing.*;
import java.lang.Math;


public class GameState {

    private final BoardSquare[][] whiteBoard;
    private final BoardSquare[][] blackBoard;

    private final int[][] boardState;

    private int currentPlayer;
    private int countFlips;
    private long startTime;


    // Constructor the initial game state
    public GameState() {

        this.boardState = new int[8][8]; // reflects white players perspective

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                this.boardState[row][col] = 0;
            }
        }

        this.boardState[3][3] = 1;
        this.boardState[4][3] = 2;
        this.boardState[3][4] = 2;
        this.boardState[4][4] = 1;

        this.whiteBoard = new BoardSquare[8][8];
        this.blackBoard = new BoardSquare[8][8];

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                this.whiteBoard[row][col] = new BoardSquare(boardState[row][col]);
                this.blackBoard[row][col] = new BoardSquare(boardState[7-row][7-col]);
            }
        }

        this.startTime = System.currentTimeMillis();
        this.currentPlayer = 0;

    }


    // Update board panels once players turn completed
    public void updateBoards() {

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                this.whiteBoard[row][col].updateState(boardState[row][col]);
                this.blackBoard[row][col].updateState(boardState[7-row][7-col]);
            }
        }
    }


    // White players turn
    public boolean whiteTurn(int row, int col){

        boolean switchPlayer = false;
        boolean fullBoard = true;
        boolean noMovesLeft = false;

        if(currentPlayer == 0 && validMove(row, col, 1, true, true)){
            boardState[row][col] = 1;
            updateBoards();

            // after white turn, check black can make valid move
            // if not, then automatically change to whites turn again
            loop:
            for(int row1 = 0; row1 < 8; row1++){
                for(int col1 = 0; col1 < 8; col1++){
                    if(boardState[row1][col1] == 0){
                        fullBoard = false;
                        if(validMove(row1, col1, 2, false, false)){
                            currentPlayer = 1;
                            switchPlayer = true;
                            break loop;
                        }else{
                            currentPlayer = 0;
                            switchPlayer = false;
                        }
                    }
                }
            }

            // if whites turn again, check for any valid moves left
            // if no valid moves, then neither side can make a move, game over
            if(!switchPlayer){
                loop:
                for(int row1 = 0; row1 < 8; row1++){
                    for(int col1 = 0; col1 < 8; col1++) {
                        if(boardState[row1][col1] == 0){
                            if(validMove(row1, col1, 1, false, false)){
                                noMovesLeft = false;
                                break loop;
                            }else{
                                noMovesLeft = true;
                            }
                        }
                    }
                }
            }

            // if no empty squares or valid moves left, end game
            if(fullBoard || noMovesLeft)
                gameOver();

        }
        return switchPlayer;
    }


    // Black players turn
    public boolean blackTurn(int row, int col){

        boolean switchPlayer = false;
        boolean fullBoard = true;
        boolean noMovesLeft = false;

        row = 7-row;
        col = 7-col;

        if(currentPlayer == 1 && validMove(row, col, 2, true, true)){
            boardState[row][col] = 2;
            updateBoards();

            // after black turn, check white can make valid move
            // if not, then automatically change to blacks turn again
            loop:
            for(int row1 = 0; row1 < 8; row1++){
                for(int col1 = 0; col1 < 8; col1++){
                    if(boardState[row1][col1] == 0){
                        fullBoard = false;
                        if(validMove(row1, col1, 1, false, false)){
                            currentPlayer = 0;
                            switchPlayer = true;
                            break loop;
                        }else{
                            currentPlayer = 1;
                            switchPlayer = false;
                        }
                    }
                }
            }

            // if blacks turn again, check for any valid moves left
            // if no valid moves, then neither side can make a move, game over
            if(!switchPlayer){
                loop:
                for(int row1 = 0; row1 < 8; row1++){
                    for(int col1 = 0; col1 < 8; col1++) {
                        if(boardState[row1][col1] == 0){
                            if(validMove(row1, col1, 2, false, false)){
                                noMovesLeft = false;
                                break loop;
                            }else{
                                noMovesLeft = true;
                            }
                        }
                    }
                }
            }

            // if no empty squares or valid moves left, end game
            if(fullBoard || noMovesLeft)
                gameOver();

        }
        return switchPlayer;
    }


    // Check square clicked is a valid move
    // If move is valid then flip appropriate discs
    public boolean validMove(int row, int col, int color,
                             boolean flip, boolean execute) {

        int[] direction1 = {-1,1,0,0,1,-1,1,-1};
        int[] direction2 = {0,0,1,-1,1,-1,-1,1};

        boolean valid = false;
        countFlips = 0;

        if(boardState[row][col] == 0){

            int positionX;
            int positionY;
            int current;

            boolean located;

            for (int i = 0; i < 8; i++) {

                int x = direction1[i];
                int y = direction2[i];

                positionX = col + x;
                positionY = row + y;
                located = false;

                if(outRange(positionX, positionY))
                    continue;
                current = boardState[positionY][positionX];

                if(current == 0 || current == color)
                    continue;

                while(!located){
                    positionX += x;
                    positionY += y;

                    if(outRange(positionX, positionY))
                        continue;
                    current = boardState[positionY][positionX];

                    if(current == color){
                        located = true;
                        valid = true;

                        // flip appropriate discs
                        if(flip){
                            positionX -= x;
                            positionY -= y;

                            if(outRange(positionX, positionY))
                                continue;
                            current = boardState[positionY][positionX];

                            while(current != 0){

                                if(outRange(positionX, positionY))
                                    continue;

                                countFlips++;

                                if(execute) // execute flip(s)
                                    boardState[positionY][positionX] = color;

                                positionX -= x;
                                positionY -= y;

                                if(outRange(positionX, positionY))
                                    continue;
                                current = boardState[positionY][positionX];
                            }
                        }
                    }
                    else if(current == -1 || current == 0)
                        located = true;
                }
            }
        }
        return valid;
    }


    // Algorithm used to find & execute optimal move for player
    // Searches all possible moves to find move which captures the most discs
    public boolean greedyAI(int color) {

        int maxFlips = 0;
        int optimalRow = 0;
        int optimalCol = 0;

        boolean validAIMove = false;

        if(color == 1 && currentPlayer == 0){ // white AI

            for(int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(boardState[row][col] == 0){
                        if(validMove(row, col, 1, true, false)){
                            if(countFlips > maxFlips){
                                maxFlips = countFlips;
                                optimalRow = row;
                                optimalCol = col;
                            }
                        }
                    }
                }
            }
            if(whiteTurn(optimalRow, optimalCol))
                validAIMove = true;

        }
        else if(color == 2 && currentPlayer == 1){ // black AI

            for(int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if(boardState[row][col] == 0){
                        if(validMove(7-row, 7-col, 2, true, false)){
                            if(countFlips > maxFlips){
                                maxFlips = countFlips;
                                optimalRow = row;
                                optimalCol = col;
                            }
                        }
                    }
                }
            }
            if(blackTurn(optimalRow, optimalCol))
                validAIMove = true;

        }
        return validAIMove;

    }


    // Counts players discs and calculates winner
    // Displays winner and time game was completed
    public void gameOver() {

        int numWhiteDiscs = 0;
        int numBlackDiscs = 0;

        for(int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(boardState[row][col] == 1)
                    numWhiteDiscs++;
                else if(boardState[row][col] == 2)
                    numBlackDiscs++;
            }
        }

        long time = (System.currentTimeMillis()-startTime)/1000;
        int seconds = Math.round(time % 60);
        int minutes = (int) Math.round((time/60.0) % 60);

        if(numWhiteDiscs > numBlackDiscs)
            JOptionPane.showMessageDialog(null, "White wins: " + minutes + ":" + seconds);
        else
            JOptionPane.showMessageDialog(null, "Black wins: " + minutes + ":" + seconds);

        resetGame();
    }


    // After game has completed reset the board to initial state
    public void resetGame() {

        for(int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                this.boardState[row][col] = 0;
            }
        }

        this.boardState[3][3] = 1;
        this.boardState[4][3] = 2;
        this.boardState[3][4] = 2;
        this.boardState[4][4] = 1;

        this.startTime = System.currentTimeMillis();
        this.currentPlayer = 0;

        updateBoards();
    }


    public boolean outRange(int x, int y){
        return x < 0 || x > 7 || y < 0 || y > 7;
    }


    public BoardSquare[][] getWhiteBoard() {
        return whiteBoard;
    }

    public BoardSquare[][] getBlackBoard() {
        return blackBoard;
    }


}
