package Reversi;

import javax.swing.*;
import java.awt.*;

public class Main {

    private final BoardSquare[][] whiteBoard;
    private final BoardSquare[][] blackBoard;

    private JLabel whiteLabel;
    private JLabel blackLabel;

    JPanel whitePanel;
    JPanel blackPanel;

    JButton whiteAI;
    JButton blackAI;

    private final GameState board;


    public Main() {

        this.board = new GameState();
        this.whiteBoard = board.getWhiteBoard();
        this.blackBoard = board.getBlackBoard();

        whiteGUI();
        blackGUI();

    }

    public void whiteGUI() {

        JFrame whiteFrame = new JFrame();
        whiteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        whiteFrame.setLocationRelativeTo(null);
        whiteFrame.getContentPane().setLayout(new BorderLayout());
        whiteFrame.setTitle("Reversi - White Player");

        whitePanel = new JPanel();

        whitePanel.setLayout(new GridLayout(8,8));
        whiteFrame.getContentPane().add(whitePanel, BorderLayout.CENTER);

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++) {

                int selectRow = row;
                int selectCol = col;

                whitePanel.add(whiteBoard[row][col]);
                whiteBoard[row][col].addActionListener(e -> {
                    if(board.whiteTurn(selectRow, selectCol))
                        updatePlayers(0);
                });

            }
        }

        whiteLabel = new JLabel("White Player - place a disc");
        whiteFrame.getContentPane().add(whiteLabel, BorderLayout.NORTH);

        whiteAI = new JButton("Greedy AI (Play White)");
        whiteAI.addActionListener(e -> { if(board.greedyAI(1)) updatePlayers(0);});
        whiteFrame.getContentPane().add(whiteAI, BorderLayout.SOUTH);

        whiteFrame.pack();
        whiteFrame.setVisible(true);

    }

    public void blackGUI() {

        JFrame blackFrame = new JFrame();

        blackFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        blackFrame.setLocationRelativeTo(null);
        blackFrame.getContentPane().setLayout(new BorderLayout());
        blackFrame.setTitle("Reversi - Black Player");

        blackPanel = new JPanel();

        blackPanel.setLayout(new GridLayout(8,8));
        blackFrame.getContentPane().add(blackPanel, BorderLayout.CENTER);

        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++) {

                int selectRow = row;
                int selectCol = col;

                blackPanel.add(blackBoard[row][col]);
                blackBoard[row][col].addActionListener(e -> {
                    if(board.blackTurn(selectRow, selectCol))
                        updatePlayers(1);
                });

            }
        }

        blackLabel = new JLabel("Black Player - not your turn");
        blackFrame.getContentPane().add(blackLabel, BorderLayout.NORTH);

        blackAI = new JButton("Greedy AI (Play Black)");
        blackAI.addActionListener(e -> { if(board.greedyAI(2)) updatePlayers(1);});
        blackFrame.getContentPane().add(blackAI, BorderLayout.SOUTH);

        blackFrame.pack();
        blackFrame.setVisible(true);

    }


    // Update labels and switch player when turn played
    public void updatePlayers(int player) {

        if(player == 0){
            blackLabel.setText("Black Player - place a disc");
            whiteLabel.setText("White Player - not your turn");
        }
        else{
            whiteLabel.setText("White Player - place a disc");
            blackLabel.setText("Black Player - not your turn");
        }

    }


    public static void main(String[] args) {

        new Main();
    }

}
