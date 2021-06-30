package Reversi;

import javax.swing.*;
import java.awt.*;

public class BoardSquare extends JButton {

    private final Color fillColor;
    private final Color borderColor;
    private final int borderSize;
    private int colorState;

    public BoardSquare(int state) {

        setMinimumSize(new Dimension(30,30));
        setPreferredSize(new Dimension(30,30));

        this.fillColor = Color.GREEN;
        this.borderColor = Color.BLACK;
        this.borderSize = 1;

        colorState = state;
    }

    @Override
    protected void paintComponent(Graphics square) {

        square.setColor(borderColor);
        square.fillRect(0,0, getWidth(),getHeight());

        square.setColor(fillColor);
        square.fillRect(borderSize, borderSize,
                getWidth()-borderSize*2,
                getWidth()-borderSize*2
        );

        if(colorState == 1){ // Repaint white disc

            square.setColor(Color.WHITE);
            square.fillOval(1, 1,
                    getWidth()-2,
                    getHeight()-2
            );

            square.setColor(Color.BLACK);
            square.drawOval(1, 1,
                    getWidth()-2,
                    getHeight()-2
            );
        }
        else if(colorState == 2){ // Repaint black disc

            square.setColor(Color.BLACK);
            square.fillOval(1, 1,
                    getWidth()-2,
                    getHeight()-2
            );

            square.setColor(Color.WHITE);
            square.drawOval(1, 1,
                    getWidth()-2,
                    getHeight()-2
            );
        }
    }

    // Change and update the square state
    public void updateState(int state) {

        this.colorState = state;
        this.repaint();
    }

}